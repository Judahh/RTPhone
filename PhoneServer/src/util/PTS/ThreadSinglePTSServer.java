package util.PTS;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.LoggingPermission;

import util.PTS.Call.Call;
import util.PTS.Log.Log;
import util.TCP.ThreadSingleTCPServer;

public class ThreadSinglePTSServer extends ThreadSingleTCPServer{

	private String	broadcast;
	private String	toCheck;
	private boolean	login;
	private boolean	on;
	private boolean	register;
	private String	username;

	public ThreadSinglePTSServer(Socket clientSocket) throws IOException{
		super(clientSocket);
		this.broadcast = new String();
		this.toCheck = new String();
		this.username = new String();
		this.login = false;
		this.register = false;
		this.on = false;
	}

	public String getBroadcast(){
		return broadcast;
	}

	public String getToCheck(){
		return toCheck;
	}

	public String getUsername(){
		return username;
	}

	public void setBroadcast(String broadcast){
		this.broadcast = broadcast;
	}

	public void setToCheck(String toCheck){
		this.toCheck = toCheck;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void clearBroadcast(){
		this.broadcast = new String();
	}

	public void clearToCheck(){
		this.toCheck = new String();
	}

	public void clearUsername(){
		this.username = new String();
	}

	public void setLogin(boolean login){
		this.login = login;
	}
	
	public void setRegister(boolean register){
		this.register = register;
	}
	
	public boolean isLogin(){
		return login;
	}
	
	public boolean isRegister(){
		return register;
	}
	
	public void setOn(boolean on){
		this.on = on;
	}
	
	public boolean isOn(){
		return on;
	}

	protected void check(){
		// TODO: Fazer todas as checagens aqui!!!
		if(this.login){
			setBroadcast(Log.getLog(username, true));
			this.on = true;
		}

		if(this.register){
			setBroadcast(Log.getLog(username, false));
		}
	}

	@Override
	public void run(){
		try{
			this.threadReceiver.start();
			this.threadSender.start();
			while(clientSocket.isConnected()){
				Vector<String> received = getReceived();
				for(int index = 0; index < received.size(); index++){
					PTS pts = new PTS(received.get(index));
					switch(pts.getType()){
						case "log":
							Log log = new Log(pts);
							setBroadcast(log.getBroadcast());
							setToCheck(log.getToCheck());
							if(!log.getUsername().isEmpty()){
								setUsername(log.getUsername());
							}
							if(!log.getToSend().isEmpty()){
								send(log.getToSend());
							}
						break;
						case "call":
							Call call = new Call(pts);
							setBroadcast(call.getBroadcast());
							setToCheck(call.getToCheck());
							if(!call.getToSend().isEmpty()){
								send(call.getToSend());
							}
					}
					check();
				}
			}
			this.threadSender.stop();
			this.threadReceiver.stop();
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
