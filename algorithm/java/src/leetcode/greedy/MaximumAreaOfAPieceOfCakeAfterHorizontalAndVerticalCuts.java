package leetcode.greedy;

import java.util.Arrays;

/*
Q.1465
You are given a rectangular cake of size h x w and two arrays of integers horizontalCuts and verticalCuts where:

horizontalCuts[i] is the distance from the top of the rectangular cake to the ith horizontal cut and similarly, and
verticalCuts[j] is the distance from the left of the rectangular cake to the jth vertical cut.
Return the maximum area of a piece of cake after you cut at each horizontal and vertical position provided in the arrays horizontalCuts and verticalCuts. Since the answer can be a large number, return this modulo 109 + 7.
 */
public class MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts {
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int maxHorizonSize = getMaxContinuousSize(horizontalCuts, h);
        int maxVerticalSize = getMaxContinuousSize(verticalCuts, w);
        long area = (long) maxHorizonSize * maxVerticalSize;
        long modulo = (long) Math.pow(10, 9) + 7;

        return (int) (area % modulo);
    }

    private int getMaxContinuousSize(int[] cuts, int totalSize) {
        int maxSize = 0;
        int startIndex = 0;
        for (int cut : cuts) {
            maxSize = Math.max(maxSize, cut - startIndex);
            startIndex = cut;
        }
        maxSize = Math.max(maxSize, totalSize - startIndex);

        return maxSize;
    }
}

/*
public int maxArea(int h, int w, int[] hCuts, int[] vCuts) {
    Arrays.sort(hCuts);
    Arrays.sort(vCuts);
    int max_h = Math.max(hCuts[0], h - hCuts[hCuts.length - 1]);
    int max_v = Math.max(vCuts[0], w - vCuts[vCuts.length - 1]);
    for (int i = 0; i < hCuts.length - 1; ++i)
        max_h = Math.max(max_h, hCuts[i + 1] - hCuts[i]);
    for (int i = 0; i < vCuts.length - 1; ++i)
        max_v = Math.max(max_v, vCuts[i + 1] - vCuts[i]);
    return (int)((long)max_h * max_v % 1000000007);
}
 */