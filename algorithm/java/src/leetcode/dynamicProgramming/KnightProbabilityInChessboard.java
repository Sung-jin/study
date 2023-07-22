package leetcode.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/*
Q.688
On an n x n chessboard, a knight starts at the cell (row, column) and attempts to make exactly k moves. The rows and columns are 0-indexed, so the top-left cell is (0, 0), and the bottom-right cell is (n - 1, n - 1).

A chess knight has eight possible moves it can make, as illustrated below. Each move is two cells in a cardinal direction, then one cell in an orthogonal direction.


Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece would go off the chessboard) and moves there.

The knight continues moving until it has made exactly k moves or has moved off the chessboard.

Return the probability that the knight remains on the board after it has stopped moving.
 */
public class KnightProbabilityInChessboard {
    private double[][] INBOUND_PERCENTAGE;
    private final int[][] MOVE = new int[][]{
            {-1, -2}, {-2, -1},
            {-2, 1}, {-1, 2},
            {1, -2}, {2, -1},
            {2, 1}, {1, 2}
    };

    public double knightProbability(int n, int k, int row, int column) {
        INBOUND_PERCENTAGE = new double[n][n];

        return getInboundPercentageAfterMove(k - 1, row, column);
    }

    private double getInboundPercentageAfterMove(int remainMove, int row, int column) {
        List<int[]> movableCoordinates = getMovable(row, column);

        if (INBOUND_PERCENTAGE[column][row] == 0) {
            INBOUND_PERCENTAGE[column][row] = movableCoordinates.size() / 8.0;
        }

        double result = INBOUND_PERCENTAGE[column][row];

        if (remainMove == 0) return result;

        for (int[] movableCoordinate : movableCoordinates) {
            result *= getInboundPercentageAfterMove(remainMove - 1, movableCoordinate[1], movableCoordinate[0]);
        }

        return result;
    }

    private List<int[]> getMovable(int row, int column) {
        List<int[]> coordinates = new ArrayList<>();

        for (int[] coordinate : MOVE) {
            int x = column - coordinate[1];
            int y = row - coordinate[0];

            if (x >= 0 && y >= 0 && x < INBOUND_PERCENTAGE.length && y < INBOUND_PERCENTAGE.length) coordinates.add(new int[]{y, x});
        }

        return coordinates;
    }
}

/*
class Solution {
    private int[][]dir = new int[][]{{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};
    private double[][][] dp;
    public double knightProbability(int N, int K, int r, int c) {
        dp = new double[N][N][K + 1];
        return find(N,K,r,c);
    }
    public double find(int N,int K,int r,int c){
        if(r < 0 || r > N - 1 || c < 0 || c > N - 1) return 0;
        if(K == 0)  return 1;
        if(dp[r][c][K] != 0) return dp[r][c][K];
        double rate = 0;
        for(int i = 0;i < dir.length;i++)   rate += 0.125 * find(N,K - 1,r + dir[i][0],c + dir[i][1]);
        dp[r][c][K] = rate;
        return rate;
    }
}
 */