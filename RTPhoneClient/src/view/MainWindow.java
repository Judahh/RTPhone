package view;

import database.ClientMessage;
import database.ClientStatus;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import remoteMethodInvocation.Client;
import realTimeTransportProtocol.Phone;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JH
 */
public class MainWindow extends javax.swing.JFrame {

   private Phone phone;
   private LoginWindow loginWindow;
   private boolean open;
   private ArrayList<database.Client> contactList;
   private DefaultListModel contactListModel;
   private DefaultComboBoxModel statusListModel;
   private database.Client me;

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

   private void getOfflineMessages(){
       ArrayList<ClientMessage> messageList=this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getMessageList(me.getUsername());
       for (int index = 0; index < messageList.size(); index++) {
           //TODO: pegar as mensagens e adicionalas por ordem do index da mensagem nas tabs de seus respectivos remetentes, caso nao exista a tab criar uma nova
       }
   }
   
   private void sendContactRequestOK(database.Client client){
       //TODO: conectar ao client e enviar o OK para entrar na lista de contatos
   }
   
   private void getContactRequests() {
      ArrayList<database.Client> contactRequestList=this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().getContactRequestList(this.loginWindow.getjTextFieldUsername().getText());
       for (int index = 0; index < contactRequestList.size(); index++) {
           String requestText="User \""+contactRequestList.get(index).getName()+"\" wants to add you to his contact list.";
          int showConfirmDialog = JOptionPane.showConfirmDialog(this, requestText);
          switch(showConfirmDialog){
              case JOptionPane.YES_OPTION:
                  if(contactRequestList.get(index).getAddress()!=null && !contactRequestList.get(index).getAddress().isEmpty()){
                      sendContactRequestOK(contactRequestList.get(index));
                  }
                  this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().makeContactRequest(me.getUsername(), contactRequestList.get(index).getUsername());
              break;
              
              case JOptionPane.NO_OPTION:
                  this.loginWindow.getDefaultServerConfigurationsWindow().getDatabase().removeContactRequest(contactRequestList.get(index).getUsername(), me.getUsername());
              break;  
          }
       }
   }
   
   private String getAddress() {
      String address = "UNKNOWN";

      try {
         String hostName = InetAddress.getLocalHost().getHostName();
         InetAddress addrs[] = InetAddress.getAllByName(hostName);
         
         for (InetAddress addr : addrs) {
//            System.out.println("addr.getHostAddress() = " + addr.getHostAddress());
//            System.out.println("addr.isLoopbackAddress() = " + addr.isLoopbackAddress());
//            System.out.println("addr.isSiteLocalAddress() = " + addr.isSiteLocalAddress());
//            System.out.println("");
            if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()) {
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
      //TODO: Update nos outros clientes
   }

   private void initPhone() {
      phone = null;
      open = true;
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
      jTabbedPane1 = new javax.swing.JTabbedPane();
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jButtonCall)
               .addComponent(jButtonChat)
               .addComponent(jButtonRemove)
               .addComponent(jButtonLogoff)
               .addComponent(jButtonAdd))
            .addContainerGap())
      );

      jTabbedPane1.addTab("Contacts", jPanel1);

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
            .addComponent(jTabbedPane1)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jTabbedPane1)
            .addContainerGap())
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      open = false;
      try {
         loginWindow.getDefaultServerConfigurationsWindow().getDatabase().logoff(loginWindow.getjTextFieldUsername().getText());
      } catch (Exception e) {
         System.out.println(e);
      }
   }//GEN-LAST:event_formWindowClosing

   private void jButtonCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCallActionPerformed
      if (jButtonCall.getText().equals("Call")) {
         try {
            //connect to user to call (String) this.jListLoggedUsers.getSelectedValue()
            String check = "";
            // in user use method call
            if (!check.isEmpty()) {
               this.phone = new Phone(check, 16384, 32766);
               this.phone.start();
               jButtonCall.setText("Hang Up");
            } else {
               JOptionPane.showMessageDialog(null, "It was not possible to complete the call!");
            }

         } catch (Exception e) {
            System.out.println(e);
         }
      } else {
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
      //TODO: Update nos outros clientes
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
      database.Client selectedClient=(database.Client) jListContact.getSelectedValue();
      if(selectedClient.getAddress()==null || selectedClient.getAddress().isEmpty()){
          JOptionPane.showMessageDialog(this, "User \""+selectedClient.getName()+"\" is Offline!", "Information", JOptionPane.INFORMATION_MESSAGE);
      }
      //TODO: mover para aba do usuaio selecionado ou abrir aba caso nao exista a aba
   }//GEN-LAST:event_jButtonChatActionPerformed
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
   private javax.swing.JTabbedPane jTabbedPane1;
   // End of variables declaration//GEN-END:variables
}
