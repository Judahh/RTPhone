
import clientrmi.ClientRMI;
import java.rmi.RemoteException;
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
//         }
//         System.out.println("H");
//         optionThread.close();
//
//      } catch (InterruptedException ex) {
//         System.out.println("Z");
//         Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      System.out.println("I");
//      return false;
   }
}
