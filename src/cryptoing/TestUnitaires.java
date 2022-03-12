/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoing;

import javax.crypto.SecretKey;
import javax.swing.JFileChooser;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import sn.presidence.dept.service.cryptoing.tool.ICrypto;

/**
 *
 * @author asus
 */
public class TestUnitaires {

    public static void main(String[] args) {
        ICrypto crypto = new CryptoImpl();
         SecretKey k=crypto.generatePBEKey("fortPWD");
        JFileChooser jfc = new JFileChooser();
        if (true) {
            jfc.setDialogTitle("Choisir le fichier à dechiffrer");
            int result = jfc.showOpenDialog(jfc);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filetoEncrypt = jfc.getSelectedFile().getAbsolutePath();
                jfc.setDialogTitle("Choisir le fichier de sortie");
                result = jfc.showSaveDialog(jfc);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String encryptedfile = jfc.getSelectedFile().getAbsolutePath();
                    boolean b = crypto.decrypt(k, filetoEncrypt, encryptedfile);
                    if (b) {
                        System.out.println("DeChifré avec success");   
                    } else {
                        System.out.println("Error");
                    }
                           
                }

            }

        }

    }
}
