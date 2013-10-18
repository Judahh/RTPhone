package remoteMethodInvocation;


import view.OptionThread;
import view.LoginWindow;
import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class Client extends UnicastRemoteObject implements ClientRemoteMethodInvocation {
   
   private OptionThread optionThread;
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
         Registry registry = LocateRegistry.getRegistry(9001);
         //Dá um nome pra ele no registro  
         registry.bind("RTPhoneServer", rmi);
      } catch (AlreadyBoundException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      } catch (AccessException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      } catch (RemoteException Re) {
         System.out.println(Re.getMessage());
      }
   }
   
   @Override
   public void register(String name, String username) throws RemoteException {
      
   }
   
   @Override
   public void login(String name, String username) throws RemoteException {
      
   }
   
   @Override
   public void logoff(String name, String username) throws RemoteException {
      
   }
   
   @Override
   public void changeStatus(String name, String username, ClientStatus status) throws RemoteException {
      
   }
   
   @Override
   public void sendMessage(String name, String username, String message) throws RemoteException {
      
   }
   
   @Override
   public boolean call(String name, String username, String Address) throws RemoteException {
//      try {
//         optionThread = new OptionThread(username);
//         optionThread.start();
//         boolean ok = false;
//         ok = (optionThread.getValue() == 0);
//         System.out.println("F");
//         if (ok) {
//            System.out.println("G");
      if (loginWindow.getMainWindow().getjButtonCall().getText().equals("Call")) {
         loginWindow.getMainWindow().setPhone(new Phone(Address, 32766, 16384));
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
