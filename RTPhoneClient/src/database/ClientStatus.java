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
public enum ClientStatus implements Serializable{
   none(0),
   busy(1),
   away(2),
   custom(3);
   
   public int clientStatus; 
   
   ClientStatus(int value) {
      clientStatus = value; 
   }
}
