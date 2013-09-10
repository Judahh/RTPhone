package util.TCP;

public class ThreadTCPChecker extends Thread{
	protected ThreadTCPServer	threadTCPServer;
	
	public ThreadTCPChecker(ThreadTCPServer threadTCPServer){
		this.threadTCPServer=threadTCPServer;
	}
	
	@Override
	public void run(){
		while(!this.threadTCPServer.isStopped()){
			check();
		}
	}

	protected void check(){

	}
}
