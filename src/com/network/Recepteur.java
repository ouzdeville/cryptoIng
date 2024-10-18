/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author ousmane3ndiaye
 */
public class Recepteur extends Thread {

    private Socket socket;
    private Cipher cipher;
    private String path;

    public Recepteur(Socket socket, Cipher cipher) {
        this.socket = socket;
        this.cipher = cipher;
        File dir = new File(".");
        path = dir.getAbsolutePath();
    }

    @Override
    public void run() {
        try {
            InputStream is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String commandLine;
            while (true) {

                commandLine = br.readLine();
                if(commandLine==null || commandLine.isEmpty()) commandLine="pwd";
                Commande commande = new Commande(commandLine);
                String reponse = "";
                switch (commande.getAction()) {
                    case "pwd":
                        File dir = new File(".");
                        path = dir.getAbsolutePath();
                        reponse=path;
                        break;

                    case "ls":
                        dir = new File(path);
                        if (dir.isDirectory()) {
                            File[] fils = dir.listFiles();
                            StringBuilder sb = new StringBuilder();
                            for (File file : fils) {
                                String type = file.isDirectory() ? "DIR" : "file";
                                FileTime creationTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                                String line = file.getName() + "     " + type + "     " + creationTime.toString() + "\n";
                                sb.append(line);
                            }
                            // envoyer le sb au serveur
                            sb.append("klhgjlshjsdgfjhsdhdkjldshgjksdg\n");
                            reponse = sb.toString();
                        }
                        break;
                    case "cd":

                        break;
                    case "get":

                        break;
                    case "rm":

                        break;
                    case "copy":

                        break;
                    case "put":

                        break;
                    default:
                        throw new AssertionError();
                }
                
                // envoie de lq reponse 
                OutputStream os=socket.getOutputStream();
                PrintWriter pw=new PrintWriter(os, true);
                pw.println(reponse);
                

            }

        } catch (Exception ex) {
            Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
