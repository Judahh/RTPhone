package util.TCP;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Vector;

public class ThreadTCPServer extends Thread{

	protected int							serverPort;
	protected ServerSocket					serverSocket;
	protected boolean						isStopped;
	protected Thread						runningThread;
	protected Vector<ThreadSingleTCPServer>	threadSingleTCPServer;
	protected Socket						newClientConnection;
	protected ThreadTCPChecker				threadTCPChecker;

	public ThreadTCPServer(int port){
		this.serverPort = port;
		this.serverSocket = null;
		this.isStopped = false;
		this.runningThread = null;
		this.threadSingleTCPServer = new Vector<>();
		this.threadTCPChecker= new ThreadTCPChecker(this);
	}

	private void acceptConnection(){
		this.newClientConnection = null;
		try{
			this.newClientConnection = this.serverSocket.accept();
		}catch(IOException e){
			if(isStopped()){
				System.out.println("Server Stopped.");
				return;
			}
			throw new RuntimeException("Error accepting client connection", e);
		}
		addConnection();
	}

	protected void addConnection(){
		try{
			ThreadSingleTCPServer threadSingleTCPServerA;
			threadSingleTCPServerA = new ThreadSingleTCPServer(
					newClientConnection);
			threadSingleTCPServerA.start();
			this.threadSingleTCPServer.add(threadSingleTCPServerA);
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	protected void check(){
//
//	}

	private void closeConnections(){
		for(ThreadSingleTCPServer iterable_element : threadSingleTCPServer){
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
//			check();
		}
		closeConnections();
	}

	public synchronized boolean isStopped(){
		return this.isStopped;
	}

	private void openServerSocket(){
		try{
			this.serverSocket = new ServerSocket(this.serverPort);
		}catch(IOException e){
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}
	
	public Vector<ThreadSingleTCPServer> getThreadSingleTCPServer(){
		// TODO Auto-generated method stub
		return this.threadSingleTCPServer;
	}

	public Vector<String> getLogged(){
		return new Vector<>();
	}

	public Vector<String> getResgistered(){
		return new Vector<>();
	}
}