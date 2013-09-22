/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

/**
 *
 * @author JH
 */
public interface ClientRMI extends Remote {

   public boolean call(String username, String Address) throws RemoteException;

}
