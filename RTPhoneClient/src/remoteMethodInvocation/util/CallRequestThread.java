/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import database.Client;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import realTimeTransportProtocol.Phone;
import view.MainWindow;

/**
 *
 * @author JH
 */
public class CallRequestThread extends RemoteMethodInvocationConnectThread {

   private database.Client me;

   public CallRequestThread(MainWindow mainWindow, Client client, database.Client me) {
      super(mainWindow, client);
      this.me = me;
   }

   @Override
   protected void work() {
      try {
         boolean call = clientRemoteMethodInvocation.call(me);
         try {
            if (call) {
               setAnswer(Answer.yes);
            } else {
               setAnswer(Answer.no);
            }
         } catch (InterruptedException exception) {
            commonError(exception);
         }
         if (call) {
            getMainWindow().setPhone(new Phone(getClient().getAddress(), 16384, 32766));
            getMainWindow().getPhone().start();
            getMainWindow().getjButtonCall().setText("Hang Up");
         } else {
            JOptionPane.showMessageDialog(getMainWindow(), "Connection refused!", "", JOptionPane.INFORMATION_MESSAGE);
         }
      } catch (RemoteException exception) {
         connectError(exception);
      }
   }
}
