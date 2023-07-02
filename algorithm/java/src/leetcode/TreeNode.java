package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode generateNode(Integer[] values) {
        if (values.length == 0) return null;

        int index = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(values[0]);

        queue.offer(root);

        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (node == null) continue;
                if (index == values.length) return root;

                Integer leftValue = values[index++];
                if (leftValue != null) {
                    TreeNode left = new TreeNode(leftValue);
                    node.left = left;
                    queue.offer(left);
                }

                if (index == values.length) return root;

                Integer rightValue = values[index++];
                if (rightValue != null) {
                    TreeNode right = new TreeNode(rightValue);
                    node.right = right;
                    queue.offer(right);
                }

                if (index == values.length) return root;
            }
        }

        return root;
    }
}
