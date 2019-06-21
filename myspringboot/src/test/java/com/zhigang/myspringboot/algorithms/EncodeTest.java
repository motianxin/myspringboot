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
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 〈AES-GCM加密〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/12 10:02
 */
public class EncodeTest {

    public static final String KEYGEN = "hellofums123";
    public static final String AES_GCM_PKCS5_PADDING = "AES/GCM/PKCS5Padding";
    public static final String AES = "AES";

    private static String aesEncode(String content) throws Exception {

        SecretKey key = createKey(KEYGEN);
        Cipher cipher = Cipher.getInstance(AES_GCM_PKCS5_PADDING);
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


    private static String aesDecode(String enMsg) throws Exception{
        byte[] message = Base64.decodeBase64(enMsg);
        SecretKey key = createKey(KEYGEN);
        Cipher cipher = Cipher.getInstance(AES_GCM_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
        return new String(decryptData);
    }


    private static SecretKey createKey(String keygen) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(AES);
        kg.init(128, new SecureRandom(keygen.getBytes()));
        SecretKey secretKey = kg.generateKey();
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), AES);

        return key;
    }

    public static void main(String[] args) {
        String name = "adfgdfag";
        String result = "1,inotify.sh";

        String aa = "1,inotify.sh";
        if (!"0".equals(result)) {
            String[] strs = result.split(",");
            if (strs.length >= 2 && "1".equals(strs[0])) {
                name = result.substring(2);
            }
        }
        System.out.println(name);
    }

}
