import leetcode.binarySearch.SearchInsertPosition;
import leetcode.binarySearch.Sqrt;
import leetcode.bruteForce.RomanToInteger;
import leetcode.bruteForce.SingleNumber2;
import leetcode.bruteForce.TwoSum;
import leetcode.bruteForce.BuddyStrings;
import leetcode.dfs.FairDistributionOfCookies;
import leetcode.dfs.MaximumNumberOfAchievableTransferRequests;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // leet-code
        // brute force
        TwoSum twoSum = new TwoSum();
        RomanToInteger romanToInteger = new RomanToInteger();
        SingleNumber2 singleNumber2 = new SingleNumber2();
        BuddyStrings buddyStrings = new BuddyStrings();

        System.out.println("brute force");
        System.out.println("Q.1 two sum");
        System.out.println("[2,7,11,15], 9 -> [0,1]: " + Arrays.toString(twoSum.twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println("[3,2,4], 6 -> [1,2]: " + Arrays.toString(twoSum.twoSum(new int[]{3,2,4}, 6)));
        System.out.println("[3,3], 6 -> [0,1]: " + Arrays.toString(twoSum.twoSum(new int[]{3,3}, 6)));
        System.out.println("------");
        System.out.println("Q.13 roman to integer");
        System.out.println("III -> 3: " + romanToInteger.romanToInt("III"));
        System.out.println("LVIII -> 58: " + romanToInteger.romanToInt("LVIII"));
        System.out.println("MCMXCIV -> 1994: " + romanToInteger.romanToInt("MCMXCIV"));
        System.out.println("------");
        System.out.println("Q.137 single number 2");
        System.out.println("[2,2,3,2] -> 3: " + singleNumber2.singleNumber(new int[]{2, 2, 3, 2}));
        System.out.println("[0,1,0,1,0,1,99] -> 99: " + singleNumber2.singleNumber(new int[]{0,1,0,1,0,1,99}));
        System.out.println("[1,2,3,1,2,4,3,1,2,3] -> 4: " + singleNumber2.singleNumber(new int[]{1,2,3,1,2,4,3,1,2,3}));
        System.out.println("[30000,500,100,30000,100,30000,100] -> 500: " + singleNumber2.singleNumber(new int[]{30000,500,100,30000,100,30000,100}));
        System.out.println("------");
        System.out.println("Q.859 buddy Strings");
        System.out.println("ab, ba -> true: " + buddyStrings.buddyStrings("ab", "ba"));
        System.out.println("ab, ab -> false: " + buddyStrings.buddyStrings("ab", "ab"));
        System.out.println("aa, aa -> true: " + buddyStrings.buddyStrings("aa", "aa"));
        System.out.println("ccccda, acccdc -> true: " + buddyStrings.buddyStrings("ccccda", "acccdc"));
        System.out.println("ccccda, accccd -> false: " + buddyStrings.buddyStrings("ccccda", "accccd"));
        System.out.println("ab, ca -> false: " + buddyStrings.buddyStrings("ab", "ca"));

        // binary search
        SearchInsertPosition searchInsertPosition = new SearchInsertPosition();
        Sqrt sqrt = new Sqrt();

        System.out.println("binary search");
        System.out.println("Q.35 Search Insert Position");
        System.out.println("[1,3,5,6], 5 -> 2: " + searchInsertPosition.searchInsert(new int[]{1,3,5,6}, 5));
        System.out.println("[1,3,5,6], 2 -> 1: " + searchInsertPosition.searchInsert(new int[]{1,3,5,6}, 2));
        System.out.println("[1,3,5,6], 7 -> 4: " + searchInsertPosition.searchInsert(new int[]{1,3,5,6}, 7));
        System.out.println("[1,3,5,6], 0 -> 0: " + searchInsertPosition.searchInsert(new int[]{1,3,5,6}, 0));
        System.out.println("[1,3], 2 -> 1: " + searchInsertPosition.searchInsert(new int[]{1,3}, 2));
        System.out.println("[1,3], 0 -> 0: " + searchInsertPosition.searchInsert(new int[]{1,3}, 0));
        System.out.println("[1,3], 2 -> 1: " + searchInsertPosition.searchInsert(new int[]{1,3}, 2));
        System.out.println("[1,3], 1 -> 0: " + searchInsertPosition.searchInsert(new int[]{1,3}, 1));
        System.out.println("------");
        System.out.println("Q.69 sqrt(x)");
//        System.out.println("4 -> 2: " + sqrt.mySqrt(4));
//        System.out.println("8 -> 2: " + sqrt.mySqrt(8));
        System.out.println("2147395599 -> 2: " + sqrt.mySqrt(2147395599));

        // dfs
        MaximumNumberOfAchievableTransferRequests maximumNumberOfAchievableTransferRequests = new MaximumNumberOfAchievableTransferRequests();
        FairDistributionOfCookies fairDistributionOfCookies = new FairDistributionOfCookies();

        System.out.println("dfs");
        System.out.println("Q.1601 Maximum Number of Achievable Transfer Requests");
        System.out.println(
                "5, [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]] -> 5: " +
                        maximumNumberOfAchievableTransferRequests.maximumRequests(5, new int[][]{{0,1},{1,0},{0,1},{1,2},{2,0},{3,4}})
        );
        System.out.println(
                "4, [[0,3],[3,1],[1,2],[2,0]] -> 4: " +
                        maximumNumberOfAchievableTransferRequests.maximumRequests(4, new int[][]{{0,3},{3,1},{1,2},{2,0}})
        );
        System.out.println("------");
        System.out.println("Q.2305 Fair Distribution of Cookies");
        System.out.println("[8,15,10,20,8], 2 -> 31: " + fairDistributionOfCookies.distributeCookies(new int[]{8,15,10,20,8}, 2));
        System.out.println("[6,1,3,2,2,4,1,2], 3 -> 7: " + fairDistributionOfCookies.distributeCookies(new int[]{6,1,3,2,2,4,1,2}, 3));
        System.out.println("[941,797,1475,638,191,712], 3 -> 1653: " + fairDistributionOfCookies.distributeCookies(new int[]{941,797,1475,638,191,712}, 3)); // 이해가 안되네..
    }

//    public static void main(String[] args) {
//        -- highScoreKit
//        HashExecute hashExecute = new HashExecute();
//        StackQueueExecute stackQueueExecute = new StackQueueExecute();
//        HeapExecute heapExecute = new HeapExecute();
//        SortingExecute sortingExecute = new SortingExecute();
//        FullExplorationExecution fullExplorationExecution = new FullExplorationExecution();
//        BinarySearchExecution binarySearchExecution = new BinarySearchExecution();
//        GraphExecution graphExecution = new GraphExecution();
//        DfsAndBfsExecute dfsAndBfsExecute = new DfsAndBfsExecute();
//        GreedyExecute greedyExecute = new GreedyExecute();
//        DynamicProgrammingExecute dynamicProgrammingExecute = new DynamicProgrammingExecute();
//
//        MockExamExecute mockExamExecute = new MockExamExecute();
//
//        System.out.println("Hash");
//        hashExecute.execute();
//        System.out.println("------");
//        System.out.println("Stack And Queue");
//        stackQueueExecute.execute();
//        System.out.println("------");
//        System.out.println("Heap");
//        heapExecute.execute();
//        System.out.println("------");
//        System.out.println("Sorting");
//        sortingExecute.execute();
//        System.out.println("------");
//        System.out.println("Full Exploration");
//        fullExplorationExecution.execute();
//        System.out.println("------");
//        System.out.println("Binary Search");
//        binarySearchExecution.execute();
//        System.out.println("------");
//        System.out.println("Graph");
//        graphExecution.execute();
//        System.out.println("------");
//        System.out.println("Dfs and Bfs");
//        dfsAndBfsExecute.execute();
//        System.out.println("------");
//        System.out.println("greedy");
//        greedyExecute.execute();
//        System.out.println("------");
//        System.out.println("dynamic programming");
//        dynamicProgrammingExecute.execute();;
//
//
//        System.out.println("------");
//        System.out.println("mock exam");
//        mockExamExecute.execute();
//    }
}
