package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import util.PTS.Exception.CallBusyException;
import util.PTS.Exception.LoginErrorException;
import util.PTS.Exception.RegisterErrorException;

public class ThreadTCPClient extends Thread{
	protected Socket			serverReceiverSocket;
	protected Socket			serverSenderSocket;
	protected String			host;
	protected int				clientReceiverport;
	protected int				clientSenderport;
	protected ThreadTCPReceiver	threadTCPReceiver;
	protected ThreadTCPSender	threadTCPSender;

	public ThreadTCPClient(String host) throws UnknownHostException,
			IOException{
		this.host = host;
		this.clientReceiverport = 9000;
		this.clientSenderport = 9001;
		this.serverReceiverSocket = new Socket(host, clientReceiverport);
		this.serverSenderSocket = new Socket(host, clientSenderport);
		this.threadTCPReceiver = new ThreadTCPReceiver(serverReceiverSocket);
		this.threadTCPSender = new ThreadTCPSender(serverSenderSocket);
		startTCP();
	}

	// public boolean isCheck(){
	// return threadTCPReceiver.isCheck();
	// }

	public Vector<String> getReceived(){
		return threadTCPReceiver.getReceived();
	}

	public void send(String toSend){
		this.threadTCPSender.send(toSend);
	}

	synchronized public InetAddress inetAddress(){
		return this.serverReceiverSocket.getInetAddress();
	}

	synchronized public String address(){
		return this.serverReceiverSocket.getInetAddress().toString();
	}

	synchronized public boolean isConnected(){
		if(serverSenderSocket.isConnected()
				&& serverReceiverSocket.isConnected()){
			return true;
		}else{
			try{
				close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		return false;
	}

	synchronized public void close() throws IOException{
		serverReceiverSocket.close();
		serverSenderSocket.close();
	}

	synchronized public String getHost(){
		return host;
	}

	public void run(){
		try{
			while(isConnected()){
				try{
					Thread.sleep(5);
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
				catch(InterruptedException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Out");
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void startTCP() throws IOException{
		threadTCPReceiver.start();
		threadTCPSender.start();
	}

	protected void check() throws CallBusyException, RegisterErrorException,
			LoginErrorException{
	}
}
