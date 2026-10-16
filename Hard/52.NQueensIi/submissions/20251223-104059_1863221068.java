import java.util.*;

class Solution {
    boolean[] col;
    boolean[] diag1;
    boolean[] diag2;
    int count = 0;

    public int totalNQueens(int n) {
        col = new boolean[n];
        diag1 = new boolean[2 * n - 1];
        diag2 = new boolean[2 * n - 1];

        backtrack(0, n);
        return count;
    }

    private void backtrack(int row, int n) {
        if (row == n) {
            count++;
            return;
        }

        for (int c = 0; c < n; c++) {
            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (col[c] || diag1[d1] || diag2[d2]) continue;

            col[c] = diag1[d1] = diag2[d2] = true;
            backtrack(row + 1, n);
            col[c] = diag1[d1] = diag2[d2] = false;
        }
    }
}
