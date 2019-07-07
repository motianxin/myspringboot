package com.zhigang.myspringboot.reflect;

import com.zhigang.myspringboot.lambda.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/6/23 22:18
 * @Version 3.2.2
 **/
public class SimpleMapper {

    /**
     * @Description: 对象数据用反射转换成字符串
     * @Param: [object]
     * @return: java.lang.String
     * @Author: admin
     * @Date: 2019/6/23 22:27
     */
    public static String toString(Object object) {
        StringBuilder sb = new StringBuilder();
        try {
            // 得到类对象
            Class<?> cls = object.getClass();
            // 打印类全路径名称
            sb.append(cls.getName() + "\n");
            // 得到类字段数组
            for (Field f : cls.getDeclaredFields()) {
                // 设置字段可见性
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                // 打印字段名称=字段值
                sb.append(f.getName() + "=" + f.get(object).toString() + "\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static Object fromString(String str) {
        Object obj = null;

        String[] lines = str.split("\n");
        if (lines.length < 1) {
            throw new IllegalArgumentException(str);
        }
        try {
            // 通过类名反射得到类对象
            Class<?> cls = Class.forName(lines[0]);
            // 创建对象实例。需要有默认实例方法
            obj = cls.newInstance();
            for (int i = 1; i < lines.length; i++) {
                String[] fv = lines[i].split("=");
                if (fv.length != 2) {
                    throw new IllegalArgumentException(lines[i]);
                }
                // 类对象通过字段名得到字段对象
                Field field = cls.getDeclaredField(fv[0]);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                setFieldValue(field, obj, fv[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    private static void setFieldValue(Field field, Object obj, String s) throws Exception{
        Class<?> type = field.getType();
        String name = type.getSimpleName();
        switch (name) {
            case "int":
                field.set(obj, Integer.parseInt(s));
                break;
            case "byte":
                field.set(obj, Byte.parseByte(s));
                break;
            case "short":
                field.set(obj, Short.parseShort(s));
                break;
            case "long":
                field.set(obj, Long.parseLong(s));
                break;
            case "float":
                field.set(obj, Float.parseFloat(s));
                break;
            case "double":
                field.set(obj, Double.parseDouble(s));
                break;
            case "char":
                field.set(obj, s.charAt(0));
                break;
            case "String":
                field.set(obj, s);
                break;
            default:
                // 假设有一个带有string类型参数的构造方法，通过构造方法创建对象
                Constructor<?> constructor = type.getConstructor(new Class[]{String.class});
                field.set(obj, constructor.newInstance(s));
                break;

        }
    }


    public static void main(String[] args) {
        System.out.println(int.class.getSimpleName());
        System.out.println(byte.class.getSimpleName());
        System.out.println(long.class.getSimpleName());
        System.out.println(short.class.getSimpleName());
        System.out.println(float.class.getSimpleName());
        System.out.println(double.class.getSimpleName());
        System.out.println(char.class.getSimpleName());
        System.out.println(String.class.getSimpleName());

        Student student = new Student("xiaoming", "7", 89);
        String stuData = toString(student);
        System.out.println(stuData);
        Student clonStu = (Student) fromString(stuData);
        System.out.println(clonStu);
    }
}
