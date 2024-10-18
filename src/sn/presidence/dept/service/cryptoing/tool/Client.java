/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author ousmane3ndiaye
 */
public class Client extends Thread {
    
    public Socket socket;
    public Client(Socket socket) {
        this.socket=socket;
    }

    @Override
    public void run() {
         // send hello au serveur
            //echange de cle sym
            //
            // recevoir la commande du serveur et repondre
        try {
            InputStream is = this.socket.getInputStream();
            OutputStream out = this.socket.getOutputStream();
            OutputStreamWriter osw=new OutputStreamWriter(out);
            CryptoImpl crypto=new CryptoImpl();
            SecretKey k = crypto.generatePBEKey("1234");
            IvParameterSpec iv = new IvParameterSpec(crypto.iv.getBytes());
            byte[] hello = crypto.processData("Hello serveur".getBytes(), k,
                    Cipher.ENCRYPT_MODE, iv);
            String helloHex = crypto.bytesToHex(hello);
            osw.write(helloHex);
            osw.flush();
           
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
}
