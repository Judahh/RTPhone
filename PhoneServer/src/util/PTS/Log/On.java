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
	
	public static PTS getLogon(String username){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("on");

		ptsTemp.setValue(username);
		return ptsTemp;
	}

	protected void start(){
		if(!pts.toString().isEmpty()){
			this.toCheck.add(pts.toString());
		}
		if(!pts.getPts().get(0).getValue().isEmpty()){
			this.username = pts.getPts().get(0).getValue();
		}
	}

	public static PTS getLogin(String username){
		PTS ptsTemp = new PTS();// TODO: colocar isto na classe In que fica
								// dentro da Log
		ptsTemp.setType("on");
		ptsTemp.setValue(username);

		return ptsTemp;
	}
}
