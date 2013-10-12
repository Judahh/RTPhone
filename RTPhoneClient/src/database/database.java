/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author JH
 */
public class database {

   protected String url;
   protected String port;
   protected String name;
   protected String user;
   protected String password;
   protected Connection connection;
   protected Statement statement;
   protected ResultSet resultSet;

   public database(String url, int port, String name, String user, String password) {
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
   
   public boolean exists(String username){//TODO:
      return false;
   }
   
   public ArrayList<Client> getLoggedList(String username, String password){//TODO:
      return null;
   }
   
   public void register(String username, String name, String password){//TODO:
      
   }
   
   public void login(String username, String password){//TODO:
      
   }
   
   public void logoff(String username){//TODO:
      
   }
}
