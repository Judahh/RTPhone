package view.PTS;

import java.io.IOException;
import java.net.UnknownHostException;

import util.PTS.PTS;
import util.TCP.ThreadTCPClient;

public class Main{

	public static void main(String[] args) throws UnknownHostException, IOException{
		PTS pts = new PTS("<log><on>value<on><log>");
		//System.out.println(pts);
		ThreadTCPClient clientTCP = new ThreadTCPClient("localhost", 9000);
		clientTCP.start();
		clientTCP.send(pts.toString());
	}

}
