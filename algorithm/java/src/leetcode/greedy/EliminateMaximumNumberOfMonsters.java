package leetcode.greedy;

/*
Q.1921. Eliminate Maximum Number of Monsters
You are playing a video game where you are defending your city from a group of n monsters. You are given a 0-indexed integer array dist of size n, where dist[i] is the initial distance in kilometers of the ith monster from the city.

The monsters walk toward the city at a constant speed. The speed of each monster is given to you in an integer array speed of size n, where speed[i] is the speed of the ith monster in kilometers per minute.

You have a weapon that, once fully charged, can eliminate a single monster. However, the weapon takes one minute to charge. The weapon is fully charged at the very start.

You lose when any monster reaches your city. If a monster reaches the city at the exact moment the weapon is fully charged, it counts as a loss, and the game ends before you can use your weapon.

Return the maximum number of monsters that you can eliminate before you lose, or n if you can eliminate all the monsters before they reach the city.
 */

import java.util.Arrays;

public class EliminateMaximumNumberOfMonsters {
    public int eliminateMaximum(int[] dist, int[] speed) {
        int[] times = new int[dist.length];
        int eliminateCount = 0;
        int time = 0;

        for (int i = 0; i < dist.length; i++) {
            times[i] = (int) Math.ceil((double)dist[i]/speed[i]);
        }

        Arrays.sort(times);

        for (int j : times) {
            time++;

            if (time <= j) eliminateCount++;
            else break;
        }

        return eliminateCount;
    }
}

/*
public class Solution {
    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        for (int i = 0; i < n; i++) {
            dist[i] = (int) Math.ceil((double) dist[i] / speed[i]);
            speed[i] = 0;
        }
        for (int num : dist) {
            if (num >= n) continue;
            speed[num]++;
        }
        for (int i = 1; i < n; i++) {
            speed[i] += speed[i - 1];
            if (speed[i] > i) {
                return i;
            }
        }
        return n;
    }
}
 */