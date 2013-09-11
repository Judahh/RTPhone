package view.PTS;

import java.io.IOException;
import java.net.UnknownHostException;

import util.PTS.PTS;
import util.PTS.ThreadPTSClient;

public class Main{

	public static void main(String[] args) throws UnknownHostException, IOException{
		ThreadPTSClient clientPTS = new ThreadPTSClient("localhost", 9000);
		clientPTS.start();
		clientPTS.register("test");
	}

}
