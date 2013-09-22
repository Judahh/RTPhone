package util.PTS.Call;

import util.PTS.PTS;

public class User extends Call{
	private String user;
	
	public User(){
		super();
	}

	public User(PTS pts){
		super(pts);
	}
	
	public User(String user){
		super();
		this.user=user;
	}
	
	public PTS getUser(){
		PTS tempPTS= new PTS();
		tempPTS.setType("user");
		tempPTS.setValue(this.user);
		return tempPTS;
	}
	
	static public PTS user(String user){
		PTS tempPTS= new PTS();
		tempPTS.setType("user");
		tempPTS.setValue(user);
		return tempPTS;
	}
	
	public boolean isAddressUser(){
		if(pts.getType().equals("log")){
			if(pts.getPts().get(1).getType().equals("user")){
				return true;
			}
		}
		return false;
	}
	
	protected void start(){
		// TODO Auto-generated method stub
		if(!pts.toString().isEmpty()){
			this.toCheck.add(pts.toString());
		}
	}
}
