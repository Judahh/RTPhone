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

	protected void start(){
		if(!pts.toString().isEmpty()){
			this.toCheck.add(pts.toString());
		}
	}
}
