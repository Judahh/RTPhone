package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class ThreadSingleTCPServer extends Thread{

	protected Socket			clientSocket;
	private BufferedReader		input;
	private Vector<String>		received;
	private DataOutputStream	output;
	private Vector<String>		toSend;

	public ThreadSingleTCPServer(Socket clientSocket) throws IOException{
		this.clientSocket = clientSocket;
		this.received = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		this.toSend = new Vector<>();
		this.output = new DataOutputStream(clientSocket.getOutputStream());
	}

	private void receive() throws IOException{
		this.received.add(this.input.readLine());
		if(this.received.size() > 0){
			System.out.println("Client received:"
					+ this.received.get(this.received.size() - 1) + '\n');
		}
	}

	public Vector<String> getReceived(){
		Vector<String> temp = received;
		this.received = new Vector<>();
		return temp;
	}

	public void send(String toSend){
		this.toSend.add(toSend);
	}

	private void send() throws IOException{
		while(!this.toSend.isEmpty()){
			this.output.write((this.toSend.get(0) + '\n').getBytes());
			this.output.flush();
			System.out.println("Client send:" + this.toSend.get(0) + '\n');
			this.toSend.remove(0);
		}
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

	private void checkTCP() throws IOException{
		receive();
		send();
	}

	protected void check(){
	}

	public void run(){
		try{
			while(clientSocket.isConnected()){
				checkTCP();
				check();
			}
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}