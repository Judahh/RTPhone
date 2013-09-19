package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

public class ThreadTCPSingle extends Thread {
	protected DataOutputStream output;
	protected Vector<String> toSend;
	protected BufferedReader input;
	protected Vector<String> received;

	public ThreadTCPSingle(Socket serverSocket) throws IOException {
		this.output = new DataOutputStream(serverSocket.getOutputStream());
		this.toSend = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));
		this.received = new Vector<>();
	}

	protected void receive() throws IOException {
		this.received.add(this.input.readLine());
		if (this.received.size() > 0) {
			System.out.println("Server received:"
					+ this.received.get(this.received.size() - 1) + '\n');
		}
	}

	protected Vector<String> getReceived() {
		Vector<String> temp = received;
		this.received = new Vector<>();
		return temp;
	}

	protected void send(String toSend) {
		this.toSend.add(toSend);
	}

	protected void send() throws IOException {
		while (!this.toSend.isEmpty()) {
			this.output.write((this.toSend.get(0) + '\n').getBytes());
			this.output.flush();
			System.out.println("Server send:" + this.toSend.get(0) + '\n');
			this.toSend.remove(0);
		}
	}

	protected void work() throws IOException {

	}

	@Override
	public void run() {
		try {
			while (true) {
				work();
			}
		} catch (IOException e) {

		}
	}
}
