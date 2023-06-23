package highScoreKit.dfsAndBfs;

public class DfsAndBfsExecute {
    public void execute() {
        MapShortestDistance mapShortestDistance = new MapShortestDistance();
        TargetNumber targetNumber = new TargetNumber();

        System.out.println(
                "mapShortestDistance > [[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,1],[0,0,0,0,1]] -> 11: " +
                        mapShortestDistance.solution(new int[][]{{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,1},{0,0,0,0,1}})
        );
        System.out.println(
                "mapShortestDistance > [[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,0],[0,0,0,0,1]] -> -1: " +
                        mapShortestDistance.solution(new int[][]{{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,0},{0,0,0,0,1}})
        );
        System.out.println("------");
        System.out.println(
                "targetNumber > [1, 1, 1, 1, 1] | 3 -> 5: " + targetNumber.solution(new int[]{1, 1, 1, 1, 1}, 3)
        );
        System.out.println(
                "targetNumber > [4, 1, 2, 1] | 4 -> 2: " + targetNumber.solution(new int[]{4, 1, 2, 1}, 4)
        );
    }
}
