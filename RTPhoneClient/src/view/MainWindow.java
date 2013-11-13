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
package view;

import database.Client;
import database.ClientMessage;
import database.ClientStatus;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import realTimeTransportProtocol.Phone;
import remoteMethodInvocation.util.CallRequestThread;
import remoteMethodInvocation.util.ChangeStatusThread;
import remoteMethodInvocation.util.ContactRequestThread;
import remoteMethodInvocation.util.HangUpThread;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {

   private Phone phone;
   private LoginWindow loginWindow;
   private ArrayList<database.Client> contactList;
   private DefaultListModel contactListModel;
   private DefaultComboBoxModel statusListModel;
   private database.Client me;
   private database.Client contact;

   /**
    * Creates new form MainWindow
    */
   public MainWindow(LoginWindow loginWindow) {
      this.loginWindow = loginWindow;
      updateAddress();
      initLists();
      initComponents();
      initMe();
      initMonitorDesign();
      initPhone();
      getContactRequests();
      getOfflineMessages();
   }

   public synchronized void setContact(Client contact) {
      this.contact = contact;
   }

   public synchronized Client getContact() {
      return contact;
   }

   public LoginWindow getLoginWindow() {
      return loginWindow;
   }

   public ArrayList<database.Client> getContactList() {
      return contactList;
   }

   public DefaultListModel getContactListModel() {
      return contactListModel;
   }

   public DefaultComboBoxModel getStatusListModel() {
      return statusListModel;
   }

   public database.Client getMe() {
      return me;
   }

   private void getOfflineMessages() {
      ArrayList<ClientMessage> messageList = this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getMessageList(me.getUsername());
      for (int index = 0; index < messageList.size(); index++) {
         boolean found = false;
         for (int index2 = 1; index2 < jTabbedPane.getTabCount(); index2++) {
            ChatTabPanel tempChatTabPanel = (ChatTabPanel) jTabbedPane.getComponentAt(index2);
            if (tempChatTabPanel.getClient().getUsername().equals(messageList.get(index).getFrom().getUsername())) {
               found = true;
               jTabbedPane.setSelectedIndex(index2);
               System.out.println("Index Pane:" + index2);
               System.out.println("Index:" + index);
               System.out.println("Message:" + messageList.get(index));
               tempChatTabPanel.append(messageList.get(index));
               break;
            } else {
               System.out.println("Nao Index Pane:" + index2);
               System.out.println("Nao Index:" + index);
               System.out.println("Nao Message:" + messageList.get(index));
            }
         }
         if (!found) {
            jTabbedPane.addTab(messageList.get(index).getFrom().toString(), new ChatTabPanel(this, messageList.get(index).getFrom()));
            jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
            ChatTabPanel tempChatTabPanel = (ChatTabPanel) jTabbedPane.getComponentAt(jTabbedPane.getTabCount() - 1);
            System.out.println("Index:" + index);
            System.out.println("Message:" + messageList.get(index));
            tempChatTabPanel.append(messageList.get(index));
         } else {
            System.out.println("Nao Index:" + index);
            System.out.println("Nao Message:" + messageList.get(index));
         }
      }
      System.out.println("FIM");
   }

   public void changeStatus(database.Client client) {
      boolean found = false;
      for (int index = 0; index < getContactListModel().size(); index++) {
         database.Client tempClient = (database.Client) getContactListModel().get(index);
         System.out.println("ME:" + getMe().getUsername());
         System.out.println("ME:" + getMe().getAddress());
         System.out.println("ME:" + getMe().getClientStatus());
         System.out.println("Contact:" + tempClient.getUsername());
         System.out.println("Contact:" + tempClient.getAddress());
         System.out.println("Contact:" + tempClient.getClientStatus());
         if (tempClient.getUsername().equals(client.getUsername())) {
            found = true;
            getContactListModel().setElementAt(client, index);
            break;
         }
      }
      if (!found) {
         getContactListModel().addElement(client);
      }
   }

   private void sendContactRequestOK(database.Client client) {
      if (client.getAddress() != null && !client.getAddress().isEmpty()) {
         ChangeStatusThread changeStatusThread = new ChangeStatusThread(this, client);
         changeStatusThread.start();
      }
      loginWindow.getDefaultServerConfigurationsWindow().getDatabase().makeContactRequest(me.getUsername(), client.getUsername());
      changeStatus(client);
   }

   private void getContactRequests() {
      ArrayList<database.Client> contactRequestList = this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getContactRequestList(this.loginWindow.getjTextFieldUsername().getText());
      for (int index = 0; index < contactRequestList.size(); index++) {
         String requestText = "User \"" + contactRequestList.get(index).getName() + "\" wants to add you to his contact list.";
         int showConfirmDialog = JOptionPane.showConfirmDialog(this, requestText, "Information", JOptionPane.INFORMATION_MESSAGE);
         switch (showConfirmDialog) {
            case JOptionPane.YES_OPTION:
               sendContactRequestOK(contactRequestList.get(index));
               break;

            case JOptionPane.NO_OPTION:
               this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().removeContactRequest(contactRequestList.get(index).getUsername(), me.getUsername());
               break;
         }
      }
   }

   private String getAddress() {
      String address = "UNKNOWN";

//      Setup Java RMI Over the Internet
//
//      Nick Hatter — April 24, 2011 - 23:26
//      Many of us are familiar with Java RMI but to get it working over the internet is a bit more involved, at least in my own experience of doing so. Please note that this post assumes a basic understanding of Java RMI. If you would like to start from scratch, see Oracle's official documentation.
//
//      The solution
//
//      Enable port forwarding for ports 1099 and 1100. Why? 1099 is the defacto-standard port for Java RMI. As for port 1100, we're going to use this for communicating with remote objects. This latter is arbitrary though and can be replaced by a different port if you prefer.
//      Run rmiregistry in your codebase on any servers. Note that this is not required for clients to receive a response.
//      In your server-side code do .rebind("//your-local-ip:1099/remoteObjectName"); This will bind the object to your local machine. However, because we are using port forwarding, we are telling our router to direct any packets to 1099 to our local IP address. Please note that you cannot use rebind on an external IP.
//      IMPORTANT:Any objects which intend to receive any response, rather than extend UnicastRemoteObject, use the following code: UnicastRemoteObject.exportObject(yourObject, 1100) This will allow your object to listen for remote calls on port 1100 (which we have port forwarded). If you do not do this, then Java will use an anonymous port (which will probably get blocked remotely).
//      In your client-side code do Naming.lookup("//the-remote-ip:1099/remoteObjectName")
//      Don't forget to compile your remote classes with rmic!
//      IMPORTANT:Finally, when running your java client/server make sure you pass -Djava.rmi.server.hostname=your-external-IP to Java. Eg: java -Djava.rmi.server.hostname=1.2.3.4 client This JVM argument when passed will set the address to be bound to exported remote objects. It can be set programmatically but doing so is left as an exercise for the reader.
//      You may get the following error:
//      java.rmi.ConnectException: Connection refused to host: 127.0.1.1 (or some remote IP); nested exception is:
//      java.net.ConnectException: Connection refused
//
//      If so, this means you may have missed a step from earlier. Unfortunately, there are many causes for this error!
//
//      Finally, rather than use string literals for the IP addresses, you will probably want to use automated methods to keep track of them. This has been left out of the code above for simplicity.

      try {
         String hostName = InetAddress.getLocalHost().getHostName();
         InetAddress addrs[] = InetAddress.getAllByName(hostName);

         for (InetAddress addr : addrs) {
            System.out.println("Address:" + addr.getHostAddress());
            if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
               System.out.println("Address In:" + addr.getHostAddress());
               address = addr.getHostAddress();
            }
         }

         if ("UNKNOWN".equals(address)) {
            address = InetAddress.getLocalHost().getHostAddress();
         }
      } catch (UnknownHostException exception) {
         Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
      }
      return address;
   }

   private void updateAddress() {
      this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().updateAddress(this.loginWindow.getjTextFieldUsername().getText(), getAddress());
   }

   private void initMe() {
      me = this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getUser(this.loginWindow.getjTextFieldUsername().getText());
      getStatus();
      for (int index = 0; index < contactListModel.size(); index++) {
         database.Client tempClient = (database.Client) contactListModel.get(index);
         if (tempClient.getAddress() != null && !tempClient.getAddress().isEmpty()) {
            ChangeStatusThread changeStatusThread = new ChangeStatusThread(this, tempClient);
            changeStatusThread.start();
         }
      }
   }

   private void initPhone() {
      phone = null;
   }

   private void initMonitorDesign() {
      jButtonLogoff.setVisible(false);//visivel apenas para monitores
      jButtonRemove.setVisible(false);//visivel apenas para monitores
   }

   public final void initLists() {
      contactListModel = new DefaultListModel();
      statusListModel = new DefaultComboBoxModel();

      statusListModel.addElement("Online");
      statusListModel.addElement("Away");
      statusListModel.addElement("Busy");
      statusListModel.addElement("");

      contactList = this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getContactList(this.loginWindow.getjTextFieldUsername().getText());
      contactListModel = new DefaultListModel();

      for (int index = 0; index < contactList.size(); index++) {
         contactListModel.addElement(contactList.get(index));
      }
   }

   public JTabbedPane getjTabbedPane() {
      return jTabbedPane;
   }

   public void setPhone(Phone phone) {
      this.phone = phone;
   }

   public Phone getPhone() {
      return phone;
   }

   public JButton getjButtonCall() {
      return jButtonCall;
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jMenuItem1 = new javax.swing.JMenuItem();
      jTabbedPane = new javax.swing.JTabbedPane();
      jPanel1 = new javax.swing.JPanel();
      jButtonCall = new javax.swing.JButton();
      jLabel1 = new javax.swing.JLabel();
      jComboBoxStatus = new javax.swing.JComboBox();
      jButtonChat = new javax.swing.JButton();
      jScrollPane1 = new javax.swing.JScrollPane();
      jListContact = new JList();
      jButtonRemove = new javax.swing.JButton();
      jButtonLogoff = new javax.swing.JButton();
      jButtonAdd = new javax.swing.JButton();
      jMenuBar1 = new javax.swing.JMenuBar();
      jMenu2 = new javax.swing.JMenu();
      jMenuItem2 = new javax.swing.JMenuItem();

      jMenuItem1.setText("jMenuItem1");

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
         }
      });

      jButtonCall.setText("Call");
      jButtonCall.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonCallActionPerformed(evt);
         }
      });

      jLabel1.setText("Status:");

      jComboBoxStatus.setEditable(true);
      jComboBoxStatus.setModel(statusListModel);
      jComboBoxStatus.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxStatusActionPerformed(evt);
         }
      });

      jButtonChat.setText("Chat");
      jButtonChat.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonChatActionPerformed(evt);
         }
      });

      jListContact.setModel(contactListModel);
      jListContact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      jListContact.setCellRenderer(new CellRender());
      jScrollPane1.setViewportView(jListContact);

      jButtonRemove.setText("Remove");

      jButtonLogoff.setText("Logoff");

      jButtonAdd.setText("Add");
      jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonAddActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jComboBoxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jButtonCall)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jButtonChat)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(jButtonLogoff)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jButtonRemove)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jButtonAdd)))
            .addContainerGap())
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jButtonCall)
               .addComponent(jButtonChat)
               .addComponent(jButtonRemove)
               .addComponent(jButtonLogoff)
               .addComponent(jButtonAdd))
            .addContainerGap())
      );

      jTabbedPane.addTab("Contacts", jPanel1);

      jMenu2.setText("Configurations");

      jMenuItem2.setText("Databases Servers");
      jMenu2.add(jMenuItem2);

      jMenuBar1.add(jMenu2);

      setJMenuBar(jMenuBar1);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      try {
         loginWindow.getDefaultServerConfigurationsWindow().getDatabase().logoff(loginWindow.getjTextFieldUsername().getText());
      } catch (Exception e) {
         System.out.println(e);
      }
      me.setAddress(null);
      for (int index = 0; index < contactListModel.size(); index++) {
         database.Client tempClient = (database.Client) contactListModel.get(index);
         if (tempClient.getAddress() != null && !tempClient.getAddress().isEmpty()) {
            ChangeStatusThread changeStatusThread = new ChangeStatusThread(this, tempClient);
            changeStatusThread.start();
         }
      }
   }//GEN-LAST:event_formWindowClosing

   private void jButtonCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCallActionPerformed
      if (jButtonCall.getText().equals("Call")) {
         database.Client selectedClient = (database.Client) jListContact.getSelectedValue();

         if (selectedClient.getAddress() == null || selectedClient.getAddress().isEmpty()) {
            JOptionPane.showMessageDialog(this, "User \"" + selectedClient.getName() + "\" is Offline!", "Information", JOptionPane.INFORMATION_MESSAGE);
         } else {
            CallRequestThread callRequestThread = new CallRequestThread(this, selectedClient);
            callRequestThread.start();
         }
      } else {
         HangUpThread hangUpThread = new HangUpThread(this, getContact());
         hangUpThread.start();
         this.phone.stop();
         this.phone = null;
         jButtonCall.setText("Call");
      }
   }//GEN-LAST:event_jButtonCallActionPerformed

   private void getStatus() {
      switch (me.getClientStatus()) {
         case away:
            jComboBoxStatus.setSelectedIndex(1);
            break;

         case busy:
            jComboBoxStatus.setSelectedIndex(2);
            break;

         case custom:
            jComboBoxStatus.setSelectedIndex(3);
            statusListModel = new DefaultComboBoxModel();
            statusListModel.addElement("Online");
            statusListModel.addElement("Away");
            statusListModel.addElement("Busy");
            statusListModel.addElement(me.getCustomStatus());
            System.out.println("status:" + me.getCustomStatus());
            jComboBoxStatus.setModel(statusListModel);
            break;

         default:
            jComboBoxStatus.setSelectedIndex(0);
      }
   }

   private void updateStatus(String status) {
      ClientStatus clientStatus;
      switch (status) {
         case "Online":
            clientStatus = ClientStatus.none;
            break;

         case "Away":
            clientStatus = ClientStatus.away;
            break;

         case "Busy":
            clientStatus = ClientStatus.busy;
            break;

         default:
            clientStatus = ClientStatus.custom;
            me.setCustomStatus(status);
      }

      if (me.getClientStatus() != clientStatus) {
         System.out.println("new clientStatus = " + clientStatus);
         System.out.println("old clientStatus = " + me.getClientStatus());
         me.setClientStatus(clientStatus);
         this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().updateStatus(me);
      }
      for (int index = 0; index < contactListModel.size(); index++) {
         database.Client tempClient = (database.Client) contactListModel.get(index);
         if (tempClient.getAddress() != null && !tempClient.getAddress().isEmpty()) {
            ChangeStatusThread changeStatusThread = new ChangeStatusThread(this, tempClient);
            changeStatusThread.start();
         }
      }
   }

   private void jComboBoxStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStatusActionPerformed
      System.out.println("entrou");
      String text = statusListModel.getSelectedItem().toString();
      System.out.println(text);

      if (!text.equals("Online") && !text.equals("Away") && !text.equals("Busy")) {
         jComboBoxStatus.setSelectedIndex(3);
         statusListModel = new DefaultComboBoxModel();
         statusListModel.addElement("Online");
         statusListModel.addElement("Away");
         statusListModel.addElement("Busy");
         statusListModel.addElement(text);
         jComboBoxStatus.setModel(statusListModel);
         jComboBoxStatus.setSelectedIndex(3);
      }

      updateStatus(text);
   }//GEN-LAST:event_jComboBoxStatusActionPerformed

   private void jButtonChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChatActionPerformed
      database.Client selectedClient = (database.Client) jListContact.getSelectedValue();
      if (selectedClient.getAddress() == null || selectedClient.getAddress().isEmpty()) {
         JOptionPane.showMessageDialog(this, "User \"" + selectedClient.getName() + "\" is Offline!", "Information", JOptionPane.INFORMATION_MESSAGE);
      }
      boolean found = false;
      for (int index = 1; index < jTabbedPane.getTabCount(); index++) {
         ChatTabPanel tempChatTabPanel = (ChatTabPanel) jTabbedPane.getTabComponentAt(index);
         if (tempChatTabPanel.getClient().getUsername().equals(selectedClient.getUsername())) {
            found = true;
            jTabbedPane.setSelectedIndex(index);
            break;
         }
      }
      if (!found) {
         jTabbedPane.addTab(selectedClient.toString(), new ChatTabPanel(this, selectedClient));
         jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
      }
   }//GEN-LAST:event_jButtonChatActionPerformed

   private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
      String showInputDialog = JOptionPane.showInputDialog(this, "Type the username of the user that you want to add:", "Add Contact", JOptionPane.INFORMATION_MESSAGE);
      if (showInputDialog != null && !showInputDialog.isEmpty() && !showInputDialog.equals(me.getUsername())) {
         database.Client contact = loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getUser(showInputDialog);
         if (contact != null) {
            if (contact.getAddress() != null && !contact.getAddress().isEmpty()) {
               System.out.println("aqui:" + contact.getAddress());
               ContactRequestThread contactRequestThread = new ContactRequestThread(this, contact);
               contactRequestThread.start();
            } else {
               loginWindow.getDefaultServerConfigurationsWindow().getDatabase().makeContactRequest(me.getUsername(), contact.getUsername());
            }
         } else {
            JOptionPane.showMessageDialog(this, "The user \"" + showInputDialog + "\" doesnt exist!", "Information", JOptionPane.INFORMATION_MESSAGE);
         }
      } else {
         if (showInputDialog == null || showInputDialog.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to write the username of the contact that you want to add!", "WARNING", JOptionPane.WARNING_MESSAGE);
         } else if (showInputDialog.equals(me.getUsername())) {
            JOptionPane.showMessageDialog(this, "You cant add yourself!", "WARNING", JOptionPane.WARNING_MESSAGE);
         }
      }
   }//GEN-LAST:event_jButtonAddActionPerformed
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonAdd;
   private javax.swing.JButton jButtonCall;
   private javax.swing.JButton jButtonChat;
   private javax.swing.JButton jButtonLogoff;
   private javax.swing.JButton jButtonRemove;
   private javax.swing.JComboBox jComboBoxStatus;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JList jListContact;
   private javax.swing.JMenu jMenu2;
   private javax.swing.JMenuBar jMenuBar1;
   private javax.swing.JMenuItem jMenuItem1;
   private javax.swing.JMenuItem jMenuItem2;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTabbedPane jTabbedPane;
   // End of variables declaration//GEN-END:variables
}
