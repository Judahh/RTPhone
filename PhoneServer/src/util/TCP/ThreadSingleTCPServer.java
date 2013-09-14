package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ThreadSingleTCPServer extends Thread{

	protected Socket			clientSocket;
	protected ThreadReceiver	threadReceiver;
	protected ThreadSender		threadSender;

	public ThreadSingleTCPServer(Socket clientSocket) throws IOException{
		this.clientSocket = clientSocket;
		this.threadReceiver = new ThreadReceiver(clientSocket.getInputStream());
		this.threadSender = new ThreadSender(clientSocket.getOutputStream());
	}

	public void addBroadcast(Vector<String> broadcast){
	}

	public void addToCheck(Vector<String> toCheck){
	}
	
	public Vector<String> getCall(){
		return new Vector<>();
	}
	
	public void addCall(Vector<String> call){
	}
	
	public void addCall(String call){
	}
	
	public void clearCall(String call){
	}
	
	public Vector<String> getReceived(){
		return this.threadReceiver.getReceived();
	}

	public void send(String toSend) throws IOException{
		this.threadSender.send(toSend);
	}

	public InetAddress inetAddress(){
		return this.clientSocket.getInetAddress();
	}

	public String getAddress(){
		return this.clientSocket.getInetAddress().toString();
	}

	public boolean isConnected(){
		return clientSocket.isConnected();
	}

	public void close() throws IOException{
		clientSocket.close();
	}

	public Vector<String> getBroadcast(){
		return new Vector<>();
	}

	public Vector<String> getToCheck(){
		return new Vector<>();
	}

	public String getUsername(){
		return new String();
	}

	public void addBroadcast(String broadcast){
	}

	public void addToCheck(String toCheck){
	}

	public void setUsername(String username){
	}
	
	public void clearBroadcast(){
	}

	public void clearToCheck(){
	}

	public void clearUsername(){
	}
	
	public void setLogin(boolean login){
	}
	
	public void setRegister(boolean register){
	}
	
	public boolean isLogin(){
		return false;
	}
	
	public boolean isRegister(){
		return false;
	}
	
	public void setOn(boolean on){
	}
	
	public boolean isOn(){
		return false;
	}

	public void run(){
		try{
			this.threadReceiver.start();
			this.threadSender.start();
			while(clientSocket.isConnected());
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}