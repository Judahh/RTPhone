/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRemoteMethodInvocation;

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

   public void register(String name, String username) throws RemoteException;
   
   public void login(String name, String username) throws RemoteException;
   
   public void logoff(String name, String username) throws RemoteException;
   
   public void changeStatus(String name, String username, ClientStatus status) throws RemoteException;
   
   public void sendMessage(String name, String username, String message) throws RemoteException;
   
   public boolean call(String name, String username, String Address) throws RemoteException;

}
