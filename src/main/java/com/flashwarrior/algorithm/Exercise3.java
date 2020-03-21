package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise3 {

    //LeetCode  第 269 题，火星字典：现有一种使用字母的全新语言，这门语言的字母顺序与英语顺序不同。
    // 假设，您并不知道其中字母之间的先后顺序。
    // 但是，会收到词典中获得一个不为空的单词列表。
    // 因为是从词典中获得的，所以该单词列表内的单词已经按这门新语言的字母顺序进行了排序。
    // 您需要根据这个输入的列表，还原出此语言中已知的字母顺序。


//    示例 1
//    输入:[ "wrt", "wrf","er","ett", "rftt"]
//    输出:"wertf"
//
//    示例 2
//    输入:[ "z", "x"]
//    输出: "zx"
//
//    示例 3
//    输入:[ "z",  "x","z"]
//    输出: ""
//    解释: 此顺序是非法的，因此返回 ""。


    /**
     * 这个就是字典表中的顺序
     * 输入:[ "wrt", "wrf","er","ett", "rftt"]
     * t -> f
     * w -> e
     * r -> t
     * e -> r
     *
     * 1.比较以w开头的字符串，它们是wrt和wrf，之所以wrt会排在wrf之前，是因为t比f在火星字典里出现的顺序要早。
     * 此时将这两个字母的关系表达为t -> f
     *
     * 2.比较wrf和er，第一个字母开始不同，因为，得到w排在e之前，记为w -> e
     *
     * 3.比较er和ett，从第二个字母开始不同，因此，得到r排在t之前，记为r -> t
     *
     * 4.比较ett和rftt，从第一个字母开始不一样，得到e排在r之前，记为e -> r
     */

    @Test
    public void test(){
        String[] words = {"wrt", "wrf","er","ett", "rftt"};
        alienOrder(words);
    }

    /**
     * 代码实现，包括两个步骤，第一步根据输入构建一个有向图；第二步是对这个有向图进行拓扑排序
     */
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) return null;
        if (words.length == 1) return words[0];

        //构建有向图：定义一个邻接链表，adjList，也可以用邻接矩阵
        Map<Character, List<Character>> adjList = new HashMap<>();

        for (int i = 0; i < words.length - 1; i++) {

            String w1 = words[i], w2 = words[i + 1];
            int n1 = w1.length(), n2 = w2.length();

            boolean found = false;

            for (int j = 0; j < Math.max(w1.length(), w2.length()); j++) {
                Character c1 = j < n1 ? w1.charAt(j) : null;
                Character c2 = j < n2 ? w2.charAt(j) : null;

                if (c1 != null && !adjList.containsKey(c1)) {
                    adjList.put(c1, new ArrayList<>());
                }

                if (c2 != null && !adjList.containsKey(c2)) {
                    adjList.put(c2, new ArrayList<>());
                }

                if (c1 != null && c2 != null && c1 != c2 && !found) {
                    adjList.get(c1).add(c2);
                    found = true;
                }

            }

        }

        return "sss";
    }


}
