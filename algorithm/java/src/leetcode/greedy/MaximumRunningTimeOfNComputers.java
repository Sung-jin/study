package leetcode.greedy;

import java.util.Arrays;
import java.util.Collections;

/*
Q.2141
You have n computers. You are given the integer n and a 0-indexed integer array batteries where the ith battery can run a computer for batteries[i] minutes. You are interested in running all n computers simultaneously using the given batteries.

Initially, you can insert at most one battery into each computer. After that and at any integer time moment, you can remove a battery from a computer and insert another battery any number of times. The inserted battery can be a totally new battery or a battery from another computer. You may assume that the removing and inserting processes take no time.

Note that the batteries cannot be recharged.

Return the maximum number of minutes you can run all the n computers simultaneously.
 */
public class MaximumRunningTimeOfNComputers {
    public long maxRunTime(int n, int[] batteries) {
        int maxRunTime = 0;
        int zeroBatteryCount = 0;
        Integer[] remainBatteries = Arrays.stream(batteries).boxed().toArray(Integer[]::new);

        while (batteries.length > zeroBatteryCount) {
            Arrays.sort(remainBatteries, Collections.reverseOrder());
            int minBatteries = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (remainBatteries[i] == 0) return maxRunTime;

                minBatteries = Math.min(minBatteries, remainBatteries[i]);
            }
            for (int i = 0; i < n; i++) {
                remainBatteries[i] -= minBatteries;

                if (remainBatteries[i] == 0) zeroBatteryCount++;
            }

            maxRunTime += minBatteries;
        }

        return maxRunTime;
    }
}

/*
// greedy
public class Solution {
    public long maxRunTime(int n, int[] batteries) {
        // Get the sum of all extra batteries.
        Arrays.sort(batteries);
        long extra = 0;
        for (int i = 0; i < batteries.length - n; i++) {
            extra += batteries[i];
        }

        // live stands for the n largest batteries we chose for n computers.

        int[] live = Arrays.copyOfRange(batteries, batteries.length - n, batteries.length);

        // We increase the total running time using 'extra' by increasing
        // the running time of the computer with the smallest battery.
        for (int i = 0; i < n - 1; i++) {
            // If the target running time is between live[i] and live[i + 1].
            if (extra < (long)(i + 1) * (live[i + 1] - live[i])) {
                return live[i] + extra / (long)(i + 1);
            }

            // Reduce 'extra' by the total power used.
            extra -= (long)(i + 1) * (live[i + 1] - live[i]);
        }

        // If there is power left, we can increase the running time
        // of all computers.
        return live[n - 1] + extra / n;
    }
}

// binary search
public class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sumPower = 0;
        for (int power : batteries)
            sumPower += power;
        long left = 1, right = sumPower / n;

        while (left < right){
            long target = right - (right - left) / 2;
            long extra = 0;

            for (int power : batteries)
                extra += Math.min(power, target);

            if (extra >= (long)(n * target))
                left = target;
            else
                right = target - 1;
        }
        return left;
    }
}
 */