package leetcode.binarySearch;

/*
Q.374
We are playing the Guess Game. The game is as follows:

I pick a number from 1 to n. You have to guess which number I picked.

Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.

You call a pre-defined API int guess(int num), which returns three possible results:

-1: Your guess is higher than the number I picked (i.e. num > pick).
1: Your guess is lower than the number I picked (i.e. num < pick).
0: your guess is equal to the number I picked (i.e. num == pick).
Return the number that I picked.
 */
public class GuessNumberHigherOrLower {
    /**
     * Forward declaration of guess API.
     * @param  num   your guess
     * @return 	     -1 if num is higher than the picked number
     *			      1 if num is lower than the picked number
     *               otherwise return 0
     * int guess(int num);
     */

    private int pick;

    public int guessNumber(int n) {
        int min = 1, max = n;
        int pick = (int)((min + (long)max) / 2);

        while(min < max) {
            int guess = guess(pick);
            if (guess == 1) min = pick + 1;
            else if (guess == -1) max = pick - 1;
            else if (guess == 0) return pick;

            pick = (int)((min + (long)max) / 2);
        }

        return pick;
    }

    public int start(int n, int pick) {
        this.pick = pick;

        return guessNumber(n);
    }

    private int guess(int num) {
        return Integer.compare(this.pick, num);
    }
}

/*
public int guessNumber(int n) {
    long s = 1;
    while (s <= n) {
        int x = (int)((n + s) / 2);
        int g = guess(x);
        if (g == 0)  return x;
        if (g < 0)   n = x - 1;
        if (g > 0)   s = x + 1;
    }
    return -1;
}
 */