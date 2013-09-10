package util.PTS;

import java.io.IOException;
import java.util.Vector;

import util.PTS.Log.In;
import util.PTS.Log.Log;
import util.PTS.Log.On;
import util.PTS.Log.Status;
import util.TCP.ThreadSingleTCPServer;
import util.TCP.ThreadTCPChecker;
import util.TCP.ThreadTCPServer;

public class ThreadPTSServer extends ThreadTCPServer{
	private Vector<String>	resgistered;
	private Vector<String>	logged;

	public ThreadPTSServer(int port){
		super(port);
		this.threadTCPChecker= new ThreadPTSChecker(this);
		this.resgistered = new Vector<>();
		this.logged = new Vector<>();
	}

	public ThreadPTSServer(){
		super(9000);
		this.resgistered = new Vector<>();
		this.logged = new Vector<>();
	}

	@Override
	protected void addConnection(){
		try{
			ThreadSinglePTSServer ThreadSinglePTSServerA;
			ThreadSinglePTSServerA = new ThreadSinglePTSServer(
					newClientConnection);
			ThreadSinglePTSServerA.start();
			this.threadSingleTCPServer.add(ThreadSinglePTSServerA);
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// OBS: isso fica num loop que roda no ran da classe ThreadTCPServer
	}

	public Vector<String> getLogged(){
		return logged;
	}

	public Vector<String> getResgistered(){
		return resgistered;
	}
}
