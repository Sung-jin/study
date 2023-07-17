package leetcode.bruteForce;

import leetcode.ListNode;

import java.util.Stack;

/*
Q.445
You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Definition for singly-linked list.
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
 */
public class AddTwoNumbersII {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<ListNode> left = getListNodeStack(l1);
        Stack<ListNode> right = getListNodeStack(l2);
        Stack<Integer> sumResult = new Stack<>();
        int round = 0;

        while (!left.isEmpty() || !right.isEmpty()) {
            int l = getValueInStack(left);
            int r = getValueInStack(right);
            int sum = l + r + round;

            if (sum >= 10) {
                round = 1;
                sum -= 10;
            } else {
                round = 0;
            }
            sumResult.add(sum);
        }

        if (round == 1) sumResult.add(round);

        return createNodeByStack(sumResult);
    }

    private Stack<ListNode> getListNodeStack(ListNode node) {
        Stack<ListNode> result = new Stack<>();
        ListNode next = node;

        while (next != null) {
            result.add(next);
            next = next.next;
        }

        return result;
    }

    private int getValueInStack(Stack<ListNode> value) {
        int result = 0;
        if (!value.isEmpty()) result = value.pop().val;

        return result;
    }

    private ListNode createNodeByStack(Stack<Integer> value) {
        if (value.isEmpty()) return new ListNode();

        ListNode result = new ListNode();
        ListNode next = result;
        next.val = value.pop();

        while (!value.isEmpty()) {
            ListNode nextNode = new ListNode(value.pop());
            next.next = nextNode;
            next = nextNode;
        }

        return result;
    }
}

/*
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    Stack<Integer> s1 = new Stack<Integer>();
    Stack<Integer> s2 = new Stack<Integer>();

    while(l1 != null) {
        s1.push(l1.val);
        l1 = l1.next;
    };
    while(l2 != null) {
        s2.push(l2.val);
        l2 = l2.next;
    }

    int sum = 0;
    ListNode list = new ListNode(0);
    while (!s1.empty() || !s2.empty()) {
        if (!s1.empty()) sum += s1.pop();
        if (!s2.empty()) sum += s2.pop();
        list.val = sum % 10;
        ListNode head = new ListNode(sum / 10);
        head.next = list;
        list = head;
        sum /= 10;
    }

    return list.val == 0 ? list.next : list;
}
 */