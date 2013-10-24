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
import java.util.ArrayList;
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
      this.semaphore = new Semaphore(1);
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
      Answer tempAnswer = this.answer;
      this.semaphore.release();
      return tempAnswer;
   }

   protected void setAnswer(Answer answer) throws InterruptedException {
      this.semaphore.acquire();
      this.answer = answer;
      this.semaphore.release();
   }

   protected void connectError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error while connecting to \"" + client.getUsername() + "\":" + exception, "Error", JOptionPane.ERROR_MESSAGE);
      updateLogoffStatus();
   }

   protected void commonError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error:" + exception, "Error", JOptionPane.ERROR_MESSAGE);
      updateLogoffStatus();
   }

   private void updateLogoffStatus() {
      mainWindow.getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().logoff(client.getUsername());
      client.setAddress(null);
      ArrayList<Client> contactList = mainWindow.getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().getContactList(client.getUsername());
      for (int index = 0; index < contactList.size(); index++) {
         System.out.println("ME:" + mainWindow.getMe().getUsername());
         System.out.println("ME:" + mainWindow.getMe().getAddress());
         System.out.println("ME:" + mainWindow.getMe().getClientStatus());
         System.out.println("Contact:" + contactList.get(index).getUsername());
         System.out.println("Contact:" + contactList.get(index).getAddress());
         System.out.println("Contact:" + contactList.get(index).getClientStatus());
         if (contactList.get(index).getAddress().equals(mainWindow.getMe().getAddress())) {
            changeStatus(client);
         } else {
            Client tempClient = contactList.get(index);
            if (tempClient.getAddress() != null && !tempClient.getAddress().isEmpty()) {
               ChangeContactStatusThread changeStatusThread = new ChangeContactStatusThread(mainWindow, client);
               changeStatusThread.start();
            }
         }
      }
   }

   public void changeStatus(database.Client client) {
      boolean found = false;
      for (int index = 0; index < mainWindow.getContactListModel().size(); index++) {
         database.Client tempClient = (database.Client) mainWindow.getContactListModel().get(index);
         if (tempClient.getUsername().equals(client.getUsername())) {
            found = true;
            mainWindow.getContactListModel().setElementAt(client, index);
            break;
         }
      }
      if (!found) {
         mainWindow.getContactListModel().addElement(client);
      }
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
