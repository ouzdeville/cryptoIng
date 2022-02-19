/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoing;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author asus
 */
public class TestCrypto {
    
    public static void main(String[] args) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom scr  = SecureRandom.getInstance("SHA1PRNG");
            scr.setSeed("Bonjour Ingieneurs###########".getBytes());
            kgen.init(256, scr);
            SecretKey macle = kgen.generateKey();
            System.out.println(macle.getEncoded().length*8 +" bits");
            
            
            //Chiffrement
            Cipher ciph = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("ingenieurs123456".getBytes()); // taille multiple de 16
            ciph.init(Cipher.ENCRYPT_MODE, macle, iv);
            byte[] ciphertext = ciph.doFinal("Bonjour les Ingénieurs, Bon Week-end".getBytes());
            
            String rmessage=bytesToHex(ciphertext);
            System.out.println(rmessage);
            
            
            
            
            /// Dechiffrement
            byte [] rciphertext=hextoBytes(rmessage);
            
            //ciphertext[0]=1;
            ciph.init(Cipher.DECRYPT_MODE, macle, iv);
            byte[] deciphertext = ciph.doFinal(ciphertext);
            
            System.out.println(new String(deciphertext));
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(TestCrypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String bytesToHex(byte[] ciphertext) {
        
        return "";
    }

    private static byte[] hextoBytes(String rmessage) {
        
        return null;
    }

   
}
