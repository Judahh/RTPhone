package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class ClientTCP extends Thread{
	private Socket			serverSocket;
	private String			host;
	private int				port;
	private ThreadReceiver	threadReceiver;
	private ThreadSender	threadSender;

	public ClientTCP(String host, int port) throws UnknownHostException,
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

	public void run(){
		try{
			this.threadReceiver.start();
			this.threadSender.start();
			while(serverSocket.isConnected());
			this.threadSender.stop();
			this.threadReceiver.stop();
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
