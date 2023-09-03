package leetcode.dynamicProgramming;

/*
Q.62 Unique Paths
There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.

Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.

The test cases are generated so that the answer will be less than or equal to 2 * 109.
 */
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] uniquePaths = new int[m][n];
        uniquePaths[0][0] = 1;

        for (int i = 0; i < uniquePaths.length; i++) {
            for (int j = 0; j < uniquePaths[i].length; j++) {
                if (i == 0 && j == 0) continue;
                int up = i > 0 ? uniquePaths[i - 1][j] : 0;
                int left = j > 0 ? uniquePaths[i][j - 1] : 0;

                uniquePaths[i][j] = up + left;
            }
        }

        return uniquePaths[m - 1][n - 1];
    }
}

/*
public class Solution {
    public int uniquePaths(int m, int n) {
        if(m == 1 || n == 1)
            return 1;
        m--;
        n--;
        if(m < n) {              // Swap, so that m is the bigger number
            m = m + n;
            n = m - n;
            m = m - n;
        }
        long res = 1;
        int j = 1;
        for(int i = m+1; i <= m+n; i++, j++){       // Instead of taking factorial, keep on multiply & divide
            res *= i;
            res /= j;
        }

        return (int)res;
    }
}
 */