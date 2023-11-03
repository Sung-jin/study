package leetcode.dfs;

import apple.laf.JRSUIUtils;
import leetcode.TreeNode;

import java.util.HashMap;
import java.util.Map;

/*
Q.2265. Count Nodes Equal to Average of Subtree
Given the root of a binary tree, return the number of nodes where the value of the node is equal to the average of the values in its subtree.

Note:

The average of n elements is the sum of the n elements divided by n and rounded down to the nearest integer.
A subtree of root is a tree consisting of root and all of its descendants.

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
public class CountNodesEqualToAverageOfSubtree {

    private Map<TreeNode, Integer> nodeCount;
    private int answer;
    public int averageOfSubtree(TreeNode root) {
        answer = 0;
        nodeCount = new HashMap<>();
        helper(root);

        return this.answer;
    }

    private int helper(TreeNode node) {
        if (node == null) return 0;

        TreeNode left = node.left, right = node.right;
        int leftTotalVal = helper(left);
        int rightTotalVal = helper(right);

        if (left == null && right == null) {
            nodeCount.put(node, 1);
        } else {
            int leftCount = nodeCount.getOrDefault(node.left, 0);
            int rightCount = nodeCount.getOrDefault(node.right, 0);
            nodeCount.put(node, leftCount + rightCount + 1);
        }

        int count = nodeCount.get(node);
        int totalVal = node.val + leftTotalVal + rightTotalVal;

        if (node.val == Math.floorDiv(totalVal, count)) {
            this.answer++;
        }

        return totalVal;
    }
}

/*
class Solution {
    int res = 0;
    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return res;
    }

    private int[] dfs(TreeNode node) {
        if(node == null) {
            return new int[] {0,0};
        }

        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        int currSum = left[0] + right[0] + node.val;
        int currCount = left[1] + right[1] + 1;

        if(currSum / currCount == node.val) {
            res++;
        }

        return new int[] {currSum, currCount};
    }
}
 */