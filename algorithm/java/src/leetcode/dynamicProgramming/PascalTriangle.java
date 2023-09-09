package leetcode.dynamicProgramming;

import java.util.ArrayList;
import java.util.List;

/*
Q.118. Pascal's Triangle

Given an integer numRows, return the first numRows of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it
 */
public class PascalTriangle {
    public List<List<Integer>> generate(int numRows) {
        // 1 <= numRows <= 30

        List<List<Integer>> answer = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> value = new ArrayList<>();
            value.add(1);
            if (i == 0) {
                answer.add(value);
                continue;
            } else {
                List<Integer> above = answer.get(i - 1);
                for (int j = 0; j < (i - 1); j++) {
                    value.add(above.get(j) + above.get(j + 1));
                }
            }
            value.add(1);
            answer.add(value);
        }

        return answer;
    }
}

/*
public class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> allrows = new ArrayList<List<Integer>>();
        ArrayList<Integer> row = new ArrayList<Integer>();
        for(int i=0;i<numRows;i++) {
            row.add(0, 1);
            for(int j=1;j<row.size()-1;j++)
                row.set(j, row.get(j)+row.get(j+1));
            allrows.add(new ArrayList<Integer>(row));
        }
        return allrows;
    }
}
 */