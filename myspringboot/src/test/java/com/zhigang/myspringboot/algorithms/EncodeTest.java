/**
 * FileName: EncodeTest
 * Author:   Administrator
 * Date:     2019/6/12 10:02
 * Description: AES-GCM加密
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 〈AES-GCM加密〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/12 10:02
 */
public class EncodeTest {

    private static final String KEYGEN = "1234567890123456";
    private static final String AES_GCM_PKCS5_PADDING = "AES/GCM/PKCS5Padding";
    private static final String AES = "AES";

    /**
     * 签名算法
     */
    private static final String SIGN_ALGORITHMS = "SHA1PRNG";


    private static String aesEncode(String content) throws Exception {

        SecretKey key = EncodeTest.createKey(EncodeTest.KEYGEN);
        Cipher cipher = Cipher.getInstance(EncodeTest.AES_GCM_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getIV();
        assert iv.length == 12;
        byte[] encryptData = cipher.doFinal(content.getBytes());
        assert encryptData.length == content.getBytes().length + 16;
        byte[] message = new byte[12 + content.getBytes().length + 16];
        System.arraycopy(iv, 0, message, 0, 12);
        System.arraycopy(encryptData, 0, message, 12, encryptData.length);

        return Base64.encodeBase64String(message);
    }


    private static String aesDecode(String enMsg) throws Exception {
        byte[] message = Base64.decodeBase64(enMsg);
        SecretKey key = EncodeTest.createKey(EncodeTest.KEYGEN);
        Cipher cipher = Cipher.getInstance(EncodeTest.AES_GCM_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
        return new String(decryptData);
    }


    private static SecretKey createKey(String keygen) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(EncodeTest.AES);
        SecureRandom random = SecureRandom.getInstance(EncodeTest.SIGN_ALGORITHMS);
        random.setSeed(keygen.getBytes(StandardCharsets.UTF_8));
        kg.init(128, random);
        SecretKey secretKey = kg.generateKey();
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), EncodeTest.AES);

        return key;
    }

    public static void main(String[] args) {
        String data = "123456789012";

        try {
            String encode = EncodeTest.aesEncode(data);
            System.out.println("加密后：" + encode);
            String decode = EncodeTest.aesDecode(encode);
            System.out.println("解密后：" + decode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
