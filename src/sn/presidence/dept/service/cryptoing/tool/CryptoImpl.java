/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author asus
 */
public class CryptoImpl implements ICrypto {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    @Override
    public byte[] generateSeedTrullyRandom() {
        byte[] tab = new byte[ICrypto.keysize / 8];
        Point p = MouseInfo.getPointerInfo().getLocation();
        Point p1;
        int i = 0;
        System.out.println("Faire Bouger Votre Souris");
        while (true) {
            p1 = MouseInfo.getPointerInfo().getLocation();
            if (!p1.equals(p)) {
                p = p1;
                tab[2 * i] = (byte) p.x;
                tab[2 * i + 1] = (byte) p.y;
                i++;
                System.out.print("#");
            }
            if (i >= (tab.length / 2)) {
                break;
            }
        }

        return tab;
    }

    @Override
    public SecretKey generateKey() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(ICrypto.algo);
            kgen.init(keysize, new SecureRandom(generateSeedTrullyRandom()));
            return kgen.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Changer l'Algo");
        }
        return null;
    }

    @Override
    public boolean saveKey(SecretKey k, String chemin) {
        try {
            FileOutputStream fos = new FileOutputStream(chemin);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(k);
            oos.close();
            fos.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public SecretKey loadKey(String chemin) {
        try {
            FileInputStream fis = new FileInputStream(chemin);
            ObjectInputStream ois = new ObjectInputStream(fis);
            SecretKey k = (SecretKey) ois.readObject();
            return k;
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public boolean saveKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SecretKey loadKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String bytesToHex(byte[] bytes) {
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
    public boolean saveHexKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SecretKey loadHexKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean encrypt(SecretKey k, String fileToencrypt, String encreptedFile) {
        try {

            FileInputStream fis = new FileInputStream(fileToencrypt);
            FileOutputStream fos = new FileOutputStream(encreptedFile);
            Cipher cif = Cipher.getInstance(transform);
            cif.init(Cipher.ENCRYPT_MODE, k, new IvParameterSpec(iv.getBytes()));

            CipherInputStream cis = new CipherInputStream(fis, cif);
            byte[] buffer = new byte[1024];
            int nbrebyteslus = 0;
            //Chiffrement du fichier 
            while ((nbrebyteslus = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, nbrebyteslus);
            }
            fos.close();
            cis.close();
            fis.close();
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean decrypt(SecretKey k, String fileToencrypt, String encreptedFile) {
        try {

            FileInputStream fis = new FileInputStream(fileToencrypt);
            FileOutputStream fos = new FileOutputStream(encreptedFile);
            Cipher cif = Cipher.getInstance(transform);
            cif.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(iv.getBytes()));

            CipherInputStream cis = new CipherInputStream(fis, cif);
            byte[] buffer = new byte[1024];
            int nbrebyteslus = 0;
            //Chiffrement du fichier 
            while ((nbrebyteslus = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, nbrebyteslus);
            }
            fos.close();
            cis.close();
            fis.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    /**
     * PBE= Password-based Encryption : chiffrement symétrique dont la clé est obtenue à partir d'un mdp
     * Description: on se base sur un KDF: Key Derivation Functions
     * Input :
     * 1- Password
     * 2- HMAC (Hash et/ou Chiffrement) =kdf
     * 3- SALT  
     * 4- ITERATIONS
     * 5- Taille de clés
     * 
     * pour obtenir une spécification PBEKeySpec
     * Ensuite il faut utiliser une KeyFactory (une fabrique de clé) pour obtenir une clé de *algo*
     */
    public SecretKey generatePBEKey(String password) {
        SecretKey key=null;
        try {
            
            PBEKeySpec pbe = new PBEKeySpec(password.toCharArray(), salt, iteration, keysize);
            password="";
            
            SecretKeyFactory skf= SecretKeyFactory.getInstance(kdf);
            key=new SecretKeySpec(skf.generateSecret(pbe).getEncoded(), algo);
            
            
        } catch (Exception ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return key;  
    }

    @Override
    public boolean HybridEnCrypt(PublicKey k, String fileToencrypt, String encreptedFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean HybridDenCrypt(PrivateKey k, String fileToencrypt, String encreptedFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean savePrivateKey(PrivateKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PrivateKey loadPriveKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
