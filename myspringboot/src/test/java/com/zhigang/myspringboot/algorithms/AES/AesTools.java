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
            for (byte aByte : bytes) {
                System.out.print(aByte + ", ");
            }
            System.out.println("加密成功");
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String aesDecode(String enMsg) {
        try {
            byte[] message = Base64.decodeBase64(enMsg);
            for (byte b : message) {
                System.out.print(b + ", ");
            }
            System.out.println("开始解密");
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
            String string = aesEncode("adgfadfgadfgadfgadgdfg");
            System.out.println(string);
            System.out.println(aesDecode(string));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(String hexString) {

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
}
