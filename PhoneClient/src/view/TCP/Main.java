package view.TCP;
import java.io.IOException;

import util.TCP.ClientTCP;

public class Main{

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException{
		ClientTCP clientTCP = new ClientTCP("localhost", 9000);
		clientTCP.start();

		for(int i = 0;; i++){
			// Thread.sleep(1000);
			System.out.println("sent from clientMain: Test" + i);
			clientTCP.send("Test" + i);
		}
		// System.out.println("Stopping Server");
		// server.stop();
	}

}
