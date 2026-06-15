/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.IvParameterSpec;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;
import static sn.presidence.dept.service.cryptoing.tool.ICrypto.iv;

/**
 *
 * @author ousmane3ndiaye
 */
public class PartageCles {

    /**
     * 1 chacun envoye la cle publique a l'autre
     *
     * 2 Recevoir la cle publique de l'autre 3 Envoie du secret chiffre
     * (Generation, chiffrement, encodage et envoie) 4 recevoir le chiffre du
     * secret (decodage, dechiffrement, convert en string) 5 concatenation des
     * secrets
     *
     * @return
     */
    public static String partageDeClesPubliques(Socket socket, boolean ordre) throws Exception {
        System.out.println("DEBUT Public key Encryption");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] buffer = new byte[16];
        sr.nextBytes(buffer);
        CryptoImpl crypto = new CryptoImpl();
        KeyPair kpair = crypto.generateKeyPair(buffer);

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(kpair.getPublic());

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        PublicKey saKpub = (PublicKey) ois.readObject();
        sr.nextBytes(buffer);
        String monSecret = new String(buffer);
        IvParameterSpec ivParam = new IvParameterSpec(iv.getBytes());
        byte[] s = crypto.processData(monSecret.getBytes(), saKpub, Cipher.ENCRYPT_MODE, ivParam);
        String Csa = crypto.bytesToHex(s);
        oos.writeObject(Csa);

        String Csb = (String) ois.readObject();
        byte[] sb = crypto.hextoBytes(Csb);
        s = crypto.processData(sb, kpair.getPrivate(), Cipher.DECRYPT_MODE, ivParam);
        String sonSecret = new String(s);
        System.out.println("FIN Public key Encryption");
        if (ordre) {
            return monSecret + sonSecret;
        } else {
            return sonSecret + monSecret;
        }

    }
    
    
    /**
     * 1 chacun envoye la cle publique a l'autre
     *
     * 2 KeyAgreement phase
     * 
     * 3 Genrerer le secret
     *
     * @return
     */
    public static String partageDeClesDH(Socket socket, boolean ordre) throws Exception {
        System.out.println("Debut DH");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] buffer = new byte[16];
        sr.nextBytes(buffer);
        CryptoImpl crypto = new CryptoImpl();
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("DH");
        //kpg.initialize();
        KeyPair kpair = kpg.generateKeyPair();

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(kpair.getPublic());

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        PublicKey saKpub = (PublicKey) ois.readObject();
        
        
        KeyAgreement ka=KeyAgreement.getInstance("DH");
        ka.init(kpair.getPrivate());
        ka.doPhase(saKpub, true);
        byte[] s = ka.generateSecret();
        System.out.println("FIN DH");
        return new String(s);

    }

}
