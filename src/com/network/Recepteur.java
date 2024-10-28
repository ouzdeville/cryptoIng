/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
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
 */
public class Recepteur extends Thread {

    private Socket socket;
    private Cipher cipher;
    private String path;
    private String password="";

    public Recepteur(Socket socket, Cipher cipher) {
        this.socket = socket;
        this.cipher = cipher;
        File dir = new File(".");
        path = dir.getAbsolutePath();
    }

    @Override
    public void run() {

        InputStream is = null;
        try {
            is = this.socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            PrintStream pw = new PrintStream(bos, true); // Auto flush à true

            String commandLine;

            CryptoImpl crypto = new CryptoImpl();
            //password = partageDeClePublic();
            password=PartageCles.partageDeClesDH(socket, false);
            while (true) {
                try {
                    commandLine = br.readLine();
                    if (commandLine == null || commandLine.isEmpty()) {
                        continue;
                    }

                    
                    commandLine = Emetteur.dechiffre(commandLine,password);

                    Commande commande = new Commande(commandLine);
                    String reponse = "";
                    switch (commande.getAction()) {
                        case "pwd":
                            File dir = new File(path);
                            path = dir.getAbsolutePath().replace("\\.", "");
                            reponse = path;
                            
                            pw.println(Emetteur.chiffre(path,password));
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
                                //sb.append("klhgjlshjsdgfjhsdhdkjldshgjksdg\n");
                                reponse = sb.toString();
                            }
                            pw.println(Emetteur.chiffre(reponse,password));
                            break;
                        case "cd":
                            System.out.println("cd");
                            dir = new File(path,password); // Chemin courant
                            String newPath = commande.options[0].split(" ")[1]; // Extraction du chemin

                            if (newPath.equals(".")) {
                                // `cd .` reste dans le répertoire courant
                                reponse = path;
                            } else if (newPath.equals("..")) {
                                // `cd ..` revient au répertoire parent
                                dir = new File(path).getParentFile(); // Récupération du répertoire parent
                                if (dir != null) {
                                    path = dir.getAbsolutePath();
                                    reponse = path; // Mise à jour du chemin courant
                                } else {
                                    reponse = path; // Si déjà à la racine, le chemin reste inchangé
                                }
                            } else {
                                // Vérification si le nouveau chemin est absolu ou relatif
                                if (newPath.contains(":") || newPath.startsWith("/")) {
                                    dir = new File(newPath); // Chemin absolu
                                } else {
                                    dir = new File(path + File.separator + newPath); // Chemin relatif
                                }

                                // Vérification si le chemin existe et est un répertoire
                                if (dir.exists() && dir.isDirectory()) {
                                    path = dir.getAbsolutePath(); // Mise à jour du chemin courant
                                    reponse = path;
                                } else {
                                    reponse = "Erreur : le répertoire '" + newPath + "' n'existe pas."; // Message d'erreur
                                }
                            }

                            // Affichage de la réponse
                            System.out.println("reponse=" + reponse);
                            // envoie de lq reponse
                            pw.println(Emetteur.chiffre(reponse,password));
                            pw.flush();
                            break;

                        case "get":

                            break;
                        case "rm":

                            break;
                        case "copy":

                            break;
                        case "put":

                            break;
                        case "enc":
                      
                        break;
                        default:
                            StringBuilder sb = new StringBuilder();
                            sb.append("Commande inconnue\n");
                            sb.append("klhgjlshjsdgfjhsdhdkjldshgjksdg\n");
                            reponse = sb.toString();
                            break;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Recepteur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        PublicKey kpub = (PublicKey) ois.readObject();

        
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte [] buffer=new byte[16];
        sr.nextBytes(buffer);
        String password=new String(buffer);
        
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        byte[] s = crypto.processData(password.getBytes(), kpub, Cipher.ENCRYPT_MODE, ivParam);
        String Csa = crypto.bytesToHex(s);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(Csa);
       
        return password;

    }

}