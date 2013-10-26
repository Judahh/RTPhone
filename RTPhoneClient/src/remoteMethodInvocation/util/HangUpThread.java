/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import database.Client;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import realTimeTransportProtocol.Phone;
import view.MainWindow;

/**
 *
 * @author JH
 */
public class HangUpThread extends RemoteMethodInvocationConnectThread {

   public HangUpThread(MainWindow mainWindow, Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         clientRemoteMethodInvocation.hangUp(getMainWindow().getMe());
      } catch (RemoteException exception) {
         connectError(exception);
         try {
            setAnswer(Answer.no);
         } catch (InterruptedException exception1) {
            Logger.getLogger(CallRequestThread.class.getName()).log(Level.SEVERE, null, exception1);
         }
      }
   }
}
