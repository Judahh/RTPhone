package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import util.PTS.Exception.CallBusyException;
import util.PTS.Exception.LoginErrorException;
import util.PTS.Exception.RegisterErrorException;

public class ThreadTCPClient extends Thread{
	protected String			host;
	protected TCPSingleClient	TCPSingle;

	public ThreadTCPClient(String host) throws Exception{
		this.host = host;
		this.TCPSingle = new TCPSingleClient(host);
	}

	public Vector<String> getReceived(){
		return TCPSingle.getReceived();
	}

	public void send(String toSend) throws ConnectorDisconnectedException,
			ConnectorCannotSendPacketException{
		this.TCPSingle.send(toSend);
	}

	synchronized public InetAddress inetAddress() throws UnknownHostException{
		return InetAddress.getByName(host);
	}

	synchronized public String address(){
		return host;
	}

	synchronized public void close() throws IOException{
		this.TCPSingle.Dispose();
	}

	synchronized public String getHost(){
		return host;
	}

	public void run(){
		try{
			while(true){
				try{
					check();
				}catch(CallBusyException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(RegisterErrorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(LoginErrorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch(ConnectorDisconnectedException e){
			try{
				this.close();
			}catch(IOException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch(ConnectorCannotSendPacketException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void check() throws CallBusyException, RegisterErrorException,
			LoginErrorException, ConnectorDisconnectedException,
			ConnectorCannotSendPacketException{
	}
}
