class Solution {
    public void solveSudoku(char[][] board) {
        solve(board,0,0);
    }
    public boolean solve(char[][] board, int row, int col) {
    if (row == 9) return true;

    int nextRow = row, nextCol = col + 1;
    if (nextCol == 9) {
        nextRow = row + 1;
        nextCol = 0;
    }

    if (board[row][col] != '.') {
        return solve(board, nextRow, nextCol);
    }

    for (int di = 1; di <= 9; di++) {
        char ch = (char)(di + '0');
        if (isSafe(board, row, col, ch)) {
            board[row][col] = ch;
            if (solve(board, nextRow, nextCol)) return true;
            board[row][col] = '.';
        }
    }
    return false;
}

public boolean isSafe(char[][] board, int row, int col, char ch) {
    for (int i = 0; i < 9; i++) {
        if (board[i][col] == ch) return false;
        if (board[row][i] == ch) return false;
    }

    int sr = (row / 3) * 3;
    int sc = (col / 3) * 3;
    for (int i = sr; i < sr + 3; i++) {
        for (int j = sc; j < sc + 3; j++) {
            if (board[i][j] == ch) return false;
        }
    }
    return true;
}


}