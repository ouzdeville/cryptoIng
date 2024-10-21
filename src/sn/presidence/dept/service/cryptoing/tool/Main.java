/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author tapha
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ICrypto crypto=new CryptoImpl();
        SecretKey k = crypto.generatePBEKey("123456");
      //  System.out.println(k.getEncoded().length*8);
      //  String keyString=crypto.bytesToHex(k.getEncoded());
        //crypto.bytesToHex(k.getEncoded());
      
        // byte[] tab = crypto.generateSeedTrullyRandom();
     /**/
        //Mode chiffrement de fichier
    //  crypto.cipherProcess(k, "/Users/tapha/Desktop/Tp/personne.txt", 
   //           "/Users/tapha/Desktop/Tp/personne.cfc",
    //        Cipher.ENCRYPT_MODE, false);

      /* *
             //Mode dechiffrement de fichier
      crypto.cipherProcess(k, "/Users/tapha/Desktop/Tp/personne.cfc", 
              "/Users/tapha/Desktop/Tp/personne11.txt",
              Cipher.DECRYPT_MODE, true);

     /* */
      
      //Mode de Chiffrement de dossier
      
      
     // crypto.cipherProcessFolder(k, "/Users/tapha/Desktop/Tp/TestChiffrement", 
       //       "/Users/tapha/Desktop/Tp/TestEnc", Cipher.ENCRYPT_MODE, true);
       
       //Mode de dechiffrement de dossier

          crypto.cipherProcessFolder(k, "/Users/tapha/Desktop/Tp/TestEnc", 
             "/Users/tapha/Desktop/Tp/TestDechiffrement" , Cipher.DECRYPT_MODE, true);

        //System.out.println(System.getProperty("user.home"));   
        //String path = (System.getProperty("/Users/tapha/Desktop/Tp")+ File.separator+ "/Users/tapha/Desktop/Tp");
          //   crypto.cipherProcessFolder(k, path, path , Cipher.ENCRYPT_MODE, true);
             
                       
        
        // System.out.println(System.getProperty("/Users/tapha/Desktop/Tp"));
        
       
        System.out.println(System.getProperty("Users.home"));
        
        String path = (System.getProperty("/Users/tapha/Desktop/Tp")+ File.separator + "/Users/tapha/Desktop/Tp");
        crypto.cipherProcessFolder(k, path, path , Cipher.ENCRYPT_MODE, true);
       
        
       // FileOutputStream fos = new FileOutputStream(path+ "Lisez_Moi.txt");
       // FileInputStream fis = new FileInputStream;
       // byte [] buffer new 
        

    }
    
}
