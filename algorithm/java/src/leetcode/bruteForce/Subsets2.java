package leetcode.bruteForce;

import java.util.ArrayList;
import java.util.List;

public class Subsets2 {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());

        for (int i = 0; i < nums.length; i++) {
            List<Integer> values = new ArrayList<>();

            for (int j = i; j < nums.length; j++) {
                values.add(nums[j]);

                if (!ans.contains(values)) ans.add(new ArrayList<>(values));
            }

            for (int j = i + 1; j < nums.length; j++) {
                List<Integer> excludeValue = new ArrayList<>(values);

                for (int k = j; k < nums.length; k++) {
                    excludeValue.remove((Object)nums[k]);
                    if (!ans.contains(excludeValue)) ans.add(new ArrayList<>(excludeValue));
                }
            }
        }

        return ans;
    }
}

/*
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        helper(res,new ArrayList<>(),nums,0);
        return res;
    }

    public void helper(List<List<Integer>> res, List<Integer> ls, int[] nums, int pos) {
        res.add(new ArrayList<>(ls));
        for(int i=pos;i<nums.length;i++) {
            if(i>pos&&nums[i]==nums[i-1]) continue;
            ls.add(nums[i]);
            helper(res,ls,nums,i+1);
            ls.remove(ls.size()-1);
        }
    }
}

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        helper(res,new ArrayList<>(),nums,0,false);
        return res;
    }

    public void helper(List<List<Integer>> res, List<Integer> ls, int[] nums, int pos, boolean choosePre) {
        if(pos==nums.length) {
            res.add(new ArrayList<>(ls));
            return;
        }
        helper(res,ls,nums,pos+1,false);
        if(pos>=1&&nums[pos]==nums[pos-1]&&!choosePre) return;
        ls.add(nums[pos]);
        helper(res,ls,nums,pos+1,true);
        ls.remove(ls.size()-1);
    }
}
 */