package util.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import util.PTS.Exception.CallBusyException;
import util.PTS.Exception.LoginErrorException;
import util.PTS.Exception.RegisterErrorException;

public class ThreadTCPClient extends Thread{
	protected Socket			serverSocket;
	protected String			host;
	protected int				port;
	private boolean				sent;
	private BufferedReader		input;
	private Vector<String>		received;
	private DataOutputStream	output;
	private Vector<String>		toSend;

	public ThreadTCPClient(String host, int port) throws UnknownHostException,
			IOException{
		this.sent = false;
		this.host = host;
		this.port = port;// 6789
		this.serverSocket = new Socket(host, port);
		this.received = new Vector<>();
		this.input = new BufferedReader(new InputStreamReader(
				serverSocket.getInputStream()));
		this.toSend = new Vector<>();
		this.output = new DataOutputStream(serverSocket.getOutputStream());
	}

	private void receive() throws IOException{
		System.out.println("Receive");
		if(this.sent){
			this.received.add(this.input.readLine());
			if(this.received.size() > 0){
				System.out.println("Client received:"
						+ this.received.get(this.received.size() - 1) + '\n');
			}
			this.sent = false;
		}
		System.out.println("ReceiveEnd");
	}
	public Vector<String> getReceived(){
		Vector<String> temp = received;
		this.received = new Vector<>();
		return temp;
	}

	public void send(String toSend){
		this.toSend.add(toSend);
		System.out.println("ADD Client send:" + this.toSend.get(0) + '\n');
	}

	private void send() throws IOException{
		System.out.println("Send");
		while(!this.toSend.isEmpty()){
			this.output.write((this.toSend.get(0) + '\n').getBytes());
			this.output.flush();
			System.out.println("Client send:" + this.toSend.get(0) + '\n');
			this.toSend.remove(0);
			this.sent = true;
		}
		System.out.println("SendEnd");
	}

	synchronized public InetAddress inetAddress(){
		return this.serverSocket.getInetAddress();
	}

	synchronized public String address(){
		return this.serverSocket.getInetAddress().toString();
	}

	synchronized public boolean isConnected(){
		return serverSocket.isConnected();
	}

	synchronized public void close() throws IOException{
		serverSocket.close();
	}

	synchronized public String getHost(){
		return host;
	}

	synchronized public int getPort(){
		return port;
	}

	public void run(){
		try{
			while(serverSocket.isConnected()){
				checkTCP();
				try{
					check();
				}catch(CallBusyException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(RegisterErrorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(LoginErrorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Out");
			close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void checkTCP() throws IOException{
		System.out.println("Check");
		send();
		receive();
		System.out.println("CheckEnd");
	}

	protected void check() throws CallBusyException, RegisterErrorException,
			LoginErrorException{
	}
}
