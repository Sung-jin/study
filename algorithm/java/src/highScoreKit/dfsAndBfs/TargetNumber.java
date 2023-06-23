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
