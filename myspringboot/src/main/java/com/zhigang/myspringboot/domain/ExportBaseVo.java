/**
 * FileName: ExportBaseVo
 * Author:   admin
 * Date:     2019/3/19 9:39
 * Description: 导出基类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import java.util.Map;

/**
 * 〈导出基类〉
 *
 * @author admin
 * @create 2019/3/19 9:39
 * @version 3.2.2
 */
public interface ExportBaseVo {
    Map<String, String> createMap(String[] tableHeader);
}
