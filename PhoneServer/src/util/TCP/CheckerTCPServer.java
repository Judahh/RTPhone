package util.TCP;

public class CheckerTCPServer extends Thread{
	private TCPServer	TCPServer;

	public CheckerTCPServer(TCPServer TCPServer){
		this.TCPServer = TCPServer;
	}

	protected TCPServer getTCPServer(){
		return TCPServer;
	}

	protected void setTCPServer(TCPServer TCPServer){
		this.TCPServer = TCPServer;
	}

	@Override
	public void run(){
		System.out.println("start");
		while(true){
			//System.out.println("check");
			check();
		}
	}

	protected void check(){

	}
}
