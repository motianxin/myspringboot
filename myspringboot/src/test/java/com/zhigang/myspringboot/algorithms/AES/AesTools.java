/**
 * FileName: AesTools
 * Author:   Administrator
 * Date:     2019/6/19 11:18
 * Description: AES加解密工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.AES;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 〈AES加解密工具类〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/19 11:18
 */
public class AesTools {
    private static final String KEYGEN = "FUMSHELLO123";
    private static final String AES_GCM_PKCS5_PADDING = "AES/GCM/PKCS5Padding";
    private static final String AES = "AES";

    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_NONCE_LENGTH = 12;
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    public static String aesEncode(String content) {

        try {
            byte[] contentByte = content.getBytes("utf-8");
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] bytes = cipher.doFinal(contentByte);
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String aesDecode(String enMsg) {
        try {
            byte[] message = Base64.decodeBase64(enMsg);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(message));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Cipher getCipher(int mode) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(AES);
        SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
        random.setSeed(KEYGEN.getBytes("UTF-8"));
        kg.init(AES_KEY_SIZE, random);
        SecretKey secretKey = kg.generateKey();
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), AES);
        final byte[] nonce = new byte[GCM_NONCE_LENGTH];
        random.nextBytes(nonce);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(AES_KEY_SIZE, nonce);
        Cipher cipher = Cipher.getInstance(AES_GCM_PKCS5_PADDING);
        cipher.init(mode, key, gcmParameterSpec);
        return cipher;
    }

    public static void main(String[] args) {
        try {
            String string = aesEncode("111111");
            System.out.println(string);
            System.out.println(aesDecode(string));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
