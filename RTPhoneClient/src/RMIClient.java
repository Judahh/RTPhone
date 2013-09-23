
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
      CallWindow callWindow = new CallWindow(username);
      callWindow.setVisible(true);
      boolean ok = false;
      while (callWindow.getValue() == -1) {
         int i=0;
         i++;
         i++;
         i++;
         i++;
         i++;
      }
      ok = (callWindow.getValue() == 0);
      if (ok) {
         Phone phone = new Phone(Address, 32766, 16384);
         callWindow.setVisible(false);
         callWindow.dispose();
         callWindow = null;
         return true;
      }
      callWindow.setVisible(false);
      callWindow.dispose();
      callWindow = null;
      return false;
   }
}
