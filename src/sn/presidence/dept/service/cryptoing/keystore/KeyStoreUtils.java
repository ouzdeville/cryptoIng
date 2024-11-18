package sn.presidence.dept.service.cryptoing.keystore;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import javax.security.auth.x500.X500PrivateCredential;

public class KeyStoreUtils {

    private static final int VALIDITY_PERIOD = 365 * 24 * 60 * 60 * 1000; // 1 an
    static Component frame = null;

    /**
     * Keystore est une methode qui permet d'initialiser un keyStore a partir
     * d'un DN du CA. 1-Ouverture qu Keystore 2-Creation du Credential 3- Entree
     * de certificat 4- Entree de cle 5- Deversement sur Fichier
     *
     * @param keyStore Chemin de fichier du keystore
     * @param storepass Mot de Passe Du initKeyStore
     * @param rootDN DN du CA
     */
    public static void initKeyStore(String keyStore, String storepass, X500PrivateCredential rootCredential, String storeType) {

        java.security.cert.Certificate[] chain = new java.security.cert.Certificate[1];
        try {
            char[] password = storepass.toCharArray();
            File file = new File(keyStore);
            KeyStore store = KeyStore.getInstance(storeType);
            store.load(null, null);
            // X500PrivateCredential rootCredential = CredentialUtils.createDefaultRootCredential(rootDN);

            store.setCertificateEntry(rootCredential.getAlias(), rootCredential.getCertificate());

            chain[0] = rootCredential.getCertificate();
            store.setKeyEntry(rootCredential.getAlias(), rootCredential.getPrivateKey(), password, chain);
            FileOutputStream fos = new java.io.FileOutputStream(file);
            store.store(fos, password);
            fos.close();
            //String file1 = file.getName();
            //JOptionPane.showMessageDialog(frame, "Le initKeyStore:  " + file1 + "  est bien créer");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean EntreedeCle(String chemin_keystore, String storepass, X500PrivateCredential credential, String key_pass, java.security.cert.Certificate[] chain, String storeType) {
        try {
            KeyStore store = KeyStore.getInstance(storeType);

            // initialize à partir de rien
            FileInputStream fis = new java.io.FileInputStream(chemin_keystore);
            store.load(fis, storepass.toCharArray());

            store.setKeyEntry(credential.getAlias(), credential.getPrivateKey(), key_pass.toCharArray(), chain);
            FileOutputStream fos = new java.io.FileOutputStream(chemin_keystore);

            store.store(fos, storepass.toCharArray());
            fos.close();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean EntreedeCertificat(String chemin_keystore, String storepass, X500PrivateCredential credential, String storeType) {
        try {
            KeyStore store = KeyStore.getInstance(storeType);

            // initialize à partir de rien
            FileInputStream fis = new java.io.FileInputStream(chemin_keystore);
            store.load(fis, storepass.toCharArray());

            store.setCertificateEntry(credential.getAlias(), credential.getCertificate());
            FileOutputStream fos = new java.io.FileOutputStream(chemin_keystore);

            store.store(fos, storepass.toCharArray());
            fos.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static Key recup_Key(String chemin_keystore, String storepass, String alias, String key_pass, String storeType) {
        Key key = null;
        try {
            KeyStore store = KeyStore.getInstance(storeType);

            // initialize à partir de rien
            FileInputStream fis = new java.io.FileInputStream(chemin_keystore);
            store.load(fis, storepass.toCharArray());
            key = store.getKey(alias, key_pass.toCharArray());
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return key;
    }

    public static X509Certificate recup_Certificate(String chemin_keystore, String storepass, String alias, String storeType) {
        X509Certificate cert = null;
        try {
            KeyStore store = KeyStore.getInstance(storeType);

            // initialize à partir de rien
            FileInputStream fis = new java.io.FileInputStream(chemin_keystore);
            store.load(fis, storepass.toCharArray());
            cert = (X509Certificate) store.getCertificate(alias);
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cert;
    }

    public static X509Certificate[] recup_Certificate_chain(String chemin_keystore, String storepass, String alias, String storeType) {
        X509Certificate[] cert = null;
        try {
            KeyStore store = KeyStore.getInstance(storeType);

            // initialize à partir de rien
            FileInputStream fis = new java.io.FileInputStream(chemin_keystore);
            store.load(fis, storepass.toCharArray());
            cert = (X509Certificate[]) store.getCertificateChain(alias);
            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cert;
    }

    static void InitClientKeyStore(String storefile, String psw, String storeType) {

        try {
            char[] password = psw.toCharArray();
            File file = new File(storefile);
            KeyStore store = KeyStore.getInstance(storeType);
            store.load(null, null);
            FileOutputStream fos = new java.io.FileOutputStream(file);
            store.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static KeyStore getKeystore(X500PrivateCredential rootCredential,X509Certificate rootCACertificate, String storeType) {

        java.security.cert.Certificate[] chain = new java.security.cert.Certificate[2];
        try {
            //char[] password = storepass.toCharArray();
            KeyStore store = KeyStore.getInstance(storeType, "BC");
            store.load(null, null);

            chain[0] = rootCredential.getCertificate();
            chain[1] = rootCACertificate;
            store.setKeyEntry(rootCredential.getAlias(), rootCredential.getPrivateKey(), null, chain);
            
            return store;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static X500PrivateCredential recupKeyPair(InputStream fis, String storepass, String storeType) {
        X509Certificate cert = null;
        PrivateKey key = null;
        X500PrivateCredential x500 = null;
        try {
            KeyStore store = KeyStore.getInstance(storeType);
            store.load(fis, storepass.toCharArray());
            Enumeration en = store.aliases();
            String alias = null;
            if (en.hasMoreElements()) {

                alias = (String) en.nextElement();
                System.out.println("alias: " + alias);
                cert = (X509Certificate) store.getCertificate(alias);
                key = (PrivateKey) store.getKey(alias, storepass.toCharArray());
                x500 = new X500PrivateCredential(cert, key, alias);
                //System.out.println(cert);
                //System.out.println(CertificatUtils.convertPriveKey(key, alias));

            }

            fis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return x500;
    }
}
