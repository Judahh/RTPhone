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
					addBroadcast(in.getBroadcast());
					addToCheck(in.getToCheck());
					setToSend(in.getToSend());
				break;

				case "on":
					On on = new On(pts);
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
