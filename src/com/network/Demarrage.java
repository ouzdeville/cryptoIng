/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Provider;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author ousmane3ndiaye
 */
public class Demarrage {
    public static  void start(){
        try {
            Socket s=new Socket("127.0.0.1", 2024);
            new Recepteur(s, null).start();
        } catch (IOException ex) {
            
                    try {
                        ServerSocket ss=new ServerSocket(2024);
                        System.out.println("serveur demarre .... ");
                        Socket s = ss.accept();
                        new Emetteur(s, null).start();
                        
                    } catch (IOException ex1) {
                        Logger.getLogger(Demarrage.class.getName()).log(Level.SEVERE, null, ex1);
                    }
            
        }
    }
    
    public static void main(String[] args) {
        if (Security.getProvider("BC") == null) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            System.out.println(" Security.insertProviderAt(new BouncyCastleProvider(), 1);");
        }
       
    for (Provider.Service s: new BouncyCastleProvider().getServices()){
        if (s.getType().equals("Cipher"))
            System.out.println("\t"+s.getType()+" "+ s.getAlgorithm());
    }

        Demarrage.start();
    }
    
}
