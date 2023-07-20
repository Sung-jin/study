package leetcode.greedy;

import java.util.ArrayList;
import java.util.List;

/*
Q.435

Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 */
public class NonOverlappingIntervals {
    public int eraseOverlapIntervals(int[][] intervals) {
        List<int[]> ranges = new ArrayList<>();

        for (int[] interval : intervals) {
            boolean isAdded = false;

            for (int i = 0; i < ranges.size(); i++) {
                int[] now = ranges.get(i);

                if (now[0] == interval[1] || now[1] == interval[0]) {
                    if (now[0] == interval[1]) {
                        now[0] = interval[0];
                    } else {
                        now[1] = interval[1];
                    }
                    ranges.set(i, now);
                    isAdded = true;
                    break;
                }
            }

            if (!isAdded) ranges.add(interval);
        }

        return ranges.size() - 1;
    }
}

/*
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
    int ans = 0;
    int k = Integer.MIN_VALUE;

    for (int i = 0; i < intervals.length; i++) {
        int x = intervals[i][0];
        int y = intervals[i][1];

        if (x >= k) {
            // Case 1
            k = y;
        } else {
            // Case 2
            ans++;
        }
    }

    return ans;
}
 */
