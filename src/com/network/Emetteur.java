/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author tapha
 */
public class Emetteur extends Thread {

    private Socket socket;
    private Cipher cipher;
    private String currentPath;

    public Emetteur(Socket socket, Cipher cipher) {
        this.socket = socket;
        this.cipher = cipher;
    }

    @Override
    public void run() {

        OutputStream os = null;
        try {
            //envoye de la reponse

            os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            InputStream is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
           
            pw.println("pwd");
            String reponse = br.readLine();
            System.out.println(reponse + ">");
            currentPath=reponse;
            Scanner sc=new Scanner(System.in);

            while (true) {
                String com = sc.nextLine();
                
                pw.println(com);
                
                while(true) {
                    reponse = br.readLine();
                    if ("nkjhiuywdgbjwasxsajkhcluopqw;ksdf".equals(reponse)|| com==null || com=="") break;
                    System.out.println(reponse);

                }
                
                System.out.print(currentPath + ">");

                //pw.println("pwd");
                //reponse = br.readLine();
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Emetteur.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
