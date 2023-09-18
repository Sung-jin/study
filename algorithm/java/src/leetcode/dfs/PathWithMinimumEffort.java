package leetcode.dfs;

/*
Q.1631 Path With Minimum Effort
You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.

A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.

Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
 */
public class PathWithMinimumEffort {
    private int MIN_EFFORT;
    private static final int[][] MOVE = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    // right, down, left, top

    public int minimumEffortPath(int[][] heights) {
        MIN_EFFORT = Integer.MAX_VALUE;
        boolean[][] visited = new boolean[heights.length][heights[0].length];
        helper(heights, visited, new int[]{0, 0}, 0);

        return MIN_EFFORT;
    }

    private void helper(int[][] heights, boolean[][] visited, int[] position, int maxEffort) {
        if (position[1] == heights.length - 1 && position[0] == heights[0].length - 1) {
            if (maxEffort < MIN_EFFORT) MIN_EFFORT = maxEffort;

            return;
        }

        visited[position[1]][position[0]] = true;

        for (int[] direction : MOVE) {
            int nextX = position[0] + direction[0], nextY = position[1] + direction[1];

            if (nextX >= heights[0].length || nextX < 0) continue;
            if (nextY >= heights.length || nextY < 0) continue;
            if (visited[nextY][nextX]) continue;

            int diff = Math.abs(heights[nextY][nextX] - heights[position[1]][position[0]]);

            if (diff > MIN_EFFORT) continue;

            helper(heights, visited, new int[]{nextX, nextY}, Math.max(maxEffort, diff));
        }

        visited[position[1]][position[0]] = false;
    }
}

/*
// Dijikstra
class Solution {
    int[] DIR = new int[]{0, 1, 0, -1, 0};
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(dist[i], Integer.MAX_VALUE);

        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        minHeap.offer(new int[]{0, 0, 0}); // distance, row, col
        dist[0][0] = 0;

        while (!minHeap.isEmpty()) {
            int[] top = minHeap.poll();
            int d = top[0], r = top[1], c = top[2];
            if (d > dist[r][c]) continue; // this is an outdated version -> skip it
            if (r == m - 1 && c == n - 1) return d; // Reach to bottom right
            for (int i = 0; i < 4; i++) {
                int nr = r + DIR[i], nc = c + DIR[i + 1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    int newDist = Math.max(d, Math.abs(heights[nr][nc] - heights[r][c]));
                    if (dist[nr][nc] > newDist) {
                        dist[nr][nc] = newDist;
                        minHeap.offer(new int[]{dist[nr][nc], nr, nc});
                    }
                }
            }
        }
        return 0; // Unreachable code, Java require to return interger value.
    }
}

// Binary search + DFS -> python version
class Solution(object):
    def minimumEffortPath(self, heights):
        m, n = len(heights), len(heights[0])
        DIR = [0, 1, 0, -1, 0]

        def dfs(r, c, visited, threadshold):
            if r == m-1 and c == n-1: return True # Reach destination
            visited[r][c] = True
            for i in range(4):
                nr, nc = r+DIR[i], c+DIR[i+1]
                if nr < 0 or nr == m or nc < 0 or nc == n or visited[nr][nc]: continue
                if abs(heights[nr][nc]-heights[r][c]) <= threadshold and dfs(nr, nc, visited, threadshold):
                    return True
            return False

        def canReachDestination(threadshold):
            visited = [[False] * n for _ in range(m)]
            return dfs(0, 0, visited, threadshold)

        left = 0
        ans = right = 10**6
        while left <= right:
            mid = left + (right-left) // 2
            if canReachDestination(mid):
                right = mid - 1 # Try to find better result on the left side
                ans = mid
            else:
                left = mid + 1
        return ans
 */
