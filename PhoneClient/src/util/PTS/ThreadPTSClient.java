package util.PTS;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import util.PTS.Call.Call;
import util.PTS.Log.Log;
import util.TCP.ThreadTCPClient;

public class ThreadPTSClient extends ThreadTCPClient{
	private Vector<String>	userOn;
	private Vector<String>	userOff;
	private String			username;
	private boolean			toCheckLogin;
	private boolean			toCheckRegister;
	private boolean			toCheckCall;
	private boolean			logged;
	private boolean			registered;

	public ThreadPTSClient(String host, int port) throws UnknownHostException,
			IOException{
		super(host, port);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
		this.username = new String();
		this.toCheckLogin = false;
		this.toCheckRegister = false;
		this.toCheckCall = false;
		this.logged = false;
		this.registered = false;
	}

	public ThreadPTSClient(String host) throws UnknownHostException,
			IOException{
		super(host, 9000);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
		this.username = new String();
		this.toCheckLogin = false;
		this.toCheckRegister = false;
		this.logged = false;
		this.registered = false;
	}

	public Vector<String> getUserOff(){
		return userOff;
	}

	public Vector<String> getUserOn(){
		return userOn;
	}

	public boolean isLogged(){
		return logged;
	}

	public boolean isRegistered(){
		return registered;
	}

	public boolean isToCheckLogin(){
		return toCheckLogin;
	}

	public boolean isToCheckRegister(){
		return toCheckRegister;
	}

	public boolean isToCheckCall(){
		return toCheckCall;
	}

	public void login(String username) throws IOException{
		this.username = username;
		login();
	}

	public void login() throws IOException{
		this.threadSender.send(Log.getLogin(username).toString());
		this.toCheckLogin = true;
	}
	
	public void logoff() throws IOException{
		this.threadSender.send(Log.getLogoff(username).toString());
		this.toCheckLogin = false;
	}

	public void register(String username) throws IOException{
		this.threadSender.send(Log.getLogon(username).toString());
		this.toCheckRegister = true;
	}

	public void call(String username) throws IOException{
		this.threadSender.send(Call.call(username).toString());
		this.toCheckCall = true;
	}

	public void call(int index) throws IOException{
		call(userOn.get(index));
	}

	protected void check(){
		// TODO: checar todas as respostas do servidor aqui usando getReceived()
		// e atualizar as listas
		// OBS: isso fica num loop que roda no ran da classe ThreadClientTCP
		Vector<String> received = this.getReceived();
		for(int index = 0; index < received.size(); index++){
			if(this.toCheckLogin){
				if(received.get(index).equals(Log.getOk())){
					this.toCheckLogin = false;
					this.logged = true;
				}

				if(received.get(index).equals(Log.getError())){
					this.toCheckLogin = false;
				}
			}

			if(this.toCheckRegister){
				if(received.get(index).equals(Log.getOk())){
					this.toCheckRegister = false;
					this.registered = true;
				}

				if(received.get(index).equals(Log.getError())){
					this.toCheckRegister = false;
				}
			}
			
			if(this.toCheckCall){
//				if(received.get(index).equals(CallStatus.getOk())){
//					this.toCheckRegister = false;
//					this.registered = true;
//				}

				if(received.get(index).equals(Call.getError())){
					this.toCheckRegister = false;
				}
			}

			PTS tempPts = new PTS(received.get(index));
			if(tempPts.getType().equals("log")){
				for(int index2 = 0; index2 < tempPts.getPts().size(); index2++){
					PTS tempPts2 = tempPts.getPts().get(index2);
					if(tempPts2.getType().equals("on")){

						if(this.userOn.contains(tempPts2.getValue())){
							this.userOn.add(tempPts2.getValue());
						}

					}else if(tempPts2.getType().equals("off")){

						if(this.userOff.contains(tempPts2.getValue())){
							this.userOff.add(tempPts2.getValue());
						}

					}
				}
			}
		}
	}

}
