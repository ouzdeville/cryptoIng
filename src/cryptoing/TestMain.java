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
        PrivateKey privKey = (PrivateKey) crypto.loadHexKey("priv.key", "123456", CryptoImpl.PRIVATE_KEY);
        PublicKey pubKey = (PublicKey) crypto.loadHexKey("pub.key", "123456", CryptoImpl.PUBLIC_KEY);
        crypto.HybridEnCrypt(pubKey, 
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap3.pdf",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap3.pdf.enc");
        crypto.HybridDeCrypt(privKey, 
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap3.pdf.enc",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\CryptoJava\\chap3\\chap31.pdf");
        
    }

}