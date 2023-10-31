package leetcode.greedy;

/*
Q.2433. Find The Original Array of Prefix Xor
You are given an integer array pref of size n. Find and return the array arr of size n that satisfies:

pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i].
Note that ^ denotes the bitwise-xor operation.

It can be proven that the answer is unique.
 */
public class FindTheOriginalArrayOfPrefixXor {
    public int[] findArray(int[] pref) {
        int[] answer = new int[pref.length];
        answer[0] = pref[0];;
        for (int i = 1; i < pref.length; i++) {
            answer[i] = pref[i - 1] ^ pref[i];
        }

        return answer;
    }
}

/*
public int[] findArray(int[] A) {
    for (int i = A.length - 1; i > 0; --i)
        A[i] ^= A[i - 1];
    return A;
}
 */