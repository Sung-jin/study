package leetcode.dfs;

import leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/*
Q.863
Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of all nodes that have a distance k from the target node.

You can return the answer in any order.

Definition for a binary tree node.
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
 */
public class AllNodesDistanceKInBinaryTree {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<TreeNode> targetNodes = findTargetWithSequence(new ArrayList<>(), root, target.val);

        return findNodesByDistance(targetNodes, k);
    }

    private List<TreeNode> findTargetWithSequence(List<TreeNode> result, TreeNode value, int target) {
        result.add(value);

        if (value.val == target) {
            return result;
        } else {
            TreeNode left = value.left;
            TreeNode right = value.right;

            if (left != null) {
                findTargetWithSequence(result, left, target);
                boolean hasTarget = result.stream().anyMatch(v -> v.val == target);

                if (hasTarget) return result;
                else result.remove(left);
            }
            if (right != null) {
                findTargetWithSequence(result, right, target);
                boolean hasTarget = result.stream().anyMatch(v -> v.val == target);

                if (hasTarget) return result;
                else result.remove(right);
            }
        }

        return result;
    }

    private List<Integer> findNodesByDistance(List<TreeNode> nodeSequence, int distance) {
        TreeNode target = nodeSequence.get(nodeSequence.size() - 1);
        List<Integer> result = new ArrayList<>(findNodesByDownDistance(target, distance));

        if (distance == 0 || nodeSequence.size() == 1) return result;

        for (int i = nodeSequence.size() - 2; i >= 0; i--) {
            TreeNode node = nodeSequence.get(i);
            TreeNode nextNode = nodeSequence.get(i + 1);
            TreeNode left = node.left;
            TreeNode right = node.right;
            int remainDistance = distance - nodeSequence.size() + i + 1;

            if (remainDistance < 0) break;
            else if (remainDistance == 0) result.add(node.val);
            else if (left != null && left != nextNode) result.addAll(findNodesByDownDistance(left, remainDistance - 1));
            else if (right != null && right != nextNode) result.addAll(findNodesByDownDistance(right, remainDistance - 1));
        }

        return result;
    }

    private List<Integer> findNodesByDownDistance(TreeNode node, int remainDistance) {
        List<Integer> result = new ArrayList<>();

        if (remainDistance == 0) {
            result.add(node.val);
        } else {
            TreeNode left = node.left;
            TreeNode right = node.right;

            if (left != null) result.addAll(findNodesByDownDistance(left, remainDistance - 1));
            if (right != null) result.addAll(findNodesByDownDistance(right, remainDistance - 1));
        }

        return result;
    }
}
// 차라리 기존 TreeNode 클래스가 아닌, parent 정보가 있는 클래스로 컨버팅 한 후 dfs 한게 더 좋을듯

/*
class Solution {

    Map<TreeNode, Integer> map = new HashMap<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new LinkedList<>();
        find(root, target);
        dfs(root, target, K, map.get(root), res);
        return res;
    }

    // find target node first and store the distance in that path that we could use it later directly
    private int find(TreeNode root, TreeNode target) {
        if (root == null) return -1;
        if (root == target) {
            map.put(root, 0);
            return 0;
        }
        int left = find(root.left, target);
        if (left >= 0) {
            map.put(root, left + 1);
            return left + 1;
        }
		int right = find(root.right, target);
		if (right >= 0) {
            map.put(root, right + 1);
            return right + 1;
        }
        return -1;
    }

    private void dfs(TreeNode root, TreeNode target, int K, int length, List<Integer> res) {
        if (root == null) return;
        if (map.containsKey(root)) length = map.get(root);
        if (length == K) res.add(root.val);
        dfs(root.left, target, K, length + 1, res);
        dfs(root.right, target, K, length + 1, res);
    }
}
 */
