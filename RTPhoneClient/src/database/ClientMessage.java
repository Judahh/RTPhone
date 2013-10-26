/*
 * Copyright (C) 2013 Judah Holanda Correia Lima <judahholanda7@gmail.com>.
 *
 * All Rights Reserved.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * 
 * Copyright (C) 2013 Judah Holanda Correia Lima <judahholanda7@gmail.com>.
 *
 * Todos os direitos reservados.
 * Esse programa é um software livre: você pode redistribuí-lo e/ou modificá-lo
 * dentro dos termos da Licença Pública Geral GNU como publicada pela 
 * Fundação do Software Livre (FSF), na versão 3 da Licença, ou 
 * (na sua opinião) qualquer versão.
 *
 * Este programa é distribuído na esperança de que possa ser útil, 
 * mas SEM NENHUMA GARANTIA; sem uma garantia implícita de
 * ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a 
 * Veja a Licença Pública Geral GNU para maiores detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU 
 * junto com este programa, se não, veja <http://www.gnu.org/licenses/>.
 */
package database;

import java.io.Serializable;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
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
