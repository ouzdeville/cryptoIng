/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoing;

import javax.crypto.SecretKey;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import sn.presidence.dept.service.cryptoing.tool.ICrypto;

/**
 *
 * @author asus
 */
public class TestUnitaires {
    public static void main(String[] args) {
        ICrypto crypto=new CryptoImpl();
        
        SecretKey k = crypto.generateKey();
        crypto.saveKey(k, "macle.ext", null);
    }
}
