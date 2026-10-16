class Solution {

    int[] row = new int[9];
    int[] col = new int[9];
    int[] box = new int[9];

    public void solveSudoku(char[][] board) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int d = board[i][j] - '1';
                    int bit = 1 << d;
                    row[i] |= bit;
                    col[j] |= bit;
                    box[(i/3)*3 + j/3] |= bit;
                }
            }
        }
        dfs(board, 0, 0);
    }

    boolean dfs(char[][] b, int r, int c) {
        if (r == 9) return true;

        int nr = r, nc = c + 1;
        if (nc == 9) { nr++; nc = 0; }

        if (b[r][c] != '.') return dfs(b, nr, nc);

        int used = row[r] | col[c] | box[(r/3)*3 + c/3];

        for (int d = 0; d < 9; d++) {
            int bit = 1 << d;
            if ((used & bit) == 0) {
                b[r][c] = (char)(d + '1');
                row[r] |= bit;
                col[c] |= bit;
                box[(r/3)*3 + c/3] |= bit;

                if (dfs(b, nr, nc)) return true;

                b[r][c] = '.';
                row[r] ^= bit;
                col[c] ^= bit;
                box[(r/3)*3 + c/3] ^= bit;
            }
        }
        return false;
    }
}
