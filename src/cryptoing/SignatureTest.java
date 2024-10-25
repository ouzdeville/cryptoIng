/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 *
 * @author ousmane3ndiaye
 */
public class SignatureTest {
    
    
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kp= KeyPairGenerator.getInstance("DSA");
        kp.initialize(2048);
        KeyPair keyPair = kp.genKeyPair();
        
        Signature signataire=Signature.getInstance("SHA256withDSA");
        signataire.initSign(keyPair.getPrivate());
        
        String msg="Hello ...";
        
        signataire.update(msg.getBytes());
        byte[] mysign = signataire.sign();
        
        
        
        Signature verificateur=Signature.getInstance("SHA256withDSA");
        verificateur.initVerify(keyPair.getPublic());
        
        
        
        verificateur.update(msg.getBytes());
        boolean test = verificateur.verify(mysign);
        
        System.out.println(test);
        
        
        
        
        
    }
    
}
