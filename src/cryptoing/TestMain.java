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
    public static void main(String[] args) throws Exception {
        CryptoImpl crypto = new CryptoImpl();
        byte[] h = crypto.hashFile("C:\\Users\\ousmane3ndiaye\\Documents\\NetBeansProjects\\cryptoIng\\mykey.sn");
        String hString = crypto.bytesToHex(h);
        System.out.println(hString);
    }
}