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
public class Client implements Serializable{

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
      setCustomStatus(customStatus);
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
      setCustomStatus(customStatus);
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

   public int getClientStatusValue() {
      switch (clientStatus) {
         case away:
            return 1;
         case busy:
            return 2;
         case custom:
            return 3;
         default:
            return 0;
      }
   }

   public void setClientStatus(ClientStatus clientStatus) {
      if (clientStatus != ClientStatus.custom) {
         customStatus = "";
      }
      this.clientStatus = clientStatus;
   }

   public String getCustomStatus() {
      return customStatus;
   }

   public void setCustomStatus(String customStatus) {
      if (!customStatus.isEmpty()) {
         clientStatus = ClientStatus.custom;
      }
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

   @Override
   public String toString() {
      String string = name;
      string += " (" + username + ") ";
      if (clientStatus == ClientStatus.custom) {
         string += "- " + customStatus;
      }
      return string;
   }
}
