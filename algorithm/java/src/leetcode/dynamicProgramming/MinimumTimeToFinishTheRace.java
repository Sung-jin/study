package leetcode.dynamicProgramming;

/*
Q.2188
You are given a 0-indexed 2D integer array tires where tires[i] = [fi, ri] indicates that the ith tire can finish its xth successive lap in fi * ri(x-1) seconds.

For example, if fi = 3 and ri = 2, then the tire would finish its 1st lap in 3 seconds, its 2nd lap in 3 * 2 = 6 seconds, its 3rd lap in 3 * 22 = 12 seconds, etc.
You are also given an integer changeTime and an integer numLaps.

The race consists of numLaps laps and you may start the race with any tire. You have an unlimited supply of each tire and after every lap, you may change to any given tire (including the current tire type) if you wait changeTime seconds.

Return the minimum time to finish the race.
 */
public class MinimumTimeToFinishTheRace {
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        int[][] timePerTires = new int[tires.length][numLaps];
        int remainLaps = numLaps;

        for (int i = 0; i < tires.length; i++) {
            for (int j = 0; j < numLaps; j++) {
                timePerTires[i][j] = getTime(tires[i][0], tires[i][1], j);
            }
        }

        for (int i = 0; i < numLaps; i++) {

        }

        return -1;
        // 다음 인덱스에 기존 타이어를 쓸지, 교체할지, 교체시 어떤 타이어를 쓸지 를 선택하는 방법을 못찾음
    }

    private int getTime(int f, int r, int useLap) {
        return f * (int)Math.pow(r, useLap);
    }

    private int getUsedTireIndex(int[][] timePerTires, int changeTime, int useLap, int remainLap) {
        int index = getFirstChangeableTireIndex(timePerTires, changeTime, useLap);
        if (index == -1) return -1;

        for (int i = 0; i < remainLap; i++) {
            int lap = useLap + i;

            for (int[] timePerTire : timePerTires) {

            }
        }

        return -1;
    }

    private int getFirstChangeableTireIndex(int[][] timePerTires, int changeTime, int lap) {
        for (int i = 0; i < timePerTires.length; i++) {
            if (changeTime < timePerTires[i][lap]) return i;
        }

        return -1;
    }
}

/*
class Solution {
    public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
        int[] minTime = new int[numLaps + 1];
        Arrays.fill(minTime, Integer.MAX_VALUE);
        for (int[] tire : tires) {
            checkMinTime(tire, minTime, changeTime, numLaps);
        }
        for (int i = 2; i <= numLaps; i++) {
            for (int j = 1; j < i; j++) {
                int remain = i % j;
                int currMin;
				// Greedy, in order to get the minimal runtime, we should repeat the same loop as much as possible.
                if (remain != 0) {
                    currMin = (i / j) * (minTime[j] + changeTime) + minTime[remain];
                } else {
					// The last changeTime is not required if remain is 0
                    currMin = (i / j) * (minTime[j] + changeTime) - changeTime;
                }
                minTime[i] = Math.min(minTime[i], currMin);
            }
        }

        return minTime[numLaps];
    }

    private void checkMinTime(int[] tire, int[] minTime, int changeTime, int numLaps) {
        int base = tire[0];
        int lap = 1;
        int curr = base;
        minTime[lap] = Math.min(minTime[lap], curr);
        int sum = base;
		// Greedy, if changeTime + base is smaller, the minimal runtime for the next lap
		// will not be better than minTime[lap - 1] + changeTime + minTime[1]
        while (curr * tire[1] - base <= changeTime && lap++ < numLaps) {
            curr *= tire[1];
            sum += curr;
            minTime[lap] = Math.min(minTime[lap], sum);
        }
    }
}
 */