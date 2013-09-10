package util.PTS.Log;

import util.PTS.PTS;

public class Off extends Log{

	public Off(PTS pts){
		super(pts);
	}

	protected void start(){
		if(pts.getPts().get(0).getValue().equals(this.username)){
			toSend = (Status.getOk());
			this.broadcast.add(pts.toString());
		}
	}
}
