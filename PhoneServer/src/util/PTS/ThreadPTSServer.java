package util.PTS;

import java.io.IOException;
import java.util.Vector;
import util.TCP.ThreadTCPServer;

public class ThreadPTSServer extends ThreadTCPServer{
	private Vector<String>	userOn;
	private Vector<String>	userOff;

	public ThreadPTSServer(int port){
		super(port);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
	}

	public ThreadPTSServer(){
		super(9000);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
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

	@Override
	protected void check(){
		//TODO: Fazer todas as checagens e broadcasts aqui!!!
		// OBS: isso fica num loop que roda no ran da classe ThreadTCPServer
		checkLogin();
		checkRegister();
		checkBroadcast();
	}

	private void checkBroadcast(){
		//TODO: Fazer todas as checagens de broadcasts aqui!!!
		
	}

	private void checkRegister(){
		//TODO: Fazer todas as checagens de registros aqui!!!
		
	}

	private void checkLogin(){
		//TODO: Fazer todas as checagens de login aqui!!!
		
	}
}
