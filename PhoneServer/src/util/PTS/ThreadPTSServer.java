package util.PTS;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import util.TCP.ThreadSingleTCPServer;
import util.TCP.ThreadTCPServer;

public class ThreadPTSServer extends ThreadTCPServer {
	private Vector<String> userOn;
	private Vector<String> userOff;

	public ThreadPTSServer(int port) {
		super(port);
		this.userOn=new Vector<>();
		this.userOff=new Vector<>();
	}

	public ThreadPTSServer() {
		super(9000);
		this.userOn=new Vector<>();
		this.userOff=new Vector<>();
	}

	@Override
	protected void addConnection() {
		try {
			ThreadSinglePTSServer ThreadSinglePTSServerA;
			ThreadSinglePTSServerA = new ThreadSinglePTSServer(newClientConnection);
			ThreadSinglePTSServerA.start();
			this.threadSingleTCPServer.add(ThreadSinglePTSServerA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void check() {
	}
}
