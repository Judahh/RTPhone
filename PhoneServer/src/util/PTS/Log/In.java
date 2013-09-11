package util.PTS.Log;

import util.PTS.PTS;

public class In extends Log{

	public In(PTS pts){
		super(pts);
	}
	
	public boolean isIn(){
		if(pts.getType().equals("log")){
			if(pts.getPts().get(0).getType().equals("in")){
				return true;
			}
		}
		return false;
	}
	
	public static PTS getLogin(String username){
		PTS ptsTemp = new PTS();// TODO: colocar isto na classe In que fica
								// dentro da Log
		ptsTemp.setType("in");
		ptsTemp.setValue(username);

		return ptsTemp;
	}

	protected void start(){
		if(!pts.toString().isEmpty()){
			this.toCheck.add(pts.toString());
		}
	}
}
