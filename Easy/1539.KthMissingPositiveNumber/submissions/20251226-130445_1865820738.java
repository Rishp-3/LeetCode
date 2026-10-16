class Solution {
    public int findKthPositive(int[] arr, int k) {
        int mis = 0;
        int ind = 1;
        int i = 0;

        while (mis < k) {
            if (i < arr.length && ind == arr[i]) {
                i++;
            } else {
                mis++;
                if (mis == k) return ind;
            }
            ind++;
        }
        return -1;
    }
}
