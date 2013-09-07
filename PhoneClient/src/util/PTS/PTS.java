package util.PTS;

import java.util.Vector;

public class PTS {
	private String type;
	private int size;
	private Vector<PTS> pts;
	private String value;
	
	public PTS() {
		this.pts=new Vector<>();
		this.size=0;
		this.type=new String();
		this.value=new String();
	}
	
	public PTS(String received) {
		this.pts=new Vector<>();
		this.size=0;
		this.type=new String();
		this.value=new String();
		init(received);
	}
	
	public Vector<PTS> getPts() {
		return pts;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isValue(){
		return !value.isEmpty();
	}

	public void init(String received) {
		for (int index = 0; index < received.length(); index++) {
			if(received.charAt(index)=='<'){
				index++;
				for (;(index < received.length())&&(received.charAt(index)!='>'); index++) {
					type+=received.charAt(index);
				}
				
				int start=index+1;
				
				String temporary=received.substring(start);
				String end="<"+type+">";
				
				
				String temporaryA=new String();
				temporaryA=temporary.substring(0, temporary.indexOf(end));
				this.size=received.indexOf(end,start)+end.length();
				
				for (int index2 = 0; index2 < temporaryA.length(); index2++) {
					if(temporaryA.charAt(index2)=='<'){
						PTS newPts=new PTS(temporaryA.substring(index2));
						this.pts.add(newPts);
						index2+=newPts.getSize()-1;
					}else{
						this.value=temporaryA.substring(index2);
						index2=temporaryA.length();
					}
				}
				
				index=received.length();//out
			}
		}
	}
}
