package util.PTS;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;
import util.PTS.Call.Address;
import util.PTS.Call.Call;
import util.PTS.Call.User;
import util.PTS.Exception.CallBusyException;
import util.PTS.Exception.LoginErrorException;
import util.PTS.Exception.RegisterErrorException;
import util.PTS.Log.Log;
import util.RTP.Phone;
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
	private Phone			phone;
	private String			caller;
	private int				port0;
	private int				port1;

	public ThreadPTSClient(String host, int port0, int port1)
			throws Exception{
		super(host);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
		this.username = new String();
		this.toCheckLogin = false;
		this.toCheckRegister = false;
		this.toCheckCall = false;
		this.logged = false;
		this.registered = false;
		this.phone = null;
		this.caller = null;
		this.port0 = port0;
		this.port1 = port1;
	}

	public ThreadPTSClient(String host) throws Exception{
		super(host);
		this.userOn = new Vector<>();
		this.userOff = new Vector<>();
		this.username = new String();
		this.toCheckLogin = false;
		this.toCheckRegister = false;
		this.logged = false;
		this.registered = false;
		this.phone = null;
		this.caller = null;
		this.port0 = 16384;
		this.port1 = 32766;
	}

	synchronized public String getCaller(){
		return caller;
	}

	synchronized public Phone getPhone(){
		return phone;
	}

	synchronized public void setPhone(Phone phone){
		this.phone = phone;
	}

	synchronized public void hangUp(){
		this.phone.stop();
		this.phone = null;
	}

	synchronized public Vector<String> getUserOff(){
		return userOff;
	}

	synchronized public Vector<String> getUserOn(){
		return userOn;
	}

	synchronized public Vector<String> getUser(){
		Vector<String> user = new Vector<>(userOn);
		user.addAll(userOff);
		return userOff;
	}

	synchronized public boolean isLogged(){
		return logged;
	}

	synchronized public boolean isRegistered(){
		return registered;
	}

	synchronized public boolean isToCheckLogin(){
		return toCheckLogin;
	}

	synchronized public boolean isToCheckRegister(){
		return toCheckRegister;
	}

	synchronized public boolean isToCheckCall(){
		return toCheckCall;
	}

	synchronized public void login(String username) throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		this.username = username;
		login();
	}

	public void login() throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		send(Log.getLogin(username).toString());
		this.toCheckLogin = true;
	}

	synchronized public void logoff() throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		send(Log.getLogoff(username).toString());
		this.toCheckLogin = false;
	}

	synchronized public void register(String username) throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		send(Log.getLogon(username).toString());
		this.toCheckRegister = true;
	}

	synchronized public void call(String username) throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		send(Call.call(username).toString());
		this.toCheckCall = true;
	}

	synchronized public void call(int index) throws IOException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		call(userOn.get(index));
	}

	synchronized public int getPort0(){
		return port0;
	}

	synchronized public int getPort1(){
		return port1;
	}

	synchronized public String getUsername(){
		return username;
	}

	synchronized public void setCaller(String caller){
		this.caller = caller;
	}

	synchronized public void setLogged(boolean logged){
		this.logged = logged;
	}

	synchronized public void setPort0(int port0){
		this.port0 = port0;
	}

	synchronized public void setPort1(int port1){
		this.port1 = port1;
	}

	synchronized public void setRegistered(boolean registered){
		this.registered = registered;
	}

	synchronized public void setToCheckCall(boolean toCheckCall){
		this.toCheckCall = toCheckCall;
	}

	synchronized public void setToCheckLogin(boolean toCheckLogin){
		this.toCheckLogin = toCheckLogin;
	}

	synchronized public void setToCheckRegister(boolean toCheckRegister){
		this.toCheckRegister = toCheckRegister;
	}

	synchronized public void setUsername(String username){
		this.username = username;
	}

	synchronized public void setUserOff(Vector<String> userOff){
		this.userOff = userOff;
	}

	synchronized public void setUserOn(Vector<String> userOn){
		this.userOn = userOn;
	}

	synchronized protected void check() throws CallBusyException,
			RegisterErrorException, LoginErrorException, ConnectorDisconnectedException, ConnectorCannotSendPacketException{
		// TODO: checar todas as respostas do servidor aqui usando getReceived()
		// e atualizar as listas
		// OBS: isso fica num loop que roda no ran da classe ThreadClientTCP
		Vector<String> received = this.getReceived();
		for(int index = 0; index < received.size(); index++){
			PTS pts = new PTS(received.get(index));

			if(isToCheckLogin()){
				if(received.get(index).equals(Log.getOk())){
					setToCheckLogin(false);
					setLogged(true);
				}

				if(received.get(index).equals(Log.getError())){
					setToCheckLogin(false);
					throw new LoginErrorException();
				}
			}

			if(isToCheckRegister()){
				if(received.get(index).equals(Log.getOk())){
					setToCheckRegister(false);
					setRegistered(true);
				}

				if(received.get(index).equals(Log.getError())){
					setToCheckRegister(false);
					throw new RegisterErrorException();
				}
			}

			if(isToCheckCall()){

				if(new Address(pts).isAddress()){
					setToCheckCall(false);
					setPhone(new Phone(pts.getPts().get(0).getValue(),
							getPort0(), getPort1()));
					getPhone().start();
				}

				if(Call.isError(received.get(index))){
					setToCheckCall(false);
					throw new CallBusyException();
				}
			}

			if(new Address(pts).isAddress()){
				if(new User(pts).isAddressUser()){
					if(getPhone() == null){
						// ok atender
						setCaller(pts.getPts().get(0).getValue());
						setPhone(new Phone(pts.getPts().get(1).getValue(),
								getPort1(), getPort0()));
						getPhone().start();
						send(Call.getOk(getCaller()).toString());
					}else{
						String tempCaller = pts.getPts().get(0).getValue();
						send(Call.getError(tempCaller).toString());
					}
				}
			}

			PTS tempPts = new PTS(received.get(index));
			if(tempPts.getType().equals("log")){
				for(int index2 = 0; index2 < tempPts.getPts().size(); index2++){
					PTS tempPts2 = tempPts.getPts().get(index2);
					if(tempPts2.getType().equals("on")){

						if(getUserOn().contains(tempPts2.getValue())){
							getUserOn().add(tempPts2.getValue());
						}

					}else if(tempPts2.getType().equals("off")){

						if(getUserOff().contains(tempPts2.getValue())){
							getUserOff().add(tempPts2.getValue());
						}

					}
				}
			}
		}
	}

}
