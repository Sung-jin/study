package leetcode.binarySearch;

/*
Q.1095. Find in Mountain Array
(This problem is an interactive problem.)

You may recall that an array arr is a mountain array if and only if:

arr.length >= 3
There exists some i with 0 < i < arr.length - 1 such that:
arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target. If such an index does not exist, return -1.

You cannot access the mountain array directly. You may only access the array using a MountainArray interface:

MountainArray.get(k) returns the element of the array at index k (0-indexed).
MountainArray.length() returns the length of the array.
Submissions making more than 100 calls to MountainArray.get will be judged Wrong Answer. Also, any solutions that attempt to circumvent the judge will result in disqualification.
 */
public class FindInMountainArray {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int topIndex = findTopIndex(mountainArr);

        if (target == mountainArr.get(topIndex)) return topIndex;

        int leftFindIndex = findTargetIndex(0, topIndex - 1, target, mountainArr);

        if (leftFindIndex != -1) return leftFindIndex;

        return findTargetIndex(topIndex + 1, mountainArr.length() - 1, target, mountainArr);
    }

    private int findTopIndex(MountainArray mountainArr) {
        int maxValue = -1;
        int left = 0, right = mountainArr.length() - 1;
        int index = 0;

        while (left < right) {
            int mid = (left + right) / 2;
            int value = mountainArr.get(mid), nextValue = mountainArr.get(mid + 1);

            if (value > nextValue) {
                if (maxValue < value) index = mid;

                maxValue = Math.max(maxValue, value);
                right = mid - 1;
            } else {
                if (maxValue < nextValue) index = mid + 1;

                maxValue = Math.max(maxValue, nextValue);
                left = mid;
            }
        }

        return index;
    }

    private int findTargetIndex(int start, int end, int target, MountainArray mountainArr) {
        int left = start, right = end;

        while (left < right) {
            int mid = (left + right) / 2;
            int value = mountainArr.get(mid);

            if (value == target) return mid;
            if (value > target) right = mid;
            else left = mid;
        }

        return -1;
    }


    interface MountainArray {
        int get(int value);
        int length();
    }
}

/*
int findInMountainArray(int target, MountainArray A) {
    int n = A.length(), l, r, m, peak = 0;
    // find index of peak
    l  = 0;
    r = n - 1;
    while (l < r) {
        m = (l + r) / 2;
        if (A.get(m) < A.get(m + 1))
            l = peak = m + 1;
        else
            r = m;
    }
    // find target in the left of peak
    l = 0;
    r = peak;
    while (l <= r) {
        m = (l + r) / 2;
        if (A.get(m) < target)
            l = m + 1;
        else if (A.get(m) > target)
            r = m - 1;
        else
            return m;
    }
    // find target in the right of peak
    l = peak;
    r = n - 1;
    while (l <= r) {
        m = (l + r) / 2;
        if (A.get(m) > target)
            l = m + 1;
        else if (A.get(m) < target)
            r = m - 1;
        else
            return m;
    }
    return -1;
}
 */
