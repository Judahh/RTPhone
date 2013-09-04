package util;
import java.io.*;
import java.net.*;

public class Connection extends Thread {
	private String clientSentence;
	private ServerSocket serverSocket;
	private int port;

	public Connection(int port) throws IOException {
		this.port = port;
		this.clientSentence = new String();
		this.serverSocket = new ServerSocket(this.port);
	}

	public Connection() throws IOException {
		this.port = 6789;
		this.clientSentence = new String();
		this.serverSocket = new ServerSocket(this.port);
	}

	public void run() {
		Socket connectionSocket;
		try {
			connectionSocket = serverSocket.accept();

			while (connectionSocket.isConnected()) {
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());

				clientSentence = inFromClient.readLine();
				System.out.println("Received: " + clientSentence);
				outToClient.writeBytes(clientSentence);
			}
			connectionSocket.close();
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}