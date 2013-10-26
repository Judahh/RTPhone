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
import database.ClientStatus;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @subAuthor Name <e-mail>
 * @author Judah Holanda Correia Lima <judahholanda7@gmail.com>
 */
public class CellRender extends JLabel implements ListCellRenderer<Object> {

   public CellRender() {
      setOpaque(true);
   }

   @Override
   public Component getListCellRendererComponent(JList<?> list,
           Object value,
           int index,
           boolean isSelected,
           boolean cellHasFocus) {

      Client client = (Client) value;
      paint(client);
//      setText(value.toString());
//
      Color background;
      Color foreground;
//
//      // check if this cell represents the current DnD drop location
      JList.DropLocation dropLocation = list.getDropLocation();
      if (dropLocation != null
              && !dropLocation.isInsert()
              && dropLocation.getIndex() == index) {

         background = Color.RED;
         foreground = Color.WHITE;
         setBackground(background);
         setForeground(foreground);
         // check if this cell is selected
      } else if (isSelected) {
         background = new Color(51, 153, 255);
         foreground = Color.WHITE;
         setBackground(background);
         setForeground(foreground);
         // unselected, and not the DnD drop location
      };


      return this;
   }

   public void paint(Client client) {
      ClientStatus status = client.getClientStatus();
      String address = client.getAddress();
      setText(client.toString());

      Color background;
      Color foreground;

      if (address == null || address.isEmpty()) {
         background = Color.GRAY;
         foreground = Color.WHITE;
      } else {
         switch (status) {
            case away:
               background = Color.YELLOW;
               foreground = Color.BLACK;
               break;

            case busy:
               background = Color.RED;
               foreground = Color.WHITE;
               break;

            case custom:
               background = Color.WHITE;
               foreground = Color.BLACK;
               break;

            default:
               background = Color.GREEN;
               foreground = Color.BLACK;
         }
      }

      setBackground(background);
      setForeground(foreground);
   }
}
