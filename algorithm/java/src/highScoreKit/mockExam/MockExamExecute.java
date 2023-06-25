package highScoreKit.mockExam;

public class MockExamExecute {
    public void execute() {
        Third third = new Third();

        System.out.println("----THIRD----");
        System.out.println(
                "one > [[0], [8], [1, 3], [2], [1], [1, 4, 6], [2, 5], [3, 6], [4]] -> 1: " +
                        third.one(new int[][]{{0}, {8}, {1, 3}, {2}, {1}, {1, 4, 6}, {2, 5}, {3, 6}, {4}})
        );
        System.out.println(
                "one > [[0], [4], [1], [2], [3]] -> 1: " +
                        third.one(new int[][]{{0}, {4}, {1}, {2}, {3}})
        );
    }
}
