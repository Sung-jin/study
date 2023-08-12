package leetcode.dfs;

/*
Q.63 Unique Paths II
You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.

An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

The testcases are generated so that the answer will be less than or equal to 2 * 10^9.
 */
public class UniquePathsII {

    private boolean[][] visited;
    private int uniquePathCount;
    private int[] RIGHT = {1, 0}, DOWN = {0, 1};

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        uniquePathCount = 0;
        visited = new boolean[obstacleGrid.length][obstacleGrid[0].length];
        helper(obstacleGrid, 0, 0);
        return uniquePathCount;
    }

    private void helper(int[][] obstacleGrid, int x, int y) {
        if (obstacleGrid[y][x] == 1) return;
        if (y == obstacleGrid.length - 1 && x == obstacleGrid[0].length - 1) {
            if (obstacleGrid[y][x] == 0) uniquePathCount++;
            return;
        }
        int[] nextRight = new int[]{x + RIGHT[0], y + RIGHT[1]}, nextDown = new int[]{x + DOWN[0], y + DOWN[1]};
        visited[y][x] = true;

        if (possibleAccess(obstacleGrid, nextRight[0], nextRight[1])) {
            helper(obstacleGrid, nextRight[0], nextRight[1]);
        }
        if (possibleAccess(obstacleGrid, nextDown[0], nextDown[1])) {
            helper(obstacleGrid, nextDown[0], nextDown[1]);
        }

        visited[y][x] = false;
    }

    private boolean possibleAccess(int[][] obstacleGrid, int x, int y) {
        if (obstacleGrid[0].length <= x || obstacleGrid.length <= y) return false;
        return obstacleGrid[y][x] != 1;
    }
}
/*
public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int width = obstacleGrid[0].length;
    int[] dp = new int[width];
    dp[0] = 1;
    for (int[] row : obstacleGrid) {
        for (int j = 0; j < width; j++) {
            if (row[j] == 1)
                dp[j] = 0;
            else if (j > 0)
                dp[j] += dp[j - 1];
        }
    }
    return dp[width - 1];
}
 */
