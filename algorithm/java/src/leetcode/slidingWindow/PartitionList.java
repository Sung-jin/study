package leetcode.slidingWindow;

import leetcode.ListNode;

/*
Q.86
Given the head of a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

 Definition for singly-linked list.
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
*/
public class PartitionList {
    public ListNode partition(ListNode head, int x) {
        ListNode next = head, beforeNode = null, start = null, end = null;

        while(next != null) {
            if (start != null && next.val > x && end == null) end = next;
            else if (start != null && end != null) {
                if (next.val < x) {
                    start.next = next;
                    beforeNode.next = next.next;
                    next.next = end;
                    start = next;
                    next = beforeNode;
                }
            } else if (next.val < x) start = next;

            beforeNode = next;
            next = next.next;
        }

        return head;
    }
}

/*
public ListNode partition(ListNode head, int x) {
    ListNode dummy1 = new ListNode(0), dummy2 = new ListNode(0);  //dummy heads of the 1st and 2nd queues
    ListNode curr1 = dummy1, curr2 = dummy2;      //current tails of the two queues;
    while (head!=null){
        if (head.val<x) {
            curr1.next = head;
            curr1 = head;
        }else {
            curr2.next = head;
            curr2 = head;
        }
        head = head.next;
    }
    curr2.next = null;          //important! avoid cycle in linked list. otherwise u will get TLE.
    curr1.next = dummy2.next;
    return dummy1.next;
}

기존 노드를 꼭 다시 재조립 할 필요 없이, 기존 순서를 보장하는 작은 단위의 리스트/큰 리스트를 각각 만든다음 합치면 되네..
 */