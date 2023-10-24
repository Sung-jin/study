package leetcode.bfs;

import leetcode.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Q.515. Find Largest Value in Each Tree Row
Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).
 */
public class FindLargestValueInEachTreeRow {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> answer = new ArrayList<>();

        if (root == null) return answer;

        Queue<TreeNode> sameLevelValues = new LinkedList<>();
        sameLevelValues.offer(root);

        while(!sameLevelValues.isEmpty()) {
            int count = sameLevelValues.size();
            int maxValue = Integer.MIN_VALUE;

            for (int i = 0; i < count; i++) {
                TreeNode node = sameLevelValues.poll();

                if (node == null) continue;
                maxValue = Math.max(maxValue, node.val);

                if (node.left != null) sameLevelValues.offer(node.left);
                if (node.right != null) sameLevelValues.offer(node.right);
            }

            answer.add(maxValue);
        }

        return answer;
    }
}

/*
public class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        helper(root, res, 0);
        return res;
    }
    private void helper(TreeNode root, List<Integer> res, int d){
        if(root == null){
            return;
        }
       //expand list size
        if(d == res.size()){
            res.add(root.val);
        }
        else{
        //or set value
            res.set(d, Math.max(res.get(d), root.val));
        }
        helper(root.left, res, d+1);
        helper(root.right, res, d+1);
    }
}
 */
