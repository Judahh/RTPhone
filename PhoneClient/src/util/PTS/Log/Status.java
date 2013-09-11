package util.PTS.Log;

import util.PTS.PTS;

public class Status extends Log{
	public Status(){
		super();
	}
	
	public static String getOk(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		ptsTemp.setValue("ok");
		return (ptsTemp.toString());
	}
	
	public static String getError(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("log");
		ptsTemp.setValue("error");
		return (ptsTemp.toString());
	}
}
