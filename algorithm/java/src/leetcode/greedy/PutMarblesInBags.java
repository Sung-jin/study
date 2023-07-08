package leetcode.greedy;

/*
Q.2551
You have k bags. You are given a 0-indexed integer array weights where weights[i] is the weight of the ith marble. You are also given the integer k.

Divide the marbles into the k bags according to the following rules:

No bag is empty.
If the ith marble and jth marble are in a bag, then all marbles with an index between the ith and jth indices should also be in that same bag.
If a bag consists of all the marbles with an index from i to j inclusively, then the cost of the bag is weights[i] + weights[j].
The score after distributing the marbles is the sum of the costs of all the k bags.

Return the difference between the maximum and minimum scores among marble distributions.
 */
public class PutMarblesInBags {
    public long putMarbles(int[] weights, int k) {
        if (weights.length == k) return 0;
        int min = Integer.MAX_VALUE, max = 0;

        for (int i = 0; i < weights.length - k; i++) {

        }

        return -1;
        // 와 어떻게 저런식으로 추론을 할 수 있지..
    }
}

/*
public long putMarbles(int[] A, int k) {
    int n = A.length - 1;
    long B[] = new long[n], res = 0;
    for (int i = 0; i < B.length; i++) {
        B[i] = A[i] + A[i + 1];
    }
    Arrays.sort(B);
    for (int i = 0; i < k - 1; i++) {
        res += B[n - 1 - i] - B[i];
    }
    return res;
}
 */
