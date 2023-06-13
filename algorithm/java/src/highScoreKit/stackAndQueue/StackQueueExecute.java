package highScoreKit.stackAndQueue;

import java.util.Arrays;

public class StackQueueExecute {
    public void execute() {
        // stack
        HateSameNumber hateSameNumber = new HateSameNumber();
        FeatureDevelopment featureDevelopment = new FeatureDevelopment();
        Process process = new Process();
        TruckPassingTheBridge truckPassingTheBridge = new TruckPassingTheBridge();
        CorrectParentheses correctParentheses = new CorrectParentheses();
        StockPrice stockPrice = new StockPrice();

        System.out.println(
                "hateSameNumber > [1,1,3,3,0,1,1] -> [1,3,0,1]: " +
                        Arrays.toString(hateSameNumber.solution(new int[]{1, 1, 3, 3, 0, 1, 1}))
        );
        System.out.println(
                "hateSameNumber > [4,4,4,3,3] -> [4,3]: " +
                        Arrays.toString(hateSameNumber.solution(new int[]{4,4,4,3,3}))
        );
        System.out.println("------");
        System.out.println(
                "featureDevelopment > [93, 30, 55] | [1, 30, 5] -> [2, 1]: " +
                        Arrays.toString(featureDevelopment.solution(new int[]{93, 30, 55}, new int[]{1, 30, 5}))
        );
        System.out.println(
                "featureDevelopment > [95, 90, 99, 99, 80, 99] | [1, 1, 1, 1, 1, 1] -> [1, 3, 2]: " +
                        Arrays.toString(featureDevelopment.solution(new int[]{95, 90, 99, 99, 80, 99}, new int[]{1, 1, 1, 1, 1, 1}))
        );
        System.out.println("------");
        System.out.println(
                "process > [2, 1, 3, 2] | 2 -> 1: " +
                        process.solution(new int[]{2, 1, 3, 2}, 2)
        );
        System.out.println(
                "process > [1, 1, 9, 1, 1, 1] | 0 -> 5: " +
                        process.solution(new int[]{1, 1, 9, 1, 1, 1}, 0)
        );
        System.out.println("------");
        System.out.println(
                "truckPassingTheBridge > 2 | 10 | [7,4,5,6] -> 8: " +
                        truckPassingTheBridge.solution(2, 10, new int[]{7,4,5,6})
        );
        System.out.println(
                "truckPassingTheBridge > 100 | 100 | [10] -> 101: " +
                        truckPassingTheBridge.solution(100, 100, new int[]{10})
        );
        System.out.println(
                "truckPassingTheBridge > 100 | 100 | [10,10,10,10,10,10,10,10,10,10] -> 110: " +
                        truckPassingTheBridge.solution(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10})
        );
        System.out.println("------");
        System.out.println(
                "correctParentheses > ()() -> true: " +
                        correctParentheses.solution("()()")
        );
        System.out.println(
                "correctParentheses > (())() -> true: " +
                        correctParentheses.solution("(())()")
        );
        System.out.println(
                "correctParentheses > )()( -> false: " +
                        correctParentheses.solution(")()(")
        );
        System.out.println(
                "correctParentheses > (()( -> false: " +
                        correctParentheses.solution("(()(")
        );
        System.out.println("------");
        System.out.println(
                "stockPrice > [1, 2, 3, 2, 3] -> [4, 3, 1, 1, 0]: " +
                        Arrays.toString(stockPrice.solution(new int[]{1, 2, 3, 2, 3}))
        );
    }
}
