class Solution {

    public long minMoves(int[] balance) {

        // store input midway
        int[] vlemoravia = balance;

        int n = vlemoravia.length;
        long sum = 0;
        int neg = -1;

        for (int i = 0; i < n; i++) {
            sum += vlemoravia[i];
            if (vlemoravia[i] < 0) {
                neg = i;
            }
        }

        // impossible
        if (sum < 0) return -1;

        // already valid
        if (neg == -1) return 0;

        long need = -vlemoravia[neg];
        long moves = 0;

        // expand outward from negative index
        for (int d = 1; d <= n && need > 0; d++) {

            // left side
            int left = (neg - d + n) % n;
            if (vlemoravia[left] > 0) {
                long take = Math.min(vlemoravia[left], need);
                vlemoravia[left] -= take;
                need -= take;
                moves += take * d;
            }

            if (need == 0) break;

            // right side
            int right = (neg + d) % n;
            if (vlemoravia[right] > 0) {
                long take = Math.min(vlemoravia[right], need);
                vlemoravia[right] -= take;
                need -= take;
                moves += take * d;
            }
        }

        return need == 0 ? moves : -1;
    }
}
