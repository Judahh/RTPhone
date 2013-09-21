package util.TCP;

import java.io.IOException;
import java.net.Socket;

public class ThreadTCPReceiver extends ThreadTCPSingle{

	public ThreadTCPReceiver(Socket serverSocket) throws IOException{
		super(serverSocket);
	}
	
	protected void work() throws IOException{
//		System.out.println("Entrou receive");
		try {
			this.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		receive();
//		System.out.println("Saiu receive");
	}
}
