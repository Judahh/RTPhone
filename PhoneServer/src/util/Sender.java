package util;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class Sender extends Thread {
	DataOutputStream output;
	private Vector<String> toSend;
	
	public Sender(OutputStream output) {
		this.output = new DataOutputStream(output);
		this.toSend = new Vector<>();
	}

	public void send(String toSend){
		this.toSend.add(toSend);
	}
	
	private void send() throws IOException {
		while (!this.toSend.isEmpty()) {
			this.output.write((this.toSend.get(0) + '\n').getBytes());
			this.output.flush();
			System.out.println(this.toSend.get(0) + '\n');
			this.toSend.remove(0);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				send();
			}
		} catch (IOException e) {

		}
	}
}
