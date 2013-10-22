/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRemoteMethodInvocation;

import database.Client;
import database.ClientMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author JH
 */
public interface ClientRemoteMethodInvocation extends Remote {
   
   public void changeStatus(Client client) throws RemoteException;
   
   public void sendMessage(ClientMessage message) throws RemoteException;
   
   public boolean contactRequest(Client user) throws RemoteException;
   
   public boolean call(Client client) throws RemoteException;

}
