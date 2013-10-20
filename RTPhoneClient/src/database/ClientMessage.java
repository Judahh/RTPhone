/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author JH
 */
public class ClientMessage {
   private Client from;
   private Client to;
   private String message;

   public ClientMessage(Client from, String message) {
      this.from = from;
      this.to = null;
      this.message = message;
   }
   
   public ClientMessage(Client from, Client to, String message) {
      this.from = from;
      this.to = to;
      this.message = message;
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
}
