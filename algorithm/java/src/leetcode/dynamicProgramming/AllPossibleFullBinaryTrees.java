package leetcode.dynamicProgramming;

import leetcode.TreeNode;

import java.util.*;

/*
Q.894
Given an integer n, return a list of all possible full binary trees with n nodes. Each node of each tree in the answer must have Node.val == 0.

Each element of the answer is the root node of one possible tree. You may return the final list of trees in any order.

A full binary tree is a binary tree where each node has exactly 0 or 2 children.

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
public class AllPossibleFullBinaryTrees {
    private static List<List<TreeNode>> FULL_BINARY_TREE = new ArrayList<>();

    static {
        FULL_BINARY_TREE.add(Collections.singletonList(new TreeNode(0)));
    }

    public List<TreeNode> allPossibleFBT(int n) {
         createFullBinaryTree(n);

         return null;
    }

    private void createFullBinaryTree(int n) {
        if (FULL_BINARY_TREE.size() >= n) return;

        for (int i = FULL_BINARY_TREE.size(); i < n; i++) {

        }
    }

    private void createFullBinaryTreeWithElementCount(int n) {
        if (n % 2 == 1) FULL_BINARY_TREE.add(null);
        else {
            List<TreeNode> parents = FULL_BINARY_TREE.get(n - 2);
        }
    }

    private List<TreeNode> addChild(TreeNode node) {
        List<TreeNode> result = new ArrayList<>();

        return null;
    }

    private TreeNode copyFullBiaryTreeNode(TreeNode node) {
        TreeNode copy = new TreeNode(node.val);
        Queue<TreeNode> nodes = new LinkedList<>();
        Queue<TreeNode> copyNodes = new LinkedList<>();
        nodes.add(node);
        copyNodes.add(copy);

        while(!nodes.isEmpty()) {
            int size = nodes.size();

            for (int i = 0; i < size; i++) {
                TreeNode next = nodes.poll();
                TreeNode copyNext = copyNodes.poll();

                TreeNode left = next.left;
                TreeNode right = next.right;

                if (left == null && right == null) continue;

                copyNext.left = new TreeNode(left.val);
                copyNext.right = new TreeNode(right.val);

                nodes.offer(left);
                nodes.offer(right);
                copyNodes.offer(copyNext.left);
                copyNodes.offer(copyNext.right);
            }
        }

        return copy;
    }

    private List<TreeNode> findAllLeaf(TreeNode node) {
        Queue<TreeNode> nodes = new LinkedList<>();
        List<TreeNode> leafs = new ArrayList<>();
        nodes.offer(node);

        while(!nodes.isEmpty()) {
            int size = nodes.size();

            for (int i = 0; i < size; i++) {
                TreeNode next = nodes.poll();

                if (next.left == null && next.right == null) leafs.add(next);
                else {
                    nodes.offer(next.left);
                    nodes.offer(next.right);
                }
            }
        }

        return leafs;
    }
}

/*
public TreeNode clone(TreeNode tree)  {
    if (null == tree)
    {
      return null;
    }

    TreeNode new_tree = new TreeNode(tree.val);
    new_tree.left = clone(tree.left);
    new_tree.right = clone(tree.right);
    return new_tree;
  }

  public List<TreeNode> allPossibleFBT(int N) {
    List<TreeNode> ret = new ArrayList<TreeNode>();
    if (1 == N) {
      ret.add(new TreeNode(0));
    } else if (N % 2 != 0) {
      for (int i = 2; i <= N; i += 2) {
        List<TreeNode> left_branch = allPossibleFBT(i - 1);
        List<TreeNode> right_branch = allPossibleFBT(N - i);
        for (Iterator<TreeNode> left_iter = left_branch.iterator(); left_iter.hasNext(); ) {
          TreeNode left = left_iter.next();
          for (Iterator<TreeNode> right_iter = right_branch.iterator(); right_iter.hasNext(); ) {
            TreeNode right = right_iter.next();

            TreeNode tree = new TreeNode(0);

            // If we're using the last right branch, then this will be the last time this left branch is used and can hence
            // be shallow copied, otherwise the tree will have to be cloned
            tree.left = right_iter.hasNext() ? clone(left) : left;

            // If we're using the last left branch, then this will be the last time this right branch is used and can hence
            // be shallow copied, otherwise the tree will have to be cloned
            tree.right = left_iter.hasNext() ? clone(right) : right;

            ret.add(tree);
          }
        }
      }
    }
    return ret;
  }
 */