/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import clientRemoteMethodInvocation.ClientRemoteMethodInvocation;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Judah
 */
public class ContactRequestThread extends Thread{

    private database.Client client;
    private database.Client me;
    
    public ContactRequestThread(database.Client me, database.Client client) {
        this.me = me;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ClientRemoteMethodInvocation rmi;
            Registry registry = LocateRegistry.getRegistry(client.getAddress(), 9000);
            rmi = (ClientRemoteMethodInvocation) registry.lookup("RTPhoneClient");
            boolean ok = rmi.contactRequest(me);
        } catch (RemoteException exception) {
            Logger.getLogger(ContactRequestThread.class.getName()).log(Level.SEVERE, null, exception);
        } catch (NotBoundException exception) {
            Logger.getLogger(ContactRequestThread.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
