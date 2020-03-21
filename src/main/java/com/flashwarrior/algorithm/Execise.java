package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.*;

public class Execise {

    /**
     * 两数相加
     */
    @Test
    public void test1() {

        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(maxArea1(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));


        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));

        ListNode l1 = new ListNode(0);

        ListNode l2 = new ListNode(1);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(2);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(7);
        l1.setNext(l2);
        l2.setNext(l3);

        l4.setNext(l5);
        l5.setNext(l6);


        ListNode listNode = mergeTwoLists(l1, l4);

        getListNode(listNode);

        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));

    }


    /**
     * 删除排序数组中的重复元素
     * [0,0,1,1,1,2,2,3,3,4] -> 5
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }


    @Test
    public void t1() {
        removeDuplicates(new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5});
    }

    public void getListNode(ListNode listNode) {

        System.out.println(listNode.getVal());

        if (listNode.next != null) {
            getListNode(listNode.next);
        }

    }


    /**
     * 合并两个有序列表
     *
     * @param l1 1 2 6
     * @param l2 3 5 9
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }


    /**
     * 最接近的三数之和
     * <p>
     * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
     * <p>
     * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {

        //排序
        Arrays.sort(nums);

        //初始化一个结果
        int ans = nums[0] + nums[1] + nums[2];

        for (int i = 0; i < nums.length; i++) {
            int start = i + 1, end = nums.length - 1;

            while (start < end) {
                int sum = nums[start] + nums[end] + nums[i];
                if (Math.abs(target - sum) < Math.abs(target - ans)) {
                    ans = sum;
                }

                if (sum > target) end--;
                else if (sum < target) start++;
                else return ans;
            }
        }

        return ans;
    }

    /**
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        if (nums == null || nums.length < 3) return ans;

        for (int i = 0; i < len; i++) {
            if (nums[i] > 0) break; // 因为是排好序的，所以如果当前数字大于0，则三数之和一定大于0，所以结束循环

            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int L = i + 1;
            int R = len - 1;

            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) L++; // 去重
                    while (L < R && nums[R] == nums[R - 1]) R--; // 去重

                    L++;
                    R--;

                } else if (sum < 0) L++;
                else if (sum > 0) R--;
            }
        }
        return ans;
    }


    /**
     * 最长公共前缀
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }


    /**
     * 最大面积 暴力法
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {

        int maxArea = 0;

        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                maxArea = Math.max(maxArea, Math.min(height[i], height[j]) * (j - i));
            }
        }
        return maxArea;
    }

    /**
     * 最大面积法 双指针
     *
     * @param height
     * @return
     */
    public int maxArea1(int[] height) {
        int maxArea = 0, l = 0, r = height.length - 1;

        while (l < r) {
            maxArea = Math.max(maxArea, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return maxArea;
    }


    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    /**
     * 一个字符串最长不重复子串
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        //定义一个计数器
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;

    }


    /**
     * 两数相加
     *
     * @param l1 1-2-4
     * @param l2 3-6-9
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;

            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (p != null) p = p.next;
            if (q != null) q = q.next;

        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummyHead.next;
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

    private int sum(int[] arr) {
        int i = 0, j = arr.length - 1;
        if (i == j) {
            return arr[i];
        } else {
            return arr[i] + sum(Arrays.copyOfRange(arr, i + 1, arr.length));
        }
    }


    @Test
    public void test11() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println(sum(arr));
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];

        int[] output = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            int max = Integer.MAX_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }

            output[i] = max;
        }
        return output;
    }
}
