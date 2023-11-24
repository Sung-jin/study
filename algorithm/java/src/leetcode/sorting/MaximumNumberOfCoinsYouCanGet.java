package leetcode.sorting;

import java.util.Arrays;

/*
Q.1561. Maximum Number of Coins You Can Get
There are 3n piles of coins of varying size, you and your friends will take piles of coins as follows:

In each step, you will choose any 3 piles of coins (not necessarily consecutive).
Of your choice, Alice will pick the pile with the maximum number of coins.
You will pick the next pile with the maximum number of coins.
Your friend Bob will pick the last pile.
Repeat until there are no more piles of coins.
Given an array of integers piles where piles[i] is the number of coins in the ith pile.

Return the maximum number of coins that you can have.
 */
public class MaximumNumberOfCoinsYouCanGet {
    public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int answer = 0;
        for (int i = 0; i < piles.length / 3; i++) {
            answer += piles[i * 2 + piles.length / 3];
        }

        return answer;
    }
}

/*
public int maxCoins(int[] A) {
    Arrays.sort(A);
    int res = 0, n = A.length;
    for (int i = n / 3; i < n; i += 2)
        res += A[i];
    return res;
}
 */