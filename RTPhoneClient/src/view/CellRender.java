/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author JH
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
