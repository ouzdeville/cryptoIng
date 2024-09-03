/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author tapha
 */
public class CryptoImpl implements ICrypto {

    @Override
    public byte[] generateSeedTrullyRandom() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        System.out.println("Faire bouger la souris");
        byte[] buffer = new byte[ICrypto.keysize / 8];
        int i = 0;
        Point precedent = new Point(0, 0);
        do {
            Point p = MouseInfo.getPointerInfo().getLocation();
            if (p.equals(precedent)) {
                continue;
            }
            buffer[i] = (byte) p.x;
            i++;
            precedent = p;
            System.out.print("#");
        } while (i < buffer.length);
        System.out.println("");
        return buffer;
    }

    @Override
    public SecretKey generateKey() {

        try {
            KeyGenerator kg = KeyGenerator.getInstance(algo);
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(generateSeedTrullyRandom());
            kg.init(keysize, sr);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean saveKey(SecretKey k, String chemin) {
        try {
            //prepation du fichier en ecriture
            FileOutputStream fos = new FileOutputStream(chemin);
            //creation dun oos pour ecrire l'objet k directement
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(k);
            oos.flush();
            oos.close();
            fos.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public SecretKey loadKey(String chemin) {
        try {
            FileInputStream fis = new FileInputStream(chemin);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (SecretKey) ois.readObject();

        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SecretKey generatePBEKey(String mdp) {
        SecretKey clepbe = null;
        //on appelle Transforme le mot de passe en tableau de Char
        char[] password = mdp.toCharArray();
        PBEKeySpec pbeSpec = new PBEKeySpec(password, salt, iteration, keysize);

        //on vide le tableau de char password
        mdp = null;
        try {
            //on appelle le KDF: PBEKeySpec pour construire une clé
            SecretKeyFactory skf = SecretKeyFactory.getInstance(kdf);
            //mixer la cle pbe
            SecretKey k = skf.generateSecret(pbeSpec);
            // comme le nom de l'algo nest pas encore donne alors on construit une nouvelle cle
            byte[] contenu = k.getEncoded();
            clepbe = new SecretKeySpec(contenu, algo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return clepbe;

    }

    @Override
    public boolean saveKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecretKey loadKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean saveHexKey(SecretKey k, String chemin, String password) {
        try {
            // le contenu de la cle
            byte[] contenu = k.getEncoded();
            // contruction de cle avec mot de pass
            SecretKey lacle = generatePBEKey(password);
            // creation de Cipher en mode Encryption
            Cipher chiffreur = Cipher.getInstance(transform);
            // initialiser le chiffreur en mode chiffrement 
            chiffreur.init(Cipher.ENCRYPT_MODE, lacle,
                    new IvParameterSpec(iv.getBytes()));
            // chiffrement
            byte[] s_contenu = chiffreur.doFinal(contenu);
            // l'encodage en HEX
            String s = this.bytesToHex(s_contenu);
            // creation de flux de sortie ver le fichier
            FileOutputStream fos = new FileOutputStream(chemin);
            // Bufferiser avec PrintWriter pour avoir un flux de caracteres
            PrintWriter pw = new PrintWriter(fos, true);
            // ecriture dans le flux les caracteres
            pw.print(s);
            //fermeture des flux
            pw.close();
            fos.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public SecretKey loadHexKey(String chemin, String password) {
        FileInputStream fis = null;
        try {
            StringBuilder sb = new StringBuilder();
            fis = new FileInputStream(chemin);
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {
                String next = sc.nextLine();
                sb.append(next);
            }
            byte[] s_contenu = hextoBytes(sb.toString());
            // contruction de cle avec mot de pass
            SecretKey lacle = generatePBEKey(password);
            // creation de Cipher en mode Encryption
            Cipher chiffreur = Cipher.getInstance(transform);
            // initialiser le chiffreur en mode chiffrement 
            chiffreur.init(Cipher.DECRYPT_MODE, lacle,
                    new IvParameterSpec(iv.getBytes()));
            // dechiffrement
            byte[] contenu = chiffreur.doFinal(s_contenu);
            return new SecretKeySpec(contenu, algo);
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2 + 1] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public byte[] hextoBytes(String message) {
        byte[] bytes = new byte[message.length() / 2];
        char[] hexChars = message.toCharArray();
        for (int j = 0; j < bytes.length; j++) {
            byte v0 = (byte) Character.digit(hexChars[2 * j], 16);
            byte v1 = (byte) Character.digit(hexChars[2 * j + 1], 16);
            bytes[j] = (byte) (16 * v1 + v0);
        }
        return bytes;
    }

    @Override
    public boolean cipherProcess(SecretKey k, String fileToencrypt, String encryptedFile, int mode, boolean deleteAfter) {
        try {
            FileInputStream fis = new FileInputStream(fileToencrypt);
            FileOutputStream fos = new FileOutputStream(encryptedFile);
            Cipher chiffreur = Cipher.getInstance(transform);
            // initialiser le chiffreur en mode chiffrement
            chiffreur.init(mode, k,
                    new IvParameterSpec(iv.getBytes()));
            CipherInputStream cis = new CipherInputStream(fis, chiffreur);
            byte[] buffer = new byte[1024 * 1024];
            int nombrebytes = 0;
            while ((nombrebytes = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, nombrebytes);
            }
            fos.close();
            cis.close();
            fis.close();
            if (deleteAfter) {
                File file = new File(fileToencrypt);
                file.delete();

            }
            return true;

        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean cipherProcessFolder(SecretKey k, String FolderToencrypt, String encryptedFolder, int mode, boolean deleteAfter) {
        File folder = new File(FolderToencrypt);
        File outputFolder = new File(encryptedFolder);
        if (!outputFolder.isDirectory()) {
            outputFolder.mkdirs();
        }
        if (folder.isDirectory()) {
            File[] fils = folder.listFiles();
            for (File f : fils) {
                //System.out.println(f.getAbsolutePath());
                if (f.isFile()) {
                    String name = (mode == Cipher.ENCRYPT_MODE) ? f.getName() + ".enc" : f.getName().replace(".enc", "");
                    String newpath = outputFolder.getAbsolutePath() + File.separator + name;
                    if (f.getAbsolutePath().equals(newpath)) {
                        newpath = outputFolder.getAbsolutePath() + File.separator + "Copy " + name;
                    }

                    System.out.println(newpath);
                    cipherProcess(k, f.getAbsolutePath(), newpath, mode, deleteAfter);
                } else if (f.isDirectory()) {
                    cipherProcessFolder(k, f.getAbsolutePath(),
                            outputFolder.getAbsolutePath() + File.separator + f.getName(), mode, deleteAfter);
                    if (!f.getAbsolutePath().equals(outputFolder.getAbsolutePath() + File.separator + f.getName())) {
                        f.delete();
                    }
                }
            }
        }
        return true;
    }

    public boolean cipherProcessFolderarallel(SecretKey k, String FolderToEncrypt, String encryptedFolder, int mode, boolean deleteAfter) {
        File folder = new File(FolderToEncrypt);
        File outputFolder = new File(encryptedFolder);

        // Créer le dossier de sortie s'il n'existe pas
        if (!outputFolder.isDirectory()) {
            outputFolder.mkdirs();
        }

        // Vérifier si le dossier à chiffrer existe et est un dossier
        if (folder.isDirectory()) {
            // Utiliser parallelStream pour traiter les fichiers et dossiers en parallèle
            Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .parallel() // Convertir le stream en parallelStream
                    .forEach(file -> {
                        String fileName = file.getName();
                        String filePath = file.getAbsolutePath();
                        String outputFilePath = outputFolder.getAbsolutePath() + File.separator;

                        if (file.isFile()) {
                            // Déterminer le nom du fichier pour le chiffrement ou le déchiffrement
                            String newName = (mode == Cipher.ENCRYPT_MODE) ? fileName + ".enc" : fileName.replace(".enc", "");
                            String newFilePath = outputFilePath + newName;

                            // Résoudre le conflit de nom de fichier
                            if (filePath.equals(newFilePath)) {
                                newFilePath = outputFilePath + "Copy " + newName;
                            }

                            System.out.println(newFilePath);
                            cipherProcess(k, filePath, newFilePath, mode, deleteAfter);
                        } else if (file.isDirectory()) {
                            // Traitement récursif pour les sous-dossiers
                            cipherProcessFolder(k, filePath, outputFilePath + fileName, mode, deleteAfter);

                            // Supprimer le dossier après traitement s'il ne correspond pas au dossier de sortie
                            if (deleteAfter && !filePath.equals(outputFilePath + fileName)) {
                                file.delete();
                            }
                        }
                    });
        }
        return true; // Le traitement est terminé avec succès
    }

    @Override
    public boolean savePrivateKey(PrivateKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PrivateKey loadPriveKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean HybridEnCrypt(PublicKey k, String fileToencrypt, String encreptedFile) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean HybridDenCrypt(PrivateKey k, String fileToencrypt, String encreptedFile) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static byte[] packKeyAndIv(Key key, IvParameterSpec ivSpec) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        bOut.write(ivSpec.getIV());
        bOut.write(key.getEncoded());

        return bOut.toByteArray();
    }

    private static Object[] unpackKeyAndIV(byte[] data) {
        byte[] keyD = new byte[16];
        byte[] iv = new byte[data.length - 16];

        return new Object[]{
            new SecretKeySpec(data, 16, data.length - 16, "AES"),
            new IvParameterSpec(data, 0, 16)
        };
    }

}
