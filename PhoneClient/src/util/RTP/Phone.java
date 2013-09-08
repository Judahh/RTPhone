package util.RTP;

public class Phone{
	private Transmitter	transmitter;
	private Receiver	receiver;
	private int			transmitterPort;
	private int			receiverPort;
	private String		host;

	public Phone(String host, int receiverPort, int transmitterPort){
		this.host = host;
		this.transmitterPort = transmitterPort;
		this.receiverPort = receiverPort;
	}

	public void start(){
		transmitter = new Transmitter(host, transmitterPort);
		receiver = new Receiver(host, receiverPort);
	}

	public void stop(){
		transmitter.stop();
		receiver.stop();
	}

	public String getHost(){
		return host;
	}

	public Receiver getReceiver(){
		return receiver;
	}

	public Transmitter getTransmitter(){
		return transmitter;
	}
}