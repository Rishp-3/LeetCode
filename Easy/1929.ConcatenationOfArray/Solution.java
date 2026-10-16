class Solution {
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2 * n];

        System.arraycopy(nums, 0, ans, 0, n);   // first copy
        System.arraycopy(nums, 0, ans, n, n);   // second copy

        return ans;
    }
}