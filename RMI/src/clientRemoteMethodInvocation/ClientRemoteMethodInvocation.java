/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRemoteMethodInvocation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.JButton;

/**
 *
 * @author JH
 */
public interface ClientRemoteMethodInvocation extends Remote {

   public boolean call(String username, String Address) throws RemoteException;

}
