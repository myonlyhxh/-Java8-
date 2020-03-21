package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Exercise2 {


    @Test
    public void test() {

        int[][] intervals = {{4, 5}, {1, 4}};

        int[][] merge = merge(intervals);

        for (int[] ints : merge) {
            System.out.println(Arrays.toString(ints));
        }
    }


    //LeetCode第56题：给出一个区间的集合，请合并所有重叠的区间
//    示例 1
//    输入: [[1,3], [2,6], [8,10], [15,18]]
//    输出: [[1,6], [8,10], [15,18]]
//
//    解释: 区间 [1,3] 和 [2,6] 重叠，将它们合并为 [1,6]。
//
//    示例 2
//    输入: [[1,4], [4,5]]
//    输出: [[1,5]]
//
//    解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。

    public int[][] merge(int[][] intervals) {
        //将所有的区间按照起始时间的先后顺序排序
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));

        //定义一个previous变量，初始化为null
        int[] previous = null;

        //定义一个result变量，用来保存最终的区间结果
        List<int[]> result = new ArrayList<>();


        //从头开始遍历给定的所有区间
        for (int[] current : intervals) {
            //如果这是第一个区间，或者当前区间和前一个区间没有重叠，那么将当前区间加入到结果中
            if (previous == null || current[0] > previous[1]) {
                previous = current;
                result.add(current);
            } else {
                //否则，两个区间发生了重叠，更新前一个区间的结束时间
                previous[1] = Math.max(previous[1], current[1]);
            }
        }

        return result.toArray(new int[result.size()][]);

    }


    //LeetCode 第 435 题：给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。
//    示例 1
//    输入: [ [1,2], [2,3], [3,4], [1,3] ]
//    输出: 1
//    解释: 移除 [1,3] 后，剩下的区间没有重叠。

//    示例 2
//    输入: [ [1,2], [1,2], [1,2] ]
//    输出: 2
//    解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。

//    示例 3
//    输入: [ [1,2], [2,3] ]
//    输出: 0


    //暴力法
    //在主体函数里，先将区间按照起始时间的先后顺序排序，然后调用递归函数
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));

        return eraseOverlapIntervals(-1, 0, intervals);

    }

    //递归函数里，先检查是否已经处理完所有的区间，是，表明不需要删除操作，直接返回
    private int eraseOverlapIntervals(int prev, int curr, int[][] intervals) {
        if (curr == intervals.length) {
            return 0;
        }

        int taken = Integer.MAX_VALUE, noTaken;

        if (prev == -1 || intervals[prev][1] <= intervals[curr][0]) {
            //prev 和 curr 没有发生重叠的时候，才可以选择保留当前区间curr
            taken = eraseOverlapIntervals(curr, curr + 1, intervals);
        }

        //其他情况，可以考虑删除curr区间，看看删除了它之后会不会产生最好的结果
        noTaken = eraseOverlapIntervals(prev, curr + 1, intervals) + 1;

        return Math.min(taken, noTaken);

    }


    //贪婪法
    public int eraseOverlapIntervals1(int[][] intervals) {
        if (intervals.length == 0) return 0;

        //将所有区间按照起始时间排序
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));

        //用一个变量end记录当前的最小结束时间点，以及一个count变量记录到目前为止删除掉了多少区间
        int end = intervals[0][1], count = 0;

        //从第二个区间开始，判断一下当前区间和前一个区间的结束时间
        for (int i = 1; i < intervals.length; i++) {
            //当前区间和前一个区间有重叠，
            //即当前区间的起始时间小于上一个区间的结束时间，end记录下两个结束时间的最小值，把结束时间晚的区间删除，计数加1。
            if (intervals[i][0] < end) {
                end = Math.min(intervals[i][1], end);
                count++;
            } else {
                //如果没有发生重叠，根据贪婪算法，更新end变量为当前区间的结束时间
                end = intervals[i][1];
            }
        }
        return count;
    }


}
