package leetcode.dynamicProgramming;

import java.util.Arrays;
import java.util.Comparator;

/*
Q.646 Maximum Length of Pair Chain
You are given an array of n pairs pairs where pairs[i] = [lefti, righti] and lefti < righti.

A pair p2 = [c, d] follows a pair p1 = [a, b] if b < c. A chain of pairs can be formed in this fashion.

Return the length longest chain which can be formed.

You do not need to use up all the given intervals. You can select pairs in any order.
 */
public class MaximumLengthOfPairChain {

    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));
        int[] chains = new int[pairs.length];
        int answer = 0;
        for (int i = 0; i < pairs.length; i++) {
            chaining(chains, pairs, i);
            answer = Math.max(answer, chains[i]);
        }

        return answer;
    }

    private void chaining(int[] chains, int[][] pairs, int index) {
        int left = pairs[index][0];

        for (int i = index - 1; i >= 0; i--) {
            int right = pairs[i][1];

            if (left > right) {
                chains[index] = chains[i] + 1;
                return;
            }
        }

        chains[index] = 1;
    }
}

/*
public int findLongestChain(int[][] pairs) {
    Arrays.sort(pairs, (a,b) -> a[1] - b[1]);
    int count = 0, i = 0, n = pairs.length;
    while (i < n) {
        count++;
        int curEnd = pairs[i][1];
        while (i < n && pairs[i][0] <= curEnd) i++;
    }
    return count;
}
 */