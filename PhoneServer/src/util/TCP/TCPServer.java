package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import Extasys.Network.TCP.Server.Listener.TCPListener;

public class TCPServer extends Extasys.Network.TCP.Server.ExtasysTCPServer{

	protected TCPListener	TCPListener;

	public TCPServer(InetAddress listenerIP, int maxConnections,
			int connectionsTimeOut, int corePoolSize, int maximumPoolSize)
			throws IOException, Exception{
		super("TCPServer", "", corePoolSize, maximumPoolSize);
		TCPListener = AddListener("Main Listener", listenerIP, 9000,
				maxConnections, 65535, connectionsTimeOut, 100, ((char) 2));
		Start();

	}

	@Override
	public void OnDataReceive(TCPClientConnection sender, DataFrame data){
		byte[] reply = new byte[data.getLength() + 1];
		System.arraycopy(data.getBytes(), 0, reply, 0, data.getLength());
		reply[data.getLength()] = ((char) 2);

		this.ReplyToAll(reply, 0, reply.length);
	}
	
	@Override
	public void OnClientConnect(TCPClientConnection client){
		// New client connected.
		client.setName(client.getIPAddress()); // Set a name for this client if
												// you want to.
		System.out.println(client.getIPAddress() + " connected.");
		System.out.println("Total clients connected: "
				+ super.getCurrentConnectionsNumber());
	}

	@Override
	public void OnClientDisconnect(TCPClientConnection client){
		// Client disconnected.
		System.out.println(client.getIPAddress() + " disconnected.");
		clientDisconnected();
	}

	protected void clientDisconnected(){
		// this.ReplyToAll(reply, 0, reply.length);
	}

	public void send(String toSend){
		super.ReplyToSender(toSend, (TCPClientConnection) TCPListener.getConnectedClients().get(0));
	}

	public void addBroadcast(Vector<String> broadcast){
	}

	public void addToCheck(Vector<String> toCheck){
	}

	public Vector<String> getCall(){
		return new Vector<>();
	}

	public void addCall(Vector<String> call){
	}

	public void addCall(String call){
	}

	public void clearCall(String call){
	}

	public InetAddress inetAddress(){
		return this.clientSenderSocket.getInetAddress();
	}

	public String getAddress(){
		return this.clientSenderSocket.getInetAddress().toString();
	}

	public boolean isConnected(){
		if(clientSenderSocket.isConnected()
				&& clientReceiverSocket.isConnected()){
			return true;
		}else{
			try{
				close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		return false;
	}

	public void close() throws IOException{
		clientSenderSocket.close();
		clientReceiverSocket.close();
	}

	public Vector<String> getBroadcast(){
		return new Vector<>();
	}

	public Vector<String> getToCheck(){
		return new Vector<>();
	}

	public String getUsername(){
		return new String();
	}

	public void addBroadcast(String broadcast){
	}

	public void addToCheck(String toCheck){
	}

	public void setUsername(String username){
	}

	public void clearBroadcast(){
	}

	public void clearToCheck(){
	}

	public void clearUsername(){
	}

	public void setLogin(boolean login){
	}

	public void setRegister(boolean register){
	}

	public boolean isLogin(){
		return false;
	}

	public boolean isRegister(){
		return false;
	}

	public void setOn(boolean on){
	}

	public boolean isOn(){
		return false;
	}
}