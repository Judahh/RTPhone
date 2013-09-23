
import clientrmi.ClientRMI;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.RTP.Phone;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class RMIClient extends UnicastRemoteObject implements ClientRMI {

   OptionThread optionThread;

   public RMIClient() throws RemoteException {
//      super(Registry.REGISTRY_PORT);
      super();
try {
         //Exporta o objeto remoto  
         ClientRMI rmi = (ClientRMI) UnicastRemoteObject
                 .exportObject(this, 0);
         //Liga o stub do objeto remoto no registro  
         Registry registry = LocateRegistry.getRegistry(9001);
         //Dá um nome pra ele no registro  
         registry.bind("RTPhoneServer", rmi);
      } catch (AlreadyBoundException ex) {
         Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
      } catch (AccessException ex) {
         Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
      } catch (RemoteException Re) {
         System.out.println(Re.getMessage());
      }
   }

   @Override
   public boolean call(String username, String Address) throws RemoteException {
//      try {
//         optionThread = new OptionThread(username);
//         optionThread.start();
//         boolean ok = false;
//         ok = (optionThread.getValue() == 0);
//         System.out.println("F");
//         if (ok) {
//            System.out.println("G");

            Phone phone = new Phone(Address, 32766, 16384);
            phone.start();
            
//            optionThread.close();
            return true;
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
