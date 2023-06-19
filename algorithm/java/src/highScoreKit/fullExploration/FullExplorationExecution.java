package highScoreKit.fullExploration;

import java.util.Arrays;

public class FullExplorationExecution {
    public void execute() {
        LeastRectangle leastRectangle = new LeastRectangle();
        Fatigue fatigue = new Fatigue();
        MockExam mockExam = new MockExam();
        FindPrimeNumber findPrimeNumber = new FindPrimeNumber();
        Carpet carpet = new Carpet();
        VowelDictionary vowelDictionary = new VowelDictionary();

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
        System.out.println("------");
        System.out.println("carpet > 10 | 2 -> [4, 3]: " + Arrays.toString(carpet.solution(10, 2)));
        System.out.println("carpet > 8 | 1 -> [3, 3]: " + Arrays.toString(carpet.solution(8, 1)));
        System.out.println("carpet > 24 | 24 -> [8, 6]: " + Arrays.toString(carpet.solution(24, 24)));
        System.out.println("------");
        System.out.println("vowelDictionary > AAAAE -> 6: " + vowelDictionary.solution("AAAAE"));
        System.out.println("vowelDictionary > AAAE -> 10: " + vowelDictionary.solution("AAAE"));
        System.out.println("vowelDictionary > I -> 1563: " + vowelDictionary.solution("I"));
        System.out.println("vowelDictionary > EIO -> 1189: " + vowelDictionary.solution("EIO"));
    }
}
