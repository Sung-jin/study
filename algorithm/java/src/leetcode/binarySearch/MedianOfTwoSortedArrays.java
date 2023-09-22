package leetcode.binarySearch;

/*
Q.4. Median of Two Sorted Arrays
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log (m+n)).
 */
public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int min = Math.min(nums1[0], nums2[0]), max = Math.max(nums1[nums1.length  -1], nums2[nums2.length - 1]);
        int left1 = 0, left2 = 0, right1 = nums1.length, right2 = nums2.length;
        int midian1 = 0, median2 = 0;

        return 0.0;
    }
}

/*
public double findMedianSortedArrays(int[] A, int[] B) {
	    int m = A.length, n = B.length;
	    int l = (m + n + 1) / 2;
	    int r = (m + n + 2) / 2;
	    return (getkth(A, 0, B, 0, l) + getkth(A, 0, B, 0, r)) / 2.0;
	}

public double getkth(int[] A, int aStart, int[] B, int bStart, int k) {
	if (aStart > A.length - 1) return B[bStart + k - 1];
	if (bStart > B.length - 1) return A[aStart + k - 1];
	if (k == 1) return Math.min(A[aStart], B[bStart]);

	int aMid = Integer.MAX_VALUE, bMid = Integer.MAX_VALUE;
	if (aStart + k/2 - 1 < A.length) aMid = A[aStart + k/2 - 1];
	if (bStart + k/2 - 1 < B.length) bMid = B[bStart + k/2 - 1];

	if (aMid < bMid)
	    return getkth(A, aStart + k/2, B, bStart,       k - k/2);// Check: aRight + bLeft
	else
	    return getkth(A, aStart,       B, bStart + k/2, k - k/2);// Check: bRight + aLeft
}
 */
