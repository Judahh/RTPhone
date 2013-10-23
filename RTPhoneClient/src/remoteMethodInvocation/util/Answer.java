/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remoteMethodInvocation.util;

/**
 *
 * @author JH
 */
public enum Answer {
   none(0),
   yes(1),
   no(2),
   cancel(3);
   
   public int answer; 
   
   Answer(int value) {
      answer = value; 
   }
}
