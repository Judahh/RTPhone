
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class OptionThread extends Thread {

   private String username;
   private CallWindow callWindow;

   public OptionThread(String username) {
      this.username = username;
   }

   public int getValue() throws InterruptedException{
      return callWindow.getValue();
   }

   public void close() {
      callWindow.setVisible(false);
      callWindow.dispose();
      callWindow = null;
   }

   @Override
   public void run() {
      this.callWindow = new CallWindow(username);
      callWindow.setVisible(true);
   }
}
