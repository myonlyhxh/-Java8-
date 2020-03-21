package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Exercise5 {

    @Test
    public void test() {
        String str = "algorithm";
        System.out.println(str);
        System.out.println(reverse(str));


        System.out.println(ectopicWord("cat","nagaram"));
    }


    public String reverse(String str) {

        char[] chars = str.toCharArray();
        int i = 0;
        int j = str.length() - 1;

        char x;

        while (i < j) {
            x = str.charAt(i);
            chars[i] = chars[j];
            chars[j] = x;
            i++;
            j--;
        }

        return String.valueOf(chars);
    }

    public boolean ectopicWord(String s1, String s2) {
        Map<Character, Integer> resultMap = new HashMap<>();

        for (char c : s1.toCharArray()) {
            if (resultMap.get(c) != null) {
                resultMap.put(c, resultMap.get(c) + 1);
            } else {
                resultMap.put(c, 1);
            }
        }

        for (char c : s2.toCharArray()) {
            if(resultMap.get(c) != null) {
                resultMap.put(c, resultMap.get(c) - 1);
            }else{
                resultMap.put(c, 1);
            }
        }
        AtomicInteger count = new AtomicInteger();
        resultMap.forEach((k,v)->{
            if(v != 0) {
                count.getAndIncrement();
            }
        });

        return count.get() == 0;
    }

    public ListNode reverseListNode(ListNode listNode){
        ListNode pre = null;
        ListNode next = null;

        while (listNode != null) {
            next = listNode.next;
            listNode.next = pre;
            pre = listNode;
            listNode = next;
        }
        return pre;
    }




    class ListNode {

        private int val;

        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }


        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

}
