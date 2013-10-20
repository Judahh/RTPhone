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
   private Client client;
   private String message;

   public ClientMessage(Client client, String message) {
      this.client = client;
      this.message = message;
   }

   public Client getClient() {
      return client;
   }

   public String getMessage() {
      return message;
   }
}
