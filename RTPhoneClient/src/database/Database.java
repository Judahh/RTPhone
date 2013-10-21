/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author JH
 */
public class Database {

   protected String url;
   protected String port;
   protected String name;
   protected String user;
   protected String password;
   protected Connection connection;
   protected Statement statement;
   protected ResultSet resultSet;

   public Database(String url, int port, String name, String user, String password) {
      this.url = url;
      this.port = Integer.toString(port);
      this.name = name;
      this.user = user;
      this.password = password;
      this.connection = null;
      this.statement = null;
      this.resultSet = null;
   }

   public String getUrl() {
      return url;
   }

   public String getPort() {
      return port;
   }

   public String getName() {
      return name;
   }

   public String getUser() {
      return user;
   }

   public String getPassword() {
      return password;
   }

   public Connection getConnection() {
      return connection;
   }

   public Statement getStatement() {
      return statement;
   }

   public ResultSet getResultSet() {
      return resultSet;
   }

   public boolean exists(String username) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         System.out.println(username);
         String query = "select * from userAuthenticationTable where username='" + username + "';";
         resultSet = statement.executeQuery(query);
         return (resultSet.next());
      } catch (ClassNotFoundException | SQLException exception) {
         System.err.println(exception);
      }
      return false;
   }

   public boolean exists(String username, String password) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         System.out.println(username);
         System.out.println(password);
         String query = "select * from userAuthenticationTable where username='" + username + "' and password='" + password + "';";
         System.out.println(query);
         resultSet = statement.executeQuery(query);
         if (resultSet.next()) {
            return true;
         }
      } catch (ClassNotFoundException | SQLException exception) {
         System.out.println(exception);
      }
      return false;
   }
   
   public void updateStatus(Client client){
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         String query = "UPDATE `RTPhoneDatabase`.`userStatusTable` SET `address`='"+client.getAddress()+"' and `status`='"+client.getClientStatusValue()+"' and `customStatus`='"+client.getCustomStatus()+"' WHERE `username`='" + client.getUsername() + "';";
         System.out.println(query);
         statement.executeUpdate(query);
      } catch (Exception exception) {
         System.out.println(exception);
      }
   }
   
   public void updateAddress(String username, String address){
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         String query = "UPDATE `RTPhoneDatabase`.`userStatusTable` SET `address`='"+address+"' WHERE `username`='" + username + "';";
         System.out.println(query);
         statement.executeUpdate(query);
      } catch (Exception exception) {
         System.out.println(exception);
      }
   }

   public Client getUser(String username) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         String query = "select C.username, C.name, D.address, D.status, D.customStatus, E.number from "
                 + "(SELECT username "
                 + "FROM RTPhoneDatabase.userAuthenticationTable "
                 + "where username = 'user0') G "
                 + "INNER JOIN RTPhoneDatabase.userInformationTable C "
                 + "ON G.username = C.username "
                 + "INNER JOIN RTPhoneDatabase.userStatusTable D "
                 + "ON G.username = D.username "
                 + "INNER JOIN RTPhoneDatabase.userTable E "
                 + "ON G.username = E.username;";
         System.out.println(query);
         resultSet = statement.executeQuery(query);
         if (resultSet.next()) {
            String tempUsername = resultSet.getString("username");
            String tempName = resultSet.getString("name");
            String tempAddress = resultSet.getString("address");
            int tempStatus = resultSet.getInt("status");
            String tempCustomStatus = resultSet.getString("customStatus");
            int tempNumber = resultSet.getInt("number");
            Client tempClient;
            if (tempStatus == 3) {
               tempClient = new Client(tempName, tempUsername, tempAddress, tempCustomStatus);
            } else {
               ClientStatus tempClientStatus = ClientStatus(tempStatus);
               tempClient = new Client(tempName, tempUsername, tempAddress, tempClientStatus);
            }
            return tempClient;
         }
      } catch (ClassNotFoundException | SQLException exception) {
         System.err.println(exception);
      }
      return null;
   }

   public ArrayList<Client> getContactList(String username) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      ArrayList<Client> client = new ArrayList<>();
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         String query = "select C.username, C.name, D.address, D.status, D.customStatus, E.number from "
                 + "(SELECT A.contact "
                 + "FROM RTPhoneDatabase.contactTable A "
                 + "INNER JOIN RTPhoneDatabase.contactTable B "
                 + "ON A.user = B.contact and A.contact = B.user "
                 + "where A.user = '" + username + "') G "
                 + "INNER JOIN RTPhoneDatabase.userInformationTable C "
                 + "ON G.contact = C.username "
                 + "INNER JOIN RTPhoneDatabase.userStatusTable D "
                 + "ON G.contact = D.username "
                 + "INNER JOIN RTPhoneDatabase.userTable E "
                 + "ON G.contact = E.username;";
         System.out.println(query);
         resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            String tempUsername = resultSet.getString("username");
            String tempName = resultSet.getString("name");
            String tempAddress = resultSet.getString("address");
            int tempStatus = resultSet.getInt("status");
            String tempCustomStatus = resultSet.getString("customStatus");
            int tempNumber = resultSet.getInt("number");
            Client tempClient;
            if (tempStatus == 3) {
               tempClient = new Client(tempName, tempUsername, tempAddress, tempCustomStatus);
            } else {
               ClientStatus tempClientStatus = ClientStatus(tempStatus);
               tempClient = new Client(tempName, tempUsername, tempAddress, tempClientStatus);
            }
            client.add(tempClient);
         }
      } catch (ClassNotFoundException | SQLException exception) {
         System.err.println(exception);
      }
      return client;
   }

   public ArrayList<ClientMessage> getMessageList(String to) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      ArrayList<ClientMessage> clientMessage = new ArrayList<>();
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         String query = "SELECT C.username, C.name, D.address, D.status, D.customStatus, E.number, G.message FROM "
                 + "(SELECT `from`,`message` "
                 + "FROM RTPhoneDatabase.messageTable "
                 + "where `to` = 'user0') G "
                 + "INNER JOIN RTPhoneDatabase.userInformationTable C "
                 + "ON G.`to` = C.username "
                 + "INNER JOIN RTPhoneDatabase.userStatusTable D "
                 + "ON G.`to` = D.username "
                 + "INNER JOIN RTPhoneDatabase.userTable E "
                 + "ON G.`to` = E.username;";
         System.out.println(query);
         resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            String tempUsername = resultSet.getString("username");
            String tempName = resultSet.getString("name");
            String tempAddress = resultSet.getString("address");
            int tempStatus = resultSet.getInt("status");
            String tempCustomStatus = resultSet.getString("customStatus");
            int tempNumber = resultSet.getInt("number");
            Client tempClient;
            String tempMessage = resultSet.getString("message");
            if (tempStatus == 3) {
               tempClient = new Client(tempName, tempUsername, tempAddress, tempCustomStatus);
            } else {
               ClientStatus tempClientStatus = ClientStatus(tempStatus);
               tempClient = new Client(tempName, tempUsername, tempAddress, tempClientStatus);
            }
            clientMessage.add(new ClientMessage(tempClient, tempMessage));
         }
      } catch (ClassNotFoundException | SQLException exception) {
         System.err.println(exception);
      }
      return clientMessage;
   }

   public void removeMessageList(String from, String to) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         String query = "DELETE FROM RTPhoneDatabase.messageTable WHERE `to`='" + to + "' and `from`='" + from + "';";//colocar para pegar os amigos logados
         System.out.println(query);
         resultSet = statement.executeQuery(query);
      } catch (ClassNotFoundException | SQLException exception) {
         System.out.println(exception);
      }
   }

   public ArrayList<Client> getContactRequestList(String username) {//TODO: para o login
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
//         name,username,address,clientStatus,customStatus;
//
//         SELECT A.contact
//         FROM RTPhoneDatabase.contactTable A
//         LEFT JOIN RTPhoneDatabase.contactTable B
//         ON A.user = B.contact and A.contact = B.user
//         where A.user = 'user0'

         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         System.out.println(username);
         String query = "select * from login where username='" + username + "' and (logged is null or logged is NULL or logged=0 or logged='');";//colocar para pegar os amigos logados
         System.out.println(query);
         resultSet = statement.executeQuery(query);
//         return (resultSet.next());
      } catch (ClassNotFoundException | SQLException exception) {
         System.out.println(exception);
      }
      return null;
   }

   public void register(String username, String name, String password) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      //String dbUrl = "jdbc:mysql://" + this.url + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         System.out.println(dbUrl);
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         String query = "INSERT INTO userTable (`username`) VALUES ('" + username + "');";
         System.out.println(query);
         statement.executeUpdate(query);

         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         query = "INSERT INTO userAuthenticationTable (`username`,`password`) VALUES ('" + username + "','" + password + "');";
         System.out.println(query);
         statement.executeUpdate(query);

         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         query = "INSERT INTO userInformationTable (`username`,`name`) VALUES ('" + username + "','" + name + "');";
         System.out.println(query);
         statement.executeUpdate(query);

         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         query = "INSERT INTO userStatusTable (`username`) VALUES ('" + username + "');";
         System.out.println(query);
         statement.executeUpdate(query);
//         startServerWindow.getUpdateRegisteredUsers().addElement(username);
      } catch (ClassNotFoundException | SQLException exception) {
         System.out.println(exception);
      }
   }

   public void remove(String username) {//TODO: REFAZER
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         String query = "INSERT INTO `RTPhoneDatabase`.`login` (`user_id`, `password`) VALUES ('" + username + "', '" + password + "');";
         System.out.println(query);
         statement.executeUpdate(query);

//         startServerWindow.getUpdateRegisteredUsers().addElement(username);
      } catch (Exception exception) {
         System.out.println(exception);
      }
   }

   public boolean login(String username, String password) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(dbUrl);
         statement = connection.createStatement();
         System.out.println(username);
         System.out.println(password);
         String query = "select * from userStatusTable where username='" + username + "' and (address is null or address is NULL or address=0 or address='');";
         System.out.println(query);
         resultSet = statement.executeQuery(query);
         if (resultSet.next()) {
            return true;
         }
      } catch (ClassNotFoundException | SQLException exception) {
         System.out.println(exception);
      }
      return false;
   }

   public void logoff(String username) {
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
         String query = "UPDATE `RTPhoneDatabase`.`userStatusTable` SET `address`=null WHERE `username`='" + username + "';";
         System.out.println(query);
         statement.executeUpdate(query);
      } catch (Exception exception) {
         System.out.println(exception);
      }
   }

   private ClientStatus ClientStatus(int tempStatus) {
      switch (tempStatus) {
         case 1:
            return ClientStatus.busy;

         case 2:
            return ClientStatus.away;

         case 3:
            return ClientStatus.custom;

         default:
            return ClientStatus.none;
      }
   }
}
