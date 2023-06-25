package highScoreKit.dfsAndBfs;

public class TargetNumber {
    public int solution(int[] numbers, int target) {
        return findTargetNumber(numbers, 0, 0, target, 0);
    }

    int findTargetNumber(int[] numbers, int index, int calculateNumber, int target, int count) {
        if (index == numbers.length) {
            if (calculateNumber == target) {
                return count + 1;
            } else return count;
        }
        int correctCount = 0;

        for (OPERATORS operator : OPERATORS.values()) {
            int result = calculate(operator, calculateNumber, numbers[index]);
            correctCount += findTargetNumber(numbers, index + 1, result, target, count);
        }

        return correctCount;
    }

    int calculate(OPERATORS operator, int from, int to) {
        if (operator == OPERATORS.P) return from + to;
        else return from - to;
    }

    enum OPERATORS {
        P, S
    }
}

/*
DFS
- 재귀
- 모든 가능성 탐색, ~하는 방법의 개수

            A

        B       C

     D

A -> B -> D -> C

--------
DFS 구조

solution() {
    dfsHelper(arg1, arg2, ..)
    return
}

dfsHelper(arg1, arg2, ...) {
    if (target) {
        return
    }
    dfsHelper(arg1', arg2', ...)
}
--------

// 자료구조/알고리즘: DFS
// 시간 복잡도: O(2^n)
// 공간 복잡도: O(1)

class Solution {
    public int solution(int[] numbers, int target) {
        return getTotalWays(numbers, target, 0, 0);
    }

    private int getTotalWays(int[] numbers, int target, int index, int sum) {
        if (index == numbers.length) {
            return sum == target ? 1 : 0;
        }
        int totalWays = 0;
        totalWays += getTotalWays(numbers, target, index + 1, sum + numbers[index]);
        totalWays += getTotalWays(numbers, target, index + 1, sum - numbers[index]);
        return totalWays;
    }
}
 */