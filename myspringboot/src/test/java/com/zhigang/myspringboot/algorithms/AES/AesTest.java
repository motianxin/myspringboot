/**
 * FileName: AesTest
 * Author:   Administrator
 * Date:     2019/6/24 16:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import java.security.SecureRandom;

/**
 * 〈〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/24 16:03
 */
public class AesTest {
    // AES-GCM parameters
    public static final int AES_KEY_SIZE = 128; // in bits
    public static final int GCM_NONCE_LENGTH = 12; // in bytes
    public static final int GCM_TAG_LENGTH = 16; // in bytes

    private static final String KEYGEN = "FUMSHELLO123";
    private static final String AES_GCM_PKCS5_PADDING = "AES/GCM/PKCS5Padding";
    private static final String AES = "AES";

    public static void main(String[] args) {
        try {
            byte[] input = "111111".getBytes();

            // Initialise random and generate key
            SecureRandom random = new SecureRandom(KEYGEN.getBytes("UTF-8"));
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(AES_KEY_SIZE, random);
            SecretKey key = keyGen.generateKey();
            // Encrypt
            // Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            Cipher cipher = Cipher.getInstance(AES_GCM_PKCS5_PADDING);
            final byte[] nonce = new byte[GCM_NONCE_LENGTH];
            random.nextBytes(nonce);
            for (byte b : nonce) {
                System.out.print(b + ", ");
            }
            System.out.println();
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);

            byte[] cipherText = cipher.doFinal(input);
            Cipher cipher2 = Cipher.getInstance(AES_GCM_PKCS5_PADDING);
            cipher2.init(Cipher.DECRYPT_MODE, key, spec);


            for (byte b : cipherText) {
                System.out.print(b + ", ");
            }

            System.out.println(new String(cipherText));


            byte[] plainText = cipher2.doFinal(cipherText);

            System.out.println(new String(plainText));
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }
}
