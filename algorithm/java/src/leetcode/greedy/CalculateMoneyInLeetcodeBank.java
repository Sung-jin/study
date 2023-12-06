package leetcode.greedy;

/*
Q.1716. Calculate Money in Leetcode Bank
Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.

He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more than the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
Given n, return the total amount of money he will have in the Leetcode bank at the end of the nth day.
 */
public class CalculateMoneyInLeetcodeBank {

    private final int[] AFTER_MONEY = new int[]{1,3,6,10,15,21,28};

    public int totalMoney(int n) {
        int target = n - 1;
        int loopCount = (target / 7);
        int answer = 0;

        for (int i = 0; i <= loopCount; i++) {
            if (loopCount > i) answer += AFTER_MONEY[6] + (i * 7);
            else answer += AFTER_MONEY[target % 7] + (i * (n % 7));
        }

        return answer;
    }
}

/*
class Solution {
    public int totalMoney(int n) {
        int m=n/7;  //(no.of full weeks)
        // first week  1 2 3 4 5 6 7 (sum is 28 i.e. 7*(i+3) if i=1)
        // second week  2 3 4 5 6 7 8 (sum is 35 i.e. 7*(i+3) if i=2)
        //.... so on
        int res=0; //for result
        //calculating full weeks
        for(int i=1;i<=m;i++){
            res+=7*(i+3);
        }
        //calculating left days
        for(int i=7*m;i<n;i++){
            res+=++m;
        }
        return res;
    }
}
 */