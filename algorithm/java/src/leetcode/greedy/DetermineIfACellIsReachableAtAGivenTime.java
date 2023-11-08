package leetcode.greedy;

/*
Q.2849. Determine if a Cell Is Reachable at a Given Time
You are given four integers sx, sy, fx, fy, and a non-negative integer t.

In an infinite 2D grid, you start at the cell (sx, sy). Each second, you must move to any of its adjacent cells.

Return true if you can reach cell (fx, fy) after exactly t seconds, or false otherwise.

A cell's adjacent cells are the 8 cells around it that share at least one corner with it. You can visit the same cell several times.
 */
public class DetermineIfACellIsReachableAtAGivenTime {
    public boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
        int xTime = Math.abs(sx - fx) + 1, yTime = Math.abs(sy - fy) + 1;

        return Math.max(xTime, yTime) <= t && (t <= xTime + yTime || (Math.max(xTime, yTime) - t) % 2 == 0);
    }
}

/*
boolean isReachableAtTime(int sx, int sy, int fx, int fy, int t) {
    int xdiff = Math.abs(sx - fx), ydiff = Math.abs(sy - fy);
    if(xdiff == 0 && ydiff == 0 && t == 1) return false;
    return Math.max(xdiff, ydiff) <= t;
}
 */