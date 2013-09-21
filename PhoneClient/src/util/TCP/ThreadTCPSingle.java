package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

public class ThreadTCPSingle extends Thread{
	protected DataOutputStream	output;
	protected Vector<String>	toSend;
	protected BufferedReader	input;
	protected Vector<String>	received;
	protected boolean			check;

	public ThreadTCPSingle(Socket serverSocket) throws IOException{
		setCheck(false);
		this.output = new DataOutputStream(serverSocket.getOutputStream());
		this.toSend = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));
		this.received = new Vector<>();
	}

	synchronized protected void receive() throws IOException{
		String temp = this.input.readLine();

		if(!temp.isEmpty()){
			this.getRealReceived().add(this.input.readLine());
			setCheck(true);
			for(int i = 0; i < this.getRealReceived().size(); i++){
				System.out.println("Client received:" + this.getRealReceived().get(i)
						+ '\n');
			}
		}
	}

	synchronized protected  Vector<String> getRealReceived(){
		return received;
	}
	
	protected Vector<String> getReceived(){
		Vector<String> temp = getRealReceived();
		getRealReceived().clear();
		setCheck(false);
		return temp;
	}

	synchronized public boolean isCheck(){
		return check;
	}
	
	synchronized public void setCheck(boolean check){
		this.check = check;
	}

	protected void send(String toSend){
		this.toSend.add(toSend + '\n');
	}

	synchronized protected void send() throws IOException{
		while(!this.toSend.isEmpty()){
			this.output.write((this.toSend.get(0) + '\n').getBytes());
			this.output.flush();
			System.out.println("Client send:" + this.toSend.get(0) + '\n');
			this.toSend.remove(0);
		}
	}

	protected void work() throws IOException{

	}

	public void run(){
		try{
			while(true){
				work();
			}
		}catch(IOException e){
			System.err.println(e);
		}
	}
}
