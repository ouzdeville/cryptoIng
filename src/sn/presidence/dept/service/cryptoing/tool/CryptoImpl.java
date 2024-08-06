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
import static sn.presidence.dept.service.cryptoing.tool.ICrypto.algo;


/**
 *
 * @author tapha
 */
public class CryptoImpl implements ICrypto {

    @Override
    public byte[] generateSeedTrullyRandom() {
       //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       System.out.println("Faire bouger la souris");
       byte[] buffer=new byte[ICrypto.keysize/8];
       int i =0; //initialisation de la souris
       Point precedent=new Point(0, 0);
       do {
           Point p=MouseInfo.getPointerInfo().getLocation(); // utlisation boucle do whlie
           if(p.equals(precedent)) continue;
           buffer[i]=(byte) p.x;
           i++;
           precedent=p;
           System.out.print("#");
           
           
       } while(i<buffer.length);
       
       return buffer;
       
    }

    @Override
    public SecretKey generateKey() {
        try {
            //   try {
            KeyGenerator kg=KeyGenerator.getInstance(algo);
            SecureRandom sr=SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(generateSeedTrullyRandom());
            kg.init (keysize, sr);
            return kg.generateKey();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public String bytesToHex(byte[] tab) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public byte[] hextoBytes(String chaine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
