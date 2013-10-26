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
public class CallRequestThread extends RemoteMethodInvocationConnectThread {

   public CallRequestThread(MainWindow mainWindow, Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         boolean call = clientRemoteMethodInvocation.call(getMainWindow().getMe());
         try {
            if (call) {
               setAnswer(Answer.yes);
               getMainWindow().setPhone(new Phone(getClient().getAddress(), 16384, 32766));
               getMainWindow().getPhone().start();
               getMainWindow().getjButtonCall().setText("Hang Up");
               getMainWindow().setContact(getClient());
            } else {
               setAnswer(Answer.no);
               JOptionPane.showMessageDialog(getMainWindow(), "Connection refused!", "", JOptionPane.INFORMATION_MESSAGE);
            }
         } catch (InterruptedException exception) {
            commonError(exception);
         }
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
