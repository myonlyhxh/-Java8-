package com.flashwarrior.functionalinterface;

import java.util.Date;
import java.util.function.*;

/**
 * 函数式编程示例
 */
public class Example1 {

    //Lambda

    //不包含参数，用()表示没有参数
    //表达式主体只有一个语句，可以省略{}
    Runnable helloWorld = () -> System.out.println("hello World");

    Runnable helloWorlds = () -> {
        System.out.println("hello World1");
        System.out.println("hello World1");
        System.out.println("hello World1");
    };

    Consumer<String> infoConsumer = msg -> System.out.println(msg + "hello");

    BinaryOperator<Integer> add1 = (Integer i, Integer j) -> i + j;

    BinaryOperator<Integer> add2 = Integer::sum;



    //Java内置常用函数

    //Predicate<T> 判断型：类似于if的作用
    //返回类型：Boolean
    Predicate<String> isAdmin = "admin"::equals;


    //Consumer<T> 消费型
    //返回类型：void
    Consumer<String> print = msg -> System.out.println(msg + "kkk");


    //Function<T, R> 功能型
    Function<Long, String> toStr = value -> String.valueOf(value);


    //Supplier<T> 提供型
    Supplier<Date> now = () -> new Date();


    //2.3 方法引用
    //Lambda表达式一种常用方法就是直接调用其他方法，凡是使用Lambda表达式的地方都可以使用方法引用
    //方法引用的标准语法为ClassName::methodName，虽然这是一个方法，但不需要在后面加括号
    //因为这里并不直接调用该方法

    Function<User, String> f1 = user -> user.getName();
    Function<User, String> f2 = User::getName;

    Supplier<User> s1 = () -> new User();
    Supplier<User> s2 = User::new;










}
