package com.flashwarrior.algorithm;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {


    @Test
    public void test() {
        int[] nums = {1, 3, 2, 7, 4, 9};


        int[] ints = sort1(nums);

        System.out.println(Arrays.toString(ints));
        int[] ints1 = sort2(nums);
        System.out.println(Arrays.toString(ints1));

        sort3(nums, 0, 5);

        System.out.println(Arrays.toString(nums));


    }


    /**
     * 冒泡排序
     *
     * @param arr
     * @return
     */
    public int[] sort1(int[] arr) {
        boolean hasChanged = true;
        for (int i = 0; i < arr.length - 1 && hasChanged; i++) {
            //没有发生交换
            hasChanged = false;

            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    hasChanged = true;
                }
            }


        }

        return arr;
    }

    public void swap(int[] nums, int start, int end) {
        int curr = nums[end];
        nums[end] = nums[start];
        nums[start] = curr;
    }

    /**
     * 插入排序 [1,3,2,7,4,9]
     *
     * @param nums
     * @return
     */
    public int[] sort2(int[] nums) {
        for (int i = 1, j, current; i < nums.length; i++) {
            current = nums[i];

            for (j = i - 1; j >= 0 && nums[j] > current; j--) {
                nums[j + 1] = nums[j];
            }

            nums[j + 1] = current;
        }
        return nums;
    }


    /**
     * 归并排序
     *
     * @param nums
     * @param lo
     * @param hi
     */
    public void sort3(int[] nums, int lo, int hi) {
        //判断是否只剩下最后一个元素
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;


        sort3(nums, lo, mid);
        sort3(nums, mid + 1, hi);

        merge(nums, lo, mid, hi);

    }

    /**
     * 合并
     *
     * @param nums
     * @param lo
     * @param mid
     * @param hi
     */
    private void merge(int[] nums, int lo, int mid, int hi) {
        int[] copy = nums.clone();

        //i->左半边的起始位置 j->右半边的起始位置
        int k = lo, i = lo, j = mid + 1;

        while (k <= hi) {
            //左半边的数都处理完毕，只剩下右半边的数，只需要将右半边的数逐个拷贝过去。因为是已经排好序的
            if (i > mid) {
                nums[k++] = copy[j++];
            } else if (j > hi) {
                //右半边的数都处理完毕，只剩下左半边的数，只需要将左半边的数逐个拷贝过去就好。因为是已经排好序的
                nums[k++] = copy[i++];
            } else if (copy[j] < copy[i]) {
                //右边的数小于左边的数，将右边的数拷贝到合适的位置，j 指针往前移动一位。
                nums[k++] = copy[j++];
            } else {
                //左边的数小于右边的数，将左边的数拷贝到合适的位置，i 指针往前移动一位。
                nums[k++] = copy[i++];
            }
        }
    }


//    public void sort4(int[] nums , int lo, int hi){
//        if(lo >= hi) return;
//
//        int p = partition(nums, lo, hi);
//
//        sort(nums, lo, p-1);
//        sort(nums, p + 1, hi);
//    }


    @Test
    public void test2() {
        //12 -> 12L 或者 1 2 AB
        int i = numDecodings("230");

        System.out.println(i);
    }

    public int numDecodings(String s) {
        char[] chars = s.toCharArray();
        return decode(chars, chars.length - 1);

    }

    private int decode(char[] chars, int index) {
        if (index <= 0) return 1;

        int count = 0;

        char curr = chars[index];
        char prev = chars[index - 1];

        if (curr > '0') {
            count = decode(chars, index - 1);
        }

        if (prev == '1' || (prev == '2') && curr <= '6') {
            count += decode(chars, index - 2);
        }

        return count;
    }


    @Test
    public void t1(){
        List<String> helper = helper(4, 5);
        helper.forEach(System.out::println);
    }

    public List<String> helper(int n, int m) {
        if (n < 0 || m < 0 || n > m) {
            throw new IllegalArgumentException("invalid input");
        }

        if (n == 0) return new ArrayList<>(Collections.singletonList(""));
        if (n == 1) return new ArrayList<>(Arrays.asList("0", "1", "8"));

        //缩小问题规模
        List<String> list = helper(n - 2, m);

        //整合结果
        List<String> res = new ArrayList<>();

        for (String s : list) {
            if (n != m) res.add("0" + s + "0");

            res.add("1" + s + "1");
            res.add("6" + s + "6");
            res.add("8" + s + "8");
            res.add("9" + s + "9");
        }

        return res;
    }

    }
