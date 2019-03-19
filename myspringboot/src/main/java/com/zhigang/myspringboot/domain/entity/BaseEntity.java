/**
 * FileName: BaseEntity
 * Author:   zghuang
 * Date:     2019/3/19 10:24
 * Description: entity基类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 〈entity基类〉
 *
 * @author zghuang
 * @create 2019/3/19 10:24
 * @version 3.2.2
 */
@MappedSuperclass
public class BaseEntity extends IdEntity{
    @Override
    @Id
    @GeneratedValue
    @Column(length=20)
    public Long getId() {
        return super.id;
    }

    @Override
    public void setId(Long id) {
        super.id = id;
    }
}
