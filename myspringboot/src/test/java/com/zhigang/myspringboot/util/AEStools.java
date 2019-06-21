/**
 * FileName: AEStools
 * Author:   Administrator
 * Date:     2019/6/14 8:45
 * Description: AES加解密工具
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

/**
 * 〈AES加解密工具〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/14 8:45
 */
public class AEStools {

    private static final int BLOCK_ARRAY_LENGTH = 4;

    private static final int EXTENF_KEY_ARRAY_LENGTH = 44;

    private static final int[] RC = {0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x36};

    private static final int[][] MIX_COLUMNS_ARRAY = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}};

    /**
     * @Author Administrator
     * @Description 初始化密钥数组
     * @Param [key]
     * @Return int[][]
     * @date 2019/6/14 8:59
     **/
    private static int[][] strTo16ByteBlock(String key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        byte[] keyBytes = key.getBytes();
        if (keyBytes.length != 16) {
            throw new IllegalArgumentException("key to bytes error, key not 128 bits");
        }
        int[][] block = new int[BLOCK_ARRAY_LENGTH][BLOCK_ARRAY_LENGTH];
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                block[i][j] = keyBytes[i * BLOCK_ARRAY_LENGTH + j];
            }
        }
        return block;
    }


    /**
     * @Author Administrator
     * @Description 扩展密钥
     * @Param [key]
     * @Return int[][]
     * @date 2019/6/14 17:32
     **/
    private static int[][] extendKey(int[][] key) {
        int[][] extendKey = new int[BLOCK_ARRAY_LENGTH][EXTENF_KEY_ARRAY_LENGTH];
        int[] tmp = new int[BLOCK_ARRAY_LENGTH];
        initKeyW(key, extendKey);
        for (int i = BLOCK_ARRAY_LENGTH; i < EXTENF_KEY_ARRAY_LENGTH; i++) {
            extendKeyCopyToTmp(tmp, extendKey, i);
            if (i % 4 == 0) {
                rotateLeft(tmp);
                wstmp(tmp, i);
            }
            tmpXorExtendKey(tmp, extendKey, i);
        }

        return extendKey;
    }


    private static void tmpXorExtendKey(int[] tmp, int[][] extendKey, int j) {
        for (int i = 0; i < tmp.length; i++) {
            extendKey[i][j] = tmp[i] ^ extendKey[i][j - 4];
        }
    }

    private static void wstmp(int[] tmp, int j) {
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            tmp[i] = Gf28.sBox[tmp[i] >> 4][tmp[i] & 0x0f];
        }
        tmp[0] = tmp[0] ^ RC[j / 4];
    }

    private static void extendKeyCopyToTmp(int[] tmp, int[][] extendKey, int j) {
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            tmp[i] = extendKey[i][j - 1];
        }

    }

    /**
     * @Author Administrator
     * @Description 字节数组循环左移一位
     * @Param [tmp]
     * @Return void
     * @date 2019/6/14 14:22
     **/
    private static void rotateLeft(int[] tmp) {
        int value = tmp[0];
        for (int i = 0; i < BLOCK_ARRAY_LENGTH - 1; i++) {
            tmp[i] = tmp[i + 1];
        }
        tmp[BLOCK_ARRAY_LENGTH - 1] = value;
    }

    /**
     * @Author Administrator
     * @Description 用密钥初始化扩展密钥
     * @Param [key, extendKey]
     * @Return void
     * @date 2019/6/14 17:31
     **/
    private static void initKeyW(int[][] key, int[][] extendKey) {
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                extendKey[i][j] = key[i][j];
            }
        }
    }


    /**
     * @Author Administrator
     * @Description 列混淆
     * @Param [content, matrix]
     * @Return void
     * @date 2019/6/14 17:30
     **/
    private static void mixColumns(int[][] content, int[][] matrix) {
        int[][] tmp = new int[BLOCK_ARRAY_LENGTH][BLOCK_ARRAY_LENGTH];
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                tmp[i][j] = Gf28.aMultiplyB(matrix[i][0], content[0][j]) ^
                        Gf28.aMultiplyB(matrix[i][1], content[1][j]) ^
                        Gf28.aMultiplyB(matrix[i][2], content[2][j]) ^
                        Gf28.aMultiplyB(matrix[i][3], content[3][j]);
            }
        }
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                content[i][j] = tmp[i][j];
            }
        }
    }

    /**
     * @Author Administrator
     * @Description 行移位
     * @Param [content]
     * @Return void
     * @date 2019/6/14 16:17
     **/
    private static void shiftRows(int[][] content) {
        int[][] temp = new int[BLOCK_ARRAY_LENGTH][BLOCK_ARRAY_LENGTH];
        for (int i = 1; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                temp[i][j] = content[i][(j + i) % BLOCK_ARRAY_LENGTH];
            }
        }

        for (int i = 1; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                content[i][j] = temp[i][j];
            }
        }
    }

    /**
     * @Author Administrator
     * @Description 字节替换
     * @Param [content]
     * @Return void
     * @date 2019/6/14 17:30
     **/
    private static void subBytes(int[][] content){
        for (int i = 1; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                content[i][j] = Gf28.sBox[content[i][j] >> 4][content[i][j] & 0x0f];
            }
        }
    }


    /**
     * @Author Administrator
     * @Description 轮密钥加
     * @Param [content, key]
     * @Return void
     * @date 2019/6/14 17:30
     **/
    private static void dddRoundKey(int[][] content, int[][] key){
        for (int i = 0; i < BLOCK_ARRAY_LENGTH; i++) {
            for (int j = 0; j < BLOCK_ARRAY_LENGTH; j++) {
                content[i][j] = content[i][j] ^ key[i][j];
            }
        }
    }

}
