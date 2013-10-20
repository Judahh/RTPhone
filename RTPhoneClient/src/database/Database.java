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
      } catch (ClassNotFoundException | SQLException e) {
         System.out.println(e);
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
      } catch (ClassNotFoundException | SQLException e) {
         System.out.println(e);
      }
      return false;
   }

   public ArrayList<Client> getAddContactList(String username) {//TODO: para o login
//      SELECT A.contact
//      FROM RTPhoneDatabase.contactTable A
//      LEFT JOIN RTPhoneDatabase.contactTable B
//      ON A.user = B.contact and A.contact = B.user
//      where A.user = 'user0'
      return null;
   }
   
   public ArrayList<Client> getUserList(String username) {//TODO: para o login
      String dbUrl = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name + "?user=" + this.user + "&password=" + this.password;
      try {
//         name,username,address,clientStatus,customStatus;
//
//         SELECT A.contact
//         FROM RTPhoneDatabase.contactTable A
//         INNER JOIN RTPhoneDatabase.contactTable B
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
      } catch (ClassNotFoundException | SQLException e) {
         System.out.println(e);
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
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   public void remove(String username) {//TODO: REFAZER
      try {
         String query = "INSERT INTO `RTPhoneDatabase`.`login` (`user_id`, `password`) VALUES ('" + username + "', '" + password + "');";
         System.out.println(query);
         statement.executeUpdate(query);

//         startServerWindow.getUpdateRegisteredUsers().addElement(username);
      } catch (Exception e) {
         System.out.println(e);
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
      } catch (Exception e) {
         System.out.println(e);
      }
      return false;
   }

   public void logoff(String username) {//TODO:
      try {
         String query = "UPDATE `RTPhoneDatabase`.`login` SET `logged`=null WHERE `user_id`='" + username + "'";
         System.out.println(query);
         statement.executeUpdate(query);
//         startServerWindow.getUpdateLoggedUsers();
//         for (int index = 0; index < startServerWindow.getUpdateLoggedUsers().getSize(); index++) {
//            if (startServerWindow.getUpdateLoggedUsers().getElementAt(index).equals(username)) {
//               startServerWindow.getUpdateLoggedUsers().removeElementAt(index);
//            }
//         }
      } catch (Exception e) {
         System.out.println(e);
      }
   }
}
