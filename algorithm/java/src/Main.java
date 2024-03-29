import leetcode.ListNode;
import leetcode.TreeNode;
import leetcode.bfs.*;
import leetcode.binarySearch.*;
import leetcode.bruteForce.*;
import leetcode.dfs.*;
import leetcode.dynamicProgramming.*;
import leetcode.greedy.*;
import leetcode.slidingWindow.*;
import leetcode.sorting.*;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        // leet-code
        // brute force
        TwoSum twoSum = new TwoSum();
        RomanToInteger romanToInteger = new RomanToInteger();
        Subsets2 subsets2 = new Subsets2();
        SingleNumber2 singleNumber2 = new SingleNumber2();
        PowerOfFour powerOfFour = new PowerOfFour();
        FindTheDifference findTheDifference = new FindTheDifference();
        AddTwoNumbersII addTwoNumbersII = new AddTwoNumbersII();
        KthSymbolInGrammar kthSymbolInGrammar = new KthSymbolInGrammar();
        BuddyStrings buddyStrings = new BuddyStrings();
        DecodedStringAtIndex decodedStringAtIndex = new DecodedStringAtIndex();
        CheckIfTwoStringArraysAreEquivalent checkIfTwoStringArraysAreEquivalent = new CheckIfTwoStringArraysAreEquivalent();
        CountOfMatchesInTournament countOfMatchesInTournament = new CountOfMatchesInTournament();
        DeleteTheMiddleNodeOfALinkedList deleteTheMiddleNodeOfALinkedList = new DeleteTheMiddleNodeOfALinkedList();
        Largest3SameDigitNumberInString largest3SameDigitNumberInString = new Largest3SameDigitNumberInString();

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
        System.out.println("Q.90 Subsets 2");
        System.out.println("[1,2,2] -> [[],[1],[1,2],[1,2,2],[2],[2,2]]: " + subsets2.subsetsWithDup(new int[]{1,2,2}));
        System.out.println("[1,2,3] -> [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]: " + subsets2.subsetsWithDup(new int[]{1,2,3}));
        System.out.println("[4,4,4,1,4] -> [[],[4],[4,4],[4,4,4],[4,4,4,1],[4,4,4,1,4],[4,4,1,4],[4,1,4],[4,4,4,4],[4,4,1],[4,1],[1,4],[1]]: " + subsets2.subsetsWithDup(new int[]{4,4,4,1,4}));
        System.out.println("[0] -> [[],[0]]: " + subsets2.subsetsWithDup(new int[]{0}));
        System.out.println("------\n");
        System.out.println("Q.137 single number 2");
        System.out.println("[2,2,3,2] -> 3: " + singleNumber2.singleNumber(new int[]{2, 2, 3, 2}));
        System.out.println("[0,1,0,1,0,1,99] -> 99: " + singleNumber2.singleNumber(new int[]{0,1,0,1,0,1,99}));
        System.out.println("[1,2,3,1,2,4,3,1,2,3] -> 4: " + singleNumber2.singleNumber(new int[]{1,2,3,1,2,4,3,1,2,3}));
        System.out.println("[30000,500,100,30000,100,30000,100] -> 500: " + singleNumber2.singleNumber(new int[]{30000,500,100,30000,100,30000,100}));
        System.out.println("------\n");
        System.out.println("Q.342. Power of Four");
        System.out.println("16 -> true: " + powerOfFour.isPowerOfFour(16));
        System.out.println("5 -> false: " + powerOfFour.isPowerOfFour(5));
        System.out.println("1 -> true: " + powerOfFour.isPowerOfFour(1));
        System.out.println("------\n");
        System.out.println("Q.389 Find the Difference");
        System.out.println("abcd, abcde: e -> " + findTheDifference.findTheDifference("abcd", "abcde"));
        System.out.println(", y: y -> " + findTheDifference.findTheDifference("", "y"));
        System.out.println("------\n");
        System.out.println("Q.445 Add Two Numbers II");
        System.out.println("[7,2,4,3], [5,6,4] -> [7,8,0,7]: " + addTwoNumbersII.addTwoNumbers(ListNode.generateNode(new Integer[]{7,2,4,3}), ListNode.generateNode(new Integer[]{5,6,4})).getAllNode());
        System.out.println("[2,4,3], [5,6,4] -> [8,0,7]: " + addTwoNumbersII.addTwoNumbers(ListNode.generateNode(new Integer[]{2,4,3}), ListNode.generateNode(new Integer[]{5,6,4})).getAllNode());
        System.out.println("[0], [0] -> [0]: " + addTwoNumbersII.addTwoNumbers(ListNode.generateNode(new Integer[]{0}), ListNode.generateNode(new Integer[]{0})).getAllNode());
        System.out.println("------\n");
        System.out.println("Q.779. K-th Symbol in Grammar");
        System.out.println("1, 1 -> 0: " + kthSymbolInGrammar.kthGrammar(1, 1));
        System.out.println("2, 1 -> 0: " + kthSymbolInGrammar.kthGrammar(2, 1));
        System.out.println("2, 2 -> 1: " + kthSymbolInGrammar.kthGrammar(2, 2));
        System.out.println("4, 7 -> 0: " + kthSymbolInGrammar.kthGrammar(4, 7));
        System.out.println("3, 3 -> 1: " + kthSymbolInGrammar.kthGrammar(3, 3));
        System.out.println("------\n");
        System.out.println("Q.859 buddy Strings");
        System.out.println("ab, ba -> true: " + buddyStrings.buddyStrings("ab", "ba"));
        System.out.println("ab, ab -> false: " + buddyStrings.buddyStrings("ab", "ab"));
        System.out.println("aa, aa -> true: " + buddyStrings.buddyStrings("aa", "aa"));
        System.out.println("ccccda, acccdc -> true: " + buddyStrings.buddyStrings("ccccda", "acccdc"));
        System.out.println("ccccda, accccd -> false: " + buddyStrings.buddyStrings("ccccda", "accccd"));
        System.out.println("ab, ca -> false: " + buddyStrings.buddyStrings("ab", "ca"));
        System.out.println("------\n");
        System.out.println("Q.880. Decoded String at Index");
        System.out.println("leet2code3, 10 -> o :" +decodedStringAtIndex.decodeAtIndex("leet2code3", 10));
        System.out.println("ha22, 5 -> h :" +decodedStringAtIndex.decodeAtIndex("ha22", 5));
        System.out.println("a2345678999999999999999, 1 -> a :" +decodedStringAtIndex.decodeAtIndex("a2345678999999999999999", 1));
        System.out.println("------\n");
        System.out.println("Q.1662. Check If Two String Arrays are Equivalent");
        System.out.println("[ab, c], [a, bc] -> true: " + checkIfTwoStringArraysAreEquivalent.arrayStringsAreEqual(new String[]{"ab", "c"}, new String[]{"a", "bc"}));
        System.out.println("[a, cb], [ab, c] -> false: " + checkIfTwoStringArraysAreEquivalent.arrayStringsAreEqual(new String[]{"a", "cb"}, new String[]{"ab", "c"}));
        System.out.println("[abc, d, defg], [abcddefg] -> true: " + checkIfTwoStringArraysAreEquivalent.arrayStringsAreEqual(new String[]{"abc", "d", "defg"}, new String[]{"abcddefg"}));
        System.out.println("------\n");
        System.out.println("Q.1688. Count of Matches in Tournament");
        System.out.println("7 -> 6: " + countOfMatchesInTournament.numberOfMatches(7));
        System.out.println("14 -> 13: " + countOfMatchesInTournament.numberOfMatches(14));
        System.out.println("------\n");
        System.out.println("Q.2095 Delete the Middle Node of a Linked List");
        System.out.println("[1,3,4,7,1,2,6] -> [1,3,4,1,2,6]: " + deleteTheMiddleNodeOfALinkedList.deleteMiddle(ListNode.generateNode(new Integer[]{1,3,4,7,1,2,6})).getAllNode());
        System.out.println("------\n");
        System.out.println("Q.2264. Largest 3-Same-Digit Number in String");
        System.out.println("6777133339 -> 777: " + largest3SameDigitNumberInString.largestGoodInteger("6777133339"));
        System.out.println("2300019 -> 000: " + largest3SameDigitNumberInString.largestGoodInteger("2300019"));
        System.out.println("42352338 -> : " + largest3SameDigitNumberInString.largestGoodInteger("42352338"));

        System.out.println("----------\n\n");

        // sliding window
        PartitionList partitionList = new PartitionList();
        MinimumSizeSubArraySum minimumSizeSubArraySum = new MinimumSizeSubArraySum();
        SlidingWindowMaximum slidingWindowMaximum = new SlidingWindowMaximum();
        IsSubsequence isSubsequence = new IsSubsequence();
        ReverseWordsInAStringIII reverseWordsInAStringIII = new ReverseWordsInAStringIII();
        AsteroidCollision asteroidCollision = new AsteroidCollision();
        BackspaceStringCompare backspaceStringCompare = new BackspaceStringCompare();
        MonotonicArray monotonicArray = new MonotonicArray();
        MaximumNumberOfVowelsInASubstringOfGivenLength maximumNumberOfVowelsInASubstringOfGivenLength = new MaximumNumberOfVowelsInASubstringOfGivenLength();
        MinimumOperationsToReduceXToZero minimumOperationsToReduceXToZero = new MinimumOperationsToReduceXToZero();
        MaximizeTheConfusionOfAnExam maximizeTheConfusionOfAnExam = new MaximizeTheConfusionOfAnExam();

        System.out.println("sliding window");
        System.out.println("------\n");
        System.out.println("Q.86 Partition List");
//        System.out.println("[1,4,3,2,5,2], 3 -> [1,2,2,4,3,5]: " + partitionList.partition(ListNode.generateNode(new Integer[]{1,4,3,2,5,2}), 3).getAllNode());
        System.out.println("[2,1], 2 -> [1,2]: " + partitionList.partition(ListNode.generateNode(new Integer[]{2,1}), 2).getAllNode());
        System.out.println("Q.209 Minimum Size Subarray Sum");
        System.out.println("7, [2,3,1,2,4,3] -> 2: " + minimumSizeSubArraySum.minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println("4, [1,4,4] -> 1: " + minimumSizeSubArraySum.minSubArrayLen(4, new int[]{1,4,4}));
        System.out.println("11, [1,1,1,1,1,1,1,1] -> 0: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,1,1,1,1,1,1,1}));
        System.out.println("11, [1,2,3,4,5] -> 3: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,2,3,4,5}));
        System.out.println("11, [1,2,3,4,5] -> 3: " + minimumSizeSubArraySum.minSubArrayLen(11, new int[]{1,2,3,4,5}));
        System.out.println("------\n");
        System.out.println("Q.239 Sliding Window Maximum");
        System.out.println("[1,3,-1,-3,5,3,6,7], 3 -> [3,3,5,5,6,7]: " + Arrays.toString(slidingWindowMaximum.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        System.out.println("[1], 1 -> [1]: " + Arrays.toString(slidingWindowMaximum.maxSlidingWindow(new int[]{1}, 1)));
        System.out.println("------\n");
        System.out.println("Q.392. Is Subsequence");
        System.out.println("abc, ahbgdc -> true: " + isSubsequence.isSubsequence("abc", "ahbgdc"));
        System.out.println("axc, ahbgdc -> false: " + isSubsequence.isSubsequence("axc", "ahbgdc"));
        System.out.println("------\n");
        System.out.println("Q.557. Reverse Words in a String III");
        System.out.println("Let's take LeetCode contest -> s'teL ekat edoCteeL tsetnoc: " + reverseWordsInAStringIII.reverseWords("Let's take LeetCode contest"));
        System.out.println("God Ding -> doG gniD: " + reverseWordsInAStringIII.reverseWords("God Ding"));
        System.out.println("------\n");
        System.out.println("Q.735 Asteroid Collision");
        System.out.println("[5,10,-5] -> [5,10]: " + Arrays.toString(asteroidCollision.asteroidCollision(new int[]{5, 10, -5})));
        System.out.println("[8,-8] -> []: " + Arrays.toString(asteroidCollision.asteroidCollision(new int[]{8,-8})));
        System.out.println("[10,2,-5] -> [10]: " + Arrays.toString(asteroidCollision.asteroidCollision(new int[]{10,2,-5})));
        System.out.println("------\n");
        System.out.println("Q.844. Backspace String Compare");
//        System.out.println("ab#c, ad#c -> true: " + backspaceStringCompare.backspaceCompare("ab#c", "ad#c"));
//        System.out.println("ab##, c#d# -> true: " + backspaceStringCompare.backspaceCompare("ab##", "c#d#"));
//        System.out.println("a#c, b -> false: " + backspaceStringCompare.backspaceCompare("a#c", "b"));
        System.out.println("y#fo##f, y#f#o##f -> true: " + backspaceStringCompare.backspaceCompare("y#fo##f", "y#f#o##f"));
        System.out.println("------\n");
        System.out.println("Q.896. Monotonic Array");
        System.out.println("[1,2,2,3] -> true: " + monotonicArray.isMonotonic(new int[]{1,2,2,3}));
        System.out.println("[6,5,4,4] -> true: " + monotonicArray.isMonotonic(new int[]{6,5,4,4}));
        System.out.println("[1,3,2] -> false: " + monotonicArray.isMonotonic(new int[]{1,3,2}));
        System.out.println("------\n");
        System.out.println("Q.1456 Maximum Number of Vowels in a Substring of Given Length");
        System.out.println("abciiidef, 3 -> 3: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("abciiidef", 3));
        System.out.println("aeiou, 2 -> 2: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("aeiou", 2));
        System.out.println("leetcode, 3 -> 2: " + maximumNumberOfVowelsInASubstringOfGivenLength.maxVowels("leetcode", 3));
        System.out.println("------\n");
        System.out.println("Q.1658. Minimum Operations to Reduce X to Zero");
//        System.out.println("[1,1,4,2,3], 5 -> 2: " + minimumOperationsToReduceXToZero.minOperations(new int[]{1,1,4,2,3}, 5));
//        System.out.println("[5,6,7,8,9], 4 -> -1: " + minimumOperationsToReduceXToZero.minOperations(new int[]{5,6,7,8,9}, 4));
//        System.out.println("[3,2,20,1,1,3], 10 -> 5: " + minimumOperationsToReduceXToZero.minOperations(new int[]{3,2,20,1,1,3}, 10));
//        System.out.println("[1,1,3,2,5], 5 -> 1: " + minimumOperationsToReduceXToZero.minOperations(new int[]{1,1,3,2,5}, 5));
        System.out.println("[6016,5483,541,4325,8149,3515,7865,2209,9623,9763,4052,6540,2123,2074,765,7520,4941,5290,5868,6150,6006,6077,2856,7826,9119], 31841 -> 6: " + minimumOperationsToReduceXToZero.minOperations(new int[]{6016,5483,541,4325,8149,3515,7865,2209,9623,9763,4052,6540,2123,2074,765,7520,4941,5290,5868,6150,6006,6077,2856,7826,9119}, 31841));
        System.out.println("------\n");
        System.out.println("Q.2024 Maximize the Confusion of an Exam");
        System.out.println("TTFF, 2 -> 4: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TTFF", 2));
        System.out.println("TFFT, 1 -> 3: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TFFT", 1));
        System.out.println("TTFTTFTT, 1 -> 5: " + maximizeTheConfusionOfAnExam.maxConsecutiveAnswers("TTFTTFTT", 1));

        System.out.println("----------\n\n");

        // sorting
        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        ReverseLinkedListII reverseLinkedListII = new ReverseLinkedListII();
        MajorityElementII majorityElementII = new MajorityElementII();
        SortArrayByParity sortArrayByParity = new SortArrayByParity();
        TheKWeakestRowsInAMatrix theKWeakestRowsInAMatrix = new TheKWeakestRowsInAMatrix();
        SortIntegersByTheNumberOf1Bits sortIntegersByTheNumberOf1Bits = new SortIntegersByTheNumberOf1Bits();
        MaximumNumberOfCoinsYouCanGet maximumNumberOfCoinsYouCanGet = new MaximumNumberOfCoinsYouCanGet();
        MaximumElementAfterDecreasingAndRearranging maximumElementAfterDecreasingAndRearranging = new MaximumElementAfterDecreasingAndRearranging();
        MinimizeMaximumPairSumInArray minimizeMaximumPairSumInArray = new MinimizeMaximumPairSumInArray();
        ReductionOperationsToMakeTheArrayElementsEqual reductionOperationsToMakeTheArrayElementsEqual = new ReductionOperationsToMakeTheArrayElementsEqual();
        SortVowelsInAString sortVowelsInAString = new SortVowelsInAString();

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
        System.out.println("------\n");
        System.out.println("Q.92. Reverse Linked List I");
        System.out.println("[1,2,3,4,5], 2, 4 -> [1,4,3,2,5]: " + reverseLinkedListII.reverseBetween(ListNode.generateNode(new Integer[]{1,2,3,4,5}), 2, 4).getAllNode());
        System.out.println("------\n");
        System.out.println("Q.229. Majority Element II");
        System.out.println("[3,2,3] -> [3]: " + majorityElementII.majorityElement(new int[]{3,2,3}));
        System.out.println("[1] -> [1]: " + majorityElementII.majorityElement(new int[]{1}));
        System.out.println("[1,2] -> [1,2]: " + majorityElementII.majorityElement(new int[]{1,2}));
        System.out.println("[2,2] -> [2]: " + majorityElementII.majorityElement(new int[]{2,2}));
        System.out.println("------\n");
        System.out.println("Q.905. Sort Array By Parity");
        System.out.println("Regardless of the order, as long as even numbers are on the left and odd numbers are on the right.");
        System.out.println("[3,1,2,4] -> [2,4,3,1]: " + Arrays.toString(sortArrayByParity.sortArrayByParity(new int[]{3, 1, 2, 4})));
        System.out.println("[0] -> [0]: " + Arrays.toString(sortArrayByParity.sortArrayByParity(new int[]{0})));
        System.out.println("------\n");
        System.out.println("Q.1337 The K Weakest Rows in a Matrix");
        System.out.println("[[1,1,0,0,0,[1,1,1,1,0],[1,0,0,0,0],[1,1,0,0,0],[1,1,1,1,1]], 3 -> [2,0,3]: " + Arrays.toString(theKWeakestRowsInAMatrix.kWeakestRows(new int[][]{{1, 1, 0, 0, 0}, {1, 1, 1, 1, 0}, {1, 0, 0, 0, 0}, {1, 1, 0, 0, 0}, {1, 1, 1, 1, 1}}, 3)));
        System.out.println("[[1,0,0,0],[1,1,1,1],[1,0,0,0],[1,0,0,0]], 2 -> [0,2]: " + Arrays.toString(theKWeakestRowsInAMatrix.kWeakestRows(new int[][]{{1,0,0,0},{1,1,1,1},{1,0,0,0},{1,0,0,0}}, 2)));
        System.out.println("------\n");
        System.out.println("Q.1356. Sort Integers by The Number of 1 Bits");
        System.out.println("[0,1,2,3,4,5,6,7,8] -> [0,1,2,4,8,3,5,6,7]: " + Arrays.toString(sortIntegersByTheNumberOf1Bits.sortByBits(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})));
        System.out.println("[1024,512,256,128,64,32,16,8,4,2,1] -> [1,2,4,8,16,32,64,128,256,512,1024]: " + Arrays.toString(sortIntegersByTheNumberOf1Bits.sortByBits(new int[]{1024,512,256,128,64,32,16,8,4,2,1})));
        System.out.println("------\n");
        System.out.println("Q.1561. Maximum Number of Coins You Can Get");
        System.out.println("[2,4,1,2,7,8] -> 9: " + maximumNumberOfCoinsYouCanGet.maxCoins(new int[]{2,4,1,2,7,8}));
        System.out.println("[2,4,5] -> 4: " + maximumNumberOfCoinsYouCanGet.maxCoins(new int[]{2,4,5}));
        System.out.println("[9,8,7,6,5,1,2,3,4] -> 18: " + maximumNumberOfCoinsYouCanGet.maxCoins(new int[]{9,8,7,6,5,1,2,3,4}));
        System.out.println("------\n");
        System.out.println("Q.1846. Maximum Element After Decreasing and Rearranging");
        System.out.println("[2,2,1,2,1] -> 2: " + maximumElementAfterDecreasingAndRearranging.maximumElementAfterDecrementingAndRearranging(new int[]{2,2,1,2,1}));
        System.out.println("[100,1,1000] -> 3: " + maximumElementAfterDecreasingAndRearranging.maximumElementAfterDecrementingAndRearranging(new int[]{100,1,1000}));
        System.out.println("[1,2,3,4,5] -> 5: " + maximumElementAfterDecreasingAndRearranging.maximumElementAfterDecrementingAndRearranging(new int[]{1,2,3,4,5}));
        System.out.println("------\n");
        System.out.println("Q.1877. Minimize Maximum Pair Sum in Array");
        System.out.println("[3,5,2,3] -> 7: " + minimizeMaximumPairSumInArray.minPairSum(new int[]{3,5,2,3}));
        System.out.println("[3,5,4,2,4,6] -> 8: " + minimizeMaximumPairSumInArray.minPairSum(new int[]{3,5,4,2,4,6}));
        System.out.println("------\n");
        System.out.println("Q.1887. Reduction Operations to Make the Array Elements Equal");
        System.out.println("[5,1,3] -> 3: " + reductionOperationsToMakeTheArrayElementsEqual.reductionOperations(new int[]{5,1,3}));
        System.out.println("[1,1,1] -> 0: " + reductionOperationsToMakeTheArrayElementsEqual.reductionOperations(new int[]{1,1,1}));
        System.out.println("[1,1,2,2,3] -> 4: " + reductionOperationsToMakeTheArrayElementsEqual.reductionOperations(new int[]{1,1,2,2,3}));
        System.out.println("------\n");
        System.out.println("Q.2785. Sort Vowels in a String");
        System.out.println("lEetcOde -> lEOtcede: " + sortVowelsInAString.sortVowels("lEetcOde"));
        System.out.println("lYmpH -> lYmpH: " + sortVowelsInAString.sortVowels("lYmpH"));

        System.out.println("----------\n\n");

        // binary search
        SearchInRotatedSortedArray searchInRotatedSortedArray = new SearchInRotatedSortedArray();
        FindFirstAndLastPositionOfElementInSortedArray findFirstAndLastPositionOfElementInSortedArray = new FindFirstAndLastPositionOfElementInSortedArray();
        SearchInsertPosition searchInsertPosition = new SearchInsertPosition();
        Sqrt sqrt = new Sqrt();
        SearchA2DMatrix searchA2DMatrix = new SearchA2DMatrix();
        MissingNumber missingNumber = new MissingNumber();
        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        GuessNumberHigherOrLower guessNumberHigherOrLower = new GuessNumberHigherOrLower();
        Find132Pattern find132Pattern = new Find132Pattern();
        PeakIndexInAMountainArray peakIndexInAMountainArray = new PeakIndexInAMountainArray();
        MinimumSpeedToArriveOnTime minimumSpeedToArriveOnTime = new MinimumSpeedToArriveOnTime();

        System.out.println("binary search");
        System.out.println("Q.33 Search in Rotated Sorted Array");
        System.out.println("[4,5,6,7,0,1,2], 0 -> 4: " + searchInRotatedSortedArray.search(new int[]{4,5,6,7,0,1,2}, 0));
        System.out.println("[4,5,6,7,0,1,2], 3 -> -1: " + searchInRotatedSortedArray.search(new int[]{4,5,6,7,0,1,2}, 3));
        System.out.println("[1], 0 -> -1: " + searchInRotatedSortedArray.search(new int[]{1}, 0));
        System.out.println("[3,1], 1 -> 1: " + searchInRotatedSortedArray.search(new int[]{3,1}, 1));
        System.out.println("[3,0,1], 1 -> 2: " + searchInRotatedSortedArray.search(new int[]{3,0,1}, 1));
        System.out.println("[1,3,5], 5 -> 2: " + searchInRotatedSortedArray.search(new int[]{1,3,5}, 5));
        System.out.println("[5,1,3], 1 -> 1: " + searchInRotatedSortedArray.search(new int[]{5,1,3}, 1));
        System.out.println("[5,1,2,3,4], 1 -> 1: " + searchInRotatedSortedArray.search(new int[]{5,1,2,3,4}, 1));
        System.out.println("------\n");
        System.out.println("34. Find First and Last Position of Element in Sorted Array");
        System.out.println("[5,7,7,8,8,10], 8 -> [3,4]: " + Arrays.toString(findFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{5,7,7,8,8,10}, 8)));
        System.out.println("[5,7,7,8,8,10], 6 -> [-1,-1]: " + Arrays.toString(findFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{5,7,7,8,8,10}, 6)));
        System.out.println("[], 0 -> [-1,-1]: " + Arrays.toString(findFirstAndLastPositionOfElementInSortedArray.searchRange(new int[]{}, 0)));
        System.out.println("------\n");
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
        System.out.println("Q.74 Search a 2D Matrix");
//        System.out.println("[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 3 -> true: " + searchA2DMatrix.searchMatrix(new int[][]{{1,3,5,7}, {10,11,16,20}, {23,30,34,60}}, 3));
//        System.out.println("[[1,3,5,7],[10,11,16,20],[23,30,34,60]], 13 -> false: " + searchA2DMatrix.searchMatrix(new int[][]{{1,3,5,7}, {10,11,16,20}, {23,30,34,60}}, 13));
//        System.out.println("[[1]], 1 -> true: " + searchA2DMatrix.searchMatrix(new int[][]{{1}}, 1));
//        System.out.println("[[1,1]], 1 -> true: " + searchA2DMatrix.searchMatrix(new int[][]{{1,1}}, 2));
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
        System.out.println("------\n");
        System.out.println("Q.287. Find the Duplicate Number");
        System.out.println("[1,3,4,2,2] -> 2: " + findTheDuplicateNumber.findDuplicate(new int[]{1,3,4,2,2}));
        System.out.println("[3,1,3,4,2] -> 3: " + findTheDuplicateNumber.findDuplicate(new int[]{3,1,3,4,2}));
        System.out.println("------\n");
        System.out.println("Q.374 Guess Number Higher or Lower");
        System.out.println("10, 6 -> 6: " + guessNumberHigherOrLower.start(10, 6));
        System.out.println("1, 1 -> 1: " + guessNumberHigherOrLower.start(1, 1));
        System.out.println("2, 1 -> 1: " + guessNumberHigherOrLower.start(2, 1));
        System.out.println("2126753390, 1702766719 -> 1702766719: " + guessNumberHigherOrLower.start(2126753390, 1702766719));
        System.out.println("------\n");
        System.out.println("Q.456. 132 Pattern");
//        System.out.println("[1,2,3,4] -> false: " + find132Pattern.find132pattern(new int[]{1,2,3,4}));
//        System.out.println("[3,1,4,2] -> true: " + find132Pattern.find132pattern(new int[]{3,1,4,2}));
//        System.out.println("[-1,3,2,0] -> true: " + find132Pattern.find132pattern(new int[]{-1,3,2,0}));
        System.out.println("------\n");
        System.out.println("Q.852 Peak Index in a Mountain Array");
        System.out.println("[0,1,0] -> 1: " + peakIndexInAMountainArray.peakIndexInMountainArray(new int[]{0,1,0}));
        System.out.println("[0,2,1,0] -> 1: " + peakIndexInAMountainArray.peakIndexInMountainArray(new int[]{0,2,1,0}));
        System.out.println("[0,10,5,2] -> 1: " + peakIndexInAMountainArray.peakIndexInMountainArray(new int[]{0,10,5,2}));
        System.out.println("[3,5,3,2,0] -> 1: " + peakIndexInAMountainArray.peakIndexInMountainArray(new int[]{3,5,3,2,0}));
        System.out.println("[3,4,5,1] -> 2: " + peakIndexInAMountainArray.peakIndexInMountainArray(new int[]{3,4,5,1}));
        System.out.println("------\n");
        System.out.println("Q.1870. Minimum Speed to Arrive on Time");
        System.out.println("[1,3,2], 6 -> 1: " + minimumSpeedToArriveOnTime.minSpeedOnTime(new int[]{1,3,2}, 6));
        System.out.println("[1,3,2], 2.7 -> 3: " + minimumSpeedToArriveOnTime.minSpeedOnTime(new int[]{1,3,2}, 2.7));
        System.out.println("[1,3,2], 1.9 -> -1: " + minimumSpeedToArriveOnTime.minSpeedOnTime(new int[]{1,3,2}, 1.9));

        System.out.println("----------\n\n");

        // dfs
        UniquePathsII uniquePathsII = new UniquePathsII();
        BinaryTreeInorderTraversal binaryTreeInorderTraversal = new BinaryTreeInorderTraversal();
        InterleavingString interleavingString = new InterleavingString();
        ReconstructItinerary reconstructItinerary = new ReconstructItinerary();
        PredictTheWinner predictTheWinner = new PredictTheWinner();
        NumberOfLongestIncreasingSubsequence numberOfLongestIncreasingSubsequence = new NumberOfLongestIncreasingSubsequence();
        MinCostClimbingStairs minCostClimbingStairs = new MinCostClimbingStairs();
        MakingALargeIsland makingALargeIsland = new MakingALargeIsland();
        AllNodesDistanceKInBinaryTree allNodesDistanceKInBinaryTree = new AllNodesDistanceKInBinaryTree();
        SmallestSufficientTeam smallestSufficientTeam = new SmallestSufficientTeam();
        MaximumNumberOfAchievableTransferRequests maximumNumberOfAchievableTransferRequests = new MaximumNumberOfAchievableTransferRequests();
        MaximumNumberOfEventsThatCanBeAttendedII maximumNumberOfEventsThatCanBeAttendedII = new MaximumNumberOfEventsThatCanBeAttendedII();
        LexicographicallySmallestStringAfterApplyingOperations lexicographicallySmallestStringAfterApplyingOperations =  new LexicographicallySmallestStringAfterApplyingOperations();
        PathWithMinimumEffort pathWithMinimumEffort = new PathWithMinimumEffort();
        CountNodesEqualToAverageOfSubtree countNodesEqualToAverageOfSubtree = new CountNodesEqualToAverageOfSubtree();
        FairDistributionOfCookies fairDistributionOfCookies = new FairDistributionOfCookies();

        System.out.println("dfs");
        System.out.println("Q.63 unique paths II");
        System.out.println("[[0,0,0],[0,1,0],[0,0,0]] -> 2: " + uniquePathsII.uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0,1,0}, {0,0,0}}));
        System.out.println("[[0,1],[0,0]] -> 1: " + uniquePathsII.uniquePathsWithObstacles(new int[][]{{0, 1}, {0,0}}));
        System.out.println("[[1,0]] -> 0: " + uniquePathsII.uniquePathsWithObstacles(new int[][]{{1, 0}}));
        System.out.println("------\n");
        System.out.println("Q.94 Binary Tree Inorder Traversal");
        System.out.println("[1,null,2,3] -> [1,3,2]: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{1,null,2,3})));
        System.out.println("[] -> []: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{})));
        System.out.println("[1] -> [1]: " + binaryTreeInorderTraversal.inorderTraversal(TreeNode.generateNode(new Integer[]{1})));
        System.out.println("------\n");
        System.out.println("Q.97 Interleaving String");
        System.out.println("aabcc, dbbca, aadbbcbcac -> true: " + interleavingString.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println("aabcc, dbbca, aadbbbaccc -> true: " + interleavingString.isInterleave("aabcc", "dbbca", "aadbbbaccc"));
        System.out.println(", , -> true: " + interleavingString.isInterleave("", "", ""));
        System.out.println("------\n");
        System.out.println("Q.332. Reconstruct Itinerary");
        System.out.println("[[MUC,LHR],[JFK,MUC],[SFO,SJC],[LHR,SFO]] -> [JFK,MUC,LHR,SFO,SJC]: " + reconstructItinerary.findItinerary(
                Arrays.asList(
                        new ArrayList<>(Arrays.asList("MUC", "LHR")),
                        new ArrayList<>(Arrays.asList("JFK", "MUC")),
                        new ArrayList<>(Arrays.asList("SFO", "SJC")),
                        new ArrayList<>(Arrays.asList("LHR", "SFO"))
                ))
        );
        System.out.println("[[JFK,SFO],[JFK,ATL],[SFO,ATL],[ATL,JFK],[ATL,SFO]] -> [JFK,ATL,JFK,SFO,ATL,SFO]: " + reconstructItinerary.findItinerary(
                Arrays.asList(
                        new ArrayList<>(Arrays.asList("JFK", "SFO")),
                        new ArrayList<>(Arrays.asList("JFK", "ATL")),
                        new ArrayList<>(Arrays.asList("SFO", "ATL")),
                        new ArrayList<>(Arrays.asList("ATL", "JFK")),
                        new ArrayList<>(Arrays.asList("ATL", "SFO"))
                ))
        );
        System.out.println("[[JFK,KUL],[JFK,NRT],[NRT,JFK]] -> [JFK,NRT,JFK,KUL]: " + reconstructItinerary.findItinerary(
                Arrays.asList(
                        new ArrayList<>(Arrays.asList("JFK", "KUL")),
                        new ArrayList<>(Arrays.asList("JFK", "NRT")),
                        new ArrayList<>(Arrays.asList("NRT", "JFK"))
                ))
        );
        System.out.println("------\n");
        System.out.println("Q.486. Predict the Winner");
        System.out.println("[1,5,2] -> false: " + predictTheWinner.PredictTheWinner(new int[]{1,5,2}));
//        System.out.println("[1,5,233,7] -> true: " + predictTheWinner.PredictTheWinner(new int[]{1,5,233,7}));
//        System.out.println("[1,1,1] -> true: " + predictTheWinner.PredictTheWinner(new int[]{1,1,1}));
//        System.out.println("[1,2] -> true: " + predictTheWinner.PredictTheWinner(new int[]{1,2}));
        System.out.println("------\n");
        System.out.println("Q.673 Number of Longest Increasing Subsequence");
        System.out.println("[1,3,5,4,7] -> 2: " + numberOfLongestIncreasingSubsequence.findNumberOfLIS(new int[]{1,3,5,4,7}));
        System.out.println("[2,2,2,2,2] -> 5: " + numberOfLongestIncreasingSubsequence.findNumberOfLIS(new int[]{2,2,2,2,2}));
        System.out.println("------\n");
        System.out.println("Q.746. Min Cost Climbing Stairs");
        System.out.println("[10,15,20] -> 15: " + minCostClimbingStairs.minCostClimbingStairs(new int[]{10,15,20}));
        System.out.println("[1,100,1,1,1,100,1,1,100,1] -> 6: " + minCostClimbingStairs.minCostClimbingStairs(new int[]{1,100,1,1,1,100,1,1,100,1}));
        System.out.println("------\n");
        System.out.println("Q.827 Making A Large Island");
        System.out.println("[[1,0],[0,1]] -> 3: " + makingALargeIsland.largestIsland(new int[][]{{1,0},{0,1}}));
        System.out.println("[[1,1],[1,0]] -> 4: " + makingALargeIsland.largestIsland(new int[][]{{1,1},{1,0}}));
        System.out.println("[[1,1],[1,1]] -> 4: " + makingALargeIsland.largestIsland(new int[][]{{1,1},{1,1}}));
        System.out.println("[[0,0,0,0,0,0,0],[0,1,1,1,1,0,0],[0,1,0,0,1,0,0],[1,0,1,0,1,0,0],[0,1,0,0,1,0,0],[0,1,0,0,1,0,0],[0,1,1,1,1,0,0]] -> 18: " + makingALargeIsland.largestIsland(new int[][]{{0,0,0,0,0,0,0},{0,1,1,1,1,0,0},{0,1,0,0,1,0,0},{1,0,1,0,1,0,0},{0,1,0,0,1,0,0},{0,1,0,0,1,0,0},{0,1,1,1,1,0,0}}));
        System.out.println("Q.863 All Nodes Distance K in Binary Tree");
        System.out.println("[3,5,1,6,2,0,8,null,null,7,4], 5, 2 -> [7,4,1]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{3,5,1,6,2,0,8,null,null,7,4}), new TreeNode(5), 2));
        System.out.println("[1], 1, 3 -> []: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{1}), new TreeNode(1), 3));
        System.out.println("[0,null,1,null,2,null,3], 1, 2 -> [3]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{0,null,1,null,2,null,3}), new TreeNode(1), 2));
        System.out.println("[0,1,null,3,2], 2, 1 -> [1]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{0,1,null,3,2}), new TreeNode(2), 1));
        System.out.println("[0,1,null,null,2,null,3,null,4], 3, 0 -> [3]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{0,1,null,null,2,null,3,null,4}), new TreeNode(3), 0));
        System.out.println("[0,null,1,null,2,null,3,null,4], 0, 2 -> [2]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{0,null,1,null,2,null,3,null,4}), new TreeNode(0), 2));
        System.out.println("[0,null,1,2,5,null,3,null,null,null,4], 2, 2 -> [4,5,0]: " + allNodesDistanceKInBinaryTree.distanceK(TreeNode.generateNode(new Integer[]{0,null,1,2,5,null,3,null,null,null,4}), new TreeNode(2), 2));
        System.out.println("------\n");
        System.out.println("Q.1125 Smallest Sufficient Team");
        System.out.println("[java,nodejs,reactjs], [[java],[nodejs],[nodejs,reactjs]] -> [0,2]: " + Arrays.toString(smallestSufficientTeam.smallestSufficientTeam(new String[]{"java", "nodejs", "reactjs"}, smallestSufficientTeam.createPeople(new String[][]{{"java"}, {"nodejs"}, {"nodejs", "reactjs"}}))));
        System.out.println("------\n");
        System.out.println("Q.1601 Maximum Number of Achievable Transfer Requests");
        System.out.println("5, [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]] -> 5: " + maximumNumberOfAchievableTransferRequests.maximumRequests(5, new int[][]{{0,1},{1,0},{0,1},{1,2},{2,0},{3,4}}));
        System.out.println("4, [[0,3],[3,1],[1,2],[2,0]] -> 4: " + maximumNumberOfAchievableTransferRequests.maximumRequests(4, new int[][]{{0,3},{3,1},{1,2},{2,0}}));
        System.out.println("------\n");
        System.out.println("Q.1625 Lexicographically Smallest String After Applying Operations");
        System.out.println("5525, 9, 2 -> 2050: " + lexicographicallySmallestStringAfterApplyingOperations.findLexSmallestString("5525", 9, 2));
        System.out.println("74, 5, 1 -> 24: " + lexicographicallySmallestStringAfterApplyingOperations.findLexSmallestString("74", 5, 1));
        System.out.println("0011, 4, 2 -> 0011: " + lexicographicallySmallestStringAfterApplyingOperations.findLexSmallestString("0011", 4, 2));
        System.out.println("------\n");
        System.out.println("Q.1631. Path With Minimum Effort");
        System.out.println("[[1,2,2],[3,8,2],[5,3,5]] -> 2: " + pathWithMinimumEffort.minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}}));
        System.out.println("[[1,2,3],[3,8,4],[5,3,5]] -> 1: " + pathWithMinimumEffort.minimumEffortPath(new int[][]{{1,2,3},{3,8,4},{5,3,5}}));
        System.out.println("[[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]] -> 0: " + pathWithMinimumEffort.minimumEffortPath(new int[][]{{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}}));
        System.out.println("------\n");
        System.out.println("Q.1751 Maximum Number of Events That Can Be Attended II");
        System.out.println("[[1,2,4],[3,4,3],[2,3,1]], 2 -> 7: " + maximumNumberOfEventsThatCanBeAttendedII.maxValue(new int[][]{{1,2,4},{3,4,3},{2,3,1}}, 2));
        System.out.println("------\n");
        System.out.println("Q.2265. Count Nodes Equal to Average of Subtree");
        System.out.println("[4,8,5,0,1,null,6] -> 5: " + countNodesEqualToAverageOfSubtree.averageOfSubtree(TreeNode.generateNode(new Integer[]{4,8,5,0,1,null,6})));
        System.out.println("[1] -> 1: " + countNodesEqualToAverageOfSubtree.averageOfSubtree(TreeNode.generateNode(new Integer[]{1})));
        System.out.println("------\n");
        System.out.println("Q.2305 Fair Distribution of Cookies");
        System.out.println("[8,15,10,20,8], 2 -> 31: " + fairDistributionOfCookies.distributeCookies(new int[]{8,15,10,20,8}, 2));
        System.out.println("[6,1,3,2,2,4,1,2], 3 -> 7: " + fairDistributionOfCookies.distributeCookies(new int[]{6,1,3,2,2,4,1,2}, 3));
        System.out.println("[941,797,1475,638,191,712], 3 -> 1653: " + fairDistributionOfCookies.distributeCookies(new int[]{941,797,1475,638,191,712}, 3)); // 이해가 안되네..

        System.out.println("----------\n\n");

        // bfs
        SameTree sameTree = new SameTree();
        SymmetricTree symmetricTree = new SymmetricTree();
        MinimumDepthOfBinaryTree minimumDepthOfBinaryTree = new MinimumDepthOfBinaryTree();
        FindLargestValueInEachTreeRow findLargestValueInEachTreeRow = new FindLargestValueInEachTreeRow();
        FindEventualSafeStates findEventualSafeStates = new FindEventualSafeStates();
        EvenOddTree evenOddTree = new EvenOddTree();

        System.out.println("bfs");
        System.out.println("Q.100 Same Tree");
        System.out.println("[1,2,3], [1,2,3] -> true: " + sameTree.isSameTree(TreeNode.generateNode(new Integer[]{1,2,3}), TreeNode.generateNode(new Integer[]{1,2,3})));
        System.out.println("[1,2], [1,null,2] -> false: " + sameTree.isSameTree(TreeNode.generateNode(new Integer[]{1,2}), TreeNode.generateNode(new Integer[]{1,null,2})));
        System.out.println("------\n");
        System.out.println("Q.101 Symmetric Tree");
        System.out.println("[1,2,2,3,4,4,3] -> true: " + symmetricTree.isSymmetric(TreeNode.generateNode(new Integer[]{1,2,2,3,4,4,3})));
        System.out.println("[1,2,2,3,4,4,3] -> true: " + symmetricTree.isSymmetric(TreeNode.generateNode(new Integer[]{1,2,2,null,3,null,3})));
        System.out.println("------\n");
        System.out.println("Q.111 Minimum Depth of Binary Tree");
        System.out.println("[3,9,20,null,null,15,7] -> 2: " + minimumDepthOfBinaryTree.minDepth(TreeNode.generateNode(new Integer[]{3,9,20,null,null,15,7})));
        System.out.println("[2,null,3,null,4,null,5,null,6] -> 5: " + minimumDepthOfBinaryTree.minDepth(TreeNode.generateNode(new Integer[]{2,null,3,null,4,null,5,null,6})));
        System.out.println("[1,2,3,4,5] -> 2: " + minimumDepthOfBinaryTree.minDepth(TreeNode.generateNode(new Integer[]{1,2,3,4,5})));
        System.out.println("------\n");
        System.out.println("Q.515. Find Largest Value in Each Tree Row");
        System.out.println("[1,3,2,5,3,null,9] -> [1,3,9]: " + findLargestValueInEachTreeRow.largestValues(TreeNode.generateNode(new Integer[]{1,3,2,5,3,null,9})));
        System.out.println("[1,2,3] -> [1,3]: " + findLargestValueInEachTreeRow.largestValues(TreeNode.generateNode(new Integer[]{1,2,3})));
        System.out.println("------\n");
        System.out.println("Q.802 Find Eventual Safe States");
        System.out.println("[[1,2],[2,3],[5],[0],[5],[],[]] -> [2,4,5,6]: " + findEventualSafeStates.eventualSafeNodes(new int[][]{{1,2},{2,3},{5},{0},{5},{},{}}));
        System.out.println("[[1,2,3,4],[1,2],[3,4],[0,4],[]] -> [4]: " + findEventualSafeStates.eventualSafeNodes(new int[][]{{1,2,3,4},{1,2},{3,4},{0,4},{}}));
        System.out.println("[[0],[2,3,4],[3,4],[0,4],[]] -> [0,1,2,3,4]: " + findEventualSafeStates.eventualSafeNodes(new int[][]{{0},{2,3,4},{3,4},{0,4}, {}}));
        System.out.println("[[],[0,2,3,4],[3],[4],[]] -> [0,1,2,3,4]: " + findEventualSafeStates.eventualSafeNodes(new int[][]{{},{0,2,3,4},{3},{4},{}}));
        System.out.println("[[4,9],[3,5,7],[0,3,4,5,6,8],[7,8,9],[5,6,7,8],[6,7,8,9],[7,9],[8,9],[9],[]] -> [0,1,2,3,4,5,6,7,8,9]: " + findEventualSafeStates.eventualSafeNodes(new int[][]{{4,9},{3,5,7},{0,3,4,5,6,8},{7,8,9},{5,6,7,8},{6,7,8,9},{7,9},{8,9},{9},{}}));
        System.out.println("------\n");
        System.out.println("Q.1609 Even Odd Tree");
        System.out.println("[1,10,4,3,null,7,9,12,8,6,null,null,2] -> true: " + evenOddTree.isEvenOddTree(TreeNode.generateNode(new Integer[]{1,10,4,3,null,7,9,12,8,6,null,null,2})));
        System.out.println("[5,4,2,3,3,7] -> false: " + evenOddTree.isEvenOddTree(TreeNode.generateNode(new Integer[]{5,4,2,3,3,7})));
        System.out.println("[5,9,1,3,5,7] -> false: " + evenOddTree.isEvenOddTree(TreeNode.generateNode(new Integer[]{5,9,1,3,5,7})));

        System.out.println("----------\n\n");

        // dynamic programming
        Pow pow = new Pow();
        UniquePaths uniquePaths = new UniquePaths();
        PascalTriangle pascalTriangle = new PascalTriangle();
        PascalTriangleII pascalTriangleII = new PascalTriangleII();
        CombinationSumIV combinationSumIV = new CombinationSumIV();
        CountingBits countingBits = new CountingBits();
        MaximumLengthOfPairChain maximumLengthOfPairChain = new MaximumLengthOfPairChain();
        KnightProbabilityInChessboard knightProbabilityInChessboard = new KnightProbabilityInChessboard();
        LongestArithmeticSubsequenceOfGivenDifference longestArithmeticSubsequenceOfGivenDifference = new LongestArithmeticSubsequenceOfGivenDifference();
        LongestSubArray longestSubArray = new LongestSubArray();
        NumberOfGoodWaysToSplitAString numberOfGoodWaysToSplitAString = new NumberOfGoodWaysToSplitAString();

        System.out.println("dynamic programming");
        System.out.println("Q.50 Pow");
        System.out.println("2.00000, n = 10 -> 1024.00000: " + pow.myPow(2.00000, 10));
        System.out.println("2.10000, n = 3 -> 9.26100: " + pow.myPow(2.10000, 3));
        System.out.println("2.00000, n = -2 -> 0.25000: " + pow.myPow(2.0000, -2));
        System.out.println("2.00000, n = -3 -> 0.12500: " + pow.myPow(2.0000, -3));
        System.out.println("------\n");
        System.out.println("Q.62 Unique Paths");
        System.out.println("3, 7 -> 28: " + uniquePaths.uniquePaths(3, 7));
        System.out.println("3, 2 -> 3: " + uniquePaths.uniquePaths(3, 2));
        System.out.println("------\n");
        System.out.println("Q.118 Pascal's Triangle");
        System.out.println("5 -> [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]: " + pascalTriangle.generate(5));
        System.out.println("5 -> [[1]]: " + pascalTriangle.generate(1));
        System.out.println("------\n");
        System.out.println("119. Pascal's Triangle II");
        System.out.println("3 -> [1,3,3,1]: " + pascalTriangleII.getRow(3));
        System.out.println("0 -> [1]: " + pascalTriangleII.getRow(0));
        System.out.println("1 -> [1,1]: " + pascalTriangleII.getRow(1));
        System.out.println("4 -> [1,4,6,4,1]: " + pascalTriangleII.getRow(4));
        System.out.println("------\n");
        System.out.println("Q.377 Combination Sum IV");
        System.out.println("[1,2,3], 4 -> 7: " + combinationSumIV.combinationSum4(new int[]{1,2,3}, 4));
        System.out.println("[9], 3 -> 0: " + combinationSumIV.combinationSum4(new int[]{9}, 3));
        System.out.println("------\n");
        System.out.println("Q.338 Counting Bits");
        System.out.println("2 -> [0,1,1]: " + Arrays.toString(countingBits.countBits(2)));
        System.out.println("5 -> [0,1,1,2,1,2]: " + Arrays.toString(countingBits.countBits(5)));
        System.out.println("------\n");
        System.out.println("Q.646. Maximum Length of Pair Chain");
        System.out.println("[[1,2],[2,3],[3,4]] -> 2: " + maximumLengthOfPairChain.findLongestChain(new int[][]{{1,2},{2,3},{3,4}}));
        System.out.println("[[1,2],[7,8],[4,5]] -> 3: " + maximumLengthOfPairChain.findLongestChain(new int[][]{{1,2},{7,8},{4,5}}));
        System.out.println("------\n");
        System.out.println("Q.688 Knight Probability in Chessboard");
        System.out.println("3, 2, 0, 0 -> 0.06250: " + knightProbabilityInChessboard.knightProbability(3, 2, 0, 0));
        System.out.println("------\n");
        System.out.println("Q.1218 Longest Arithmetic Subsequence of Given Difference");
        System.out.println("[1,2,3,4], 1 -> 4: " + longestArithmeticSubsequenceOfGivenDifference.longestSubsequence(new int[]{1,2,3,4}, 1));
        System.out.println("[1,3,5,7], 1 -> 1: " + longestArithmeticSubsequenceOfGivenDifference.longestSubsequence(new int[]{1,3,5,7}, 1));
        System.out.println("[1,5,7,8,5,3,4,2,1], -2 -> 4: " + longestArithmeticSubsequenceOfGivenDifference.longestSubsequence(new int[]{1,5,7,8,5,3,4,2,1}, -2));
        System.out.println("------\n");
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
        LetterCombinationsOfAPhoneNumber letterCombinationsOfAPhoneNumber = new LetterCombinationsOfAPhoneNumber();
        WordBreak wordBreak = new WordBreak();
        Candy candy = new Candy();
        ExcelSheetColumnTitle excelSheetColumnTitle = new ExcelSheetColumnTitle();
        KthLargestElementInAnArray kthLargestElementInAnArray = new KthLargestElementInAnArray();
        RemoveDuplicateLetters removeDuplicateLetters = new RemoveDuplicateLetters();
        FrogJump frogJump = new FrogJump();
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        NonOverlappingIntervals nonOverlappingIntervals = new NonOverlappingIntervals();
        RepeatedSubstringPattern repeatedSubstringPattern = new RepeatedSubstringPattern();
        TransposeMatrix transposeMatrix = new TransposeMatrix();
        TwoCityScheduling twoCityScheduling = new TwoCityScheduling();
        GroupThePeopleGivenTheGroupSizeTheyBelongTo groupThePeopleGivenTheGroupSizeTheyBelongTo = new GroupThePeopleGivenTheGroupSizeTheyBelongTo();
        DiagonalTraverseII diagonalTraverseII = new DiagonalTraverseII();
        BuildAnArrayWithStackOperations buildAnArrayWithStackOperations = new BuildAnArrayWithStackOperations();
        MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts maximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts = new MaximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts();
        SumOfAbsoluteDifferencesInASortedArray sumOfAbsoluteDifferencesInASortedArray = new SumOfAbsoluteDifferencesInASortedArray();
        CalculateMoneyInLeetcodeBank calculateMoneyInLeetcodeBank = new CalculateMoneyInLeetcodeBank();
        CountNumberOfHomogenousSubstrings countNumberOfHomogenousSubstrings = new CountNumberOfHomogenousSubstrings();
        CountNicePairsInAnArray countNicePairsInAnArray = new CountNicePairsInAnArray();
        EliminateMaximumNumberOfMonsters eliminateMaximumNumberOfMonsters = new EliminateMaximumNumberOfMonsters();
        FindUniqueBinaryString findUniqueBinaryString = new FindUniqueBinaryString();
        MaximumRunningTimeOfNComputers maximumRunningTimeOfNComputers = new MaximumRunningTimeOfNComputers();
        FindTheOriginalArrayOfPrefixXor findTheOriginalArrayOfPrefixXor = new FindTheOriginalArrayOfPrefixXor();
        MaximizeGreatnessOfAnArray maximizeGreatnessOfAnArray = new MaximizeGreatnessOfAnArray();
        MinimizeTheMaximumDifferenceOfPairs minimizeTheMaximumDifferenceOfPairs = new MinimizeTheMaximumDifferenceOfPairs();
        DetermineIfACellIsReachableAtAGivenTime determineIfACellIsReachableAtAGivenTime = new DetermineIfACellIsReachableAtAGivenTime();

        System.out.println("greedy");
        System.out.println("Q.17 Letter Combinations of a Phone Number");
        System.out.println("23 -> [ad,ae,af,bd,be,bf,cd,ce,cf]: " + letterCombinationsOfAPhoneNumber.letterCombinations("23"));
        System.out.println(" -> []: " + letterCombinationsOfAPhoneNumber.letterCombinations(""));
        System.out.println("2 -> [a,b,c]: " + letterCombinationsOfAPhoneNumber.letterCombinations("2"));
        System.out.println("------\n");
        System.out.println("Q.139 Word Break");
        System.out.println(wordBreak.wordBreak("leetcode", Arrays.asList("leet", "code")));
        System.out.println(wordBreak.wordBreak("applepenapple", Arrays.asList("apple", "pen")));
        System.out.println(wordBreak.wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));
        System.out.println("------\n");
        System.out.println("Q.135 Candy");
        System.out.println("[1,0,2] -> 5: " + candy.candy(new int[]{1,0,2}));
        System.out.println("[1,2,2] -> 4: " + candy.candy(new int[]{1,2,2}));
        System.out.println("------\n");
        System.out.println("Q.168 Excel Sheet Column Title");
//        System.out.println("1 -> A: " + excelSheetColumnTitle.convertToTitle(1));
        System.out.println("28 -> AB: " + excelSheetColumnTitle.convertToTitle(28));
        System.out.println("701 -> ZY: " + excelSheetColumnTitle.convertToTitle(701));
        System.out.println("------\n");
        System.out.println("Q.215");
        System.out.println("[3,2,1,5,6,4], 2 -> 5: " + kthLargestElementInAnArray.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        System.out.println("[3,2,3,1,2,4,5,5,6], 4 -> 4: " + kthLargestElementInAnArray.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
        System.out.println("------\n");
        System.out.println("Q.316. Remove Duplicate Letters");
        System.out.println("bcabc -> abc: " + removeDuplicateLetters.removeDuplicateLetters("bcabc"));
        System.out.println("cbacdcbc -> acdb: " + removeDuplicateLetters.removeDuplicateLetters("cbacdcbc"));
        System.out.println("------\n");
        System.out.println("Q.403 Frog Jump");
        System.out.println("[0,1,3,5,6,8,12,17] -> true: " + frogJump.canCross(new int[]{0,1,3,5,6,8,12,17}));
        System.out.println("[0,1,2,3,4,8,9,11] -> false: " + frogJump.canCross(new int[]{0,1,2,3,4,8,9,11}));
        System.out.println("[0,1,3,6,10,15,16,21] -> true: " + frogJump.canCross(new int[]{0,1,3,6,10,15,16,21}));
        System.out.println("------\n");
        System.out.println("Q.409 Longest Palindrome");
        System.out.println("abccccdd -> 7: " + longestPalindrome.longestPalindrome("abccccdd"));
        System.out.println("a -> 1: " + longestPalindrome.longestPalindrome("a"));
        System.out.println("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth -> 983: " + longestPalindrome.longestPalindrome("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
        System.out.println("------\n");
        System.out.println("Q.435 Non-overlapping Intervals");
        System.out.println("[[1,2],[2,3],[3,4],[1,3]] -> 1: " + nonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{1,3}}));
        System.out.println("[[1,2],[1,2],[1,2]] -> 2: " + nonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1,2},{1,2},{1,2}}));
        System.out.println("[[1,2],[2,3]] -> 0: " + nonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{1,2},{2,3}}));
        System.out.println("------\n");
        System.out.println("Q.459 Repeated Substring Pattern");
        System.out.println("abab -> true: " + repeatedSubstringPattern.repeatedSubstringPattern("abab"));
        System.out.println("aba -> false: " + repeatedSubstringPattern.repeatedSubstringPattern("aba"));
        System.out.println("abcabcabcabc -> true: " + repeatedSubstringPattern.repeatedSubstringPattern("abcabcabcabc"));
        System.out.println("------\n");
        System.out.println("Q.867. Transpose Matrix");
        System.out.println("[[1,2,3],[4,5,6],[7,8,9]] -> [[1,4,7],[2,5,8],[3,6,9]]: " + Arrays.deepToString(transposeMatrix.transpose(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})));
        System.out.println("[[1,2,3],[4,5,6]] -> [[1,4],[2,5],[3,6]]: " + Arrays.deepToString(transposeMatrix.transpose(new int[][]{{1,2,3}, {4,5,6}})));
        System.out.println("------\n");
        System.out.println("Q.1029 Two City Scheduling");
        System.out.println("[[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]] -> 1859: " + twoCityScheduling.twoCitySchedCost(new int[][]{{259,770},{448,54},{926,667},{184,139},{840,118},{577,469}}));
        System.out.println("[[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]] -> 3086: " + twoCityScheduling.twoCitySchedCost(new int[][]{{515,563},{451,713},{537,709},{343,819},{855,779},{457,60},{650,359},{631,42}}));
        System.out.println("------\n");
        System.out.println("Q.1282. Group the People Given the Group Size They Belong To");
        System.out.println("[3,3,3,3,3,1,3] -> [[5],[0,1,2],[3,4,6]]: " + groupThePeopleGivenTheGroupSizeTheyBelongTo.groupThePeople(new int[]{3,3,3,3,3,1,3}));
        System.out.println("[2,1,3,3,3,2] -> [[1],[0,5],[2,3,4]]: " + groupThePeopleGivenTheGroupSizeTheyBelongTo.groupThePeople(new int[]{2,1,3,3,3,2}));
        System.out.println("------\n");
        System.out.println("Q.1424. Diagonal Traverse II");
        System.out.println("[[1,2,3],[4,5,6],[7,8,9]] -> [1,4,2,7,5,3,8,6,9]: " + Arrays.toString(diagonalTraverseII.findDiagonalOrder(
                Arrays.asList(
                        new ArrayList<>(Arrays.asList(1,2,3)),
                        new ArrayList<>(Arrays.asList(4,5,6)),
                        new ArrayList<>(Arrays.asList(7,8,9))
                )))
        );
        System.out.println("[[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]] -> [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]: " + Arrays.toString(diagonalTraverseII.findDiagonalOrder(
                Arrays.asList(
                        new ArrayList<>(Arrays.asList(1,2,3,4,5)),
                        new ArrayList<>(Arrays.asList(6,7)),
                        new ArrayList<>(Arrays.asList(8)),
                        new ArrayList<>(Arrays.asList(9,10,11)),
                        new ArrayList<>(Arrays.asList(12,13,14,15,16))
                )))
        );
        System.out.println("------\n");
        System.out.println("Q.1441. Build an Array With Stack Operations");
        System.out.println("[1,3], 3 -> [Push,Push,Pop,Push]: " + buildAnArrayWithStackOperations.buildArray(new int[]{1,3}, 3));
        System.out.println("[1,2,3], 3 -> [Push,Push,Push]: " + buildAnArrayWithStackOperations.buildArray(new int[]{1,2,3}, 3));
        System.out.println("[1,2], 4 -> [Push,Push]: " + buildAnArrayWithStackOperations.buildArray(new int[]{1,2}, 4));
        System.out.println("------\n");
        System.out.println("Q.1465 Maximum Area of a Piece of Cake After Horizontal and Vertical Cuts");
        System.out.println("5, 4, [1,2,4], [1,3] -> 4: " + maximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts.maxArea(5, 4, new int[]{1,2,4}, new int[]{1,3}));
        System.out.println("5, 4, [3,1], [1] -> 6: " + maximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts.maxArea(5, 4, new int[]{3,1}, new int[]{1}));
        System.out.println("5, 4, [3], [3] -> 9: " + maximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts.maxArea(5, 4, new int[]{3}, new int[]{3}));
        System.out.println("1000000000, 1000000000, [2], [2] -> 81: " + maximumAreaOfAPieceOfCakeAfterHorizontalAndVerticalCuts.maxArea(1000000000, 1000000000, new int[]{2}, new int[]{2}));
        System.out.println("------\n");
        System.out.println("Q.1685. Sum of Absolute Differences in a Sorted Array");
        System.out.println("[2,3,5] -> [4,3,5]: " + Arrays.toString(sumOfAbsoluteDifferencesInASortedArray.getSumAbsoluteDifferences(new int[]{2, 3, 5})));
        System.out.println("[1,4,6,8,10] -> [24,15,13,15,21]: " + Arrays.toString(sumOfAbsoluteDifferencesInASortedArray.getSumAbsoluteDifferences(new int[]{1,4,6,8,10})));
        System.out.println("------\n");
        System.out.println("Q.1716. Calculate Money in Leetcode Bank");
        System.out.println("4 -> 10: " + calculateMoneyInLeetcodeBank.totalMoney(4));
        System.out.println("10 -> 37: " + calculateMoneyInLeetcodeBank.totalMoney(10));
        System.out.println("20 -> 96: " + calculateMoneyInLeetcodeBank.totalMoney(20));
        System.out.println("------\n");
        System.out.println("Q.1759. Count Number of Homogenous Substrings");
        System.out.println("abbcccaa -> 13: " + countNumberOfHomogenousSubstrings.countHomogenous("abbcccaa"));
        System.out.println("xy -> 2: " + countNumberOfHomogenousSubstrings.countHomogenous("xy"));
        System.out.println("zzzzz -> 15: " + countNumberOfHomogenousSubstrings.countHomogenous("zzzzz"));
        System.out.println("------\n");
        System.out.println("Q.1814. Count Nice Pairs in an Array");
        System.out.println("[42,11,1,97] -> 2: " + countNicePairsInAnArray.countNicePairs(new int[]{42,11,1,97}));
        System.out.println("[13,10,35,24,76] -> 4: " + countNicePairsInAnArray.countNicePairs(new int[]{13,10,35,24,76}));
        System.out.println("------\n");
        System.out.println("Q.1921. Eliminate Maximum Number of Monsters");
        System.out.println("[1,3,4], [1,1,1] -> 3: " + eliminateMaximumNumberOfMonsters.eliminateMaximum(new int[]{1,3,4}, new int[]{1,1,1}));
        System.out.println("[1,1,2,3], [1,1,1,1] -> 1: " + eliminateMaximumNumberOfMonsters.eliminateMaximum(new int[]{1,1,2,3}, new int[]{1,1,1,1}));
        System.out.println("[3,2,4], [5,3,2] -> 1: " + eliminateMaximumNumberOfMonsters.eliminateMaximum(new int[]{3,2,4}, new int[]{5,3,2}));
        System.out.println("------\n");
        System.out.println("Q.1980. Find Unique Binary String");
        System.out.println("[01, 10] -> 11 or 00: " + findUniqueBinaryString.findDifferentBinaryString(new String[]{"01", "10"}));
        System.out.println("[00, 01] -> 11 or 10: " + findUniqueBinaryString.findDifferentBinaryString(new String[]{"00", "01"}));
        System.out.println("[111, 011, 001] -> 000 or 010 or 100 or 101 or 110: " + findUniqueBinaryString.findDifferentBinaryString(new String[]{"111", "011", "001"}));
        System.out.println("------\n");
        System.out.println("Q.2141. Maximum Running Time of N Computers");
        System.out.println("2, [3,3,3] -> 4: " + maximumRunningTimeOfNComputers.maxRunTime(2, new int[]{3,3,3}));
        System.out.println("2, [1,1,1,1] -> 2: " + maximumRunningTimeOfNComputers.maxRunTime(2, new int[]{1,1,1,1}));
        System.out.println("------\n");
        System.out.println("Q.2433. Find The Original Array of Prefix Xor");
        System.out.println("[5,2,0,3,1] -> [5,7,2,3,2]: " + Arrays.toString(findTheOriginalArrayOfPrefixXor.findArray(new int[]{5, 2, 0, 3, 1})));
        System.out.println("[13] -> [13]: " + Arrays.toString(findTheOriginalArrayOfPrefixXor.findArray(new int[]{13})));
        System.out.println("------\n");
        System.out.println("Q.2592 Maximize Greatness of an Array");
        System.out.println("[1,3,5,2,1,3,1] -> 4: " + maximizeGreatnessOfAnArray.maximizeGreatness(new int[]{1,3,5,2,1,3,1}));
        System.out.println("[1,2,3,4] -> 3: " + maximizeGreatnessOfAnArray.maximizeGreatness(new int[]{1,2,3,4}));
        System.out.println("------\n");
        System.out.println("Q.2616 Minimize the Maximum Difference of Pairs\n");
        System.out.println("[10,1,2,7,1,3], 2 -> 1: " + minimizeTheMaximumDifferenceOfPairs.minimizeMax(new int[]{10,1,2,7,1,3}, 2));
        System.out.println("------\n");
        System.out.println("Q.2849. Determine if a Cell Is Reachable at a Given Time");
        System.out.println("2, 4, 7, 7, 6: true -> " + determineIfACellIsReachableAtAGivenTime.isReachableAtTime(2,4,7,7,6));
        System.out.println("3, 1, 7, 3, 3: false -> " + determineIfACellIsReachableAtAGivenTime.isReachableAtTime(3,1,7,3,3));
        System.out.println("1, 1, 1, 1, 3: true -> " + determineIfACellIsReachableAtAGivenTime.isReachableAtTime(1,1,1,1,3));
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
