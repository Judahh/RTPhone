package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;
//import java.util.concurrent.Semaphore;

public class ThreadTCPSingle extends Thread{
	protected DataOutputStream	output;
	// protected Vector<String> toSend;
	protected BufferedReader	input;
	protected Vector<String>	received;
	// protected Semaphore receivedSemaphore;
	// protected boolean check;
	Socket						serverSocket;

	public ThreadTCPSingle(Socket serverSocket) throws IOException{
		// receivedSemaphore = new Semaphore(0);
		// setCheck(false);
		this.output = new DataOutputStream(serverSocket.getOutputStream());
		// this.toSend = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));

		this.serverSocket = serverSocket;

		this.received = new Vector<>();
		try{
			Thread.sleep(10);
		}catch(InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void receive() throws IOException{
		if(this.input.ready()){
			System.out.println("está:" + this.input.ready());
			System.out.println("entrou de fato!");
			String temp = new String();
			char tmp = 0;
			System.out.println("entrou de fato! 2");
			while(tmp != '\n' && this.input.ready()){
				temp += tmp;
				System.out.println(serverSocket.isConnected());
				System.out.println(serverSocket.isClosed());
//				try{
					System.out.println("está:" + this.input.ready());
					System.out.println("está:" + this.input.ready());
					tmp = (char) this.input.read();
//				}catch(IOException e){
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			temp = temp.trim();
			if(!temp.isEmpty()){
				this.getRealReceived().add(temp);
				// setCheck(true);
				for(int i = 0; i < this.getRealReceived().size(); i++){
					System.out.println("Server received:"
							+ this.getRealReceived().get(i) + '\n');
				}
			}
		}
		// receivedSemaphore.release();
	}

	/* synchronized */protected Vector<String> getRealReceived(){
		return received;
	}

	/* synchronized */protected void clearRealReceived(){
		received = new Vector<>();
	}

	synchronized protected Vector<String> getReceived(){
		// try{
		// receivedSemaphore.acquire();
		// }catch(InterruptedException e){
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		System.out.println("CLEAR");
		Vector<String> temp = getRealReceived();
		// getRealReceived().clear();
		// setCheck(false);
		return temp;
	}

	synchronized protected void send(String toSend) throws IOException{
		this.output.write((toSend + '\n').getBytes());
		// this.output.flush();
		System.out.println("Server send:" + toSend + '\n');
	}

	protected void work() throws IOException{

	}

	@Override
	public void run(){
		try{
			work();
		}catch(IOException e){
			System.err.println(e);
		}
	}
}
