
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class OptionThread extends javax.swing.JFrame {

   private int value;
   private String username;

   public OptionThread(String username) {
      value = JOptionPane.showConfirmDialog(null, username+" is calling, Anwser?");
   }

   public int getValue() {
      return value;
   }
}
