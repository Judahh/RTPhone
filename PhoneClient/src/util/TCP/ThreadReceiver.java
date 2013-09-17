package util.TCP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class ThreadReceiver extends Thread{
	BufferedReader			input;
	private Vector<String>	received;

	public ThreadReceiver(InputStream input){
		this.input = new BufferedReader(new InputStreamReader(input));
		this.received = new Vector<>();
	}

	private void receive() throws IOException{
		this.received.add(this.input.readLine());
	}

	public Vector<String> getReceived(){
		Vector<String> temp = received;
		this.received = new Vector<>();
		return temp;
	}

	@Override
	public void run(){
		try{
			while(true){
				Thread.sleep(1000);
				receive();
			}
		}catch(IOException e){

		}catch(InterruptedException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
