package util.PTS.Call;

import util.PTS.PTS;

public class CallStatus extends Call{
	public CallStatus(){
		super();
	}
	
	public CallStatus(PTS pts){
		super(pts);
	}
	
	public static PTS getError(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("status");
		ptsTemp.setValue("busy");
		return ptsTemp;
	}
	
	public static PTS getOk(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("status");
		ptsTemp.setValue("ok");
		return ptsTemp;
	}
}
