/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;

/**
 *
 * @author ousmane3ndiaye
 */
public class SignatureTest {
    
    
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kp= KeyPairGenerator.getInstance("DSA");
        kp.initialize(2048);
        KeyPair signKeyPair = kp.genKeyPair();
        
        CryptoImpl crypto = new CryptoImpl();
        KeyPair encryptKeypair = crypto.generateKeyPair("jhgjhgjh".getBytes());
        
        crypto.HybridEnCryptSign(encryptKeypair.getPublic(),signKeyPair.getPrivate(),
                "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf.cry");
        
        
         crypto.HybridDeCryptSign(encryptKeypair.getPrivate(),signKeyPair.getPublic(),
                 "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf.cry",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap11.pdf");
           
    }
    
}
