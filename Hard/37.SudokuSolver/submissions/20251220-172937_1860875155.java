class Solution {

    int[] row = new int[9];
    int[] col = new int[9];
    int[] box = new int[9];

    public void solveSudoku(char[][] board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int d = board[i][j] - '1';
                    int mask = 1 << d;
                    row[i] |= mask;
                    col[j] |= mask;
                    box[(i/3)*3 + j/3] |= mask;
                }
            }
        }
        solve(board, 0, 0);
    }

    boolean solve(char[][] b, int r, int c) {
        if (r == 9) return true;

        int nr = r, nc = c + 1;
        if (nc == 9) { nr++; nc = 0; }

        if (b[r][c] != '.') return solve(b, nr, nc);

        int used = row[r] | col[c] | box[(r/3)*3 + c/3];

        for (int d = 0; d < 9; d++) {
            int mask = 1 << d;
            if ((used & mask) == 0) {
                b[r][c] = (char)(d + '1');
                row[r] |= mask;
                col[c] |= mask;
                box[(r/3)*3 + c/3] |= mask;

                if (solve(b, nr, nc)) return true;

                b[r][c] = '.';
                row[r] ^= mask;
                col[c] ^= mask;
                box[(r/3)*3 + c/3] ^= mask;
            }
        }
        return false;
    }
}
