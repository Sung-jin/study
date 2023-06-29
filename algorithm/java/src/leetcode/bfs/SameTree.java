package leetcode.bfs;

/*
Q.100

Given the roots of two binary trees p and q, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

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

import java.util.LinkedList;
import java.util.Queue;

public class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        Queue<TreeNode> queueP = new LinkedList<>();
        Queue<TreeNode> queueQ = new LinkedList<>();

        queueP.add(p);
        queueQ.add(q);

        while(!queueP.isEmpty()) {
            int pSize = queueP.size();

            if (pSize != queueQ.size()) return false;

            for (int i = 0; i < pSize; i++) {
                TreeNode nowP = queueP.poll();
                TreeNode nowQ = queueQ.poll();

                if (nowP == null && nowQ == null) continue;
                if (nowP == null || nowQ == null) return false;

                if (nowP.val != nowQ.val) return false;
                if ((nowP.left == null && nowQ.left != null) || (nowP.left != null && nowQ.left == null) ||
                        (nowP.right == null && nowQ.right != null) || (nowP.right != null && nowQ.right == null)
                ) return false;

                if (nowP.left != null) queueP.add(nowP.left);
                if (nowP.right != null) queueP.add(nowP.right);
                if (nowQ.left != null) queueP.add(nowQ.left);
                if (nowQ.right != null) queueP.add(nowQ.right);
            }
        }

        return true;
    }

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
}

/*
public boolean isSameTree(TreeNode p, TreeNode q) {
    if(p == null && q == null) return true;
    if(p == null || q == null) return false;
    if(p.val == q.val)
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    return false;
}
 */