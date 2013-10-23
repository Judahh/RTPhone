/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
import database.Client;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;
import view.MainWindow;

/**
 *
 * @author JH
 */
public class RemoteMethodInvocationConnectThread extends Thread {

   private database.Client client;
   private MainWindow mainWindow;
   protected ClientRemoteMethodInvocation clientRemoteMethodInvocation;
   private Answer answer;
   private Semaphore semaphore;

   public RemoteMethodInvocationConnectThread(MainWindow mainWindow, Client client) {
      this.client = client;
      this.mainWindow = mainWindow;
      this.answer = Answer.none;
      this.semaphore=new Semaphore(1);
   }

   protected MainWindow getMainWindow() {
      return mainWindow;
   }

   protected Client getClient() {
      return client;
   }
   
   @Override
   public void run() {
      connect();
      work();
   }

   public Answer getAnswer() throws InterruptedException {
      this.semaphore.acquire();
      Answer tempAnswer=this.answer;
      this.semaphore.release();
      return tempAnswer;
   }

   protected void setAnswer(Answer answer) throws InterruptedException {
      this.semaphore.acquire();
      this.answer = answer;
      this.semaphore.release();
   }
   
   protected void connectError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error while connecting to \""+client.getUsername()+"\":"+exception, "Error", JOptionPane.ERROR_MESSAGE);
   }
   
   protected void commonError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error:"+exception, "Error", JOptionPane.ERROR_MESSAGE);
      mainWindow.getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().logoff(client.getUsername());
   }

   private void connect() {
      try {
         Registry registry = LocateRegistry.getRegistry(client.getAddress(), 9000);
         clientRemoteMethodInvocation = (ClientRemoteMethodInvocation) registry.lookup("RTPhoneClient");
      } catch (NotBoundException | RemoteException exception) {
         connectError(exception);
      }
   }

   protected void work() {
      throw new UnsupportedOperationException("Not implemented");
   }
}
