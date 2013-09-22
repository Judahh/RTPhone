/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author JH
 */
public interface RMI extends Remote{
   public boolean checkUser(String username, String password, String Address) throws RemoteException;
   public boolean login(String username, String password, String Address) throws RemoteException;
   public boolean logoff(String username, String password) throws RemoteException;
   public boolean register(String username, String password) throws RemoteException;
   
   public String call(String username, String caller) throws RemoteException;
   
   public Vector<String> getRegisteredUsers() throws RemoteException;
   public Vector<String> getLoggedUsers() throws RemoteException;
}
