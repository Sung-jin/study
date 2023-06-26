package highScoreKit.greedy;

public class GreedyExecute {
    public void execute() {
        GymSuit gymSuit = new GymSuit();
        BigNumber bigNumber = new BigNumber();
        JoyStick joyStick = new JoyStick();
        Lifeboat lifeboat = new Lifeboat();

        System.out.println("gymSuit > 5 | [2, 4] | [1, 3, 5] -> 5: " + gymSuit.solution(5, new int[]{2, 4}, new int[]{1, 3, 5}));
        System.out.println("gymSuit > 5 | [2, 4] | [3] -> 4: " + gymSuit.solution(5, new int[]{2, 4}, new int[]{3}));
        System.out.println("gymSuit > 3 | [3] | [1] -> 2: " + gymSuit.solution(3, new int[]{3}, new int[]{1}));
        System.out.println("------");
        System.out.println("bigNumber > 1924 | 2 -> 94: " + bigNumber.solution("1924", 2));
        System.out.println("bigNumber > 1231234 | 3 -> 3234: " + bigNumber.solution("1231234", 3));
        System.out.println("bigNumber > 4177252841 | 4 -> 775841: " + bigNumber.solution("4177252841", 4));
        System.out.println("------");
        System.out.println("joyStick > JEROEN -> 56: " + joyStick.solution("JEROEN"));
        System.out.println("joyStick > JAN -> 23: " + joyStick.solution("JAN"));
        System.out.println("joyStick > AAAAAAAB -> 2: " + joyStick.solution("AAAAAAAB"));
        System.out.println("joyStick > BAABBBBAAA -> 11: " + joyStick.solution("BAABBBBAAA"));
        System.out.println("joyStick > AAAAAA -> 0: " + joyStick.solution("AAAAAA"));
        System.out.println("------");
        System.out.println("lifeboat > [70, 50, 80, 50] | 100 -> 3: " + lifeboat.solution(new int[]{70, 50, 80, 50}, 100));
        System.out.println("lifeboat > [70, 80, 50] | 100 -> 3: " + lifeboat.solution(new int[]{70, 80, 50}, 100));
    }
}
