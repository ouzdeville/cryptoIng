/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author asus
 */
public interface ICrypto {

    public static final String algo = "AES";
    public static final int keysize = 256;
    public static final String transform = "AES/CBC/PKCS5Padding";
    public static final String iv = "16carat_res01251";
    public final String kdf = "PBKDF2WithHmacSHA256";
    public final int iteration = 1000;
    public final byte[] salt = "MO5-°HG3YEH255367gdsjhgd".getBytes();
    //Faire un programme pour recupérer un seed avec une bonne entropie
    public static final String algoAsym = "RSA";
    public static final int keysizeAsym = 2048;

    
   
    /**
     * Recupérer des coordonnees différentes de la souris de l'utilisateur pour
     * avoir 256 bits aléatoires.
     *
     * @return
     */
    public byte[] generateSeedTrullyRandom();
       
      
    /**
     * la méthode generateKey permet de générer une clé à partir des paramètres
     * spécifiés.
     *
     * @return
     */
    public SecretKey generateKey();

    
    public SecretKey generatePBEKey(String password);

    public String bytesToHex(byte[] tab);

    public byte[] hextoBytes(String chaine);

    

    
    
    

    public boolean cipherProcess(SecretKey k, String fileToencrypt, String encryptedFile, int mode, boolean deleteAfter);
    
    public boolean cipherProcessFolder(SecretKey k, String FolderToencrypt, String encryptedFolder, int mode, boolean deleteAfter);

    
    public KeyPair generateKeyPair(byte [] seed);

    public Key loadHexKey(String chemin, String password, int type);
    public boolean saveHexKey(Key k, String chemin, String password);
    
    public boolean HybridEnCrypt(PublicKey k, String fileToencrypt, String encreptedFile);

    public boolean HybridDenCrypt(PrivateKey k, String fileToencrypt, String encreptedFile);

   

}
