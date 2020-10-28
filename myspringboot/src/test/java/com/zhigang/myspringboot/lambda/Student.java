package com.zhigang.myspringboot.lambda;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.util.Lists;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
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

    public static void main(String[] args) {
        List<String> stringList = Lists.newArrayList("100.00", "75.00");
        int sum = (int) stringList.stream().mapToDouble(Double::parseDouble).peek(System.out::println).sum();
        System.out.println(sum);
        DecimalFormat df = new DecimalFormat("#.000000");
        double x = Double.parseDouble(String.valueOf(sum)) * 100 / 4800;
        System.out.println(df.format(x));
        System.out.println(x);
    }
}
