package leetcode.greedy;

/*
Q.867. Transpose Matrix
Given a 2D integer array matrix, return the transpose of matrix.

The transpose of a matrix is the matrix flipped over its main diagonal, switching the matrix's row and column indices.
 */
public class TransposeMatrix {
    public int[][] transpose(int[][] matrix) {
        int[][] answer = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                answer[j][i] = matrix[i][j];
            }
        }

        return answer;
    }
}

/*
public int[][] transpose(int[][] A) {
    int M = A.length, N = A[0].length;
    int[][] B = new int[N][M];
    for (int j = 0; j < N; j++)
        for (int i = 0; i < M; i++)
            B[j][i] = A[i][j];
    return B;
}
 */
