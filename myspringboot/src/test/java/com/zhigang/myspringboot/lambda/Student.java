package com.zhigang.myspringboot.lambda;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <Student>
 *
 * @author admin
 * @since 2020/10/29 1:40
 */
@Getter
@Setter
@ToString
public class Student {
    private String name;

    private String grade;

    private int score;

    public Student() {
        this("", "", 0);
    }

    public Student(String name, String grade, int score) {
        this.name = name;
        this.grade = grade;
        this.score = score;
    }
}
