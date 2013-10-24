/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import database.Client;
import database.ClientMessage;
import java.rmi.RemoteException;
import javax.swing.JTextArea;
import view.MainWindow;

/**
 *
 * @author JH
 */
public class SendMessageThread extends RemoteMethodInvocationConnectThread{

   private JTextArea jTextAreaSend;
   
   public SendMessageThread(MainWindow mainWindow, Client client, JTextArea jTextAreaSend) {
      super(mainWindow, client);
      this.jTextAreaSend=jTextAreaSend;
   }

   @Override
   protected void work() {
      ClientMessage clientMessage = new ClientMessage(getMainWindow().getMe(), getClient(), jTextAreaSend.getText());
      if (getClient().getAddress() == null || getClient().getAddress().isEmpty()) {
         getMainWindow().getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().makeMessage(clientMessage);
      } else {
         try {
            clientRemoteMethodInvocation.sendMessage(clientMessage);
         } catch (RemoteException exception) {
            commonError(exception);
         }
      }
      jTextAreaSend.setText("");
   }
}
