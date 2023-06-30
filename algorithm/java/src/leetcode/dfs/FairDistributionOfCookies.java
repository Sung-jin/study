package leetcode.dfs;

import java.util.Arrays;

/*
Q.2305

You are given an integer array cookies, where cookies[i] denotes the number of cookies in the ith bag. You are also given an integer k that denotes the number of children to distribute all the bags of cookies to. All the cookies in the same bag must go to the same child and cannot be split up.

The unfairness of a distribution is defined as the maximum total cookies obtained by a single child in the distribution.

Return the minimum unfairness of all distributions.
 */
public class FairDistributionOfCookies {

    public int distributeCookies(int[] cookies, int k) {
        int maxCookies = 0;

        for (int minimumUnfairnessDistributeCookie : findMinimumUnfairnessDistributeCookies(new int[k], cookies, 0)) {
            maxCookies = Math.max(maxCookies, minimumUnfairnessDistributeCookie);
        }

        return maxCookies;
    }

    private int[] findMinimumUnfairnessDistributeCookies(int[] distributes, int[] cookies, int index) {
        if (cookies.length - 1 == index) {
            int minDiffCount = Integer.MAX_VALUE;
            int minIndex = 0;

            for (int i = 0; i < distributes.length; i++) {
                int[] distributeResult = Arrays.copyOfRange(distributes, 0, distributes.length);
                distributeResult[i] += cookies[index];
                int diff = getMinMaxCookieDiffCount(distributeResult);

                if (minDiffCount > diff) {
                    minIndex = i;
                    minDiffCount = diff;
                }

//                if (Arrays.stream(distributeResult).anyMatch(v -> v == 1653)) {
//                    System.out.println(Arrays.toString(distributeResult) + ": " + getMinMaxCookieDiffCount(distributeResult)); 체크용, 봐도 말이 안됨
//                }
            }

            distributes[minIndex] += cookies[index];

            return distributes;
        }

        int minDiffCount = Integer.MAX_VALUE;
        int[] minResult = new int[cookies.length - 1];

        for (int i = 0; i < distributes.length; i++) {
            int[] distributeResults = Arrays.copyOfRange(distributes, 0, distributes.length);
            distributeResults[i] += cookies[index];

            int[] minDistributeResult = findMinimumUnfairnessDistributeCookies(distributeResults, cookies, index + 1);
            int diffCount = getMinMaxCookieDiffCount(minDistributeResult);

            if (minDiffCount > diffCount) {
                minDiffCount = diffCount;
                minResult = minDistributeResult;
            }
        }

        return minResult;
    }

    private int getMinMaxCookieDiffCount(int[] distributes) {
        int min = Integer.MAX_VALUE;
        int max = 0;

        for (int distribute : distributes) {
            min = Math.min(min, distribute);
            max = Math.max(max, distribute);
        }

        return max - min;
    }
}
/*
말이 안되는게, [941,797,1475,638,191,712] -> 1653 이라는데, 1653 이 되는 케이스 중 최대 차는 178 이 나오고,
1579, 1509, 1666 으로 배분이 가능하고, 이때 차이는 157 인데, 왜 정답이 1653 이라는거야 ㅡㅡ 이해가 안되네


int ans = Integer.MAX_VALUE;

void helper(int[] cookies, int start, int k, int[] temp) {
    if (start == cookies.length) {
        int max = 0;
        for (int c : temp)
            max = Math.max(max, c);
        ans = Math.min(ans, max);
        return;
    }
    for (int i = 0; i < k; i++) {
        temp[i] += cookies[start];
        helper(cookies, start + 1, k, temp);
        temp[i] -= cookies[start]; <- 빼주면 되니까 카이할 필요가 없었구나 괜히 공간 더 썻네
    }
}

public int distributeCookies(int[] cookies, int k) {
    helper(cookies, 0, k, new int[k]);
    return ans;
}
 */

/*
discussion

[941,797,1475,638,191,712]
3

with this test case given sol is 1653 but according to my algo the optimize distribution for the above is 1579 ,1509 ,1666 with and with this distribution answer should be 1666 not 1653 ......
can anyone explain me with this........

->
Return the minimum unfairness of ALL distributions.
NOT FAIREST distribution.

아니.. 뭔말이야 전체를 배분한 것 중 가장 적은 불공평한 배분을 리턴? 이게 결국 제일 공정한 분배아냐?
 */