package leetcode.binarySearch;

/*
Q.74 Search a 2D Matrix
You are given an m x n integer matrix matrix with the following two properties:

Each row is sorted in non-decreasing order.
The first integer of each row is greater than the last integer of the previous row.
Given an integer target, return true if target is in matrix or false otherwise.

You must write a solution in O(log(m * n)) time complexity.
*/
public class SearchA2DMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        int[] row = findRow(matrix, target);

        return isContainTarget(row, target);
    }

    private int[] findRow(int[][] matrix, int target) {
        if (matrix.length == 1) return matrix[0];

        int s = 0, e = matrix.length - 1;

        while (s < e) {
            int mid = (s + e) / 2;
            int[] targetMatrix = matrix[mid];

            if (targetMatrix[0] <= target && targetMatrix[targetMatrix.length - 1] >= target) return targetMatrix;
            else if (targetMatrix[0] < target) s = mid - 1;
            else e = mid;
        }

        return new int[]{};
    }

    private boolean isContainTarget(int[] row, int target) {
        if (row.length == 0) return false;
        if (row.length == 1 && row[0] == target) return true;
        int l = 0, r = row.length - 1;

        while (l < r) {
            int mid = (l + r) / 2;

            if (row[mid] == target) return true;
            else if (row[mid] > target) r = mid - 1;
            else l = mid;
        }

        return false;
    }
}

/*
public boolean searchMatrix(int[][] matrix, int target) {

	int row_num = matrix.length;
	int col_num = matrix[0].length;

	int begin = 0, end = row_num * col_num - 1;

	while(begin <= end){
		int mid = (begin + end) / 2;
		int mid_value = matrix[mid/col_num][mid%col_num];

		if( mid_value == target){
			return true;

		}else if(mid_value < target){
			//Should move a bit further, otherwise dead loop.
			begin = mid+1;
		}else{
			end = mid-1;
		}
	}

	return false;
}
// 2 차원 배열이라고 하더라도, 전체적으로 오름차순으로 정렬 되어 있기 때문에,
// 하나의 배열로 판단하여 binary search 를 하면 되네.
 */