package highScoreKit.binarySearch;

public class BinarySearchExecution {
    public void execute() {
        SteppingStone steppingStone = new SteppingStone();

        System.out.println(
                "steppingStone > 25 | [2, 14, 11, 21, 17] | 2 -> 4: " + steppingStone.solution(25, new int[]{2, 14, 11, 21, 17}, 2)
        );
    }
}
