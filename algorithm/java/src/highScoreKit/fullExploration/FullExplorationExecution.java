package highScoreKit.fullExploration;

import java.util.Arrays;

public class FullExplorationExecution {
    public void execute() {
        LeastRectangle leastRectangle = new LeastRectangle();
        Fatigue fatigue = new Fatigue();
        MockExam mockExam = new MockExam();
        FindPrimeNumber findPrimeNumber = new FindPrimeNumber();

        System.out.println(
                "leastRectangle > [[60, 50], [30, 70], [60, 30], [80, 40]] -> 4000: " +
                        leastRectangle.solution(new int[][]{{60, 50}, {30, 70}, {60, 30}, {80, 40}})
        );
        System.out.println(
                "leastRectangle > [[10, 7], [12, 3], [8, 15], [14, 7], [5, 15]] -> 120: " +
                        leastRectangle.solution(new int[][]{{10, 7}, {12, 3}, {8, 15}, {14, 7}, {5, 15}})
        );
        System.out.println(
                "leastRectangle > [[14, 4], [19, 6], [6, 16], [18, 7], [7, 11]] -> 133: " +
                        leastRectangle.solution(new int[][]{{14, 4}, {19, 6}, {6, 16}, {18, 7}, {7, 11}})
        );
        System.out.println("------");
        System.out.println(
                "fatigue > 80 | [[80,20],[50,40],[30,10]] -> 3: " +
                        fatigue.solution(80, new int[][]{{80, 20},{50, 40},{30, 10}})
        );
        System.out.println("------");
        System.out.println(
                "mockExam > [1,2,3,4,5] -> [1]: " +
                        Arrays.toString(mockExam.solution(new int[]{1, 2, 3, 4, 5}))
        );
        System.out.println(
                "mockExam > [1,3,2,4,2] -> [1,2,3]: " +
                        Arrays.toString(mockExam.solution(new int[]{1, 3, 2, 4, 2}))
        );
        System.out.println("------");
        System.out.println("findPrimeNumber > 17 -> 3: " + findPrimeNumber.solution("17"));
        System.out.println("findPrimeNumber > 011 -> 2: " + findPrimeNumber.solution("011"));
    }
}
