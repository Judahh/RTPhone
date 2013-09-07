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
	protected Vector<ThreadSingleTCPServer> workerRunnable;

	public ThreadTCPServer(int port) {
		this.serverPort = port;
		this.serverSocket = null;
		this.isStopped = false;
		this.runningThread = null;
		this.workerRunnable = new Vector<>();
	}

	public Vector<ThreadSingleTCPServer> getWorkerRunnable() {
		return workerRunnable;
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection",
						e);
			}
			try {
				ThreadSingleTCPServer workerRunnableA;
				workerRunnableA = new ThreadSingleTCPServer(clientSocket);
				workerRunnableA.start();
				this.workerRunnable.add(workerRunnableA);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (ThreadSingleTCPServer iterable_element : workerRunnable) {
			try {
				iterable_element.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Server Stopped.");
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