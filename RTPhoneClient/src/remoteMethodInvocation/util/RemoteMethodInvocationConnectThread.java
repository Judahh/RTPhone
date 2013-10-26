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
package remoteMethodInvocation.util;

import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
import database.Client;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;
import view.MainWindow;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
 */
public class RemoteMethodInvocationConnectThread extends Thread {

   private database.Client client;
   private MainWindow mainWindow;
   protected ClientRemoteMethodInvocation clientRemoteMethodInvocation;
   private Answer answer;
   private Semaphore semaphore;

   public RemoteMethodInvocationConnectThread(MainWindow mainWindow, Client client) {
      this.client = client;
      this.mainWindow = mainWindow;
      this.answer = Answer.none;
      this.semaphore = new Semaphore(1);
   }

   protected MainWindow getMainWindow() {
      return mainWindow;
   }

   protected Client getClient() {
      return client;
   }

   @Override
   public void run() {
      connect();
      work();
   }

   public Answer getAnswer() throws InterruptedException {
      this.semaphore.acquire();
      Answer tempAnswer = this.answer;
      this.semaphore.release();
      return tempAnswer;
   }

   protected void setAnswer(Answer answer) throws InterruptedException {
      this.semaphore.acquire();
      this.answer = answer;
      this.semaphore.release();
   }

   protected void connectError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error while connecting to \"" + client.getUsername() + "\":" + exception, "Error", JOptionPane.ERROR_MESSAGE);
      updateLogoffStatus();
   }

   protected void commonError(Exception exception) {
      JOptionPane.showMessageDialog(mainWindow, "Ocurred an error:" + exception, "Error", JOptionPane.ERROR_MESSAGE);
      updateLogoffStatus();
   }

   private void updateLogoffStatus() {
      mainWindow.getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().logoff(client.getUsername());
      client.setAddress(null);
      ArrayList<Client> contactList = mainWindow.getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().getContactList(client.getUsername());
      for (int index = 0; index < contactList.size(); index++) {
         System.out.println("ME:" + mainWindow.getMe().getUsername());
         System.out.println("ME:" + mainWindow.getMe().getAddress());
         System.out.println("ME:" + mainWindow.getMe().getClientStatus());
         System.out.println("Contact:" + contactList.get(index).getUsername());
         System.out.println("Contact:" + contactList.get(index).getAddress());
         System.out.println("Contact:" + contactList.get(index).getClientStatus());
         if (contactList.get(index).getAddress().equals(mainWindow.getMe().getAddress())||contactList.get(index).getUsername().equals(mainWindow.getMe().getUsername())) {
            changeStatus(client);
         } else {
            Client tempClient = contactList.get(index);
            if (tempClient.getAddress() != null && !tempClient.getAddress().isEmpty()) {
               ChangeContactStatusThread changeStatusThread = new ChangeContactStatusThread(mainWindow, client);
               changeStatusThread.start();
            }
         }
      }
   }

   public void changeStatus(database.Client client) {
      boolean found = false;
      for (int index = 0; index < mainWindow.getContactListModel().size(); index++) {
         database.Client tempClient = (database.Client) mainWindow.getContactListModel().get(index);
         if (tempClient.getUsername().equals(client.getUsername())) {
            found = true;
            mainWindow.getContactListModel().setElementAt(client, index);
            break;
         }
      }
      if (!found) {
         mainWindow.getContactListModel().addElement(client);
      }
   }

   private void connect() {
      try {
         Registry registry = LocateRegistry.getRegistry(client.getAddress(), 9000);
         clientRemoteMethodInvocation = (ClientRemoteMethodInvocation) registry.lookup("RTPhoneClient");
      } catch (NotBoundException | RemoteException exception) {
         connectError(exception);
      }
   }

   protected void work() {
      throw new UnsupportedOperationException("Not implemented");
   }
}
