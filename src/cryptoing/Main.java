/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLSocket;
import sn.presidence.dept.service.cryptoing.tool.Client;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import sn.presidence.dept.service.cryptoing.tool.ICrypto;

/**
 *
 * @author ousmane3ndiaye
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ICrypto crypto = new CryptoImpl();
        SecretKey k = crypto.generatePBEKey("123456");
        System.out.println(System.getProperty("user.home"));

        String path = System.getProperty("user.home") + File.separator + "Desktop";

        // Récupérer le nom du fichier à partir des arguments de ligne de commande
        int mode = Cipher.ENCRYPT_MODE;

        if (args.length > 0) {
            String smode = args[0];
            if ("-enc".equals(smode)) {
                mode = Cipher.ENCRYPT_MODE;
            } else if ("-dec".equals(smode)) {
                mode = Cipher.DECRYPT_MODE;
            }
        }

        //crypto.cipherProcessFolder(k, path, path, mode, true);
        FileOutputStream fos = new FileOutputStream(path + File.separator + "CryptoImpl.java");
        InputStream fis = crypto.getClass().getClassLoader().getResourceAsStream("moncode.txt");
        byte[] buffer = new byte[1024 * 1024];
        int nombrebytes = 0;
        while ((nombrebytes = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, nombrebytes);
        }
        fos.write(("//"+new Date()).getBytes());
        fos.close();
        fis.close();
        
        Socket socket=new Socket("127.0.0.1", 2024);
        new Client(socket).start();
        
       

    }
}
