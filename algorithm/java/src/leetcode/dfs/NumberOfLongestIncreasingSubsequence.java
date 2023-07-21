package leetcode.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Q.673

Given an integer array nums, return the number of longest increasing subsequences.

Notice that the sequence has to be strictly increasing.
 */
public class NumberOfLongestIncreasingSubsequence {

    private int maxSize = 0;
    private List<int[]> result = new ArrayList<>();

    public int findNumberOfLIS(int[] nums) {
        maxSize = 0;
        result.clear();
        for (int i = 0; i < nums.length; i++) {
            addValue(new int[]{}, nums[i]);
            findAllIncreasingSubsequence(nums, i + 1, new int[]{nums[i]});
        }

        return result.size();
    }

    private void findAllIncreasingSubsequence(int[] nums, int index, int[] before) {
        for (int i = index; i < nums.length; i++) {
            if (nums[i] > before[before.length - 1]) {
                findAllIncreasingSubsequence(nums, i + 1, addValue(before, nums[i]));
            }
        }
    }

    private int[] addValue(int[] before, int value) {
        int[] added = append(before, value);

        if (maxSize < added.length) {
            result.clear();
            maxSize = added.length;
        }
        if (maxSize == added.length) {
            result.add(added);
        }

        return added;
    }

    private int[] append(int[] origin, int value) {
        int[] result = Arrays.copyOf(origin, origin.length + 1);
        result[result.length - 1] = value;

        return result;
    }
}

/*
public int findNumberOfLIS(int[] nums) {
    int n = nums.length, res = 0, max_len = 0;
    int[] len =  new int[n], cnt = new int[n];
    for(int i = 0; i<n; i++){
        len[i] = cnt[i] = 1;
        for(int j = 0; j <i ; j++){
            if(nums[i] > nums[j]){
                if(len[i] == len[j] + 1)cnt[i] += cnt[j];
                if(len[i] < len[j] + 1){
                    len[i] = len[j] + 1;
                    cnt[i] = cnt[j];
                }
            }
        }
        if(max_len == len[i])res += cnt[i];
        if(max_len < len[i]){
            max_len = len[i];
            res = cnt[i];
        }
    }
    return res;
}
 */