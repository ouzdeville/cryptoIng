/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.awt.MouseInfo;
import java.awt.Point;
import java.security.NoSuchAlgorithmException;
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
            if(i >=(tab.length/2)){
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
    public boolean saveKey(SecretKey k, String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SecretKey loadKey(String chemin, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String bytesToHex(byte[] tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] hextoBytes(String chaine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean decrypt(SecretKey k, String fileToencrypt, String encreptedFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
