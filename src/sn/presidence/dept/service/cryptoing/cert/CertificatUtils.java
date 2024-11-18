/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.presidence.dept.service.cryptoing.cert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
//import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;

import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

public class CertificatUtils {

    
    private static final int VALIDITY_PERIOD = 365 ; // 1 an
    public static final String provider = "BC";
    public static final String signProvider = "BC";
    public static final String P12_EXTENSTION = ".p12";
    public static final String PFX_EXTENSTION = ".pfx";
    public static final String CRL_EXTENSTION = ".crl";
    public static final String P7B_EXTENSTION = ".p7b";
    private static final String SERVER_BASE_REST_PKI_URL = "http://localhost:8080/rest/pki/";
    private static final String CRL_URL = "/crl";
    private static final String AIA_URL = "/cert";
    private static final String Default_ALGO = "RSA";
    private static final String signAlgo = "SHA256withRSA";
    private static final int keysize = 2048;

    

    /**
     * Generate a sample V1 certificate to use as a CA root certificate
     *
     * @param pair
     * @param DN "CN=xyz.sn, OU=My_Entreprise, O=My_Departement, L=Dakar,
     * ST=Senegal, C=SN";
     * @param serialnumber
     * @return
     * @throws java.security.cert.CertificateEncodingException
     * @throws java.security.NoSuchProviderException
     * @throws java.security.InvalidKeyException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.SignatureException
     */
    public static X509Certificate generateDefaultRootCert(KeyPair pair, String DN, BigInteger serialnumber) throws Exception {
// Date de début et fin de validité du certificat
        Date notBefore = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(notBefore);
        c.add(Calendar.DAY_OF_YEAR, VALIDITY_PERIOD);
        Date notAfter = c.getTime();

        // Construire le certificat avec X509v3CertificateBuilder
        X500Name subject = new X500Name(DN);
        X500Name issuer = subject;
        PublicKey entityKey = pair.getPublic();
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                serialnumber,
                notBefore,
                notAfter,
                subject,
                entityKey
        );

        // Extensions standards
        JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();

        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(entityKey));
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(entityKey));
        
        
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.basicConstraints,
                true, new BasicConstraints(true) // Certificat racine auto-signé
        );
        certBuilder.addExtension(
                org.bouncycastle.asn1.x509.Extension.keyUsage,
                true,
                new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature | KeyUsage.cRLSign)
        );
       

        

        // Signer le certificat avec la clé privée CA
        ContentSigner signer = new JcaContentSignerBuilder(signAlgo).setProvider(signProvider).build(pair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(signer);

        // Convertir en X509Certificate
        return new JcaX509CertificateConverter().setProvider(provider).getCertificate(certHolder);
    

       
    }

   

    /**
     * Generate a sample V3 certificate to use as an end entity certificate
     *
     * @param entityKey
     * @param caKey
     * @param caCert
     * @param DN "CN=xyz.sn, OU=My_Entreprise, O=My_Departement, L=Dakar,
     * ST=Senegal, C=SN";
     * @param serialNumber
     * @param serialnumber
     * @return
     * @throws java.security.cert.CertificateParsingException
     * @throws java.security.cert.CertificateEncodingException
     * @throws java.lang.Exception
     * @throws java.security.NoSuchProviderException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.SignatureException
     * @throws java.security.InvalidKeyException
     */
    public static X509Certificate generateDefaultEndEntityCert(
            PublicKey entityKey, PrivateKey caKey, X509Certificate caCert, String DN, 
            BigInteger serialNumber)
            throws Exception {

        // Générer les dates de validité du certificat
        Date notBefore = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(notBefore);
        c.add(Calendar.DAY_OF_YEAR, VALIDITY_PERIOD);
        Date notAfter = c.getTime();

        // Construire le certificate builder
        X500Name issuer = new X500Name(caCert.getSubjectX500Principal().getName());
        X500Name subject = new X500Name(DN);
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                serialNumber,
                notBefore,
                notAfter,
                subject,
                entityKey
        );

        // Ajouter les extensions
        JcaDigestCalculatorProviderBuilder digestCalcProviderBuilder = new JcaDigestCalculatorProviderBuilder();
        DigestCalculator digestCalculator = digestCalcProviderBuilder.build().get(CertificateID.HASH_SHA1);

        // Authority Key Identifier
        AuthorityKeyIdentifier authorityKeyIdentifier = new JcaX509ExtensionUtils().createAuthorityKeyIdentifier(caCert.getPublicKey());
        certBuilder.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);

        // Subject Key Identifier
        SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(entityKey);
        certBuilder.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);

        // Basic Constraints - pas une autorité de certification (false)
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));

        // Key Usage
        certBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));

        // Extended Key Usage - usage pour serveur
        certBuilder.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        // Signer le certificat
        ContentSigner signer = new JcaContentSignerBuilder(signAlgo).setProvider(signProvider).build(caKey);
        X509CertificateHolder certHolder = certBuilder.build(signer);

        // Convertir en X509Certificate
        return new JcaX509CertificateConverter().setProvider(provider).getCertificate(certHolder);
    }

    public static void ExportCertificat(X509Certificate xc, String fichier) throws CertificateEncodingException, FileNotFoundException, IOException {

        File file = null;
        byte[] buf = xc.getEncoded();
        FileOutputStream os = new FileOutputStream(fichier);
        //os.write(buf);
        Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));

        // BASE64Encoder encoder = new BASE64Encoder();
        String cert_begin = "-----BEGIN CERTIFICATE-----" + System.lineSeparator();
        String end_cert = System.lineSeparator() + "-----END CERTIFICATE-----" + System.lineSeparator();
        wr.write(cert_begin);
        wr.write(brutToBase64(buf));
        wr.write(end_cert);
        wr.flush();
        os.close();

    }

    

    /**
     * Les commandes ci-dessous (vers et à partir de l'extension .CER) sont donc
     * valables pour les .CER, .CRT et .PEM. Les certificats ayant pour
     * extension .PFX ou .P12 sont identiques (PKCS#12). Il vous est donc
     * possible de renommer l'extension des fichiers .PFX en .P12 inversement.
     *
     * @param fichier
     * @return
     */
    public static X509Certificate ChargeCertficat(String fichier) {
        FileInputStream fis = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", provider);
            fis = new FileInputStream(fichier);
            java.security.cert.Certificate cert = cf.generateCertificate(fis);
            fis.close();
            return (X509Certificate) cert;
        } catch (Exception ex) {
            //  Logger.getLogger(CryptoSymImpl.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();

        }
        return null;
    }

    

    public static String brutToHexa(byte[] t) {
        byte[] tab = Hex.encode(t);

        return new String(tab);

    }//fin method brutToHexa

    public static byte[] HexaTobrut(String h) {

        byte[] tab = Hex.decode(h);

        return tab;

    }//fin method HexaTobrut

    public static String brutToBase64(byte[] t) {
        byte[] tab = Base64.encode(t);

        return new String(tab);

    }

    public static byte[] base64Tobrut(String h) {

        byte[] tab = Base64.decode(h);

        return tab;

    }


}
