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
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author ousmane3ndiaye
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
            // envoie de lq reponse
            os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            InputStream is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            pw.println("pwd");
            String reponse = br.readLine();
            System.out.print(reponse + ">");
            currentPath = reponse;
            Scanner sc = new Scanner(System.in);
            while (true) {
                String com = sc.nextLine();
                System.out.println("Envoi de la commande : " + com); // Débogage
                pw.println(com);
                pw.flush(); // Assurez-vous que flush() est appelé après chaque commande

                Commande commande = new Commande(com);

                switch (commande.getAction()) {
                    case "pwd":
                        reponse = br.readLine();
                        System.out.println("Réponse pwd : " + reponse); // Débogage
                        currentPath = reponse.isEmpty() ? currentPath : reponse;
                        System.out.print(currentPath + ">");
                        break;

                    case "ls":
                        System.out.println("Demande de liste de fichiers..."); // Débogage
                        while (true) {
                            reponse = br.readLine();
                            if ("klhgjlshjsdgfjhsdhdkjldshgjksdg".equals(reponse)) {
                                break;
                            }
                            System.out.println("Fichier : " + reponse); // Débogage
                        }
                        System.out.print(currentPath + ">");
                        break;

                    case "cd":
                        reponse = br.readLine();
                        System.out.println("Réponse cd : " + reponse); // Débogage
                        currentPath = reponse.isEmpty() ? currentPath : reponse;
                        pw.println("pwd");
                        reponse = br.readLine();
                        System.out.print(reponse + ">");
                        currentPath = reponse;
                        break;

                    // Ajoutez d'autres commandes ici...
                    default:
                        System.out.println("Commande inconnue : " + commande.getAction());
                        break;
                }

                
            }

        } catch (Exception ex) {
            Logger.getLogger(Emetteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
