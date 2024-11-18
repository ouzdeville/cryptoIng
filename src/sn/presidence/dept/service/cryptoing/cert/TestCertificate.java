/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sn.presidence.dept.service.cryptoing.cert;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sn.presidence.dept.service.cryptoing.tool.CryptoImpl;

/**
 *
 * @author ousmane3ndiaye
 */
public class TestCertificate {
    public static void main(String[] args) throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
        }
        CryptoImpl crypto=new CryptoImpl();
        KeyPair kpair = crypto.generateKeyPair("sdad".getBytes());
        String dn="CN=DCSSI CA, OU=DCSSI CA, O=PRESIDENCE, L=Dakar, ST=Senegal, C=SN";
        X509Certificate cert = CertificatUtils.generateDefaultRootCert(kpair, dn, BigInteger.TEN);
        CertificatUtils.ExportCertificat(cert, "test.crt");
        crypto.saveHexKey(kpair.getPrivate(), "cleprive.txt", "password");
        
         dn="CN=ouzdeville.com, OU=DCSSI CA, O=PRESIDENCE, L=Dakar, ST=Senegal, C=SN";
        
        PrivateKey priv=(PrivateKey) crypto.loadHexKey("cleprive.txt",  "password", 1);
        
         kpair = crypto.generateKeyPair("sdad".getBytes());
        X509Certificate caCert = CertificatUtils.ChargeCertficat("test.crt");
        
        X509Certificate Endcert = CertificatUtils.generateDefaultEndEntityCert(
                kpair.getPublic(), priv, caCert, dn, BigInteger.TWO);
        
        CertificatUtils.ExportCertificat(Endcert, "EndCert.crt");
        
        
        
    }
}
