package util;
import java.io.*;
import java.net.*;

public class Connection extends Thread {
	String sentence;
	String modifiedSentence;
	Socket clientSocket;
	String host;
	int port;

	public Connection() throws UnknownHostException, IOException {
		this.sentence = new String();
		this.modifiedSentence = new String();
		this.host = "localhost";
		this.port = 6789;
		this.clientSocket = new Socket(this.host, this.port);
	}

	public Connection(int port) throws UnknownHostException, IOException {
		this.sentence = new String();
		this.modifiedSentence = new String();
		this.host = "localhost";
		this.port = port;
		this.clientSocket = new Socket(this.host, this.port);
	}
	
	public Connection(String host) throws UnknownHostException,
			IOException {
		this.sentence = new String();
		this.modifiedSentence = new String();
		this.host = host;
		this.port = 6789;
		this.clientSocket = new Socket(this.host, this.port);
	}

	public Connection(String host, int port) throws UnknownHostException,
			IOException {
		this.sentence = new String();
		this.modifiedSentence = new String();
		this.host = host;
		this.port = port;
		this.clientSocket = new Socket(this.host, this.port);
	}

	public void run() {

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		DataOutputStream outToServer;
		try {
			outToServer = new DataOutputStream(clientSocket.getOutputStream());

			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}