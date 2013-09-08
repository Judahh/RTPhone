package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ThreadSingleTCPServer extends Thread{

	protected Socket	clientSocket;
	protected Receiver	receiver;
	protected Sender	sender;

	public ThreadSingleTCPServer(Socket clientSocket) throws IOException{
		this.clientSocket = clientSocket;
		this.receiver = new Receiver(clientSocket.getInputStream());
		this.sender = new Sender(clientSocket.getOutputStream());
	}

	public Vector<String> getReceived(){
		return this.receiver.getReceived();
	}

	public void send(String toSend) throws IOException{
		this.sender.send(toSend);
	}

	public InetAddress inetAddress(){
		return this.clientSocket.getInetAddress();
	}

	public String address(){
		return this.clientSocket.getInetAddress().toString();
	}

	public boolean isConnected(){
		return clientSocket.isConnected();
	}

	public void close() throws IOException{
		clientSocket.close();
	}

	public String getBroadcast(){
		return new String();
	}

	public String getToCheck(){
		return new String();
	}

	public String getUsername(){
		return new String();
	}

	public void setBroadcast(String broadcast){
	}

	public void setToCheck(String toCheck){
	}

	public void setUsername(String username){
	}

	public void run(){
		try{
			this.receiver.start();
			this.sender.start();
			while(clientSocket.isConnected());
			this.sender.stop();
			this.receiver.stop();
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}