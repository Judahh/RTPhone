/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author JH
 */
public class Client {

   private String name;
   private int number;
   private String username;
   private String address;
   private String password;
   private ClientStatus clientStatus;
   private String customStatus;

   public Client() {
   }

   public Client(String name, String username, String address, String password, ClientStatus clientStatus) {
      this.name = name;
      this.username = username;
      this.address = address;
      this.password = password;
      this.clientStatus = clientStatus;
   }

   public Client(String name, String username, String address, String password, String customStatus) {
      this.name = name;
      this.username = username;
      this.address = address;
      this.password = password;
      this.clientStatus = ClientStatus.custom;
      this.customStatus = customStatus;
   }

   public Client(String name, String username, String address, ClientStatus clientStatus) {
      this.name = name;
      this.username = username;
      this.address = address;
      this.clientStatus = clientStatus;
   }

   public Client(String name, String username, String address, String customStatus) {
      this.name = name;
      this.username = username;
      this.address = address;
      this.clientStatus = ClientStatus.custom;
      this.customStatus = customStatus;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public int getNumber() {
      return number;
   }

   public ClientStatus getClientStatus() {
      return clientStatus;
   }

   public void setClientStatus(ClientStatus clientStatus) {
      this.clientStatus = clientStatus;
   }

   public String getCustomStatus() {
      return customStatus;
   }

   public void setCustomStatus(String customStatus) {
      this.customStatus = customStatus;
   }
   
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
