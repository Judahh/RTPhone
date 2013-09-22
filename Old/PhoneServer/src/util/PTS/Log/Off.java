package util.PTS.Log;

import util.PTS.PTS;

public class Off extends Log{

	public Off(PTS pts){
		super(pts);
	}

	public boolean isOff(){
		if(pts.getType().equals("log")){
			if(pts.getPts().get(0).getType().equals("off")){
				return true;
			}
		}
		return false;
	}
	
	public static PTS getLogoff(String username){
		PTS ptsTemp = new PTS();// TODO: colocar isto na classe In que fica
								// dentro da Log
		ptsTemp.setType("off");
		ptsTemp.setValue(username);

		return ptsTemp;
	}
	
	protected void start(){
//		if(pts.getPts().get(0).getValue().equals(this.username)){
//			toSend = (LogStatus.getOk());
//			this.broadcast.add(pts.toString());
//		}
	}
}
