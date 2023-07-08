import leetcode.ListNode;
import leetcode.TreeNode;
import leetcode.bfs.SameTree;
import leetcode.bfs.SymmetricTree;
import leetcode.binarySearch.MissingNumber;
import leetcode.binarySearch.SearchInsertPosition;
import leetcode.binarySearch.Sqrt;
import leetcode.bruteForce.*;
import leetcode.dfs.BinaryTreeInorderTraversal;
import leetcode.dfs.FairDistributionOfCookies;
import leetcode.dfs.MaximumNumberOfAchievableTransferRequests;
import leetcode.dynamicProgramming.LongestSubArray;
import leetcode.dynamicProgramming.NumberOfGoodWaysToSplitAString;
import leetcode.greedy.LongestPalindrome;
import leetcode.greedy.MaximizeGreatnessOfAnArray;
import leetcode.greedy.TwoCityScheduling;
import leetcode.slidingWindow.MaximizeTheConfusionOfAnExam;
import leetcode.slidingWindow.MaximumNumberOfVowelsInASubstringOfGivenLength;
import leetcode.slidingWindow.MinimumSizeSubArraySum;
import leetcode.sorting.MergeSortedArray;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // leet-code
        // brute force
        TwoSum twoSum = new TwoSum();
        RomanToInteger romanToInteger = new RomanToInteger();
        SingleNumber2 singleNumber2 = new SingleNumber2();
        BuddyStrings buddyStrings = new BuddyStrings();
        DeleteTheMiddleNodeOfALinkedList deleteTheMiddleNodeOfALinkedList = new DeleteTheMiddleNodeOfALinkedList();

        System.out.println("brute force");
        System.out.println("Q.1 two sum");
        System.out.println("[2,7,11,15], 9 -> [0,1]: " + Arrays.toString(twoSum.twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println("[3,2,4], 6 -> [1,2]: " + Arrays.toString(twoSum.twoSum(new int[]{3,2,4}, 6)));
        System.out.println("[3,3], 6 -> [0,1]: " + Arrays.toString(twoSum.twoSum(new int[]{3,3}, 6)));
        System.out.println("------\n");
        System.out.println("Q.13 roman to integer");
        System.out.println("III -> 3: " + romanToInteger.romanToInt("III"));
        System.out.println("LVIII -> 58: " + romanToInteger.romanToInt("LVIII"));
        System.out.println("MCMXCIV -> 1994: " + romanToInteger.romanToInt("MCMXCIV"));
        System.out.println("------\n");
        System.out.println("Q.137 single number 2");
        System.out.println("[2,2,3,2] -> 3: " + singleNumber2.singleNumber(new int[]{2, 2, 3, 2}));
        System.out.println("[0,1,0,1,0,1,99] -> 99: " + singleNumber2.singleNumber(new int[]{0,1,0,1,0,1,99}));
        System.out.println("[1,2,3,1,2,4,3,1,2,3] -> 4: " + singleNumber2.singleNumber(new int[]{1,2,3,1,2,4,3,1,2,3}));
        System.out.println("[30000,500,100,30000,100,30000,100] -> 500: " + singleNumber2.singleNumber(new int[]{30000,500,100,30000,100,30000,100}));
        System.out.println("------\n");
        System.out.println("Q.859 buddy Strings");
        System.out.println("ab, ba -> true: " + buddyStrings.buddyStrings("ab", "ba"));
        System.out.println("ab, ab -> false: " + buddyStrings.buddyStrings("ab", "ab"));
        System.out.println("aa, aa -> true: " + buddyStrings.buddyStrings("aa", "aa"));
        System.out.println("ccccda, acccdc -> true: " + buddyStrings.buddyStrings("ccccda", "acccdc"));
        System.out.println("ccccda, accccd -> false: " + buddyStrings.buddyStrings("ccccda", "accccd"));
        System.out.println("ab, ca -> false: " + buddyStrings.buddyStrings("ab", "ca"));
        System.out.println("------\n");
        System.out.println("Q.2095 Delete the Middle Node of a Linked List");
        System.out.println("[1,3,4,7,1,2,6] -> [1,3,4,1,2,6]: " + deleteTheMiddleNodeOfALinkedList.deleteMiddle(ListNode.generateNode(new Integer[]{1,3,4,7,1,2,6})).getAllNode());

        System.out.println("----------\n\n");

        // sliding window
        MinimumSizeSubArraySum minimumSizeSubArraySum = new MinimumSizeSubArraySum();
        MaximumNumberOfVowelsInASubstringOfGivenLength maximumNumberOfVowelsInASubstringOfGivenLength = new MaximumNumberOfVowelsInASubstringOfGivenLength();
        MaximizeTheConfusionOfAnExam maximizeTheConfusionOfAnExam = new MaximizeTheConfusionOfAnExam();

        System.out.println("sliding window");
        System.out.println("Q.209 Minimum Size Subarray Sum");
        System.out.println("7, [2,3,1,2,4,3] -> 2: " + minimumSizeSubArraySum.minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("4, [1,4,4] -> 1: " + minimumSizeSubArraySum.minSubArrayLen(4, new int[]{1,4,4}));
        System.out.println("11, [1,1,1,1,1,1,1,1] -> 0: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,1,1,1,1,1,1,1}));
        System.out.println("11, [1,2,3,4,5] -> 3: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,2,3,4,5}));
        System.out.println("11, [1,2,3,4,5] -> 3: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,2,3,4,5}));
        System.out.println("------\n");
        System.out.println("Q.1456 Maximum Number of Vowels in a Substring of Given Length");
        System.out.println("abciiidef, 3 -> 3: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("abciiidef", 3));
        System.out.println("aeiou, 2 -> 2: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("aeiou", 2));
        System.out.println("leetcode, 3 -> 2: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("leetcode", 3));
        System.out.println("------\n");
        System.out.println("Q.2024 Maximize the Confusion of an Exam");
        System.out.println("TTFF, 2 -> 4: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TTFF", 2));
        System.out.println("TFFT, 1 -> 3: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TFFT", 1));
        System.out.println("TTFTTFTT, 1 -> 5: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TTFTTFTT", 1));

        System.out.println("----------\n\n");

        // sorting
        MergeSortedArray mergeSortedArray = new MergeSortedArray();

        System.out.println("sorting");
        System.out.println("Q.88 Merge Sorted Array");
        System.out.println("[1,2,3,0,0,0], 3, [2,5,6], 3 -> [1,2,2,3,5,6]: ");
        mergeSortedArray.merge(new int[]{1,2,3,0,0,0}, 3, new int[]{2,5,6}, 3);
        System.out.println("[1], 1, [], 0 -> [1]: ");
        mergeSortedArray.merge(new int[]{1}, 1, new int[]{}, 0);
        System.out.println("[0], 0, [1], 1 -> [1]: ");
        mergeSortedArray.merge(new int[]{0}, 0, new int[]{1}, 1);
        System.out.println("[4,5,6,0,0,0], 3, [1,2,3], 3 -> [1,2,3,4,5,6]: ");
        mergeSortedArray.merge(new int[]{4,5,6,0,0,0}, 3, new int[]{1,2,3}, 3);

        System.out.println("----------\n\n");

        // binary search
        SearchInsertPosition searchInsertPosition = new SearchInsertPosition();
        Sqrt sqrt = new Sqrt();
        MissingNumber missingNumber = new MissingNumber();

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
        System.out.println("------\n");
        System.out.println("Q.69 sqrt(x)");
        System.out.println("4 -> 2: " + sqrt.mySqrt(4));
        System.out.println("8 -> 2: " + sqrt.mySqrt(8));
        System.out.println("2147395599 -> 2: " + sqrt.mySqrt(2147395599));
        System.out.println("------\n");
        System.out.println("Q.268 Missing Number");
        System.out.println("[3,0,1] -> 2: " + missingNumber.missingNumber(new int[]{3,0,1}));
        System.out.println("[0,1] -> 2: " + missingNumber.missingNumber(new int[]{0,1}));
        System.out.println("[9,6,4,2,3,5,7,0,1] -> 8: " + missingNumber.missingNumber(new int[]{9,6,4,2,3,5,7,0,1}));
        System.out.println("[0,2,3] -> 1: " + missingNumber.missingNumber(new int[]{0,2,3}));
        System.out.println("[1,2,3] -> 0: " + missingNumber.missingNumber(new int[]{1,2,3}));
        System.out.println("[0,1,2,3,5,6,7] -> 4: " + missingNumber.missingNumber(new int[]{0,1,2,3,5,6,7}));
        System.out.println("[1,2] -> 0: " + missingNumber.missingNumber(new int[]{1,2}));
        System.out.println("[9,3,7,1,5,4,8,0,2] -> 6: " + missingNumber.missingNumber(new int[]{9,3,7,1,5,4,8,0,2}));

        System.out.println("----------\n\n");

        // dfs
        BinaryTreeInorderTraversal binaryTreeInorderTraversal = new BinaryTreeInorderTraversal();
        MaximumNumberOfAchievableTransferRequests maximumNumberOfAchievableTransferRequests = new MaximumNumberOfAchievableTransferRequests();
        FairDistributionOfCookies fairDistributionOfCookies = new FairDistributionOfCookies();

        System.out.println("dfs");
        System.out.println("Q.94 Binary Tree Inorder Traversal");
        System.out.println("[1,null,2,3] -> [1,3,2]: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{1,null,2,3})));
        System.out.println("[] -> []: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{})));
        System.out.println("[1] -> [1]: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{1})));
        System.out.println("------\n");
        System.out.println("Q.1601 Maximum Number of Achievable Transfer Requests");
        System.out.println(
                "5, [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]] -> 5: " +
                        maximumNumberOfAchievableTransferRequests.maximumRequests(5, new int[][]{{0,1},{1,0},{0,1},{1,2},{2,0},{3,4}})
        );
        System.out.println(
                "4, [[0,3],[3,1],[1,2],[2,0]] -> 4: " +
                        maximumNumberOfAchievableTransferRequests.maximumRequests(4, new int[][]{{0,3},{3,1},{1,2},{2,0}})
        );
        System.out.println("------\n");
        System.out.println("Q.2305 Fair Distribution of Cookies");
        System.out.println("[8,15,10,20,8], 2 -> 31: " + fairDistributionOfCookies.distributeCookies(new int[]{8,15,10,20,8}, 2));
        System.out.println("[6,1,3,2,2,4,1,2], 3 -> 7: " + fairDistributionOfCookies.distributeCookies(new int[]{6,1,3,2,2,4,1,2}, 3));
        System.out.println("[941,797,1475,638,191,712], 3 -> 1653: " + fairDistributionOfCookies.distributeCookies(new int[]{941,797,1475,638,191,712}, 3)); // 이해가 안되네..

        System.out.println("----------\n\n");

        // bfs
        SameTree sameTree = new SameTree();
        SymmetricTree symmetricTree = new SymmetricTree();

        System.out.println("bfs");
        System.out.println("Q.100 Same Tree");
        System.out.println("[1,2,3], [1,2,3] -> true: " + sameTree.isSameTree(TreeNode.generateNode(new Integer[]{1,2,3}), TreeNode.generateNode(new Integer[]{1,2,3})));
        System.out.println("[1,2], [1,null,2] -> false: " + sameTree.isSameTree(TreeNode.generateNode(new Integer[]{1,2}), TreeNode.generateNode(new Integer[]{1,null,2})));
        System.out.println("------\n");
        System.out.println("Q.101 Symmetric Tree");
        System.out.println("[1,2,2,3,4,4,3] -> true: " + symmetricTree.isSymmetric(TreeNode.generateNode(new Integer[]{1,2,2,3,4,4,3})));
        System.out.println("[1,2,2,3,4,4,3] -> true: " + symmetricTree.isSymmetric(TreeNode.generateNode(new Integer[]{1,2,2,null,3,null,3})));

        System.out.println("----------\n\n");

        // dynamic programming
        LongestSubArray longestSubArray = new LongestSubArray();
        NumberOfGoodWaysToSplitAString numberOfGoodWaysToSplitAString = new NumberOfGoodWaysToSplitAString();

        System.out.println("dynamic programming");
        System.out.println("Q.1493 Longest Subarray of 1's After Deleting One Element");
        System.out.println("[1,1,0,1] -> 3: " + longestSubArray.longestSubarray(new int[]{1,1,0,1}));
        System.out.println("[0,1,1,1,0,1,1,0,1] -> 5: " + longestSubArray.longestSubarray(new int[]{0,1,1,1,0,1,1,0,1}));
        System.out.println("[1,1,1] -> 2: " + longestSubArray.longestSubarray(new int[]{1,1,1}));
        System.out.println("------\n");
        System.out.println("Q.1525. Number of Good Ways to Split a String");
        System.out.println("aacaba -> 2: " + numberOfGoodWaysToSplitAString.numSplits("aacaba"));
        System.out.println("abcd -> 1: " + numberOfGoodWaysToSplitAString.numSplits("abcd"));
        System.out.println("aabbaa -> 1: " + numberOfGoodWaysToSplitAString.numSplits("aabbaa"));
        System.out.println("aaaaa -> 4: " + numberOfGoodWaysToSplitAString.numSplits("aaaaa"));
        System.out.println("abdacaddbddab -> 0: " + numberOfGoodWaysToSplitAString.numSplits("abdacaddbddab"));

        System.out.println("----------\n\n");

        // greedy
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        TwoCityScheduling twoCityScheduling = new TwoCityScheduling();
        MaximizeGreatnessOfAnArray maximizeGreatnessOfAnArray = new MaximizeGreatnessOfAnArray();

        System.out.println("greedy");
        System.out.println("Q.409 Longest Palindrome");
        System.out.println("abccccdd -> 7: " + longestPalindrome.longestPalindrome("abccccdd"));
        System.out.println("a -> 1: " + longestPalindrome.longestPalindrome("a"));
        System.out.println("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth -> 983: " + longestPalindrome.longestPalindrome("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
        System.out.println("------\n");
        System.out.println("Q.1029 Two City Scheduling");
        System.out.println("[[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]] -> 1859: " + twoCityScheduling.twoCitySchedCost(new int[][]{{259,770},{448,54},{926,667},{184,139},{840,118},{577,469}}));
        System.out.println("[[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]] -> 3086: " + twoCityScheduling.twoCitySchedCost(new int[][]{{515,563},{451,713},{537,709},{343,819},{855,779},{457,60},{650,359},{631,42}}));
        System.out.println("------\n");
        System.out.println("Q.2592 Maximize Greatness of an Array");
        System.out.println("[1,3,5,2,1,3,1] -> 4: " + maximizeGreatnessOfAnArray.maximizeGreatness(new int[]{1,3,5,2,1,3,1}));
        System.out.println("[1,2,3,4] -> 3: " + maximizeGreatnessOfAnArray.maximizeGreatness(new int[]{1,2,3,4}));
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
