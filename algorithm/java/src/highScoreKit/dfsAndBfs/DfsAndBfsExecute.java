package highScoreKit.dfsAndBfs;

public class DfsAndBfsExecute {
    public void execute() {
        MapShortestDistance mapShortestDistance = new MapShortestDistance();

        System.out.println(
                "mapShortestDistance > [[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,1],[0,0,0,0,1]] -> 11: " +
                        mapShortestDistance.solution(new int[][]{{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,1},{0,0,0,0,1}})
        );
        System.out.println(
                "mapShortestDistance > [[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,0],[0,0,0,0,1]] -> -1: " +
                        mapShortestDistance.solution(new int[][]{{1,0,1,1,1},{1,0,1,0,1},{1,0,1,1,1},{1,1,1,0,0},{0,0,0,0,1}})
        );
    }
}
