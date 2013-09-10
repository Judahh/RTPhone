package util.PTS;

import java.io.IOException;
import java.util.Vector;

import util.PTS.Log.In;
import util.PTS.Log.Log;
import util.PTS.Log.On;
import util.PTS.Log.Status;
import util.TCP.ThreadSingleTCPServer;
import util.TCP.ThreadTCPServer;

public class ThreadPTSServer extends ThreadTCPServer{
	private Vector<String>	resgistered;

	public ThreadPTSServer(int port){
		super(port);
		this.resgistered = new Vector<>();
	}

	public ThreadPTSServer(){
		super(9000);
		this.resgistered = new Vector<>();
	}

	@Override
	protected void addConnection(){
		try{
			ThreadSinglePTSServer ThreadSinglePTSServerA;
			ThreadSinglePTSServerA = new ThreadSinglePTSServer(
					newClientConnection);
			ThreadSinglePTSServerA.start();
			this.threadSingleTCPServer.add(ThreadSinglePTSServerA);
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// OBS: isso fica num loop que roda no ran da classe ThreadTCPServer
	}

	@Override
	protected void check(){
		// TODO: Fazer todas as checagens e broadcasts aqui!!!
		// OBS: isso fica num loop que roda no ran da classe ThreadTCPServer
		checkLogin();
		checkRegister();
		checkBroadcast();
	}

	private void checkBroadcast(){
		for(ThreadSingleTCPServer iterable_element : threadSingleTCPServer){
			if(!iterable_element.getBroadcast().isEmpty()){
				for(ThreadSingleTCPServer iterable_element2 : threadSingleTCPServer){
					try{
						iterable_element2.send(iterable_element.getBroadcast());
					}catch(IOException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				iterable_element.clearBroadcast();
			}
		}
	}

	private void checkRegister(){
		// TODO: Fazer todas as checagens de registros aqui!!!
		for(ThreadSingleTCPServer iterable_element : threadSingleTCPServer){

			for(int index = 0; index < iterable_element.getToCheck().size();){
				if(new On(new PTS(iterable_element.getToCheck().get(0))).isOn()){
					boolean ok = true;
					for(String iterable_element2 : resgistered){
						if(iterable_element.getUsername().equals(
								iterable_element2)){
							try{
								iterable_element.send(Status.getError());
								ok = false;
								break;
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}

					if(ok){
						try{
							resgistered.add(iterable_element.getUsername());
							iterable_element.send(Status.getOk());
						}catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					try{
						iterable_element.send(Status.getOk());
						PTS ptsTemp = Log.getLog();
						Vector<String> on = new Vector<String>();
						for(ThreadSingleTCPServer iterable_element2 : threadSingleTCPServer){
							if(iterable_element2.isConnected()){
								ptsTemp.addValue(On.getOn(
										iterable_element2.getUsername(),
										iterable_element2.isOn()));
								on.add(iterable_element2.getUsername());
							}
						}
						for(String iterable_element2 : resgistered){
							ok = true;
							for(String iterable_element3 : on){
								if(iterable_element2.equals(iterable_element3)){
									ok = false;
									break;
								}
							}
							if(ok){
								ptsTemp.addValue(On.getOn(iterable_element2,
										false));
							}
						}
						iterable_element.send(ptsTemp.toString());
						iterable_element.getToCheck().remove(0);
						iterable_element.setRegister(true);
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}

		}
	}

	private void checkLogin(){
		// TODO: Fazer todas as checagens de login aqui!!!
		for(ThreadSingleTCPServer iterable_element : threadSingleTCPServer){
			if(new In(new PTS(iterable_element.getToCheck())).isIn()){
				for(ThreadSingleTCPServer iterable_element2 : threadSingleTCPServer){
					if(!iterable_element.equals(iterable_element2)){
						if(iterable_element.getUsername().equals(
								iterable_element2.getUsername())){
							try{
								iterable_element.send(Status.getError());
								break;
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}
				}
				try{
					iterable_element.send(Status.getOk());
					PTS ptsTemp = Log.getLog();
					for(ThreadSingleTCPServer iterable_element2 : threadSingleTCPServer){
						if(iterable_element2.isConnected()){
							ptsTemp.addValue(On.getOn(
									iterable_element2.getUsername(),
									iterable_element2.isOn()));
						}else{
							ptsTemp.addValue(On.getOn(
									iterable_element2.getUsername(),
									iterable_element2.isConnected()));
						}
					}
					iterable_element.send(ptsTemp.toString());
					iterable_element.clearToCheck();
					iterable_element.setRegister(true);
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
