/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sn.presidence.dept.service.cryptoing.tool;

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
        SecretKey k = crypto.generateKey();
        System.out.println(k.getEncoded().length*8);
       // String keyString=crypto.bytesToHex(tab)
        crypto.bytesToHex(k.getEncoded());
        
         byte[] tab = crypto.generateSeedTrullyRandom();
      
      
      //Exercice 
    }
    
}
