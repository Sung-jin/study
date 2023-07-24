package leetcode.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/*
Q.50
Implement pow(x, n), which calculates x raised to the power n (i.e., x^n).
 */
public class Pow {

    private Map<Integer, Double> cache = new HashMap<>();

    public double myPow(double x, int n) {
        if (n == 0) return 1;

        cache = new HashMap<>();
        return calculate(x, n);
    }

    private double calculate(double x, int n) {
        double value = n >= 0 ? x : 1 / x;
        int pow = Math.abs(n);

        if (pow == 1) {
            cache.put(1, value);
            return value;
        }
        if (cache.containsKey(pow)) {
            return cache.get(pow);
        }

        int upper = Math.abs(n) % 2 == 1 ? 1 : 0;
        double next = calculate(x, n / 2) * calculate(x, n / 2 + (upper * (n >= 0 ? 1 : -1)));

        cache.put(pow, next);

        return next;
    }
}

/*
class Solution {
    public double myPow(double x, int n) {

        if(n < 0){
            n = -n;
            x = 1 / x;
        }

        double pow = 1;

        while(n != 0){
            if((n & 1) != 0){
                pow *= x;
            }

            x *= x;
            n >>>= 1;

        }

        return pow;
    }
}
 */