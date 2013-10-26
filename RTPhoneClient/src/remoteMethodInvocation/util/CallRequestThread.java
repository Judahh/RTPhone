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
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import realTimeTransportProtocol.Phone;
import view.MainWindow;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
 */
public class CallRequestThread extends RemoteMethodInvocationConnectThread {

   public CallRequestThread(MainWindow mainWindow, Client client) {
      super(mainWindow, client);
   }

   @Override
   protected void work() {
      try {
         boolean call = clientRemoteMethodInvocation.call(getMainWindow().getMe());
         try {
            if (call) {
               setAnswer(Answer.yes);
               getMainWindow().setPhone(new Phone(getClient().getAddress(), 16384, 32766));
               getMainWindow().getPhone().start();
               getMainWindow().getjButtonCall().setText("Hang Up");
               getMainWindow().setContact(getClient());
            } else {
               setAnswer(Answer.no);
               JOptionPane.showMessageDialog(getMainWindow(), "Connection refused!", "", JOptionPane.INFORMATION_MESSAGE);
            }
         } catch (InterruptedException exception) {
            commonError(exception);
         }
      } catch (RemoteException exception) {
         connectError(exception);
         try {
            setAnswer(Answer.no);
         } catch (InterruptedException exception1) {
            Logger.getLogger(CallRequestThread.class.getName()).log(Level.SEVERE, null, exception1);
         }
      }
   }
}
