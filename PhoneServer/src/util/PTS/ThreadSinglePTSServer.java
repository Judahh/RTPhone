package util.PTS;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import util.PTS.Call.Address;
import util.PTS.Call.Call;
import util.PTS.Log.Log;
import util.TCP.ThreadSingleTCPServer;

public class ThreadSinglePTSServer extends ThreadSingleTCPServer{

	private Vector<String>	broadcast;
	private Vector<String>	call;
	private Vector<String>	toCheck;
	private boolean			login;
	private boolean			on;
	private boolean			register;
	private String			username;

	public ThreadSinglePTSServer(Socket clientSocket) throws IOException{
		super(clientSocket);
		this.broadcast = new Vector<>();
		this.toCheck = new Vector<>();
		this.call = new Vector<>();
		this.username = new String();
		this.login = false;
		this.register = false;
		this.on = false;
	}

	public Vector<String> getCall(){
		return call;
	}

	public void addCall(Vector<String> call){
		this.call.addAll(call);
	}

	public void addCall(String call){
		this.call.add(call);
	}

	public void clearCall(String call){
		this.call = new Vector<>();
	}

	public Vector<String> getBroadcast(){
		return broadcast;
	}

	public Vector<String> getToCheck(){
		return toCheck;
	}

	public String getUsername(){
		return username;
	}

	public void addBroadcast(String broadcast){
		this.broadcast.add(broadcast);
	}

	public void addToCheck(String toCheck){
		this.toCheck.add(toCheck);
	}

	public void addBroadcast(Vector<String> broadcast){
		this.broadcast.addAll(broadcast);
	}

	public void addToCheck(Vector<String> toCheck){
		this.toCheck.addAll(toCheck);
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void clearBroadcast(){
		this.broadcast = new Vector<>();
	}

	public void clearToCheck(){
		this.toCheck = new Vector<>();
	}

	public void clearUsername(){
		this.username = new String();
	}

	public void setLogin(boolean login){
		this.login = login;
	}

	public void setRegister(boolean register){
		this.register = register;
	}

	public boolean isLogin(){
		return login;
	}

	public boolean isRegister(){
		return register;
	}

	public void setOn(boolean on){
		this.on = on;
	}

	public boolean isOn(){
		return on;
	}

	protected void check(){
		// TODO: Fazer todas as checagens aqui!!!
		Vector<String> received = getReceived();
		for(int index = 0; index < received.size(); index++){
			if(!received.get(index).isEmpty()){
				System.out.println("all received:" + received.get(index)
						+ "fim");
				PTS pts = new PTS(received.get(index));
				System.out.println("all received pts:" + pts.toString());
				switch(pts.getType()){
					case "log":
						Log log = new Log(pts);
						addBroadcast(log.getBroadcast());
						addToCheck(log.getToCheck());
						System.out.println("to check:" + log.getToCheck());
						if(!log.getUsername().isEmpty()){
							setUsername(log.getUsername());
						}
						if(!log.getToSend().isEmpty()){
							System.out.println("to send:" + log.getToSend());
							send(log.getToSend());
						}
					break;
					case "call":
						if((Call.isOk(received.get(index)))
								|| (Call.isError(received.get(index)))){
							addCall(received.get(index));
						}else{
							Call call = new Call(pts, username, new Address(
									this.getAddress()));
							addCall(call.getToCheck());
						}
				}
			}
		}

		if(this.login){
			addBroadcast(Log.getLog(username, true));
			this.on = true;
		}

		if(this.register){
			addBroadcast(Log.getLog(username, false));
		}
	}
}
