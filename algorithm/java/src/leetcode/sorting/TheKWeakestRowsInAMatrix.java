package leetcode.sorting;

import java.util.ArrayList;
import java.util.List;

/*
Q.1337. The K Weakest Rows in a Matrix
You are given an m x n binary matrix mat of 1's (representing soldiers) and 0's (representing civilians). The soldiers are positioned in front of the civilians. That is, all the 1's will appear to the left of all the 0's in each row.

A row i is weaker than a row j if one of the following is true:

The number of soldiers in row i is less than the number of soldiers in row j.
Both rows have the same number of soldiers and i < j.
Return the indices of the k weakest rows in the matrix ordered from weakest to strongest.
 */
public class TheKWeakestRowsInAMatrix {
    public int[] kWeakestRows(int[][] mat, int k) {
        List<int[]> index = new ArrayList<>();

        for (int i = 0; i < mat.length; i++) {
            int firstSoliderIndex = 1;
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 0) break;
                firstSoliderIndex++;
            }
            index.add(new int[]{firstSoliderIndex - 1, i});
        }

        return  index.stream()
                .sorted((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1])
                .limit(k)
                .mapToInt(v -> v[1])
                .toArray();
    }
}

/*
// binary search
class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]);
        int[] ans = new int[k];

        for (int i = 0; i < mat.length; i++) {
            pq.offer(new int[] {numOnes(mat[i]), i});
            if (pq.size() > k)
                pq.poll();
        }

        while (k > 0)
            ans[--k] = pq.poll()[1];

        return ans;
    }

    private int numOnes(int[] row) {
        int lo = 0;
        int hi = row.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (row[mid] == 1)
                lo = mid + 1;
            else
                hi = mid;
        }

        return lo;
    }
}
 */