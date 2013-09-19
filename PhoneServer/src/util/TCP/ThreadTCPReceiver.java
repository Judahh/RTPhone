package util.TCP;

import java.io.IOException;
import java.net.Socket;

public class ThreadTCPReceiver extends ThreadTCPSingle{

	public ThreadTCPReceiver(Socket serverSocket) throws IOException{
		super(serverSocket);
	}
	
	protected void work() throws IOException{
		receive();
	}
}
