/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import sn.presidence.dept.service.cryptoing.tool.ICrypto;

/**
 *
 * @author ousmane3ndiaye
 */
public class TestMain {
    
    public static void main(String[] args) {
        ICrypto crypto = new CryptoImpl();
        KeyPair kpair = crypto.generateKeyPair(crypto.generateSeedTrullyRandom());
        crypto.saveHexKey(kpair.getPublic(), "pub.key", "123456");
        crypto.saveHexKey(kpair.getPrivate(), "priv.key", "123456");
        crypto.HybridEnCrypt(kpair.getPublic(), 
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap3.pdf",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap3.pdf.enc");
        
    }

}