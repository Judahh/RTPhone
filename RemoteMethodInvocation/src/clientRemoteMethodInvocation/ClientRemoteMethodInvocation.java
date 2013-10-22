/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRemoteMethodInvocation;

import database.Client;
import database.ClientMessage;
import database.ClientStatus;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.JButton;

/**
 *
 * @author JH
 */
public interface ClientRemoteMethodInvocation extends Remote {
   
   public void changeStatus(Client client) throws RemoteException;
   
   public void sendMessage(ClientMessage message) throws RemoteException;
   
   public boolean call(Client client) throws RemoteException;

}
