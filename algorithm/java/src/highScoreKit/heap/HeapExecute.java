package highScoreKit.heap;

import java.util.Arrays;

public class HeapExecute {
    public void execute() {
        // heap
        MoreSpicy moreSpicy = new MoreSpicy();
        DiskController diskController = new DiskController();
        DoublePriorityQueue doublePriorityQueue = new DoublePriorityQueue();

        System.out.println(
                "moreSpicy > [1, 2, 3, 9, 10, 12] | 7 -> 2: " +
                        moreSpicy.solution(new int[]{1, 2, 3, 9, 10, 12}, 7)
        );
        System.out.println("------");
        System.out.println(
                "diskController > [[0, 3], [1, 9], [2, 6]] -> 9: " +
                        diskController.solution(new int[][]{{0, 3}, {1, 9}, {2, 6}})
        );
        System.out.println("------");
        System.out.println(
                "doublePriorityQueue > [I 16, I -5643, D -1, D 1, D 1, I 123, D -1] -> [0, 0]: " +
                        Arrays.toString(doublePriorityQueue.solution(new String[]{"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"}))
        );
        System.out.println(
                "doublePriorityQueue > [I -45, I 653, D 1, I -642, I 45, I 97, D 1, D -1, I 333] -> [333, -45]: " +
                        Arrays.toString(doublePriorityQueue.solution(new String[]{"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"}))
        );
    }
}
