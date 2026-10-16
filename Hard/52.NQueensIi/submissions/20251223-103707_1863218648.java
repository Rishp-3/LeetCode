class Solution {
    boolean[] col, diag1, diag2;
    int num=0;

    public int totalNQueens(int n) {
        char[][] board = new char[n][n];
        for(char[] r : board) Arrays.fill(r, '.');

        col = new boolean[n];
        diag1 = new boolean[2*n-1];
        diag2 = new boolean[2*n-1];

        backtrack(board ,0, n);
        return num;
    }

    void backtrack(char[][]board, int row, int n) {
        if (row == n) {
            num++;
            return;
        }

        for (int c = 0; c < n; c++) {
            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (!col[c] && !diag1[d1] && !diag2[d2]) {
                board[row][c] = 'Q';
                col[c] = diag1[d1] = diag2[d2] = true;

                backtrack(board,row + 1, n);

                board[row][c] = '.';
                col[c] = diag1[d1] = diag2[d2] = false;
            }
        }
    }
}