package highScoreKit.sorting;

import java.util.Arrays;

public class SortingExecute {
    public void execute() {
        // sorting
        KthNumber kthNumber = new KthNumber();
        HIndex hIndex = new HIndex();
        BiggestNumber biggestNumber = new BiggestNumber();

        System.out.println(
                "kthNumber > [1, 5, 2, 6, 3, 7, 4] | [2, 5, 3], [4, 4, 1], [1, 7, 3] -> [5, 6, 3]: " +
                        Arrays.toString(kthNumber.solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}}))
        );
        System.out.println("------");
        System.out.println("hIndex > [3, 0, 6, 1, 5] -> 3: " + hIndex.solution(new int[]{3, 0, 6, 1, 5}));
        System.out.println("------");
        System.out.println("biggestNumber > [6, 10, 2] -> 6210: " + biggestNumber.solution(new int[]{6, 10, 2}));
        System.out.println("biggestNumber > [3, 30, 34, 5, 9] -> 9534330: " + biggestNumber.solution(new int[]{3, 30, 34, 5, 9}));
    }
}
