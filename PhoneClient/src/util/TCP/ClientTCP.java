package util.TCP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

public class ClientTCP extends Thread {
	private Socket serverSocket;
	private String host;
	private int port;
	private Receive receive;
	private Vector <String> toSend;

	public ClientTCP(String host, int port) throws UnknownHostException,
			IOException {
		this.host = host;
		this.port = port;// 6789
		this.toSend = new Vector<>();
		this.serverSocket = new Socket(host, port);
		this.receive = new Receive(serverSocket.getInputStream());
	}

	public void send(String string) throws IOException {
		this.toSend.add(string);
	}

	public void send() throws IOException {
		while (serverSocket.isConnected() && !this.toSend.isEmpty()) {
			DataOutputStream output = new DataOutputStream(
					serverSocket.getOutputStream());
			output.write((this.toSend.get(0) + '\n').getBytes());
			output.flush();
			System.out.println((this.toSend.get(0) + '\n'));
			this.toSend.remove(0);
		}

	}

	public boolean isConnected() {
		return serverSocket.isConnected();
	}

	public void close() throws IOException {
		serverSocket.close();
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void run() {
		try {
			this.receive.start();
			while (serverSocket.isConnected()) {
				send();
			}
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
