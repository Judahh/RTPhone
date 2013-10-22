/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;

/**
 *
 * @author JH
 */
public class ClientMessage  implements Serializable{
   private Client from;
   private Client to;
   private String message;
   private int index;

   public ClientMessage(Client from, String message) {
      this.index = -1;
      this.from = from;
      this.to = null;
      this.message = message;
   }
   
   public ClientMessage(Client from, Client to, String message) {
      this.index = -1;
      this.from = from;
      this.to = to;
      this.message = message;
   }
   
   public ClientMessage(int index ,Client from, String message) {
      this.index = index;
      this.from = from;
      this.to = null;
      this.message = message;
   }
   
   public ClientMessage(int index ,Client from, Client to, String message) {
      this.index = index;
      this.from = from;
      this.to = to;
      this.message = message;
   }

   public int getIndex() {
       return index;
   }
   
   public Client getFrom() {
      return from;
   }

   public Client getTo() {
      return to;
   }

   public String getMessage() {
      return message;
   }

   @Override
   public String toString() {
      String message=from.getName()+":\n";
      message+=this.message+"\n\n";
      return message;
   }
}
