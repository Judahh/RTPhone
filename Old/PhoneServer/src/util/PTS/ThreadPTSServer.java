package util.PTS;

import java.io.IOException;
import java.util.Vector;
import util.TCP.ThreadTCPServer;

public class ThreadPTSServer extends ThreadTCPServer{
	private Vector<String>	resgistered;
	private Vector<String>	logged;

	public ThreadPTSServer(){
		super();
//		this.threadTCPChecker= new ThreadPTSChecker(this);
		this.resgistered = new Vector<>();
		this.logged = new Vector<>();
	}

	@Override
	protected void addConnection(){
		try{
			ThreadSinglePTSServer ThreadSinglePTSServerA;
			ThreadSinglePTSServerA = new ThreadSinglePTSServer(
					newClientSenderConnection, newClientReceiverConnection, this);
			ThreadSinglePTSServerA.start();
			getThreadSingleTCPServer().add(ThreadSinglePTSServerA);
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
