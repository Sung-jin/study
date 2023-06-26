package highScoreKit.dynamicProgramming;

public class DynamicProgrammingExecute {
    public void execute() {
        IntegerTriangle integerTriangle = new IntegerTriangle();

        System.out.println(
                "integerTriangle > [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]] -> 30: " +
                        integerTriangle.solution(new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}})
        );
    }
}
