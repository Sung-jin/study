package highScoreKit.greedy;

public class GreedyExecute {
    public void execute() {
        GymSuit gymSuit = new GymSuit();
        BigNumber bigNumber = new BigNumber();

        System.out.println("gymSuit > 5 | [2, 4] | [1, 3, 5] -> 5: " + gymSuit.solution(5, new int[]{2, 4}, new int[]{1, 3, 5}));
        System.out.println("gymSuit > 5 | [2, 4] | [3] -> 4: " + gymSuit.solution(5, new int[]{2, 4}, new int[]{3}));
        System.out.println("gymSuit > 3 | [3] | [1] -> 2: " + gymSuit.solution(3, new int[]{3}, new int[]{1}));
        System.out.println("------");
        System.out.println("bigNumber > 1924 | 2 -> 94: " + bigNumber.solution("1924", 2));
        System.out.println("bigNumber > 1231234 | 3 -> 3234: " + bigNumber.solution("1231234", 3));
        System.out.println("bigNumber > 4177252841 | 4 -> 775841: " + bigNumber.solution("4177252841", 4));
    }
}
