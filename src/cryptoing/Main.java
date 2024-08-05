/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

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
        System.out.println("");
        String keyString=crypto.bytesToHex(k.getEncoded());
        System.out.println(keyString);
        SecretKey k1 = crypto.generateKey();
        String keyString1=crypto.bytesToHex(k1.getEncoded());
        System.out.println("");
        System.out.println(keyString1);
    }
}
