package util.TCP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Vector;

import Extasys.DataFrame;
import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;

public class TCPSingleClient
		extends
			Extasys.Network.TCP.Client.ExtasysTCPClient{
	private Vector<String>		received;

	public TCPSingleClient(String serverAddress) throws Exception{
		super("SingleTCPClient", "", 4, 8);
		received = new Vector<>();
		// super.AddConnector("Main Connector",
		// InetAddress.getByName(serverAddress), 9000, 10240);

		AddConnector("Main Connector",
				InetAddress.getByName(serverAddress), 9000, 10240, ((char) 2));
		Start();
	}

	@Override
	public void OnDataReceive(TCPConnector connector, DataFrame data){
		getAllReceived().add(new String(data.getBytes()));
	}

	@Override
	public void OnConnect(TCPConnector connector){
		System.out.println("Connected to server");
	}

	@Override
	public void OnDisconnect(TCPConnector connector){
		System.out.println("Disconnected from server");
	}

	public void Connect(){
		try{
			super.Start();
		}catch(Exception ex){

		}
	}

	private synchronized Vector<String> getAllReceived(){
		return received;
	}

	public Vector<String> getReceived(){
		Vector<String> received = getAllReceived();

		getAllReceived().clear();

		return received;
	}

	protected void send(String toSend) throws ConnectorDisconnectedException,
			ConnectorCannotSendPacketException{
		SendData(toSend + (char) 2);
	}
}
