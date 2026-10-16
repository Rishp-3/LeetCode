class Solution {
    public int countPartitions(int[] nums) {
        long sum = 0;
        for (int x : nums) sum += x;  // long is safer because |nums[i]| <= 1e9

        // If total sum is even, all n-1 splits work; else none.
        return (sum % 2 == 0) ? nums.length - 1 : 0;
    }
}
