package leetcode.dfs;

/*
Q.486
You are given an integer array nums. Two players are playing a game with this array: player 1 and player 2.

Player 1 and player 2 take turns, with player 1 starting first. Both players start the game with a score of 0. At each turn, the player takes one of the numbers from either end of the array (i.e., nums[0] or nums[nums.length - 1]) which reduces the size of the array by 1. The player adds the chosen number to their score. The game ends when there are no more elements in the array.

Return true if Player 1 can win the game. If the scores of both players are equal, then player 1 is still the winner, and you should also return true. You may assume that both players are playing optimally.
 */
public class PredictTheWinner {

    private int LEFT_MAX = 0;
    private int RIGHT_MAX = 0;
    private int LEFT_INDEX, RIGHT_INDEX;

    public boolean PredictTheWinner(int[] nums) {
        if (nums.length == 1) return true;

        LEFT_MAX = 0;
        RIGHT_MAX = 0;
        LEFT_INDEX = 0;
        RIGHT_INDEX = nums.length - 1;

        pickScore(nums, 0, 0, true);

        return LEFT_MAX >= RIGHT_MAX;
    }

    private void pickScore(int[] nums, int leftScore, int rightScore, boolean leftTurn) {
        int score = nums[LEFT_INDEX++];
        int newLeftScore = leftScore + (leftTurn ? score : 0);
        int newRightScore = rightScore + (leftTurn ? 0 : score);

        if (LEFT_INDEX > RIGHT_INDEX) {
            LEFT_MAX = Math.max(LEFT_MAX, newLeftScore);
            RIGHT_MAX = Math.max(RIGHT_MAX, newRightScore);
            return;
        }

        pickScore(nums, newLeftScore, newRightScore, !leftTurn);

        LEFT_INDEX--;
        score = nums[RIGHT_INDEX--];
        newLeftScore = leftScore + (leftTurn ? score : 0);
        newRightScore = rightScore + (leftTurn ? 0 : score);
        pickScore(nums, newLeftScore, newRightScore, !leftTurn);
    }
}

// dfs 로 풀수가 없네. 1,5,2 의 경우 dfs 로 하면 left 1or2 , 5 / right 2or1 선택할 수 있는 케이스가 발생하지만,
// 요구사항은 사실상 left player 가 가장 최적으로 상대방의 다음수를 고려했으 때 이기는 케이스? 이어야 함. 즉, 모든 케이스를 따지면 안됨

/*
public boolean PredictTheWinner(int[] nums) {
    if (nums == null) { return true; }
    int n = nums.length;
    if ((n & 1) == 0) { return true; } // Improved with hot13399's comment.
    int[] dp = new int[n];
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            if (i == j) {
                dp[i] = nums[i];
            } else {
                dp[j] = Math.max(nums[i] - dp[j], nums[j] - dp[j - 1]);
            }
        }
    }
    return dp[n - 1] >= 0;
}
 */