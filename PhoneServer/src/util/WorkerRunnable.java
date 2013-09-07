package util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class WorkerRunnable extends Thread {

	protected Socket clientSocket;
	private Receiver receiver;
	private Sender sender;

	public WorkerRunnable(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		this.receiver = new Receiver(clientSocket.getInputStream());
		this.sender = new Sender(clientSocket.getOutputStream());
	}

	public Vector<String> getReceived() {
		return this.receiver.getReceived();
	}
	
	public void send(String toSend) throws IOException {
		this.sender.send(toSend);
	}

	public InetAddress inetAddress() {
		return this.clientSocket.getInetAddress();
	}

	public String address() {
		return this.clientSocket.getInetAddress().toString();
	}

	public boolean isConnected() {
		return clientSocket.isConnected();
	}

	public void close() throws IOException {
		clientSocket.close();
	}

	public void run() {
		try {
			this.receiver.start();
			this.sender.start();
			while (clientSocket.isConnected());
			this.sender.stop();
			this.receiver.stop();
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}