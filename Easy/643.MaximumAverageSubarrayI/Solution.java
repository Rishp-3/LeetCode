class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int window = 0;
        for (int i = 0; i < k; i++) {
            window += nums[i];
        }

        int best = window;
        for (int i = k; i < nums.length; i++) {
            window += nums[i] - nums[i - k];
            best = Math.max(best, window);
        }
        return (double) best / k;
    }
}