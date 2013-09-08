package util.TCP;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTCPServer extends Thread {

	protected int serverPort;
	protected ServerSocket serverSocket;
	protected boolean isStopped;
	protected Thread runningThread;
	protected Vector<ThreadSingleTCPServer> threadSingleTCPServer;
	protected Socket newClientConnection;

	public ThreadTCPServer(int port) {
		this.serverPort = port;
		this.serverSocket = null;
		this.isStopped = false;
		this.runningThread = null;
		this.threadSingleTCPServer = new Vector<>();
	}

	public Vector<ThreadSingleTCPServer> getWorkerRunnable() {
		return threadSingleTCPServer;
	}

	private void acceptConnection() {
		this.newClientConnection = null;
		try {
			this.newClientConnection = this.serverSocket.accept();
		} catch (IOException e) {
			if (isStopped()) {
				System.out.println("Server Stopped.");
				return;
			}
			throw new RuntimeException("Error accepting client connection", e);
		}
		addConnection();
	}
	
	protected void addConnection() {
		try {
			ThreadSingleTCPServer threadSingleTCPServerA;
			threadSingleTCPServerA = new ThreadSingleTCPServer(newClientConnection);
			threadSingleTCPServerA.start();
			this.threadSingleTCPServer.add(threadSingleTCPServerA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void check() {
	}
	
	private void closeConnections() {
		for (ThreadSingleTCPServer iterable_element : threadSingleTCPServer) {
			try {
				iterable_element.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Server Stopped.");
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			acceptConnection();
			check();
		}
		closeConnections();
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}
}