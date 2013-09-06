package util;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class WorkerRunnable extends Thread {

	protected Socket clientSocket;
	private Receive receive;
	private Vector<String> toSend;

	public WorkerRunnable(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.toSend=new Vector<>();
		this.receive=new Receive(clientSocket.getInputStream());
	}

	public void send(String string) throws IOException {
		this.toSend.add(string);
	}
	
	public void send() throws IOException {
		while (clientSocket.isConnected() && !this.toSend.isEmpty()) {
			DataOutputStream output = new DataOutputStream(
					clientSocket.getOutputStream());
			output.write((this.toSend.get(0) + '\n').getBytes());
			output.flush();
			System.out.println(this.toSend.get(0) + '\n');
			this.toSend.remove(0);
		}
		
	}
	
	public boolean isConnected() {
		return clientSocket.isConnected();
	}
	
	public void close() throws IOException {
		clientSocket.close();
	}

	public void run() {
		try {
			this.receive.start();
			while (clientSocket.isConnected()) {
				//System.out.println("fsad");
				send();
				//System.out.println("yoy");
			}
			this.receive.stop();
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}