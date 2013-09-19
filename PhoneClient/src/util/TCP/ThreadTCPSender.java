package util.TCP;

import java.io.IOException;
import java.net.Socket;

public class ThreadTCPSender extends ThreadTCPSingle{

	public ThreadTCPSender(Socket serverSocket) throws IOException{
		super(serverSocket);
	}
	
	protected void work() throws IOException{
		System.out.println("Entrou send");
		send();
		System.out.println("Saiu send");
	}
}
