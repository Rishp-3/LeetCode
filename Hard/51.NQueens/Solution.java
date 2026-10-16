class Solution {
    List<List<String>> ans = new ArrayList<>();
    boolean[] col, diag1, diag2;
    char[][] board;

    public List<List<String>> solveNQueens(int n) {
        board = new char[n][n];
        for(char[] r : board) Arrays.fill(r, '.');

        col = new boolean[n];
        diag1 = new boolean[2*n-1];
        diag2 = new boolean[2*n-1];

        backtrack(0, n);
        return ans;
    }

    void backtrack(int row, int n) {
        if (row == n) {
            List<String> temp = new ArrayList<>();
            for (char[] r : board)
                temp.add(new String(r));
            ans.add(temp);
            return;
        }

        for (int c = 0; c < n; c++) {
            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (!col[c] && !diag1[d1] && !diag2[d2]) {
                board[row][c] = 'Q';
                col[c] = diag1[d1] = diag2[d2] = true;

                backtrack(row + 1, n);

                board[row][c] = '.';
                col[c] = diag1[d1] = diag2[d2] = false;
            }
        }
    }
}
