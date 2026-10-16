class Solution {

    boolean[][] row = new boolean[9][10];
    boolean[][] col = new boolean[9][10];
    boolean[][] box = new boolean[9][10];

    public void solveSudoku(char[][] board) {

        // prefill
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    box[(i / 3) * 3 + (j / 3)][num] = true;
                }
            }
        }

        solve(board, 0, 0);
    }

    boolean solve(char[][] board, int r, int c) {
        if (r == 9) return true;

        int nr = r, nc = c + 1;
        if (nc == 9) {
            nr = r + 1;
            nc = 0;
        }

        if (board[r][c] != '.') {
            return solve(board, nr, nc);
        }

        int b = (r / 3) * 3 + (c / 3);

        for (int d = 1; d <= 9; d++) {
            if (!row[r][d] && !col[c][d] && !box[b][d]) {

                board[r][c] = (char)(d + '0');
                row[r][d] = col[c][d] = box[b][d] = true;

                if (solve(board, nr, nc)) return true;

                board[r][c] = '.';
                row[r][d] = col[c][d] = box[b][d] = false;
            }
        }
        return false;
    }
}
