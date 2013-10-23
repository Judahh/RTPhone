/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import java.rmi.RemoteException;
import view.MainWindow;

/**
 *
 * @author Judah
 */
public class ContactRequestThread extends RemoteMethodInvocationConnectThread {

   public ContactRequestThread(MainWindow mainWindow, database.Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         boolean contactRequest = clientRemoteMethodInvocation.contactRequest(getMainWindow().getMe());
         try {
            if (contactRequest) {
               setAnswer(Answer.yes);
            } else {
               setAnswer(Answer.no);
            }
         } catch (InterruptedException exception) {
            commonError(exception);
         }
      } catch (RemoteException exception) {
         connectError(exception);
      }
   }
}
