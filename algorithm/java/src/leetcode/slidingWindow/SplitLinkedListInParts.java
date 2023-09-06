package leetcode.slidingWindow;

import leetcode.ListNode;

/*
Q.725 Split Linked List in Parts
Given the head of a singly linked list and an integer k, split the linked list into k consecutive linked list parts.

The length of each part should be as equal as possible: no two parts should have a size differing by more than one. This may lead to some parts being null.

The parts should be in the order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal to parts occurring later.

Return an array of the k parts.

Definition for singly-linked list.
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
 */
public class SplitLinkedListInParts {
    public ListNode[] splitListToParts(ListNode head, int k) {
        if (k == 1) return new ListNode[]{head};

        ListNode[] answer = new ListNode[k];
        ListNode next = head;
        int size = 0;

        while (next != null) {
            size++;
            next = next.next;
        }
        int splitSize = size / k, remain = size % k;
        next = head;

        for (int i = 0; i < k; i++) {
            if (next == null) break;

            ListNode prev = null;
            answer[i] = next;
            for (int j = 0; j < splitSize; j++) {
                prev = next;
                next = next.next;
            }

            if (remain > 0) {
                remain--;
                prev = next;
                next = next.next;
            }
            prev.next = null;
        }

        return answer;
    }
}

/*
class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] parts = new ListNode[k];
        int len = 0;
        for (ListNode node = root; node != null; node = node.next)
            len++;
        int n = len / k, r = len % k; // n : minimum guaranteed part size; r : extra nodes spread to the first r parts;
        ListNode node = root, prev = null;
        for (int i = 0; node != null && i < k; i++, r--) {
            parts[i] = node;
            for (int j = 0; j < n + (r > 0 ? 1 : 0); j++) {
                prev = node;
                node = node.next;
            }
            prev.next = null;
        }
        return parts;
    }
}
 */
