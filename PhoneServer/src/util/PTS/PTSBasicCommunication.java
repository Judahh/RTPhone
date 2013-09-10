package util.PTS;

public class PTSBasicCommunication{
	protected PTS		pts;
	protected String	broadcast;
	protected String	toCheck;
	protected String	username;
	protected String	toSend;
	
	public PTSBasicCommunication(){
		this.pts = new PTS();
		this.toSend = new String();
		this.broadcast = new String();
		this.toCheck = new String();
		this.username = new String();
	}

	public PTSBasicCommunication(PTS pts){
		this.pts = new PTS();
		this.toSend = new String();
		this.broadcast = new String();
		this.toCheck = new String();
		this.username = new String();
		start();
	}

	public String getBroadcast(){
		return broadcast;
	}

	public PTS getPts(){
		return pts;
	}

	public String getToCheck(){
		return toCheck;
	}

	public String getToSend(){
		return toSend;
	}

	public String getUsername(){
		return username;
	}
	
	protected void setBroadcast(String broadcast){
		this.broadcast = broadcast;
	}
	
	protected void setPts(PTS pts){
		this.pts = pts;
	}
	
	protected void setToCheck(String toCheck){
		this.toCheck = toCheck;
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
