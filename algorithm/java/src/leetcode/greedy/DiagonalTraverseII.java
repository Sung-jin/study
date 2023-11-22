package leetcode.greedy;

import java.util.List;

/*
Q.1424. Diagonal Traverse II
Given a 2D integer array nums, return all elements of nums in diagonal order as shown in the below images.
 */
public class DiagonalTraverseII {
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int totalLength = 0;
        for (List<Integer> num : nums) {
            totalLength += num.size();
        }
        int[] answer = new int[totalLength];
        int index = 0;

        for (int i = 0; i < totalLength; i++) {
            for (int y = Math.min(i, nums.size()); y >= 0; y--) {
                int x = i - y;

                if (x < nums.get(y).size()) {
                    answer[index] = nums.get(y).get(x);
                    index++;
                }
            }
        }

        return answer;
    }
}

/*
class Solution {
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int n = 0, i = 0, maxKey = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int r = nums.size() - 1; r >= 0; --r) { // The values from the bottom rows are the starting values of the diagonals.
            for (int c = 0; c < nums.get(r).size(); ++c) {
                map.putIfAbsent(r + c, new ArrayList<>());
                map.get(r + c).add(nums.get(r).get(c));
                maxKey = Math.max(maxKey, r + c);
                n++;
            }
        }
        int[] ans = new int[n];
        for (int key = 0; key <= maxKey; ++key) {
            List<Integer> value = map.get(key);
            if (value == null) continue;
            for (int v : value) ans[i++] = v;
        }
        return ans;
    }
}
 */