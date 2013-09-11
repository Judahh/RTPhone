package util.PTS;

import java.io.IOException;
import java.util.Vector;

import util.PTS.Log.In;
import util.PTS.Log.Log;
import util.PTS.Log.On;
import util.TCP.ThreadSingleTCPServer;
import util.TCP.ThreadTCPChecker;
import util.TCP.ThreadTCPServer;

public class ThreadPTSChecker extends ThreadTCPChecker{

	public ThreadPTSChecker(ThreadTCPServer threadTCPServer){
		super(threadTCPServer);
	}

	public ThreadPTSChecker(ThreadPTSServer threadPTSServer){
		super(threadPTSServer);
	}

	protected void check(){
		// TODO: Fazer todas as checagens e broadcasts aqui!!!
		// OBS: isso fica num loop que roda no ran da classe ThreadTCPServer
//		System.out.println("check");
		checkLogin();
		checkRegister();
		checkBroadcast();
		clean();
	}

	synchronized private void clean(){
		// TODO Auto-generated method stub
		for(int index = 0; index < this.threadTCPServer.getThreadSingleTCPServer().size(); index++){
			if(!this.threadTCPServer.getThreadSingleTCPServer().get(index).isConnected()){
				this.threadTCPServer.getThreadSingleTCPServer().remove(index);
				index = 0;
			}
		}
	}

	synchronized private void checkBroadcast(){
		for(ThreadSingleTCPServer iterable_element : this.threadTCPServer.getThreadSingleTCPServer()){
			for(int index = 0; index < iterable_element.getBroadcast().size();){
				for(ThreadSingleTCPServer iterable_element2 : this.threadTCPServer.getThreadSingleTCPServer()){
					try{
						iterable_element2.send(iterable_element.getBroadcast()
								.get(0));
					}catch(IOException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				iterable_element.getBroadcast().remove(0);
			}
		}
	}

	synchronized private void checkRegister(){
		// TODO: Fazer todas as checagens de registros aqui!!!
		for(ThreadSingleTCPServer iterable_element : this.threadTCPServer.getThreadSingleTCPServer()){

			for(int index = 0; index < iterable_element.getToCheck().size();){
				if(new On(new PTS(iterable_element.getToCheck().get(0))).isOn()){
					boolean ok = true;
					for(String iterable_element2 : this.threadTCPServer.getResgistered()){
						if(iterable_element.getUsername().equals(
								iterable_element2)){
							try{
								iterable_element.send(Log.getError());
								System.out.println("erro");
								ok = false;
								break;
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}

					if(ok){
						try{
							iterable_element.setRegister(true);
							this.threadTCPServer.getResgistered().add(iterable_element.getUsername());
							iterable_element.send(Log.getOk());
							System.out.println("ok");
						}catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					iterable_element.getToCheck().remove(0);

				}
			}

		}
	}

	synchronized private void checkLogin(){
		// TODO: Fazer todas as checagens de login aqui!!!
		for(ThreadSingleTCPServer iterable_element : this.threadTCPServer.getThreadSingleTCPServer()){
			for(int index = 0; index < iterable_element.getToCheck().size();){
				if(new In(new PTS(iterable_element.getToCheck().get(0))).isIn()){
					boolean ok = false;

					for(String iterable_element2 : this.threadTCPServer.getResgistered()){
						if(iterable_element.getUsername().equals(
								iterable_element2)){
							ok = true;
						}
					}

					if(!ok){
						try{
							iterable_element.send(Log.getError());// não
																		// registrado
						}catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					for(String iterable_element2 : this.threadTCPServer.getLogged()){
						if(!iterable_element.equals(iterable_element2)){
							if(iterable_element.getUsername().equals(
									iterable_element2)){
								try{
									iterable_element.send(Log.getError());// já
																				// loggado
									ok = false;
									break;
								}catch(IOException e){
									e.printStackTrace();
								}
							}
						}
					}

					if(ok){
						try{
							iterable_element.setLogin(true);
							this.threadTCPServer.getLogged().add(iterable_element.getUsername());
							iterable_element.send(Log.getOk());
						}catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					try{
						iterable_element.send(Log.getOk());
						PTS ptsTemp = Log.getLog();
						Vector<String> on = new Vector<String>();
						for(ThreadSingleTCPServer iterable_element2 : this.threadTCPServer.getThreadSingleTCPServer()){
							if(iterable_element2.isConnected()){
								ptsTemp.addValue(Log.getLogon(
										iterable_element2.getUsername(),
										iterable_element2.isOn()));
								on.add(iterable_element2.getUsername());
							}
						}
						for(String iterable_element2 : this.threadTCPServer.getResgistered()){
							ok = true;
							for(String iterable_element3 : on){
								if(iterable_element2.equals(iterable_element3)){
									ok = false;
									break;
								}
							}
							if(ok){
								ptsTemp.addValue(Log.getLogon(iterable_element2,
										false));
							}
						}
						iterable_element.send(ptsTemp.toString());
						iterable_element.getToCheck().remove(0);
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
