package leetcode.binarySearch;

/*
Q.69

Given a non-negative integer x, return the square root of x rounded down to the nearest integer. The returned integer should be non-negative as well.

You must not use any built-in exponent function or operator.

For example, do not use pow(x, 0.5) in c++ or x ** 0.5 in python.
 */
public class Sqrt {
    public int mySqrt(int x) {
        int min = 1, max = x - 1;

        while(max > min) {
            int mid = (min + max) / 2;
            long square = (long) mid * mid;

            if (square == x) return mid;
            else if (square > x) max = mid - 1;
            else min = mid + 1;
        }

        int minSquare = min * min;

        if (minSquare > x) return min - 1;
        else return min;
    }
}

/*
public int sqrt(int x) {
    if (x == 0)
        return 0;
    int left = 1, right = Integer.MAX_VALUE;
    while (true) {
        int mid = left + (right - left)/2;
        if (mid > x/mid) { <- 이러면 long 으로 캐스팅 할 필요도 없네
            right = mid - 1;
        } else {
            if (mid + 1 > x/(mid + 1))
                return mid;
            left = mid + 1;
        }
    }
}
 */