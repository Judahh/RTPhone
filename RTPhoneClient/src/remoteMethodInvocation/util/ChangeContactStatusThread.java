/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import database.Client;
import java.rmi.RemoteException;
import view.MainWindow;

/**
 *
 * @author JH
 */
public class ChangeContactStatusThread extends RemoteMethodInvocationConnectThread{

   public ChangeContactStatusThread(MainWindow mainWindow, Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         clientRemoteMethodInvocation.changeStatus(getClient());
      } catch (RemoteException exception) {
         commonError(exception);
      }
   }
}
