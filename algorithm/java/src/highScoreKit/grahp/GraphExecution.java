package highScoreKit.grahp;

public class GraphExecution {
    public void execute() {
        FurthestNode furthestNode = new FurthestNode();

        System.out.println(
                "furthestNode > [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]] -> 3: " +
                        furthestNode.solution(6, new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}})
        );
    }
}
