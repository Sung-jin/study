package leetcode.sorting;

import leetcode.ListNode;

import java.util.Stack;

/*
Q.92. Reverse Linked List II
Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes of the list from position left to position right, and return the reversed list.
 */
public class ReverseLinkedListII {
    // 1 <= left <= right <= n
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) return reverseNodes(head, right - left + 1);

        ListNode pointer = head;
        for (int i = 2; i < left; i++) {
            pointer = pointer.next;
        }

        pointer.next = reverseNodes(pointer.next, right - left + 1);

        return head;
    }

    private ListNode reverseNodes(ListNode head, int size) {
        if (size == 0 || size == 1) return head;

        ListNode tail = head;
        Stack<ListNode> stack = new Stack<>();

        for (int i = 0; i < size; i++) {
            stack.add(tail);
            tail = tail.next;
        }

        ListNode res = stack.pop();
        ListNode pointer = res;

        for (int i = 0; i < (size - 1); i++) {
            ListNode next = stack.pop();
            pointer.next = next;
            pointer = next;
        }

        pointer.next = tail;

        return res;
    }
}

/*
public ListNode reverseBetween(ListNode head, int m, int n) {
    if(head == null) return null;
    ListNode dummy = new ListNode(0); // create a dummy node to mark the head of this list
    dummy.next = head;
    ListNode pre = dummy; // make a pointer pre as a marker for the node before reversing
    for(int i = 0; i<m-1; i++) pre = pre.next;

    ListNode start = pre.next; // a pointer to the beginning of a sub-list that will be reversed
    ListNode then = start.next; // a pointer to a node that will be reversed

    // 1 - 2 -3 - 4 - 5 ; m=2; n =4 ---> pre = 1, start = 2, then = 3
    // dummy-> 1 -> 2 -> 3 -> 4 -> 5

    for(int i=0; i<n-m; i++)
    {
        start.next = then.next;
        then.next = pre.next;
        pre.next = then;
        then = start.next;
    }

    // first reversing : dummy->1 - 3 - 2 - 4 - 5; pre = 1, start = 2, then = 4
    // second reversing: dummy->1 - 4 - 3 - 2 - 5; pre = 1, start = 2, then = 5 (finish)

    return dummy.next;

}
 */
