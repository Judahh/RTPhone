package realTimeTransportProtocol;

import java.awt.*;
import java.net.*;
import java.util.Vector;

import javax.media.*;
import javax.media.rtp.*;
import javax.media.rtp.event.*;
import javax.media.rtp.rtcp.*;
import javax.media.protocol.*;
import javax.media.control.BufferControl;

/**
 * AVReceive to receive RTP transmission.
 */
public class AVReceive
		implements
			ReceiveStreamListener,
			SessionListener,
			ControllerListener{
	private String			sessions[]		= null;
	private SessionManager	mgrs[]			= null;
	private Vector			playerWindows	= null;
	private String			temp;
	private boolean			dataReceived	= false;
	private Object			dataSync		= new Object();

	public AVReceive(String sessions[]){
		this.sessions = sessions;
	}

	protected boolean initialize(){

		try{
			InetAddress ipAddr;
			SessionAddress localAddr = new SessionAddress();
			SessionAddress destAddr;

			mgrs = new com.sun.media.rtp.RTPSessionMgr[sessions.length];
			playerWindows = new Vector();

			SessionLabel session;

			// Open the RTP sessions.
			for(int i = 0; i < sessions.length; i++){

				// Parse the session addresses.
				try{
					session = new SessionLabel(sessions[i]);
				}catch(IllegalArgumentException e){
					temp = "Failed to parse the session address given: "
							+ sessions[i] + "\n";
					System.out.println(temp);
					return false;
				}
				temp = " Open connection for Receiving at Addr : "
						+ session.addr + "port: " + session.port + "\n";
				System.out.println(temp);
				// System.err.println("  - Open RTP session for: addr: " +
				// session.addr + " port: " + session.port + " ttl: " +
				// session.ttl);

				mgrs[i] = new com.sun.media.rtp.RTPSessionMgr();
				mgrs[i].addSessionListener(this);
				mgrs[i].addReceiveStreamListener(this);

				ipAddr = InetAddress.getByName(session.addr);
				destAddr = new SessionAddress(ipAddr, session.port, ipAddr,
						session.port + 1);
				mgrs[i].initSession(localAddr, getSDES(mgrs[i]), .05, .25);

				// You can try out some other buffer size to see
				// if you can get better smoothness.
				BufferControl bc = (BufferControl) mgrs[i]
						.getControl("javax.media.control.BufferControl");
				if(bc != null)
					bc.setBufferLength(350);

				mgrs[i].startSession(destAddr, session.ttl, null);
			}

		}catch(Exception e){
			temp = "Cannot create The session" + e.getMessage();
			// System.err.println("Cannot create the RTP Session: " +
			// e.getMessage());
			return false;
		}

		// Wait for data to arrive before moving on.

		long then = System.currentTimeMillis();
		long waitingPeriod = 600000; // wait for a maximum of 600 secs.

		try{
			synchronized(dataSync){
				while(!dataReceived
						&& System.currentTimeMillis() - then < waitingPeriod){
					if(!dataReceived)
						// System.err.println("  - Waiting for RTP data to arrive...");
						dataSync.wait(1000);
				}
			}
		}catch(Exception e){
		}

		if(!dataReceived){
			temp = "No Data was received\n";
			System.out.println(temp);
			// System.err.println("No RTP data was received.");
			close();
			return false;
		}
		System.out.println("Receiving A call...call Established\n");
		return true;
	}

	/**
	 * Find out the host info.
	 */
	String	cname	= null;

	private SourceDescription[] getSDES(SessionManager mgr){
		SourceDescription[] desclist = new SourceDescription[3];
		if(cname == null)
			cname = mgr.generateCNAME();

		desclist[0] = new SourceDescription(SourceDescription.SOURCE_DESC_NAME,
				System.getProperty("user.name"), 1, false);
		desclist[1] = new SourceDescription(
				SourceDescription.SOURCE_DESC_CNAME, cname, 1, false);
		desclist[2] = new SourceDescription(SourceDescription.SOURCE_DESC_TOOL,
				"AVReceive powered by JMF", 1, false);
		return desclist;
	}

	public boolean isDone(){
		return playerWindows.size() == 0;
	}

	/**
	 * Close the players and the session managers.
	 */
	protected void close(){

		for(int i = 0; i < playerWindows.size(); i++){
			try{
				((PlayerWindow) playerWindows.elementAt(i)).close();
			}catch(Exception e){
			}
		}

		playerWindows.removeAllElements();

		// close the RTP session.
		for(int i = 0; i < mgrs.length; i++){
			if(mgrs[i] != null){
				mgrs[i].closeSession("Closing session from AVReceive");
				mgrs[i] = null;
			}
		}
	}

	PlayerWindow find(Player p){
		for(int i = 0; i < playerWindows.size(); i++){
			PlayerWindow pw = (PlayerWindow) playerWindows.elementAt(i);
			if(pw.player == p)
				return pw;
		}
		return null;
	}

	PlayerWindow find(ReceiveStream strm){
		for(int i = 0; i < playerWindows.size(); i++){
			PlayerWindow pw = (PlayerWindow) playerWindows.elementAt(i);
			if(pw.stream == strm)
				return pw;
		}
		return null;
	}

	/**
	 * SessionListener.
	 */
	public synchronized void update(SessionEvent evt){
		if(evt instanceof NewParticipantEvent){
			Participant p = ((NewParticipantEvent) evt).getParticipant();
			temp = " A Call is established with the user " + " " + p.getCNAME()
					+ "\n";
			System.out.println(temp);
			// System.err.println("  - A new participant had just joined: " +
			// p.getCNAME());
		}
	}

	/**
	 * ReceiveStreamListener
	 */
	public synchronized void update(ReceiveStreamEvent evt){

		SessionManager mgr = (SessionManager) evt.getSource();
		Participant participant = evt.getParticipant(); // could be null.
		ReceiveStream stream = evt.getReceiveStream(); // could be null.

		if(evt instanceof RemotePayloadChangeEvent){

			System.err.println("  - Received an RTP PayloadChangeEvent.");
			System.err.println("Sorry, cannot handle payload change.");
			System.exit(0);

		}

		else if(evt instanceof NewReceiveStreamEvent){

			try{
				stream = ((NewReceiveStreamEvent) evt).getReceiveStream();
				DataSource ds = stream.getDataSource();

				// Find out the formats.
				RTPControl ctl = (RTPControl) ds
						.getControl("javax.media.rtp.RTPControl");
				if(ctl != null){
					;// System.err.println("  - Recevied new RTP stream: " +
						// ctl.getFormat());
				}else
					;// System.err.println("  - Recevied new RTP stream");

				if(participant == null)
					;// System.err.println("      The sender of this stream had yet to be identified.");
				else{
					;// System.err.println("      The stream comes from: " +
						// participant.getCNAME());
				}

				// create a player by passing datasource to the Media Manager
				Player p = javax.media.Manager.createPlayer(ds);
				if(p == null)
					return;

				p.addControllerListener(this);
				p.realize();
				PlayerWindow pw = new PlayerWindow(p, stream);
				playerWindows.addElement(pw);

				// Notify intialize() that a new stream had arrived.
				synchronized(dataSync){
					dataReceived = true;
					dataSync.notifyAll();
				}

			}catch(Exception e){
				System.err.println("NewReceiveStreamEvent exception "
						+ e.getMessage());
				return;
			}

		}

		else if(evt instanceof StreamMappedEvent){

			if(stream != null && stream.getDataSource() != null){
				DataSource ds = stream.getDataSource();
				// Find out the formats.
				RTPControl ctl = (RTPControl) ds
						.getControl("javax.media.rtp.RTPControl");
				// System.err.println("  - The previously unidentified stream ");
				if(ctl != null)
					System.err.println("      " + ctl.getFormat());
				temp = " Call Established with " + " " + participant.getCNAME()
						+ "\n";
				// System.err.println("      had now been identified as sent by: "
				// + participant.getCNAME());
			}
		}

		else if(evt instanceof ByeEvent){
			temp = " connection terminated by " + " " + participant.getCNAME()
					+ "\n";
			System.out.println(temp);
			System.err.println("  - Got \"bye\" from: "
					+ participant.getCNAME());
			PlayerWindow pw = find(stream);
			if(pw != null){
				pw.close();
				playerWindows.removeElement(pw);
			}
		}

	}

	/**
	 * ControllerListener for the Players.
	 */
	public synchronized void controllerUpdate(ControllerEvent ce){

		Player p = (Player) ce.getSourceController();

		if(p == null)
			return;

		// Get this when the internal players are realized.
		if(ce instanceof RealizeCompleteEvent){
			PlayerWindow pw = find(p);
			if(pw == null){
				// Some strange happened.
				System.err.println("Internal error!");
				System.exit(-1);
			}
			pw.initialize();
			pw.setVisible(true);
			p.start();
		}

		if(ce instanceof ControllerErrorEvent){
			p.removeControllerListener(this);
			PlayerWindow pw = find(p);
			if(pw != null){
				pw.close();
				playerWindows.removeElement(pw);
			}
			System.err.println("AVReceive internal error: " + ce);
		}

	}

	/**
	 * A utility class to parse the session addresses.
	 */
	class SessionLabel{

		public String	addr	= null;
		public int		port;
		public int		ttl		= 1;

		SessionLabel(String session) throws IllegalArgumentException{

			int off;
			String portStr = null, ttlStr = null;

			if(session != null && session.length() > 0){
				while(session.length() > 1 && session.charAt(0) == '/')
					session = session.substring(1);

				// Now see if there's a addr specified.
				off = session.indexOf('/');
				if(off == -1){
					if(!session.equals(""))
						addr = session;
				}else{
					addr = session.substring(0, off);
					session = session.substring(off + 1);
					// Now see if there's a port specified
					off = session.indexOf('/');
					if(off == -1){
						if(!session.equals(""))
							portStr = session;
					}else{
						portStr = session.substring(0, off);
						session = session.substring(off + 1);
						// Now see if there's a ttl specified
						off = session.indexOf('/');
						if(off == -1){
							if(!session.equals(""))
								ttlStr = session;
						}else{
							ttlStr = session.substring(0, off);
						}
					}
				}
			}

			if(addr == null)
				throw new IllegalArgumentException();

			if(portStr != null){
				try{
					Integer integer = Integer.valueOf(portStr);
					if(integer != null)
						port = integer.intValue();
				}catch(Throwable t){
					throw new IllegalArgumentException();
				}
			}else
				throw new IllegalArgumentException();

			if(ttlStr != null){
				try{
					Integer integer = Integer.valueOf(ttlStr);
					if(integer != null)
						ttl = integer.intValue();
				}catch(Throwable t){
					throw new IllegalArgumentException();
				}
			}
		}
	}

	/**
	 * GUI classes for the Player.
	 */
	class PlayerWindow extends Frame{

		Player			player;
		ReceiveStream	stream;

		PlayerWindow(Player p, ReceiveStream strm){
			player = p;
			stream = strm;
		}

		public void initialize(){
			add(new PlayerPanel(player));
		}

		public void close(){
			player.close();
			setVisible(false);
			dispose();
		}

		public void addNotify(){
			super.addNotify();
			pack();
		}
	}

	/**
	 * GUI classes for the Player.
	 */
	class PlayerPanel extends Panel{

		Component	vc, cc;

		PlayerPanel(Player p){
			setLayout(new BorderLayout());
			if((vc = p.getVisualComponent()) != null)
				add("Center", vc);
			if((cc = p.getControlPanelComponent()) != null)
				add("South", cc);
		}

		public Dimension getPreferredSize(){
			int w = 0, h = 0;
			if(vc != null){
				Dimension size = vc.getPreferredSize();
				w = size.width;
				h = size.height;
			}
			if(cc != null){
				Dimension size = cc.getPreferredSize();
				if(w == 0)
					w = size.width;
				h += size.height;
			}
			if(w < 160)
				w = 160;
			return new Dimension(w, h);
		}
	}

	/*
	 * public static void main(String argv[]) { if (argv.length == 0) prUsage();
	 * 
	 * AVReceive avReceive = new AVReceive(argv); if (!avReceive.initialize()) {
	 * System.err.println("Failed to initialize the sessions.");
	 * System.exit(-1); }
	 * 
	 * // Check to see if AVReceive is done. try { while (!avReceive.isDone())
	 * Thread.sleep(1000); } catch (Exception e) {}
	 * 
	 * System.err.println("Exiting AVReceive"); }
	 * 
	 * 
	 * static void prUsage() {
	 * System.err.println("Usage: AVReceive <session> <session> ...");
	 * System.err.println("     <session>: <address>/<port>/<ttl>");
	 * System.exit(0); }
	 */

}// end of AVReceive

