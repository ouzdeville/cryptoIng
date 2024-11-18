/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.security.KeyPair;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;

/**
 *
 * @author ousmane3ndiaye
 */
public class MacTest {

    public static void main(String[] args) throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            System.out.println(" Security.insertProviderAt(new BouncyCastleProvider(), 1);");
        }
        CryptoImpl crypto = new CryptoImpl();
        KeyPair kpair = crypto.generateKeyPair("jkhhjcsdf".getBytes());
        
        crypto.HybridEnCryptMAC(kpair.getPublic(), "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf.cry");
         crypto.HybridDeCryptMAC(kpair.getPrivate(), "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap1.pdf.cry",
                "C:\\Users\\ousmane3ndiaye\\Desktop\\chap1\\chap11.pdf");
        
    }
}
