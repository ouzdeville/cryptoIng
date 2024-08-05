/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.awt.MouseInfo;
import java.awt.Point;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 *
 * @author asus
 */
public class CryptoImpl implements ICrypto {

    @Override
    public byte[] generateSeedTrullyRandom() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        System.out.println("Faire bouger la souris");
        byte[] buffer=new byte[ICrypto.keysize/8];
        int i=0;
        Point precedent=new Point(0, 0);
        do {
          Point p=MouseInfo.getPointerInfo().getLocation();
          if(p.equals(precedent)) continue;
          buffer[i]=(byte) p.x;
          i++;
          precedent=p;
          System.out.print("#");
        }while(i<buffer.length);
        
        return buffer;
    }

    @Override
    public SecretKey generateKey() {

        try {
            KeyGenerator kg=KeyGenerator.getInstance(algo);
            SecureRandom sr=SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(generateSeedTrullyRandom());
            kg.init(keysize, sr);
            return kg.generateKey();
            
            
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SecretKey generatePBEKey(String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean saveKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean saveKey(SecretKey k, String chemin) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecretKey loadKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecretKey loadKey(String chemin) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public boolean saveHexKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecretKey loadHexKey(String chemin, String password) {
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

    @Override
    public boolean savePrivateKey(PrivateKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PrivateKey loadPriveKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cipherProcess(SecretKey k, String fileToencrypt, String encryptedFile, int mode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean cipherProcessFolder(SecretKey k, String FolderToencrypt, String encryptedFolder, int mode) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
