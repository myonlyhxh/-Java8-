package com.flashwarrior.algorithm;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Execrise1 {


    /**
     * 二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = maxDepth(root.left);
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }


    /* Definition for a binary tree node. */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    //LeetCode 第 23 题：合并 k 个排好序的链表，返回合并后的排序链表。分析和描述算法的复杂度。
    //    示例
    //
    //    输入：
    //
    //            [
    //
    //            1 -> 4 -> 5,
    //
    //            1 -> 3 -> 4,
    //
    //            2 -> 6
    //
    //            ]
    //
    //
    //
    //    输出：1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6


    @Test
    public void t2() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(7);
        l1.next = l2;
        l2.next = l3;

        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(4);
        ListNode l6 = new ListNode(5);
        l4.next = l5;
        l5.next = l6;

        ListNode l7 = new ListNode(2);
        ListNode l8 = new ListNode(8);
        l7.next = l8;


        ListNode[] lists = new ListNode[]{l1, l4, l7};
        ListNode listNode = mergeKList(lists);


    }


    /**
     * 最小堆法
     * 时间复杂度：O(nk×log(k))
     *
     * @param lists
     * @return
     */
    public ListNode mergeKList(ListNode[] lists) {
        //利用一个空的链表头方便插入节点
        ListNode fakeHead = new ListNode(0), p = fakeHead;
        int k = lists.length;


        //定义一个最小堆来保存k个链表节点，将k个链表的头放入到最小堆里
        PriorityQueue<ListNode> heap = new PriorityQueue<>(k, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });


        //从最小堆里将当前最小的节点取出，插入到结果链表中
        for (ListNode node : lists) {
            if (node != null) {
                heap.offer(node);
            }
        }


        while (!heap.isEmpty()) {
            ListNode node = heap.poll();

            p.next = node;
            p = p.next;


            //如果发现节点后面还有后续节点，将后续节点加入到最小堆里
            if (node.next != null) {
                heap.offer(node.next);
            }

        }

        return fakeHead.next;

    }

    public ListNode mergeKList(ListNode[] lists, int low, int high) {
        if (low == high) return lists[low];

        //从中间切一刀
        int middle = low + (high - low) / 2;

        //递归地处理左边和右边的列表，最后合并
        return mergeTwoLists(
                mergeKList(lists, low, middle),
                mergeKList(lists, middle + 1, high)
        );
    }

    private ListNode mergeTwoLists(ListNode a, ListNode b) {

        if (a == null) return b;
        if (b == null) return a;

        if (a.val < b.val) {
            a.next = mergeTwoLists(a.next, b);
            return a;
        }

        b.next = mergeTwoLists(a, b.next);
        return b;
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
