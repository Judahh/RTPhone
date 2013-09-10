package util.PTS.Log;

import util.PTS.PTS;
import util.PTS.PTSBasicCommunication;

public class Log extends PTSBasicCommunication{

	public Log(){
		super();
	}

	public Log(PTS pts){
		super(pts);
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
	
	public static String getLog(String username,boolean isConnected){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		
		PTS ptsTemp2 = new PTS();
		if(isConnected){
			ptsTemp2.setType("on");
		}else{
			ptsTemp2.setType("off");
		}
		
		ptsTemp2.setValue(username);
		
		ptsTemp.setValue(ptsTemp2);
		return ptsTemp.toString();
	}
	
	public static PTS getLog(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		return ptsTemp;
	}

	protected void start(){
		if(!pts.isValue()){
			switch(pts.getPts().get(0).getType()){
				case "in":
					In in = new In(pts);
					setBroadcast(in.getBroadcast());
					setToCheck(in.getToCheck());
					setToSend(in.getToSend());
				break;

				case "on":
					On on = new On(pts);
					setBroadcast(on.getBroadcast());
					setToCheck(on.getToCheck());
					setToSend(on.getToSend());
				break;

				case "off":
					Off off = new Off(pts);
					setBroadcast(off.getBroadcast());
					setToCheck(off.getToCheck());
					setToSend(off.getToSend());
				break;
			}
		}
	}

}
