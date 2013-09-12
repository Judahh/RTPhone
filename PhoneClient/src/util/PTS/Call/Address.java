package util.PTS.Call;

import util.PTS.PTS;

public class Address extends Call{
	public Address(){
		super();
	}

	public Address(PTS pts){
		super(pts);
	}
	
	public boolean isAddress(){
		if(pts.getType().equals("log")){
			if(pts.getPts().get(0).getType().equals("address")){
				return true;
			}
		}
		return false;
	}
}
