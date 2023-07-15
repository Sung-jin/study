package leetcode.dfs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
Q.1751

You are given an array of events where events[i] = [startDayi, endDayi, valuei]. The ith event starts at startDayi and ends at endDayi, and if you attend this event, you will receive a value of valuei. You are also given an integer k which represents the maximum number of events you can attend.

You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on the same day.

Return the maximum sum of values that you can receive by attending events.
 */
public class MaximumNumberOfEventsThatCanBeAttendedII {

    private Map<String, Integer> MAX_VALUE;

    public int maxValue(int[][] events, int k) {
        if (events.length == 0) return 0;

        MAX_VALUE = new HashMap<>();
        Arrays.sort(events, Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < events.length; i++) {
            int startDay = events[i][0];
            calculateMaxAttendedValue(events, 0, startDay, -1, 0, k, 0);
        }

        return MAX_VALUE.values().stream().max((a, b) -> a > b ? a : b).get();
    }

    private void calculateMaxAttendedValue(int[][] events, int index, int start, int end, int beforeValue, int maxSize, int addedCount) {
        if (addedCount == maxSize) return;

        for (int i = index; i < events.length; i++) {
            int startDay = events[i][0];
            int endDay = events[i][1];
            int value = events[i][2];

            if (startDay > end) {
                String key = start + "_" + endDay;
                int sumValue = MAX_VALUE.getOrDefault(key, 0);
                int nextValue = Math.max(sumValue, beforeValue + value);
                MAX_VALUE.put(key, nextValue);

                calculateMaxAttendedValue(events, i + 1, start, endDay, nextValue, maxSize, addedCount++);
            }
        }
    }
}

/*
public int maxValue(int[][] events, int k) {
	Arrays.sort(events, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

	// <count + "_" + end, sum>
	Map<String, Integer> cache = new HashMap<>();

	return dfs(events, 0, 0, k, 0, cache);
}

private int dfs(int[][] events, int cur, int count, int k, int end, Map<String, Integer> cache) {
	if (count == k || cur == events.length) {
		return 0;
	}

	String key = count + "_" + end;
	Integer val = cache.get(key);
	if (val != null) {
		return val;
	}

	// skip
	int max = dfs(events, cur + 1, count, k, end, cache);
	if (events[cur][0] > end) {
		// take current
		max = Math.max(max, dfs(events, cur + 1, count + 1, k, events[cur][1], cache) + events[cur][2]);
	}

	cache.put(key, max);
	return max;
}
 */