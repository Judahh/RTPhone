package util.TCP;

import java.io.IOException;
import java.net.Socket;

public class ThreadTCPReceiver extends ThreadTCPSingle{
	protected ThreadTCPServer		threadTCPServer;
	protected ThreadSingleTCPServer	threadSingleTCPServer;

	public ThreadTCPReceiver(Socket serverSocket,
			ThreadTCPServer threadTCPServer,
			ThreadSingleTCPServer threadSingleTCPServer) throws IOException{
		super(serverSocket);
	}

	public ThreadTCPServer getThreadTCPServer(){
		return threadTCPServer;
	}

	protected void work() throws IOException{
		while(true){
			//System.out.println("Entrou receive");
			receive();
			//System.out.println("Saiu receive");
			check();
			//System.out.println("Saiu receive");
		}
	}

	protected void check(){
		// TODO Auto-generated method stub
		checkReceived();
		checkLogin();
		checkRegister();
		checkBroadcast();
		checkCall();
	}

	protected void checkClean(){

	}
	
	protected void checkReceived(){
		// TODO Auto-generated method stub

	}

	protected void checkCall(){
		// TODO Auto-generated method stub

	}

	protected void checkBroadcast(){
		// TODO Auto-generated method stub

	}

	protected void checkRegister(){
		// TODO Auto-generated method stub

	}

	protected void checkLogin(){
		// TODO Auto-generated method stub

	}
}
