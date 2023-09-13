package leetcode.greedy;

import java.util.Arrays;

/*
Q.135 Candy
There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.

You are giving candies to these children subjected to the following requirements:

Each child must have at least one candy.
Children with a higher rating get more candies than their neighbors.
Return the minimum number of candies you need to have to distribute the candies to the children.
 */
public class Candy {
    public int candy(int[] ratings) {
        if (ratings.length == 1) return 1;

        int[] distributeCandies = new int[ratings.length];
        Arrays.fill(distributeCandies, 1);

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i - 1] < ratings[i]) distributeCandies[i] = distributeCandies[i - 1] + 1;
        }
        for (int i = ratings.length - 1; i > 0; i--) {
            if (ratings[i] < ratings[i - 1]) distributeCandies[i - 1] = Math.max(distributeCandies[i] + 1, distributeCandies[i - 1]);
        }

        return Arrays.stream(distributeCandies).sum();
    }
}
