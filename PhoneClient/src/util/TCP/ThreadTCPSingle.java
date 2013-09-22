package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class ThreadTCPSingle extends Thread{
	protected DataOutputStream	output;
	protected Vector<String>	toSend;
	protected BufferedReader	input;
	protected Vector<String>	received;
	protected Semaphore			receivedSemaphore;
//	protected boolean			check;

	public ThreadTCPSingle(Socket serverSocket) throws IOException{
		receivedSemaphore=new Semaphore(0);
//		setCheck(false);
		this.output = new DataOutputStream(serverSocket.getOutputStream());
		this.toSend = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));
		
		this.received = new Vector<>();
	}

	synchronized protected void receive() throws IOException{
		String temp=new String();
		char tmp=0;
		while(tmp!='\n'){
			temp+=tmp;
			tmp=(char)this.input.read();
		}
		temp=temp.trim();
		if(!temp.isEmpty()){
			this.getRealReceived().add(temp);
//			setCheck(true);
			for(int i = 0; i < this.getRealReceived().size(); i++){
				System.out.println("Client received:"
						+ this.getRealReceived().get(i) + '\n');
			}
		}
		receivedSemaphore.release();
	}

	synchronized protected Vector<String> getRealReceived(){
		return received;
	}
	
	synchronized protected void clearRealReceived(){
		received=new Vector<>();
	}

	synchronized protected Vector<String> getReceived(){
		try{
			receivedSemaphore.acquire();
		}catch(InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CLEAR");
		Vector<String> temp = getRealReceived();
//		getRealReceived().clear();
//		setCheck(false);
		return temp;
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

	@Override
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
