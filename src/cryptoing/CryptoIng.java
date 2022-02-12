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
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;



/**
 *
 * @author asus
 */
public class CryptoIng {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            SecureRandom scr = SecureRandom.getInstance("SHA1PRNG");
            scr.setSeed("Bonjour Ingieneus".getBytes());
            byte[] buffer=new byte[32];
            scr.nextBytes(buffer);
            for (int i = 0; i < buffer.length; i++) {
                System.out.print(buffer[i]+" ");
            }
            System.out.println("");
            int a=scr.nextInt();
            System.out.println(a);
            a=scr.nextInt();
            System.out.println(a);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(256, scr);
            SecretKey key = kgen.generateKey();
            System.out.println(key.getEncoded().toString());
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CryptoIng.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
