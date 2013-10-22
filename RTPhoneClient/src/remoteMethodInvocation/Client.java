package remoteMethodInvocation;

import view.LoginWindow;
import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
import database.ClientMessage;
import database.ClientStatus;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import realTimeTransportProtocol.Phone;
import view.ChatTabPanel;

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
//      super(Registry.REGISTRY_PORT);
      super();
      try {
         this.loginWindow = loginWindow;
         //Exporta o objeto remoto  
         ClientRemoteMethodInvocation rmi = (ClientRemoteMethodInvocation) UnicastRemoteObject
                 .exportObject(this, 0);
         //Liga o stub do objeto remoto no registro  
         Registry registry = LocateRegistry.getRegistry(9000);
         //Dá um nome pra ele no registro  
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
   public void changeStatus(database.Client client) throws RemoteException {
      boolean found=false;
      for (int index = 0; index < loginWindow.getMainWindow().getContactListModel().size(); index++) {
         database.Client tempClient =(database.Client) loginWindow.getMainWindow().getContactListModel().get(index);
         if(tempClient.getUsername().equals(client.getUsername())){
            found=true;
            loginWindow.getMainWindow().getContactListModel().setElementAt(client, index);
            break;
         }
      }
      if(!found){
         loginWindow.getMainWindow().getContactListModel().addElement(client);
      }
   }
   
   @Override
   public void sendMessage(ClientMessage message) throws RemoteException {
      boolean found = false;
      for (int index = 1; index < loginWindow.getMainWindow().getjTabbedPane().getTabCount(); index++) {
         ChatTabPanel tempChatTabPanel = (ChatTabPanel) loginWindow.getMainWindow().getjTabbedPane().getTabComponentAt(index);
         if (tempChatTabPanel.getClient().getUsername().equals(message.getFrom().getUsername())) {
            found = true;
            loginWindow.getMainWindow().getjTabbedPane().setSelectedIndex(index);
            tempChatTabPanel.append(message);
            break;
         }
      }
      if (!found) {
         loginWindow.getMainWindow().getjTabbedPane().addTab(message.getFrom().toString(), new ChatTabPanel(loginWindow.getMainWindow(), message.getFrom()));
         loginWindow.getMainWindow().getjTabbedPane().setSelectedIndex(loginWindow.getMainWindow().getjTabbedPane().getTabCount()-1);
         ChatTabPanel tempChatTabPanel = (ChatTabPanel) loginWindow.getMainWindow().getjTabbedPane().getTabComponentAt(loginWindow.getMainWindow().getjTabbedPane().getTabCount()-1);
         tempChatTabPanel.append(message);
      }
   }
   
   @Override
   public boolean call(database.Client caller) throws RemoteException {
//      try {
//         optionThread = new OptionThread(username);
//         optionThread.start();
//         boolean ok = false;
//         ok = (optionThread.getValue() == 0);
//         System.out.println("F");
//         if (ok) {
//            System.out.println("G");
      if (loginWindow.getMainWindow().getjButtonCall().getText().equals("Call")) {
         loginWindow.getMainWindow().setPhone(new Phone(caller.getAddress(), 32766, 16384));//tentar o contrario
         loginWindow.getMainWindow().getPhone().start();
//         Phone phone = new Phone(Address, 32766, 16384);
//         phone.start();
         loginWindow.getMainWindow().getjButtonCall().setText("Hang Up");
//            optionThread.close();
         return true;
      }
      return false;
//
//         }
//         System.out.println("H");
//        optionThread.close();
//
//      } catch (InterruptedException ex) {
//         System.out.println("Z");
//         Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      System.out.println("I");
//      return false;
   }
}
