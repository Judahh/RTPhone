package util.PTS;

import java.util.Vector;

public class PTSBasicCommunication{
	protected PTS		pts;
	protected Vector<String>	broadcast;
	protected Vector<String>	toCheck;
	protected String	username;
	protected String	toSend;
	
	public PTSBasicCommunication(){
		this.pts = new PTS();
		this.toSend = new String();
		this.broadcast = new Vector<>();
		this.toCheck = new Vector<>();
		this.username = new String();
	}

	public PTSBasicCommunication(PTS pts){
		this.pts = pts;
		this.toSend = new String();
		this.broadcast = new Vector<>();
		this.toCheck = new Vector<>();
		this.username = new String();
		start();
	}

	public Vector<String> getBroadcast(){
		return broadcast;
	}

	public PTS getPts(){
		return pts;
	}

	public Vector<String> getToCheck(){
		return toCheck;
	}

	public String getToSend(){
		return toSend;
	}

	public String getUsername(){
		return username;
	}
	
	protected void addBroadcast(String broadcast){
		this.broadcast.add(broadcast);
	}
	
	protected void addBroadcast(Vector<String> broadcast){
		this.broadcast.addAll(broadcast);
	}
	
	protected void setPts(PTS pts){
		this.pts = pts;
	}
	
	protected void addToCheck(String toCheck){
		this.toCheck.add(toCheck);
	}
	
	protected void addToCheck(Vector<String> toCheck){
		this.toCheck.addAll(toCheck);
	}
	
	protected void setToSend(String toSend){
		this.toSend = toSend;
	}
	
	protected void setUsername(String username){
		this.username = username;
	}

	protected void start(){
		
	}
}
