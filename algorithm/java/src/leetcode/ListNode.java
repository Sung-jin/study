package leetcode;

public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public static ListNode generateNode(Integer[] values) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode next = head;

        for (int i = 1; i < values.length; i++) {
            ListNode nextNode = new ListNode(values[i]);
            next.next = nextNode;
            next = nextNode;
        }

        return head;
    }

    public String getAllNode() {
        StringBuilder builder = new StringBuilder();
        ListNode next = this;

        builder.append("[");
        builder.append(next.val).append(", ");

        while (next.next != null) {
            next = next.next;
            builder.append(next.val).append(", ");
        }

        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");

        return builder.toString();
    }
}

