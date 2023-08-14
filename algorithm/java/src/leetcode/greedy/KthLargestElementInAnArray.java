package leetcode.greedy;

import java.util.Stack;

/*
Q.215 Kth Largest Element in an Array
Given an integer array nums and an integer k, return the kth largest element in the array.

Note that it is the kth largest element in the sorted order, not the kth distinct element.

Can you solve it without sorting?
 */
public class KthLargestElementInAnArray {
    public int findKthLargest(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        for (int num : nums) {
            add(stack, num);
        }

        return stack.get(k - 1);
    }

    private void add(Stack<Integer> stack, int value) {
        if (stack.isEmpty()) {
            stack.add(value);
        } else {
            Stack<Integer> stock = new Stack<>();
            int compare = stack.pop();

            while(!stack.isEmpty() && compare < value) {
                stock.add(compare);
                compare = stack.pop();
            }

            stack.add(Math.max(compare, value));
            stack.add(Math.min(compare, value));
            while(!stock.isEmpty()) {
                stack.add(stock.pop());
            }
        }
    }
}

/*
public int findKthLargest(int[] nums, int k) {
  return quickSelect(nums, 0, nums.length - 1, k);
}

int quickSelect(int[] nums, int low, int high, int k) {
  int pivot = low;

  // use quick sort's idea
  // put nums that are <= pivot to the left
  // put nums that are  > pivot to the right
  for (int j = low; j < high; j++) {
    if (nums[j] <= nums[high]) {
      swap(nums, pivot++, j);
    }
  }
  swap(nums, pivot, high);

  // count the nums that are > pivot from high
  int count = high - pivot + 1;
  // pivot is the one!
  if (count == k) return nums[pivot];
  // pivot is too small, so it must be on the right
  if (count > k) return quickSelect(nums, pivot + 1, high, k);
  // pivot is too big, so it must be on the left
  return quickSelect(nums, low, pivot - 1, k - count);
}
 */