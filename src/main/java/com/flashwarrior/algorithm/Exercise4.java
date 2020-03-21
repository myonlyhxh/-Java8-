package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Exercise4 {

    //LeetCode 第 772 题，基本计算器：实现一个基本的计算器来计算简单的表达式字符串。
//    说明：
//    表达式字符串可以包含左括号 ( 和右括号 )，加号 + 和减号 -，非负整数和空格。
//    表达式字符串只包含非负整数， +  -  *  / 操作符，左括号 ( ，右括号 ) 和空格。整数除法需要向下截断。
//
//    示例 1：
//            "1 + 1" = 2
//            " 6-4 / 2 " = 4
//            "2×(5+5×2)/3+(6/2+8)" = 21
//            "(2+6×3+5- (3×14/7+2)×5)+3" = -12

    /**
     * 解题思路1：只有加号
     * 若表达式里只有数字和加法符号，没有减法，没有空格，并且输入的表达式一定合法
     * 那么应该怎么处理？例如：1+2+10
     */
    public int calculate(String s) {
        Queue<Character> queue = new LinkedList<>();
        for (char c : s.toCharArray()) {
            //过滤空格
            if (c != ' ') {
                queue.offer(c);
            }
        }

        queue.offer('c');

        //定义两个变量，num用来表示当前的数字，sum用来记录最后的和
        int num = 0, sum = 0;

        while (!queue.isEmpty()) {
            Character c = queue.poll();

            //如果当前字符是数字，那么就更新num变量，如果遇到了加号，就把当前的num加到sum中，将num清零
            if (Character.isDigit(c)) {
                num = 10 * num + c - '0';
            } else {
                sum += num;
                num = 0;
            }
        }
        return sum;
    }

    @Test
    public void test3(){
        String str = "1+2-10";
        int i = calculate1(str);
        System.out.println(i);
    }

    /**
     * 解题思路2：引入减号
     * 例题：1+2-10
     * 解法1：借助两个stack，一个stack专门用来放数字；另一个stack专门用来放符号
     * 解法2：将表达式看作+1+2+(-10)，把-10看做一个整体，同时，利用一个变量sign来表示该数字前的符号，这样既可沿用之前的解法
     */
    public int calculate1(String s) {
        Queue<Character> queue = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c != ' ') {
                queue.offer(c);
            }
        }
        queue.offer('+');

        //1+2-3+

        char sign = '+';

        int num = 0, sum = 0;

        while (!queue.isEmpty()) {
            Character c = queue.poll();

            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else {
                //遇到了符号，表明我们要开始统计一下当前的结果
                if (sign == '+') {
                    sum += num;
                } else if (sign == '-') {
                    sum -= num;
                }

                num = 0;
                sign = c;
            }
        }
        return sum;
    }

    /**
     * 解题思路3：引入乘法
     * 例如：1+2*10
     * 解法：要考虑符号的优先级问题，不能再简单地对sum进行单向操作了。当遇到乘号的时候，sum=1，num=2
     * 而乘法的优先级比较高，得先处理2*10才能加1。因此暂时将它们记录起来
     */
    public int calculate2(String s) {
        Queue<Character> queue = new LinkedList<>();

        for (char c : s.toCharArray()) {
            if (c != ' ') {
                queue.offer(c);
            }
        }

        queue.offer('+');

        char sign = '+';
        int num = 0;

        //定义一个新的变量stack，用来记录要被处理的数
        Stack<Integer> stack = new Stack<>();

        while (!queue.isEmpty()) {
            Character c = queue.poll();

            if(Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }else{
                if(sign == '+') {
                    stack.push(num); //遇到加号，把当前的数压入到堆栈中
                }else if(sign == '-') {
                    stack.push(-num); //遇到减号，把当前数的相反数压入到堆栈中
                }else if(sign == '*') {
                    stack.push(stack.pop() * num); // 乘号，把前一个数从堆栈中取出，然后和当前的数相乘，再放回堆中
                }else if (sign == '/') {
                    stack.push(stack.pop() / num); //除号，把前一个数从堆栈中取出，然后除以当前的数，再把结果放回堆栈中
                }

                num = 0;
                sign = c;
            }
        }

        int sum = 0;
        //堆栈里存储的都是各个需要相加起来的结果，把它们加起来，返回总和即可
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }



}
