package leetcode.sorting;

import java.util.Arrays;

/*
Q.88
You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.

Merge nums1 and nums2 into a single array sorted in non-decreasing order.

The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 */
public class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] copyNums1 = Arrays.copyOfRange(nums1, 0, nums1.length);
        int size = m + n;
        int mIndex = 0, nIndex = 0;
        for (int i = 0; i < size; i++) {
            if (mIndex < m && nIndex < n) {
                if (copyNums1[mIndex] < nums2[nIndex]) nums1[i] = copyNums1[mIndex++];
                else nums1[i] = nums2[nIndex++];
            } else if (mIndex == m) nums1[i] = nums2[nIndex++];
            else nums1[i] = copyNums1[mIndex++];
        }

        System.out.println("merge result: " + Arrays.toString(nums1));
    }
}

/*
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;
    int j = n - 1;
    int k = m + n - 1;

    while (j >= 0) {
        if (i >= 0 && nums1[i] > nums2[j]) {
            nums1[k--] = nums1[i--];
        } else {
            nums1[k--] = nums2[j--];
        }
    }
}
오름차순으로 기본으로 정렬되어 있고, num1 의 크기와 같은 배열로 병합되게 요청되므로
꼭 앞에서 부터 할 필요 없이 뒤에서 부터 높은 순으로 채우면 되네..
*/
