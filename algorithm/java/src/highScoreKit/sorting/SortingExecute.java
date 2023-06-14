package highScoreKit.sorting;

import java.util.Arrays;

public class SortingExecute {
    public void execute() {
        // sorting
        KthNumber kthNumber = new KthNumber();

        System.out.println(
                "kthNumber > [1, 5, 2, 6, 3, 7, 4] | [2, 5, 3], [4, 4, 1], [1, 7, 3] -> [5, 6, 3]: " +
                        Arrays.toString(kthNumber.solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}}))
        );
    }
}
