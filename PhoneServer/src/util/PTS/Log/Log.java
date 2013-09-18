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
	
	public static String getOk(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		ptsTemp.setValue(LogStatus.getOk());
		return (ptsTemp.toString());
	}
	
	public static String getError(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		ptsTemp.setValue(LogStatus.getError());
		return (ptsTemp.toString());
	}
	
	public static PTS getLogon(String username,boolean isConnected){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		
		if(isConnected){
			ptsTemp.setValue(On.getLogon(username));
		}else{
			ptsTemp.setValue(Off.getLogoff(username));
		}
		
		return ptsTemp;
	}
	
	public static String getLog(String username,boolean isConnected){
		return getLog().toString();
	}
	
	public static PTS getLogin(String username){
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("log");

		pts.setValue(In.getLogin(username));
		return pts;
	}
	
	public static PTS getLogon(String username){
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("log");

		pts.setValue(On.getLogon(username));
		return pts;
	}
	
	public static PTS getLogoff(String username){
		PTS pts = new PTS();// TODO: colocar isto na classe Log
		pts.setType("log");

		pts.setValue(Off.getLogoff(username));
		return pts;
	}
	
	public static PTS getLog(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		return ptsTemp;
	}

	protected void start(){
		if(!pts.isValue()){
//			System.out.println("received:"+pts.toString());
			switch(pts.getPts().get(0).getType()){
				case "in":
					In in = new In(pts);
					setUsername(in.getUsername());
					addBroadcast(in.getBroadcast());
					addToCheck(in.getToCheck());
					setToSend(in.getToSend());
				break;

				case "on":
					On on = new On(pts);
					setUsername(on.getUsername());
					addBroadcast(on.getBroadcast());
					addToCheck(on.getToCheck());
					setToSend(on.getToSend());
				break;

				case "off":
					Off off = new Off(pts);
					addBroadcast(off.getBroadcast());
					addToCheck(off.getToCheck());
					setToSend(off.getToSend());
				break;
			}
		}
	}

}
