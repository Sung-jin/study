package leetcode.bruteForce;

import leetcode.ListNode;

/*
Q.2095
You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.

The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing, where ⌊x⌋ denotes the largest integer less than or equal to x.

For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.
 */
public class DeleteTheMiddleNodeOfALinkedList {
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) return null;

        int size = 1;
        ListNode next = head;
        while(next.next != null) {
            size++;
            next = next.next;
        }

        ListNode beforeNode = null;
        next = head;
        for (int i = 0; i < (size / 2); i++) {
            beforeNode = next;
            next = next.next;
        }

        beforeNode.next = next.next;
        next = null;

        return head;
    }
}

/*
public ListNode deleteMiddle(ListNode head) {
    if (head == null || head.next == null) return null;
    ListNode slow = head, fast = head.next.next;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    slow.next = slow.next.next;
    return head;
    // 와... 글네 어차피 절반 지점을 찾으면 되니까, 2칸씩 넘어가서 null 이 되는 순간이 절반 지점일테니, 그 순간의 node 의 next 를 옮기면 되네
}
 */