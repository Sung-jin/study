package leetcode.binarySearch;

import java.util.Arrays;

/*
Q.1870
You are given a floating-point number hour, representing the amount of time you have to reach the office. To commute to the office, you must take n trains in sequential order. You are also given an integer array dist of length n, where dist[i] describes the distance (in kilometers) of the ith train ride.

Each train can only depart at an integer hour, so you may need to wait in between each train ride.

For example, if the 1st train ride takes 1.5 hours, you must wait for an additional 0.5 hours before you can depart on the 2nd train ride at the 2 hour mark.
Return the minimum positive integer speed (in kilometers per hour) that all the trains must travel at for you to reach the office on time, or -1 if it is impossible to be on time.

Tests are generated such that the answer will not exceed 107 and hour will have at most two digits after the decimal point.
 */
public class MinimumSpeedToArriveOnTime {
    public int minSpeedOnTime(int[] dist, double hour) {
        if (dist.length - 1 > hour) return -1;

        int min = 1, max = Arrays.stream(dist).max().getAsInt();
        int mid, minSpeed = max;
        double totalHour;

        while (minSpeed >= min) {
            totalHour = 0;
            mid = (int) Math.ceil((min + max) / 2.0);

            for (int i = 0; i < dist.length - 1; i++) {
                totalHour += takeTime(dist[i], mid);
            }
            totalHour += ((double) dist[dist.length - 1] / mid);

            if (totalHour == hour) return mid;
            else if (totalHour > hour) {
                min = mid;
            } else {
                max = mid;

                if (minSpeed == mid) return minSpeed;
                else minSpeed = mid;
            }
        }

        return minSpeed;
    }

    private int takeTime(int hour, int speed) {
        return hour / speed + (hour % speed > 0 ? 1 : 0);
    }
}

/*
class Solution {
    public int minSpeedOnTime(int[] dist, double hour) {
        int n = dist.length;
        int min = 1, max = 10000000;
        int ans = -1;
        while(min <= max){
            int mid = (max + min)/2;
            double sum = 0;
            for(int i = 0; i<n-1; ++i){
                sum += Math.ceil( ( (double) dist[i]) /mid);
            }
            sum = sum + ( ( (double) dist[n-1]) /mid);
            if(sum > hour){
                min = mid+1;
            }else{
                ans = mid;
                max = mid-1;
            }
        }
        return ans;
    }
}

너무 복잡하게 생각했나보네.
단순히 시간이 더 오래 걸리면 최소값을 변경, 더 짧으면 최댓값을 변경하면서 이때 해당 값으로 리턴값 조정
변경하면서 최대가 최소보다 작아지면, 그 시점이 가장 가운데인 부분.

min, max 를 잡고 mid 를 어떤 것으로 잡아야 할지가 계속 헷갈리네.
 */