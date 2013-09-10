package util.PTS.Log;

import util.PTS.PTS;

public class On extends Log{

	public On(PTS pts){
		super(pts);
	}

	public boolean isOn(){
		if(pts.getType().equals("log")){
			if(pts.getPts().get(0).getType().equals("on")){
				return true;
			}
		}
		return false;
	}

	protected void start(){
		if(!pts.toString().isEmpty()){
			this.toCheck.add(pts.toString());
		}
		if(!pts.getPts().get(0).getValue().isEmpty()){
			this.username = pts.getPts().get(0).getValue();
		}
	}
	
	public static PTS getOn(String username,boolean isConnected){
		PTS ptsTemp2 = new PTS();
		if(isConnected){
			ptsTemp2.setType("on");
		}else{
			ptsTemp2.setType("off");
		}
		ptsTemp2.setValue(username);
		
		return ptsTemp2;
	}
}
