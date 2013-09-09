package util.PTS;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import util.TCP.ThreadTCPClient;

public class PTSClientManager extends ThreadTCPClient{
	private Vector<String>	userOn;
	private Vector<String>	userOff;

	public PTSClientManager(String host, int port) throws UnknownHostException,
			IOException{
		super(host, port);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
	}

	public PTSClientManager(String host) throws UnknownHostException,
			IOException{
		super(host, 9000);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
	}

	public void login(String username) throws IOException{
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("log");

		PTS ptsTemp = new PTS();// TODO: colocar isto na classe In que fica
								// dentro da Log
		ptsTemp.setType("in");
		ptsTemp.setValue(username);

		pts.setValue(ptsTemp);
		this.threadSender.send(pts.toString());
	}

	public void register(String username) throws IOException{
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("log");

		PTS ptsTemp = new PTS();// TODO: colocar isto na classe On que fica
								// dentro da Log
		ptsTemp.setType("on");
		ptsTemp.setValue(username);

		pts.setValue(ptsTemp);
		this.threadSender.send(pts.toString());
	}

	public void call(String username) throws IOException{
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("call");

		PTS ptsTemp = new PTS();// TODO: colocar isto na classe On que fica
								// dentro da Log
		ptsTemp.setType("user");
		ptsTemp.setValue(username);

		pts.setValue(ptsTemp);
		this.threadSender.send(pts.toString());
	}

	public void call(int index) throws IOException{
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("call");

		PTS ptsTemp = new PTS();// TODO: colocar isto na classe On que fica
								// dentro da Log
		ptsTemp.setType("user");
		ptsTemp.setValue(userOn.get(index));

		pts.setValue(ptsTemp);
		this.threadSender.send(pts.toString());
	}

	protected void check(){
		// TODO: checar todas as respostas do servidor aqui usando getReceived()
		// e atualizar as listas
		// OBS: isso fica num loop que roda no ran da classe ThreadClientTCP
	}

}
