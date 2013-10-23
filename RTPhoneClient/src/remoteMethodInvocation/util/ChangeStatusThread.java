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
public class ChangeStatusThread extends RemoteMethodInvocationConnectThread{

   public ChangeStatusThread(MainWindow mainWindow, Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         clientRemoteMethodInvocation.changeStatus(getMainWindow().getMe());
      } catch (RemoteException exception) {
         commonError(exception);
      }
   }
}
