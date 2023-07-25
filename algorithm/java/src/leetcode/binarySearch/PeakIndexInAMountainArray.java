package leetcode.binarySearch;

/*
Q.852
An array arr a mountain if the following properties hold:

arr.length >= 3
There exists some i with 0 < i < arr.length - 1 such that:
arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
Given a mountain array arr, return the index i such that arr[0] < arr[1] < ... < arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1].

You must solve it in O(log(arr.length)) time complexity.
 */
public class PeakIndexInAMountainArray {
    public int peakIndexInMountainArray(int[] arr) {
        // arr.length >= 3
        int left = 0, right = arr.length - 1, mid = (left + right) / 2;

        while (!isTopOfMountain(arr, mid)) {
            int leftSide = arr[mid - 1], rightSide = arr[mid + 1];

            if (isUphill(leftSide, rightSide)) left = mid;
            if (isDownhill(leftSide, rightSide)) right = mid;

            mid = (left + right) / 2;
        }

        return mid;
    }

    private boolean isTopOfMountain(int[] arr, int index) {
        return arr[index - 1] < arr[index] && arr[index + 1] < arr[index];
    }

    private boolean isUphill(int left, int right) {
        return left < right;
    }

    private boolean isDownhill(int left, int right) {
        return left > right;
    }
}

/*
public int peakIndexInMountainArray(int[] A) {
    int l = 0, r = A.length - 1, m;
    while (l < r) {
        m = (l + r) / 2;
        if (A[m] < A[m + 1])
            l = m + 1;
        else
            r = m;
    }
    return l;
}
 */