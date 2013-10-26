package remoteMethodInvocation;

import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
import database.ClientMessage;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import realTimeTransportProtocol.Phone;
import view.ChatTabPanel;
import view.LoginWindow;
import view.MainWindow;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class Client extends UnicastRemoteObject implements ClientRemoteMethodInvocation {

   private LoginWindow loginWindow;

   public Client(LoginWindow loginWindow) throws RemoteException {
      super();
      try {
         this.loginWindow = loginWindow;
         ClientRemoteMethodInvocation rmi = (ClientRemoteMethodInvocation) UnicastRemoteObject
                 .exportObject(this, 0);
         Registry registry = LocateRegistry.getRegistry(9000);
         registry.bind("RTPhoneClient", rmi);
      } catch (AlreadyBoundException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      } catch (AccessException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      } catch (RemoteException Re) {
         System.out.println(Re.getMessage());
      }
   }

   @Override
   public synchronized void changeStatus(database.Client client) throws RemoteException {
      boolean found = false;
      for (int index = 0; index < loginWindow.getMainWindow().getContactListModel().size(); index++) {
         database.Client tempClient = (database.Client) loginWindow.getMainWindow().getContactListModel().get(index);
         System.out.println("ME:" + loginWindow.getMainWindow().getMe().getUsername());
         System.out.println("ME:" + loginWindow.getMainWindow().getMe().getAddress());
         System.out.println("ME:" + loginWindow.getMainWindow().getMe().getClientStatus());
         System.out.println("Contact:" + tempClient.getUsername());
         System.out.println("Contact:" + tempClient.getAddress());
         System.out.println("Contact:" + tempClient.getClientStatus());
         if (tempClient.getUsername().equals(client.getUsername())) {
            found = true;
            loginWindow.getMainWindow().getContactListModel().setElementAt(client, index);
            break;
         }
      }
      if (!found) {
         loginWindow.getMainWindow().getContactListModel().addElement(client);
      }
   }

   @Override
   public synchronized void sendMessage(ClientMessage message) throws RemoteException {
      boolean found = false;
      for (int index = 1; index < loginWindow.getMainWindow().getjTabbedPane().getTabCount(); index++) {
         ChatTabPanel tempChatTabPanel = (ChatTabPanel) loginWindow.getMainWindow().getjTabbedPane().getComponentAt(index);
         if (tempChatTabPanel.getClient().getUsername().equals(message.getFrom().getUsername())) {
            found = true;
            loginWindow.getMainWindow().getjTabbedPane().setSelectedIndex(index);
            tempChatTabPanel.append(message);
            break;
         }
      }
      if (!found) {
         loginWindow.getMainWindow().getjTabbedPane().addTab(message.getFrom().toString(), new ChatTabPanel(loginWindow.getMainWindow(), message.getFrom()));
         loginWindow.getMainWindow().getjTabbedPane().setSelectedIndex(loginWindow.getMainWindow().getjTabbedPane().getTabCount() - 1);
         ChatTabPanel tempChatTabPanel = (ChatTabPanel) loginWindow.getMainWindow().getjTabbedPane().getComponentAt(loginWindow.getMainWindow().getjTabbedPane().getTabCount() - 1);
         tempChatTabPanel.append(message);
      }
   }

   @Override
   public synchronized boolean call(database.Client caller) throws RemoteException {
      if (loginWindow.getMainWindow().getjButtonCall().getText().equals("Call")) {
         String requestText = "User \"" + caller.getName() + "\" is calling.";
         int showConfirmDialog = JOptionPane.showConfirmDialog(this.loginWindow.getMainWindow(), requestText, "Information", JOptionPane.INFORMATION_MESSAGE);
         switch (showConfirmDialog) {
            case JOptionPane.YES_OPTION:
               loginWindow.getMainWindow().setPhone(new Phone(caller.getAddress(), 32766, 16384));//tentar o contrario
               loginWindow.getMainWindow().getPhone().start();
               loginWindow.getMainWindow().getjButtonCall().setText("Hang Up");
               return true;
         }

      }
      return false;
   }

   @Override
   public synchronized void hangUp(database.Client client) throws RemoteException {
      if (loginWindow.getMainWindow().getjButtonCall().getText().equals("Hang Up")) {
         loginWindow.getMainWindow().getjButtonCall().setText("Call");
         String requestText = "User \"" + client.getName() + "\" hung up.";
         JOptionPane.showMessageDialog(this.loginWindow.getMainWindow(), requestText, "Information", JOptionPane.INFORMATION_MESSAGE);
      }
   }
   
   @Override
   public synchronized boolean contactRequest(database.Client user) throws RemoteException {
      System.out.println("contactRequestThread!!!!!!!!!!");
      String requestText = "User \"" + user.getName() + "\" wants to add you to his contact list.";
      int showConfirmDialog = JOptionPane.showConfirmDialog(loginWindow.getMainWindow(), requestText, "Information", JOptionPane.INFORMATION_MESSAGE);
      switch (showConfirmDialog) {
         case JOptionPane.YES_OPTION:
            sendContactRequestOK(user);
            return true;

         case JOptionPane.NO_OPTION:
            this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().removeContactRequest(user.getUsername(), loginWindow.getMainWindow().getMe().getUsername());
            return false;
      }
      return false;
   }

   private synchronized void sendContactRequestOK(database.Client client) throws RemoteException {
      if (client.getAddress() != null && !client.getAddress().isEmpty()) {
         try {
            ClientRemoteMethodInvocation rmi;
            Registry registry = LocateRegistry.getRegistry(client.getAddress(), 9000);
            rmi = (ClientRemoteMethodInvocation) registry.lookup("RTPhoneClient");
            rmi.changeStatus(loginWindow.getMainWindow().getMe());
         } catch (NotBoundException exception) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
         } catch (AccessException exception) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
         } catch (RemoteException exception) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
         }
      }
      loginWindow.getDefaultServerConfigurationsWindow().getDatabase().makeContactRequest(loginWindow.getMainWindow().getMe().getUsername(), client.getUsername());
      changeStatus(client);
   }
}
