package leetcode.greedy;

import java.util.ArrayList;
import java.util.List;

/*
Q.1441. Build an Array With Stack Operations
You are given an integer array target and an integer n.

You have an empty stack with the two following operations:

"Push": pushes an integer to the top of the stack.
"Pop": removes the integer on the top of the stack.
You also have a stream of the integers in the range [1, n].

Use the two stack operations to make the numbers in the stack (from the bottom to the top) equal to target. You should follow the following rules:

If the stream of the integers is not empty, pick the next integer from the stream and push it to the top of the stack.
If the stack is not empty, pop the integer at the top of the stack.
If, at any moment, the elements in the stack (from the bottom to the top) are equal to target, do not read new integers from the stream and do not do more operations on the stack.
Return the stack operations needed to build target following the mentioned rules. If there are multiple valid answers, return any of them.
 */
public class BuildAnArrayWithStackOperations {
    public List<String> buildArray(int[] target, int n) {
        List<String> answer = new ArrayList<>();
        int value = 1;
        int index = 0;
        int targetValue = target[index]; // 이게 없어도 되네. index 로 어차피 값을 찾으니, 해당 값으로 비교하면 되네.

        while(value < target[target.length - 1]) {
            answer.add("Push");

            if (targetValue != value) answer.add("Pop");
            else targetValue = target[++index];
            value++;
        }
        answer.add("Push");

        return answer;
    }
}

/*
public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<>();
        int j=0;
        for (int i=1;i<=n && j<target.length;i++) {
            result.add("Push");
            if(target[j]==i) {
                j++;
            } else {
                result.add("Pop");
            }
        }
        return result;
    }
 */