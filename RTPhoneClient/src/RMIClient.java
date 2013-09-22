
import clientrmi.ClientRMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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

   public RMIClient() throws RemoteException {
      super();
   }

   @Override
   public boolean call(String username, String Address) throws RemoteException {
      if(JOptionPane.showConfirmDialog(null, username+" is calling, Anwser?")==0){
         Phone phone = new Phone(Address, 32766, 16384);
         return true;
      }
      return false;
   }
}
