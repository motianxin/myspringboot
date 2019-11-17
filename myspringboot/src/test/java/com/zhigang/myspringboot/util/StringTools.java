/**
 * FileName: StringTools
 * Author:   admin
 * Date:     2019/4/29 10:25
 * Description: str tools
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

import org.assertj.core.util.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 〈str tools〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/29 10:25
 */
public class StringTools {

    public static final String STRING_SPLIT = ",";

    /**
     * IP地址段分隔符
     */
    public static final String IPBLOCK_SPLIT = "~";

    /**
     * IP地址分隔符
     */
    public static final String IPADDRESS_SPLIT_REX = "\\.";

    /**
     * IP地址分隔符
     */
    public static final String IPADDRESS_SPLIT = ".";

    public static void main(String[] args) {
        String ipStr = "10.96.155.163,10.156.120.250~10.156.121.5,10.123.156.153~10.123.156.168";
        System.out.println(ipStr);

        List<String> ipList = ipStrToIpList(ipStr);
        String[] ips = new String[ipList.size()];
        ipList.toArray(ips);
        System.out.println(listToStr(ips));
    }

    private static List<String> ipStrToIpList(String filterIps) {
        List<String> ips = new ArrayList<>();
        String[] ipArray = filterIps.split(STRING_SPLIT);
        System.out.println("直接打印数组");
        System.out.println(ipArray);
        System.out.println(listToStr(ipArray));
        for (String ip : ipArray) {
            System.out.println(ip);
            if (ip.contains(IPBLOCK_SPLIT)) {
                ips.addAll(getIpListByBlock(ip));
            } else {
                ips.add(ip);
            }
        }
        return ips;
    }

    private static List<String> getIpListByBlock(String ip) {
        List<String> ips = new ArrayList<>();
        String[] ipBlock = ip.split(IPBLOCK_SPLIT);
        System.out.println(listToStr(ipBlock));
        try {
            if (ipBlock.length > 1) {
                addIpsByBlock(ips, ipBlock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ips;
    }

    /**
     * @Author admin
     * @Description 通过IP地址段添加IP集合，IP段格式：abc.xxx.kkk.jjj~abc.xxx.mmm.nnn
     * @Param [ips, ipBlock]
     * @Return void
     * @date 2019/4/28 14:31
     **/
    private static void addIpsByBlock(List<String> ips, String[] ipBlock) {
        String[] ipBegin = ipBlock[0].split(IPADDRESS_SPLIT_REX);
        String[] ipEnd = ipBlock[1].split(IPADDRESS_SPLIT_REX);
        System.out.println(listToStr(ipBegin));
        System.out.println(listToStr(ipEnd));
        if (ipBegin[0].equals(ipEnd[0]) && ipBegin[1].equals(ipEnd[1])) {
            if (ipBegin[2].equals(ipEnd[2])) {
                for (int i = Integer.parseInt(ipBegin[3]); i <= Integer.parseInt(ipEnd[3]); i++) {
                    ips.add(new StringJoiner(IPADDRESS_SPLIT).add(ipBegin[0]).add(ipBegin[1]).add(ipBegin[2]).add(String.valueOf(i)).toString());
                }
            } else {
                for (int i = Integer.parseInt(ipBegin[3]); i <= 254; i++) {
                    ips.add(new StringJoiner(IPADDRESS_SPLIT).add(ipBegin[0]).add(ipBegin[1]).add(ipBegin[2]).add(String.valueOf(i)).toString());
                }
                for (int j = 1; j <= Integer.parseInt(ipEnd[3]); j++) {
                    ips.add(new StringJoiner(IPADDRESS_SPLIT).add(ipEnd[0]).add(ipEnd[1]).add(ipEnd[2]).add(String.valueOf(j)).toString());
                }

                for (int k = Integer.parseInt(ipBegin[2]) + 1; k < Integer.parseInt(ipEnd[2]); k++) {
                    for (int m = 1; m <= 254; m++) {
                        ips.add(new StringJoiner(IPADDRESS_SPLIT).add(ipEnd[0]).add(ipEnd[1]).add(String.valueOf(k)).add(String.valueOf(m)).toString());
                    }
                }
            }
        }
    }

    private static String listToStr(String[] strArray) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        Arrays.asList(strArray).stream().forEach(s -> sj.add(s.toString()));
        return sj.toString();

    }

}
