package leetcode.bfs;

import leetcode.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/*
Q. 101
Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

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
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        if ((root.left == null && root.right != null) || (root.left != null && root.right == null)) return false;

        Queue<TreeNode> left = new LinkedList<>();
        Queue<TreeNode> right = new LinkedList<>();

        left.add(root.left);
        right.add(root.right);

        while(!left.isEmpty() && !right.isEmpty()) {
            int leftSize = left.size(), rightSize = right.size();

            if (leftSize != rightSize) return false;

            for (int i = 0; i < leftSize; i++) {
                TreeNode leftNode = left.poll();
                TreeNode rightNode = right.poll();

                if (leftNode == null && rightNode == null) continue;
                if (leftNode == null || rightNode == null) return false;
                if (leftNode.val != rightNode.val) return false;

                left.add(leftNode.left);
                right.add(rightNode.right);
                left.add(leftNode.right);
                right.add(rightNode.left);
            }
        }

        return true;
    }
}

/*
-- recursive
public boolean isSymmetric(TreeNode root) {
    return root==null || isSymmetricHelp(root.left, root.right);
}

private boolean isSymmetricHelp(TreeNode left, TreeNode right){
    if(left==null || right==null)
        return left==right;
    if(left.val!=right.val)
        return false;
    return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
}

-- non-recursive
public boolean isSymmetric(TreeNode root) {
    if(root==null)  return true;

    Stack<TreeNode> stack = new Stack<TreeNode>();
    TreeNode left, right;
    if(root.left!=null){
        if(root.right==null) return false;
        stack.push(root.left);
        stack.push(root.right);
    }
    else if(root.right!=null){
        return false;
    }

    while(!stack.empty()){
        if(stack.size()%2!=0)   return false;
        right = stack.pop();
        left = stack.pop();
        if(right.val!=left.val) return false;

        if(left.left!=null){
            if(right.right==null)   return false;
            stack.push(left.left);
            stack.push(right.right);
        }
        else if(right.right!=null){
            return false;
        }

        if(left.right!=null){
            if(right.left==null)   return false;
            stack.push(left.right);
            stack.push(right.left);
        }
        else if(right.left!=null){
            return false;
        }
    }

    return true;
}
 */