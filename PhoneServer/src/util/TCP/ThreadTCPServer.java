package util.TCP;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Vector;

public class ThreadTCPServer extends Thread{
	protected ServerSocket					serverSenderSocket;
	protected ServerSocket					serverReceiverSocket;
	protected boolean						isStopped;
	protected Thread						runningThread;
	protected Vector<TCPServer>	threadSingleTCPServer;
	protected Socket						newClientSenderConnection;
	protected Socket						newClientReceiverConnection;
	protected ThreadTCPChecker				threadTCPChecker;

	public ThreadTCPServer(){
		this.serverSenderPort = 9000;
		this.serverSenderSocket = null;
		this.serverReceiverSocket = null;
		this.isStopped = false;
		this.runningThread = null;
		this.threadSingleTCPServer = new Vector<>();
		this.threadTCPChecker = new ThreadTCPChecker(this);
	}

	synchronized private void acceptConnection(){
		this.newClientSenderConnection = null;
		this.newClientReceiverConnection = null;
		try{
			this.newClientSenderConnection = this.serverSenderSocket.accept();
			this.newClientReceiverConnection = this.serverReceiverSocket
					.accept();
		}catch(IOException e){
			if(isStopped()){
				System.out.println("Server Stopped.");
				return;
			}
			throw new RuntimeException("Error accepting client connection", e);
		}
		addConnection();
	}

	synchronized protected void addConnection(){
		try{
			TCPServer threadSingleTCPServerA;
			threadSingleTCPServerA = new TCPServer(
					newClientSenderConnection, newClientReceiverConnection);
			threadSingleTCPServerA.start();
			getThreadSingleTCPServer().add(threadSingleTCPServerA);
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized private void closeConnections(){
		for(TCPServer iterable_element : getThreadSingleTCPServer()){
			try{
				iterable_element.close();
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Server Stopped.");
	}

	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		this.threadTCPChecker.start();
		while(!isStopped()){
			acceptConnection();
		}
		closeConnections();
	}

	public synchronized boolean isStopped(){
		return this.isStopped;
	}

	private void openServerSocket(){
		try{
			this.serverSenderSocket = new ServerSocket(this.serverSenderPort);
			this.serverReceiverSocket = new ServerSocket(
					this.serverReceiverPort);
		}catch(IOException e){
			throw new RuntimeException("Cannot open port", e);
		}
	}

	public Vector<TCPServer> getThreadSingleTCPServer(){
		return this.threadSingleTCPServer;
	}

	public Vector<String> getLogged(){
		return new Vector<>();
	}

	public Vector<String> getResgistered(){
		return new Vector<>();
	}
}