/**
 * FileName: HexDump
 * Author:   Administrator
 * Date:     2019/6/24 16:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.algorithms.AES;

import java.nio.ByteBuffer;

/**
 * 〈〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/6/24 16:38
 */
public class HexDump {
    private static byte[] highDigits = null;
    private static byte[] lowDigits = null;


    static {

        final byte[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {

            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];

        }

        highDigits = high;
        lowDigits = low;

    }


    public static final byte[] toArray(String hexs) {

        ByteBuffer buf = ByteBuffer.wrap(new byte[hexs.length() / 2]);
        byte b1;
        char c1, c2;
        for (int i = 0; i < hexs.length() - 1; i += 2) {

            c1 = hexs.charAt(i);
            c2 = hexs.charAt(i + 1);
            b1 = (byte) ((char2byte(c1) << 4) | (0x0f & char2byte(c2)));
            buf.put(b1);

        }
        buf.flip();
        return buf.array();

    }

    public static final byte[] cat(byte[] src1, byte[] src2) {

        ByteBuffer buf = ByteBuffer.allocate(src1.length + src2.length);
        buf.put(src1).put(src2);
        return buf.array();

    }

    public static final String toHex(byte[] in, int offset, int len) {

        if (null == in || offset < 0 || len < 0) {
            return "empty";
        }
        if (offset < 0) {
            offset = 0;
        }
        if (len < 0) {
            len = in.length;
        }
        if ((offset + len) > in.length) {
            len = in.length - offset;
        }
        StringBuffer out = new StringBuffer(len * 2);

        int byteValue;


        for (; len > 0; len--) {

            byteValue = in[offset++] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);

        }
        return out.toString();

    }

    public static final String toHex(byte[] in) {

        return toHex(in, 0, in.length);

    }


    public static String hexDump(ByteBuffer in) {

        if (null == in) {
            return "null";
        }
        int size = in.remaining();

        if (size == 0) {

            return "empty";

        }

        StringBuffer out = new StringBuffer((in.remaining() * 3) - 1);


        int i = in.position();


        int byteValue = in.get(i) & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        size--;
        i++;

        for (; size > 0; size--) {

            out.append(' ');
            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;

        }


        return out.toString();

    }

    public static String hexDumpCompact(ByteBuffer in) {

        if (null == in) {
            return "";
        }
        int size = in.remaining();

        if (size == 0) {

            return "";

        }

        StringBuffer out = new StringBuffer(in.remaining() * 2);

        int i = in.position();


        int byteValue;

        for (; size > 0; size--) {

            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;

        }


        return out.toString();

    }

    public static String hexDumpCompactSilent(ByteBuffer in) {

        if (null == in) {
            return "";
        }
        int size = in.remaining();

        if (size == 0) {

            return "";

        }

        StringBuffer out = new StringBuffer(in.remaining() * 2);

        int byteValue;

        int i = in.position();
        for (; size > 0; size--) {

            byteValue = in.get(i) & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
            i++;

        }

        return out.toString();

    }

    public static String hexDump(byte[] in, int offset, int length) {

        int size = in.length;
        if (size == 0 || length < 0 || length > size || offset < 0 || offset > size) {
            return "empty";
        }

        if (offset + length > size) {

            return "empty";

        }

        StringBuffer out = new StringBuffer(length * 3 - 1);


        int byteValue = in[offset++] & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        length--;


        for (; length > 0; length--) {

            out.append(' ');
            byteValue = in[offset++] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);

        }

        return out.toString();

    }

    public static String hexDumpCompact(byte[] in, int offset, int length) {

        if (null == in) {
            return "";
        }
        int size = in.length;
        if (size == 0 || length < 0 || length > size || offset < 0 || offset > size) {
            return "";
        }

        if (offset + length > size) {

            return "";

        }

        StringBuffer out = new StringBuffer(length * 2);

        int byteValue;


        for (; length > 0; length--) {

            byteValue = in[offset++] & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);

        }

        return out.toString();

    }

    public static String toHex(byte byteValue) {

        StringBuffer out = new StringBuffer(2);
        int index = byteValue & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();

    }

    public static String toHex(short shortValue) {

        StringBuffer out = new StringBuffer(5);
        int index = (shortValue >> 8) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = shortValue & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();

    }

    public static String toHex(int i) {


        StringBuffer out = new StringBuffer(8);

        int index;
        index = (i >> 24) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = (i >> 16) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = (i >> 8) & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        index = i & 0xFF;
        out.append((char) highDigits[index]);
        out.append((char) lowDigits[index]);
        return out.toString();

    }

    public static ByteBuffer toByteBuffer(String str) {

        ByteBuffer buff = ByteBuffer.wrap(new byte[str.length() / 2]);
        byte b1;
        char c1, c2;
        for (int i = 0; i < str.length() - 1; i += 2) {

            c1 = str.charAt(i);
            c2 = str.charAt(i + 1);
            b1 = (byte) ((char2byte(c1) << 4) | (0x0f & char2byte(c2)));
            buff.put(b1);

        }
        buff.flip();
        return buff;

    }

    public static ByteBuffer toByteBuffer(final ByteBuffer dest, String str) {

        if (null == str || str.length() == 0) {
            return dest;
        }
        byte b1;
        char c1, c2;
        for (int i = 0; i < str.length() - 1; i += 2) {

            c1 = str.charAt(i);
            c2 = str.charAt(i + 1);
            b1 = (byte) ((char2byte(c1) << 4) | (0x0f & char2byte(c2)));
            dest.put(b1);

        }
        return dest;

    }

    private static byte char2byte(char c) {

        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        }
        if (c >= 'A' && c <= 'F') {
            return (byte) (c - 'A' + 10);
        }
        if (c >= 'a' && c <= 'f') {
            return (byte) (c - 'a' + 10);
        }
        return 0;

    }
}
