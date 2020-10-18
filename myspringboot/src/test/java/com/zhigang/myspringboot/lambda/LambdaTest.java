/**
 * FileName: LambdaTest
 * Author:   admin
 * Date:     2019/1/22 19:23
 * Description: lambda test
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.lambda;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 〈一句话功能简述〉<br>
 * 〈lambda test〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/1/22 19:23
 */
public class LambdaTest {

    public static void main(String[] args) {
        int n = 30;
        System.out.println("begin init list");
        List<Student> students = LambdaTest.initStuList(n);
        students.forEach(System.out::println);
        System.out.println("after init list");

        List<Student> above70list =
            students.stream().filter(student1 -> student1.getScore() > 70).collect(Collectors.toList());
        System.out.println("after filter:");
        above70list.forEach(System.out::println);

        System.out.println("get score above 40 name_scoreMap:");
        Map<String, Integer> name_scoreMap = students.stream().filter(student -> student.getScore() > 40)
            .collect(Collectors.toMap(Student::getName, Student::getScore, (oldValue, value) -> value));
        System.out.println(name_scoreMap);

        System.out.println("get 60 score names:");
        List<String> above60names = students.stream().filter(student -> student.getScore() > 60).map(Student::getName)
            .collect(Collectors.toList());
        above60names.forEach(System.out::println);


        System.out.println("学生列表按年级分组，得到名字列表：");
        Map<String, List<String>> map = students.stream().collect(
            Collectors.groupingBy(Student::getGrade, Collectors.mapping(Student::getName, Collectors.toList())));
        System.out.println(map);

        System.out.println("找出一个年级有多少人");
        Map<String, Long> grade_personNumMap =
            students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        System.out.println(grade_personNumMap);

        System.out.println("找到一个年级对应分数最高分的学生：");
        Map<String, Student> gradeMaxScoreStuMap = students.stream().collect(Collectors
            .toMap(Student::getGrade, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Student::getScore))));
        System.out.println(gradeMaxScoreStuMap);


        System.out.println("取每个年级学生前三名：");
        /*Map<String, List<Student>> grade_sortStuMap = students.stream().collect(Collectors.groupingBy
        (Student::getGrade, collectingAndSort(Collectors.toList(), Comparator.comparing(Student::getScore).reversed()
        )));
        System.out.println(grade_sortStuMap);*/


        Map<String, List<Student>> grade_sortStuMap = students.stream()
            .collect(Collectors.groupingBy(Student::getGrade, Collectors.collectingAndThen(Collectors.toList(), (r) -> {
                r.sort(Comparator.comparing(Student::getScore).reversed());
                return r.stream().limit(3).collect(Collectors.toList());
            })));

        System.out.println(grade_sortStuMap);

        System.out.println("按年级分组并过滤掉低于50分的学生");
        /*Map<String, List<Student>> grade_lowStuMap = students.stream().collect(Collectors.groupingBy
        (Student::getGrade, collectingAndFilter(Collectors.toList(), student -> student.getScore() > 50)));*/

        Map<String, List<Student>> grade_lowStuMap = students.stream().collect(Collectors.groupingBy(Student::getGrade,
            Collectors.collectingAndThen((Collectors.toList()),
                (r) -> r.stream().filter(student -> student.getScore() > 50).collect(Collectors.toList()))));

        System.out.println(grade_lowStuMap);

        System.out.println("查询每个年级的及格人数：");
        Map<String, Long> grade_above60NumMap = students.stream().collect(Collectors.groupingBy(Student::getGrade,
            Collectors.collectingAndThen(Collectors.toList(),
                (r) -> r.stream().filter(student -> student.getScore() >= 60).count())));
        System.out.println(grade_above60NumMap);
    }

    public static <T> Collector<T, ?, List<T>> collectingAndFilter(Collector<T, ?, List<T>> downstream,
                                                                   Predicate<T> predicate) {
        return Collectors
            .collectingAndThen(downstream, (r) -> r.stream().filter(predicate).collect(Collectors.toList()));
    }

    private static <T> Collector<T, ?, List<T>> collectingAndSort(Collector<T, ?, List<T>> downstream,
                                                                  Comparator<? super T> comparator) {
        return Collectors.collectingAndThen(downstream, (r) -> {
            r.sort(comparator);
            return r;
        });
    }

    private static List<Student> initStuList(int n) {
        Random random = new Random();
        return Stream.generate(() -> new Student(
            Stream.generate(() -> ((char) ('a' + random.nextInt(26))) + "").limit(10).collect(Collectors.joining()),
            random.nextInt(5) + "", random.nextInt(100))).limit(n).collect(Collectors.toList());
    }

    private static void countSameNames(String path) throws Exception {
        List<String> lines = FileUtils.readLines(new File(path), Charset.forName("GBK"));
        lines.stream().map(s -> s.split(",")).collect(Collectors.toMap(s -> s[1], s -> s[0], (vo, ve) -> vo + "," + ve))
            .entrySet().forEach(e -> System.out.println(
            String.format("%s : " + "%d 人，工号为：%s.", e.getKey(), e.getValue().split(",").length, e.getValue())));
    }
}
