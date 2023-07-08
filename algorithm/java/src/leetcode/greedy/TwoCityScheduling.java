package leetcode.greedy;

import java.util.Arrays;
import java.util.Comparator;

/*
Q.1029
A company is planning to interview 2n people. Given the array costs where costs[i] = [aCosti, bCosti], the cost of flying the ith person to city a is aCosti, and the cost of flying the ith person to city b is bCosti.

Return the minimum cost to fly every person to a city such that exactly n people arrive in each city.
 */
public class TwoCityScheduling {
    public int twoCitySchedCost(int[][] costs) {
        int totalCosts = 0;
        int remainLeftCount = costs.length / 2, remainRightCount = costs.length / 2;
        Arrays.sort(costs, (a, b) -> Math.abs(b[0] - b[1]) - Math.abs(a[0] - a[1]));

        for (int[] cost : costs) {
            if (remainLeftCount == 0) totalCosts += cost[1];
            else if (remainRightCount == 0) totalCosts += cost[0];
            else {
                if (cost[0] > cost[1]) {
                    totalCosts += cost[1];
                    remainRightCount--;
                } else {
                    totalCosts += cost[0];
                    remainLeftCount--;
                }
            }
        }

        return totalCosts;
    }
}

/*
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        int N = costs.length/2;
        int[] refund = new int[N * 2];
        int minCost = 0, index = 0;
        for(int[] cost : costs){
            refund[index++] = cost[1] - cost[0];
            minCost += cost[0];
        }
        Arrays.sort(refund);
        for(int i = 0; i < N; i++){
            minCost += refund[i];
        }
        return minCost;
    }
}
 */