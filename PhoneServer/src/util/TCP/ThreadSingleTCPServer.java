package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ThreadSingleTCPServer extends Thread{

	protected Socket			clientSenderSocket;
	protected Socket			clientReceiverSocket;
	protected ThreadTCPSender	threadTCPSender;
	protected ThreadTCPReceiver	threadTCPReceiver;

	public ThreadSingleTCPServer(Socket clientSenderSocket,
			Socket clientReceiverSocket) throws IOException{
		this.clientSenderSocket = clientSenderSocket;
		this.clientReceiverSocket = clientReceiverSocket;
		threadTCPSender = new ThreadTCPSender(clientSenderSocket);
		threadTCPReceiver= new ThreadTCPReceiver(clientReceiverSocket);
		startTCP();
	}

	public Vector<String> getReceived(){
		return this.threadTCPReceiver.getReceived();
	}

	public void send(String toSend){
		this.threadTCPSender.toSend.add(toSend);
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

	public InetAddress inetAddress(){
		return this.clientSenderSocket.getInetAddress();
	}

	public String getAddress(){
		return this.clientSenderSocket.getInetAddress().toString();
	}

	public boolean isConnected(){
		if(clientSenderSocket.isConnected()
				&& clientReceiverSocket.isConnected()){
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

	public void close() throws IOException{
		clientSenderSocket.close();
		clientReceiverSocket.close();
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

	private void startTCP(){
		threadTCPSender.start();
		threadTCPReceiver.start();
	}

	protected void check(){
	}

	public void run(){
		try{
			while(isConnected()){
				Thread.sleep(10);
				check();
			}
			close();
		}catch(IOException | InterruptedException e){
			e.printStackTrace();
		}
	}
}