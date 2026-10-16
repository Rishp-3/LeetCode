class Solution {
    int count = 0;

    public int totalNQueens(int n) {
        solve(0, 0, 0, 0, n);
        return count;
    }

    private void solve(int row, int cols, int diag1, int diag2, int n) {
        if (row == n) {
            count++;
            return;
        }

        int available = ((1 << n) - 1) & ~(cols | diag1 | diag2);

        while (available != 0) {
            int pick = available & -available; // lowest set bit
            available -= pick;

            solve(
                row + 1,
                cols | pick,
                (diag1 | pick) << 1,
                (diag2 | pick) >> 1,
                n
            );
        }
    }
}
