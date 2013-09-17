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
	protected Socket			serverSocket;
	protected String			host;
	protected int				port;
	protected ThreadReceiver	threadReceiver;
	protected ThreadSender	threadSender;

	public ThreadTCPClient(String host, int port) throws UnknownHostException,
			IOException{
		this.host = host;
		this.port = port;// 6789
		this.serverSocket = new Socket(host, port);
		// this.threadReceiver = new
		// ThreadReceiver(serverSocket.getInputStream());
		// this.threadSender = new ThreadSender(serverSocket.getOutputStream());
		this.threadReceiver = new ThreadReceiver(serverSocket);
		this.threadSender = new ThreadSender(serverSocket);
	}

	synchronized public Vector<String> getReceived(){
		return this.threadReceiver.getReceived();
	}

	synchronized public void send(String toSend) throws IOException{
		this.threadSender.send(toSend);
	}

	synchronized public InetAddress inetAddress(){
		return this.serverSocket.getInetAddress();
	}

	synchronized public String address(){
		return this.serverSocket.getInetAddress().toString();
	}

	synchronized public boolean isConnected(){
		return serverSocket.isConnected();
	}

	synchronized public void close() throws IOException{
		serverSocket.close();
	}

	synchronized public String getHost(){
		return host;
	}

	synchronized public int getPort(){
		return port;
	}
	
	synchronized private void startClientTCP(){
		this.threadReceiver.start();
		this.threadSender.start();
	}

	public void run(){
		startClientTCP();
		while(serverSocket.isConnected()){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		try{
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void check() throws CallBusyException, RegisterErrorException, LoginErrorException{
		// TODO Auto-generated method stub
		
	}
}
