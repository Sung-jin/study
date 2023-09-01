package leetcode.dynamicProgramming;

/*
Q.338 Counting Bits
Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 */
public class CountingBits {

    private int[] BIT_COUNT = new int[100000];

    public int[] countBits(int n) {
        int[] answer = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            answer[i] = findBitCount(i);
        }

        return answer;
    }

    private int findBitCount(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (BIT_COUNT[n] != 0) return BIT_COUNT[n];

        BIT_COUNT[n] = (n % 2) + findBitCount(n / 2);

        return BIT_COUNT[n];
    }
}
