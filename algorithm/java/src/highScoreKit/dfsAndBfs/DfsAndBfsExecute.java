package highScoreKit.dfsAndBfs;

public class DfsAndBfsExecute {
    public void execute() {
        MapShortestDistance mapShortestDistance = new MapShortestDistance();
        TargetNumber targetNumber = new TargetNumber();
        Network network = new Network();
        ConvertWord convertWord = new ConvertWord();
        PickUpItem pickUpItem = new PickUpItem();

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
        System.out.println("------");
        System.out.println("network > 3 | [[1, 1, 0], [1, 1, 0], [0, 0, 1]] -> 2: " + network.solution(3, new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        System.out.println("network > 3 | [[1, 1, 0], [1, 1, 1], [0, 1, 1]] -> 1: " + network.solution(3, new int[][]{{1, 1, 0}, {1, 1, 1}, {0, 1, 1}}));
        System.out.println("------");
        System.out.println(
                "convertWord > hit | cog | [hot, dot, dog, lot, log, cog] -> 4: " +
                        convertWord.solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log", "cog"})
        );
        System.out.println(
                "convertWord > hit | cog | [hot, dot, dog, lot, log] -> 4: " +
                        convertWord.solution("hit", "cog", new String[]{"hot", "dot", "dog", "lot", "log"})
        );
        System.out.println("------");
        System.out.println(
                "pickUpItem > [[1,1,7,4],[3,2,5,5],[4,3,6,9],[2,6,8,8]] | 1 | 3 | 7 | 8 -> 17: " +
                        pickUpItem.solution(new int[][]{{1,1,7,4},{3,2,5,5},{4,3,6,9},{2,6,8,8}}, 1, 3, 7, 8)
        );
        System.out.println(
                "pickUpItem > [[1,1,8,4],[2,2,4,9],[3,6,9,8],[6,3,7,7]] | 9 | 7 | 5 | 1 -> 11: " +
                        pickUpItem.solution(new int[][]{{1,1,8,4},{2,2,4,9},{3,6,9,8},{6,3,7,7}}, 9, 7, 6, 1)
        );
        System.out.println(
                "pickUpItem > [[1,1,5,7]] | 1 | 3 | 7 | 8 -> 9: " +
                        pickUpItem.solution(new int[][]{{1,1,5,7},{3,2,5,5},{4,3,6,9},{2,6,8,8}}, 1, 1, 4, 7)
        );
        System.out.println(
                "pickUpItem > [[2,1,7,5],[6,4,10,10]] | 1 | 3 | 7 | 8 -> 15: " +
                        pickUpItem.solution(new int[][]{{2,1,7,5},{6,4,10,10}}, 3, 1, 7, 10)
        );
        System.out.println(
                "pickUpItem > [[2,2,5,5],[1,3,6,4],[3,1,4,6]] | 1 | 3 | 7 | 8 -> 10: " +
                        pickUpItem.solution(new int[][]{{2,2,5,5},{1,3,6,4},{3,1,4,6}}, 1, 4, 6, 3)
        );
    }
}
