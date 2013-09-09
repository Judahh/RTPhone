package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

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
		this.threadReceiver = new ThreadReceiver(serverSocket.getInputStream());
		this.threadSender = new ThreadSender(serverSocket.getOutputStream());
	}

	public Vector<String> getReceived(){
		return this.threadReceiver.getReceived();
	}

	public void send(String toSend) throws IOException{
		this.threadSender.send(toSend);
	}

	public InetAddress inetAddress(){
		return this.serverSocket.getInetAddress();
	}

	public String address(){
		return this.serverSocket.getInetAddress().toString();
	}

	public boolean isConnected(){
		return serverSocket.isConnected();
	}

	public void close() throws IOException{
		serverSocket.close();
	}

	public String getHost(){
		return host;
	}

	public int getPort(){
		return port;
	}
	
	private void startClientTCP(){
		this.threadReceiver.start();
		this.threadSender.start();
	}

	public void run(){
		startClientTCP();
		while(serverSocket.isConnected()){
			check();
		}
		this.threadSender.stop();
		this.threadReceiver.stop();
		try{
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	protected void check(){
		// TODO Auto-generated method stub
		
	}
}
