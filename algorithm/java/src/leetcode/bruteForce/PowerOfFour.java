package leetcode.bruteForce;

/*
Q.342. Power of Four
Given an integer n, return true if it is a power of four. Otherwise, return false.

An integer n is a power of four, if there exists an integer x such that n == 4x.
 */
public class PowerOfFour {
    public boolean isPowerOfFour(int n) {
        return checkPowerOfFour(n);
    }

    private boolean checkPowerOfFour(int n) {
        if (n == 1 || n == 4) return true;
        if (n % 4 != 0 || n == 0) return false;

        return checkPowerOfFour(n / 4);
    }
}

/*
public boolean isPowerOfFour(int num) {
    return num > 0 && (num&(num-1)) == 0 && (num & 0x55555555) != 0;
    //0x55555555 is to get rid of those power of 2 but not power of 4
    //so that the single 1 bit always appears at the odd position
}
 */