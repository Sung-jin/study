package leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/*
Q.827

You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.

Return the size of the largest island in grid after applying this operation.

An island is a 4-directionally connected group of 1s.
 */
public class MakingALargeIsland {

    private int[][] area;
    private int[][] areaColor;
    private List<int[]> zeroIndex;
    private int size = 0;

    public int largestIsland(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) return 0;

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        area = new int[grid.length][grid[0].length];
        areaColor = new int[grid.length][grid[0].length];
        zeroIndex = new ArrayList<>();
        int color = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 0) zeroIndex.add(new int[]{x, y});
                else if (!visited[y][x]) {
                    size = 0;
                    List<int[]> coordinates = new ArrayList<>();
                    fillInAreaSize(grid, visited, x, y, coordinates);
                    color++;

                    for (int[] coordinate : coordinates) {
                        area[coordinate[1]][coordinate[0]] = size;
                        areaColor[coordinate[1]][coordinate[0]] = color;
                    }
                }
            }
        }

        return changeZeroThenGetMaxSize();
    }

    private void fillInAreaSize(int[][] grid, boolean[][] visited, int x, int y, List<int[]> coordinate) {
        size++;
        visited[y][x] = true;
        coordinate.add(new int[]{x, y});

        if (y - 1 >= 0 && grid[y - 1][x] == 1 && !visited[y - 1][x]) fillInAreaSize(grid, visited, x, y - 1, coordinate); // up
        if (x + 1 < visited[y].length && grid[y][x + 1] == 1 && !visited[y][x + 1]) fillInAreaSize(grid, visited, x + 1, y, coordinate); // right
        if (y + 1 < visited.length && grid[y + 1][x] == 1 && !visited[y + 1][x]) fillInAreaSize(grid, visited, x, y + 1, coordinate); // down
        if (x - 1 >= 0 && grid[y][x - 1] == 1 && !visited[y][x - 1]) fillInAreaSize(grid, visited, x - 1, y, coordinate); // left
    }

    private int changeZeroThenGetMaxSize() {
        if (zeroIndex.isEmpty()) return area[0].length * area.length;

        int maxSize = 0;
        for (int[] index : zeroIndex) {
            int x = index[0], y = index[1];
            List<Integer> colors = new ArrayList<>();
            List<Integer> size = new ArrayList<>();

            if (y - 1 >= 0) getArea(colors, size, x, y - 1);
            if (x + 1 < area[y].length) getArea(colors, size, x + 1, y);
            if (y + 1 < area.length) getArea(colors, size, x, y + 1);
            if (x - 1 >= 0) getArea(colors, size, x - 1, y);

            maxSize = Math.max(maxSize, size.stream().mapToInt(v -> v).sum() + 1);
        }

        return maxSize;
    }

    private void getArea(List<Integer> colors, List<Integer> size, int x, int y) {
        int color = areaColor[y][x];

        if (!colors.contains(color)) {
            colors.add(color);
            size.add(area[y][x]);
        }
    }
}

/*
import javafx.util.Pair;
class Solution {
    public int N = 0;
    public int largestIsland(int[][] grid) {
        N = grid.length;
        //DFS every island and give it an index of island
        int index = 3, res = 0;
        HashMap<Integer, Integer> area = new HashMap<>();
        for (int x = 0; x < N; ++x) for (int y = 0; y < N; ++y)
            if (grid[x][y] == 1) {
                area.put(index, dfs(grid, x, y, index));
                res = Math.max(res, area.get(index++));
            }

        //traverse every 0 cell and count biggest island it can conntect
        for (int x = 0; x < N; ++x) for (int y = 0; y < N; ++y)
            if (grid[x][y] == 0) {
                HashSet<Integer> seen = new HashSet<>();
                int cur = 1;
                for (Pair<Integer, Integer> p : move(x, y)) {
                    index = grid[p.getKey()][p.getValue()];
                    if (index > 1 && !seen.contains(index)) {
                        seen.add(index);
                        cur += area.get(index);
                    }
                }
                res = Math.max(res, cur);
            }
        return res;
    }

    public List <Pair<Integer, Integer>> move(int x, int y) {
        ArrayList <Pair<Integer, Integer>> res = new ArrayList<>();
        if (valid(x, y + 1)) res.add(new Pair<Integer, Integer>(x, y + 1));
        if (valid(x, y - 1)) res.add(new Pair<Integer, Integer>(x, y - 1));
        if (valid(x + 1, y)) res.add(new Pair<Integer, Integer>(x + 1, y));
        if (valid(x - 1, y)) res.add(new Pair<Integer, Integer>(x - 1, y));
        return res;
    }

    public boolean valid(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    public int dfs(int[][] grid, int x, int y, int index) {
        int area = 0;
        grid[x][y] = index;
        for (Pair<Integer, Integer> p : move(x, y))
            if (grid[p.getKey()][p.getValue()] == 1)
                area += dfs(grid, p.getKey(), p.getValue(), index);
        return area + 1;
    }
}
 */
