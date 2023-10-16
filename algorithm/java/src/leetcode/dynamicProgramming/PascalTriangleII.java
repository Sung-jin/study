package leetcode.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/*
Q.119. Pascal's Triangle II
Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

Follow up: Could you optimize your algorithm to use only O(rowIndex) extra space?
 */
public class PascalTriangleII {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> answer = new ArrayList<>();

        for (int i = 0; i <= rowIndex; i++) {
            if (i == 0 || i == 1) answer.add(1);
            else {
                answer.add(1);

                for (int j = i - 1; j > 0; j--) {
                    answer.set(j, answer.get(j - 1) + answer.get(j));
                }
            }
        }

        return answer;
    }
}

/*
class Solution {
public:
    vector<int> getRow(int rowIndex) {
        vector<int> A(rowIndex+1, 0);
        A[0] = 1;
        for(int i=1; i<rowIndex+1; i++)
            for(int j=i; j>=1; j--)
                A[j] += A[j-1];
        return A;
    }
};
 */
