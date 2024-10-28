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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.security.KeyPair;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import static sn.presidence.dept.service.cryptoing.tool.ICrypto.iv;

/**
 *
 * @author ousmane3ndiaye

 * @author tapha
 */
public class Emetteur extends Thread {

    private Socket socket;
    private Cipher cipher;
    private String currentPath;
    private static String password = "@srtongpassword";

    public Emetteur(Socket socket, Cipher cipher) {
        this.socket = socket;
        this.cipher = cipher;
    }

    @Override
    public void run() {

        OutputStream os = null;
        try {
            // envoie de lq reponse
            //envoye de la reponse

            os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            InputStream is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // Implementer le partage des cles
            //password = partageDeClePublic();
            password=PartageCles.partageDeClesDH(socket, true);

            String com = "pwd";
            String com1 = chiffre(com,password);
            pw.println(com1);

            String reponse = dechiffre(br.readLine(), password);
            System.out.print(reponse + ">");
            currentPath = reponse;
            Scanner sc = new Scanner(System.in);

            while (true) {
                com = sc.nextLine();
                System.out.println("Envoi de la commande : " + com); // Débogage

                com1 = chiffre(com,password);
                //envoie de commande chiffre
                pw.println(com1);

                pw.flush(); // Assurez-vous que flush() est appelé après chaque commande

                Commande commande = new Commande(com);

                switch (commande.getAction()) {
                    case "pwd":
                        reponse = dechiffre(br.readLine(),password);
                        System.out.println("Réponse pwd : " + reponse); // Débogage
                        currentPath = reponse.isEmpty() ? currentPath : reponse;
                        System.out.print(currentPath + ">");
                        break;

                    case "ls":
                        System.out.println("Demande de liste de fichiers..."); // Débogage
                        // while (true) {
                        reponse = dechiffre(br.readLine(),password);
                        if ("klhgjlshjsdgfjhsdhdkjldshgjksdg".equals(reponse)) {
                            break;
                        }
                        System.out.println("Fichier : " + reponse); // Débogage
                        // }
                        System.out.print(currentPath + ">");
                        break;

                    case "cd":
                        reponse = dechiffre(br.readLine(),password);
                        System.out.println("Réponse cd : " + reponse); // Débogage
                        currentPath = reponse.isEmpty() ? currentPath : reponse;
                        pw.println(chiffre("pwd",password));
                        reponse = dechiffre(br.readLine(),password);
                        System.out.print(reponse + ">");
                        currentPath = reponse;
                        break;

                    // Ajoutez d'autres commandes ici...
                    case "enc":
                      
                        break;
                    default:
                        System.out.println("Commande inconnue : " + commande.getAction());
                        break;
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(Emetteur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String chiffre(String com, String password) {

        CryptoImpl crypto = new CryptoImpl();
        SecretKey key = crypto.generatePBEKey(password);

        byte[] comCrypt = crypto.processData(com.getBytes(), key, Cipher.ENCRYPT_MODE, new IvParameterSpec(iv.getBytes()));
        String com1 = crypto.bytesToHex(comCrypt);
        return com1;
    }

    public static String dechiffre(String com, String password) {

        CryptoImpl crypto = new CryptoImpl();
        SecretKey key = crypto.generatePBEKey(password);

        byte[] enc = crypto.hextoBytes(com);

        byte[] comDrypt = crypto.processData(enc, key, Cipher.DECRYPT_MODE, new IvParameterSpec(iv.getBytes()));
        String com1 = new String(comDrypt);
        return com1;
    }

    /**
     * 1 envoyer la cle publique au client 
     * 
     * 2 recevoir le chiffre du secret 
     * 
     * 3 dechiffrement et retourner la valeur
     *
     * @return
     */
    private String partageDeClePublic() throws Exception {
        CryptoImpl crypto = new CryptoImpl();
        KeyPair kpair = crypto.generateKeyPair("sdasd".getBytes());
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(kpair.getPublic());

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        String Csa = (String) ois.readObject();
        byte[] sa = crypto.hextoBytes(Csa);
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        byte[] s = crypto.processData(sa, kpair.getPrivate(), Cipher.DECRYPT_MODE, ivParam);
        return new String(s);

    }
}
