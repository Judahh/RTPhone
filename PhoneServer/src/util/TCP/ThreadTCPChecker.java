package util.TCP;

public class ThreadTCPChecker extends Thread{
	private ThreadTCPServer	threadTCPServer;

	public ThreadTCPChecker(ThreadTCPServer threadTCPServer){
		this.threadTCPServer = threadTCPServer;
	}

	protected ThreadTCPServer getThreadTCPServer(){
		return threadTCPServer;
	}

	protected boolean isCheck(){
		for(int index = 0; index < threadTCPServer.getThreadSingleTCPServer()
				.size(); index++){
			if(threadTCPServer.getThreadSingleTCPServer().get(index).isCheck()){
				return true;
			}
		}
		return false;
	}

	protected void setThreadTCPServer(ThreadTCPServer threadTCPServer){
		this.threadTCPServer = threadTCPServer;
	}

	@Override
	public void run(){
		System.out.println("start");
		while(true){
			// System.out.println("check");
			try{
				Thread.sleep(5);
				if(isCheck()){
					check();
				}
				Thread.sleep(5);
				checkClean();
			}catch(InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void checkClean(){
		
	}

	protected void check(){

	}
}
