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

/**
 * 〈AES-GCM加密〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/12 10:02
 */
public class EncodeTest {
    public static void main(String[] args) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
            kg.init(128);
            SecretKey secretKey = kg.generateKey();
            System.out.println("密钥：" + Base64.encodeBase64String(secretKey.getEncoded()));

            SecretKey key = new SecretKeySpec(secretKey.getEncoded(), "AES");

            String txt = "testtxt";

            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = cipher.getIV();
            assert iv.length == 12;
            byte[] encryptData = cipher.doFinal(txt.getBytes());
            assert encryptData.length == txt.getBytes().length + 16;
            byte[] message = new byte[12 + txt.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            System.out.println("加密后：" + Base64.encodeBase64String(message));
            if (message.length < 12 + 16) throw new IllegalArgumentException();
            GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            byte[] decryptData = cipher.doFinal(message, 12, message.length - 12);
            System.out.println("解密后：" + new String(decryptData));
        } catch (Exception e) {
            System.out.println("adfgadfgad");
            e.printStackTrace();
        }
    }
}
