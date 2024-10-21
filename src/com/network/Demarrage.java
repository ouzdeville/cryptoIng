/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tapha
 */
public class Demarrage {

    public static void start() {
        
        try {
            Socket s = new Socket("127.0.0.1", 2024);

           // Socket s = new Socket("172.16.0.15", 2024);
            new Recepteur(s, null).start();
        } catch (IOException ex) {

            try {
                ServerSocket ss = new ServerSocket(2024);
                System.out.println("Serveur demarrer");

                Socket s = ss.accept();
                new Emetteur(s, null).start();
            } catch (IOException ex1) {
                Logger.getLogger(Demarrage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void main(String[] args) {
        Demarrage.start();
    }
}
