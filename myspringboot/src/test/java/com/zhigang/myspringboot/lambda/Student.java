package com.zhigang.myspringboot.lambda;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author zghuang
 * @Date 2019/6/9 17:35
 * @Version 3.2.2
 **/
@Getter
@Setter
@ToString
public class Student {
    private String name;
    private String grade;
    private int score;

    public Student() {
    }

    public Student(String name, String grade, int score) {
        this.name = name;
        this.grade = grade;
        this.score = score;
    }
}
