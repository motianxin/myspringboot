/**
 * FileName: IdEntity
 * Author:   admin
 * Date:     2019/3/19 10:00
 * Description: IdEntity封装了实体类的id属性
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain.entity;

/**
 * 〈IdEntity封装了实体类的id属性〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/3/19 10:00
 */
public abstract class IdEntity {
    protected Long id;

    public abstract Long getId();

    public abstract void setId(Long id);
}
