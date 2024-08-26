/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.crypto.SecretKey;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import sn.presidence.dept.service.cryptoing.tool.ICrypto;

/**
 *
 * @author ousmane3ndiaye
 */
public class Main {
    public static void main(String[] args) {
        ICrypto crypto=new CryptoImpl();
        SecretKey k = crypto.generateKey();
        crypto.saveHexKey(k, "keySecret.ing", "123456");
        
        SecretKey k1 = crypto.loadHexKey("keySecret.ing", "123456");
        System.out.println(crypto.bytesToHex(k.getEncoded()));
        System.out.println(crypto.bytesToHex(k1.getEncoded()));
        
    }
}
