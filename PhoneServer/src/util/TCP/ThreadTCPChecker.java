package util.TCP;

public class ThreadTCPChecker extends Thread{
	private ThreadTCPServer	threadTCPServer;

	public ThreadTCPChecker(ThreadTCPServer threadTCPServer){
		this.threadTCPServer = threadTCPServer;
	}

	protected ThreadTCPServer getThreadTCPServer(){
		return threadTCPServer;
	}

	protected void setThreadTCPServer(ThreadTCPServer threadTCPServer){
		this.threadTCPServer = threadTCPServer;
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
