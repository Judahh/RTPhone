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

import database.Client;
import database.ClientMessage;
import java.rmi.RemoteException;
import javax.swing.JTextArea;
import view.MainWindow;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
 */
public class SendMessageThread extends RemoteMethodInvocationConnectThread{

   private JTextArea jTextAreaSend;
   
   public SendMessageThread(MainWindow mainWindow, Client client, JTextArea jTextAreaSend) {
      super(mainWindow, client);
      this.jTextAreaSend=jTextAreaSend;
   }

   @Override
   protected void work() {
      ClientMessage clientMessage = new ClientMessage(getMainWindow().getMe(), getClient(), jTextAreaSend.getText());
      if (getClient().getAddress() == null || getClient().getAddress().isEmpty()) {
         getMainWindow().getLoginWindow().getDefaultServerConfigurationsWindow().getDatabase().makeMessage(clientMessage);
      } else {
         try {
            clientRemoteMethodInvocation.sendMessage(clientMessage);
         } catch (RemoteException exception) {
            commonError(exception);
         }
      }
      jTextAreaSend.setText("");
   }
}
