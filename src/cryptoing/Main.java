/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptoing;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author ousmane3ndiaye
 */
public class Main {

    public static List<Object> listHsmAlgorithms(Provider provider) {
        Map<String, Object> alg = new LinkedHashMap<>();
        List<String> algoSign = new ArrayList<>();
        List<String> keySpec = new ArrayList<>();
        List<Object> algoList = new ArrayList<>();
        for (Provider.Service algo : provider.getServices()) {
            alg = new LinkedHashMap<>();
            if ("KeyPairGenerator".equalsIgnoreCase(algo.getType())) {

                algoSign = new ArrayList<>();
                for (Provider.Service sign : provider.getServices()) {
                    if ("Signature".equalsIgnoreCase(sign.getType())
                            && sign.getAlgorithm().toUpperCase().contains(algo.getAlgorithm().toUpperCase())) {
                        algoSign.add(sign.getAlgorithm());
                    }
                }
                // Valeurs standards par défaut
                keySpec = new ArrayList<>();
                switch (algo.getAlgorithm().toUpperCase()) {
                    case "RSA":
                        keySpec.addAll(Arrays.asList("2048", "3072", "4096"));
                        break;
                    case "EC":
                    case "ECDSA":
                        keySpec.addAll(Arrays.asList("P-256", "P-384", "P-521"));
                        break;
                    case "DSA":
                        keySpec.addAll(Arrays.asList("2048", "3072"));
                        break;
                    default:
                        keySpec.add("non spécifié");
                }
                alg.put("name", algo.getAlgorithm());
                alg.put("keySpec", keySpec);
                alg.put("algoSign", algoSign);
                algoList.add(alg);

            }

        }
        System.out.println(algoList);
        return algoList;

    }

    public static void main(String[] args) {
        for (Provider.Service s : new BouncyCastleProvider().getServices()) {
            // On s'intéresse aux services de type "Signature"
            if (s.getAlgorithm().contains("SHA256WithECDSA".toUpperCase())) {
                System.out.println(s.getType() + " : " + s.getAlgorithm());
            }

        }
        listHsmAlgorithms(new BouncyCastleProvider());
        listHsmAlgorithms(new BouncyCastleProvider());
    }
}
