/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.tool;

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
    public final String kdf = "PBKDF2WithHmacSHA1";
    public final int iteration = 1000;
    public final byte[] salt = "MO5-°HG3YEH255367gdsjhgd".getBytes();
    //Faire un programme pour recupérer un seed avec une bonne entropie

    
   
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

    public boolean saveHexKey(SecretKey k, String chemin, String password);

    public SecretKey loadHexKey(String chemin, String password);

    
    /**
     *
     * @param k : la clé à stocker
     * @param chemin : le chemin du fichier qui doit stocker la clé
     * @param password : le mot qui permettra de chiffrer la clé avant stockage.
     * @return un bloolean pour savoir si reussi ou pas
     */
    public boolean saveKey(SecretKey k, String chemin, String password);

    public boolean saveKey(SecretKey k, String chemin);

    /**
     * Pour charger la clé depuis un fichier
     *
     * @param chemin : le chemin du fichier qui contient la clé à charger
     * @param password : le mot de passe pour déchiffrer la clé
     * @return la clé retrouvée
     */
    public SecretKey loadKey(String chemin, String password);

    public SecretKey loadKey(String chemin);

    public boolean cipherProcess(SecretKey k, String fileToencrypt, String encryptedFile, int mode);
    
    public boolean cipherProcessFolder(SecretKey k, String FolderToencrypt, String encryptedFolder, int mode);

    
    public boolean HybridEnCrypt(PublicKey k, String fileToencrypt, String encreptedFile);

    public boolean HybridDenCrypt(PrivateKey k, String fileToencrypt, String encreptedFile);

    public boolean savePrivateKey(PrivateKey k, String chemin, String password);

    public PrivateKey loadPriveKey(String chemin, String password);

}
