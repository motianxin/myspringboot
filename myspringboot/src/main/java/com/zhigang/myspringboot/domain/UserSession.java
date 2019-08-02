package com.zhigang.myspringboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/8/2 11:54
 * @Version 3.2.2
 **/

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSession implements Serializable {

    private String id;
    private Date logintime;
    private String userName;
    private boolean status;


}
