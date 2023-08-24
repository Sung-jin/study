package leetcode.bfs;

import leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/*
Q.1609. Even Odd Tree
A binary tree is named Even-Odd if it meets the following conditions:

The root of the binary tree is at level index 0, its children are at level index 1, their children are at level index 2, etc.
For every even-indexed level, all nodes at the level have odd integer values in strictly increasing order (from left to right).
For every odd-indexed level, all nodes at the level have even integer values in strictly decreasing order (from left to right).
Given the root of a binary tree, return true if the binary tree is Even-Odd, otherwise return false.

Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
 */
public class EvenOddTree {
    public boolean isEvenOddTree(TreeNode root) {
        int level = 0;
        ArrayList<TreeNode> next = new ArrayList<>();
        next.add(root);

        while (!next.isEmpty()) {
            if (!checkValidValue(next, level)) return false;

            level++;
        }

        return true;
    }

    private boolean checkValidValue(List<TreeNode> nodes, int level) {
        int beforeValue = level % 2 == 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int size = nodes.size();

        for (int i = 0; i < size; i++) {
            TreeNode node = nodes.remove(0);

            if (level % 2 == 0 && (beforeValue >= node.val || node.val % 2 == 0)) return false;
            else if (level % 2 == 1 && (beforeValue <= node.val || node.val % 2 == 1)) return false;

            if (node.left != null) nodes.add(node.left);
            if (node.right != null) nodes.add(node.right);

            beforeValue = node.val;
        }

        return true;
    }
}


/*
public boolean isEvenOddTree(TreeNode root) {
    if(root == null) return true;
    Queue<TreeNode> q = new LinkedList();
    q.add(root);
    boolean even = true;
    while(q.size() > 0) {
        int size = q.size();
        int prevVal = even ? Integer.MIN_VALUE : Integer.MAX_VALUE; // init preVal based on level even or odd
        while(size-- > 0) { // level by level
            root = q.poll();
            if(even && (root.val % 2 == 0 || root.val <= prevVal)) return false; // invalid case on even level
            if(!even && (root.val % 2 == 1 || root.val >= prevVal)) return false; // invalid case on odd level
            prevVal = root.val; // update the prev value
            if(root.left != null) q.add(root.left); // add left child if exist
            if(root.right != null) q.add(root.right); // add right child if exist
        }
        even = !even; // alter the levels
    }

    return true;
}
 */