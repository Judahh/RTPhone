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
   none,
   busy,
   away,
   custom
}
