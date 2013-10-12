package realTimeTransportProtocol;

public class Receiver implements Runnable{
	private Thread		receiveThread;
	private AVReceive	AVReceive;
	private int			port;
	private String		host;

	public Receiver(String host, int port){
		this.receiveThread = new Thread(this, "Audio-Phone");
		this.receiveThread.start();
		this.host = host;
		this.port = port;
	}

	public void run(){
		String temp = this.host + "/" + this.port;
		String[] info = new String[1];
		info[0] = temp;

		AVReceive = new AVReceive(info);
		if(!AVReceive.initialize()){
			System.exit(-1);
		}

		// Check to see if AVReceive is done.
		try{
			while(!AVReceive.isDone())
				Thread.sleep(1000);
		}catch(Exception e){
		}
	}

	public void stop(){
		AVReceive.close();
	}

	public AVReceive getAVReceive(){
		return AVReceive;
	}

	public Thread getReceiveThread(){
		return receiveThread;
	}

	public String getHost(){
		return host;
	}

	public int getPort(){
		return port;
	}
}
