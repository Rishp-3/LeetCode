class Solution {
    public int missingNumber(int[] nums) {
        int xor = nums.length; // start with n

        for (int i = 0; i < nums.length; i++) {
            xor ^= i;       // XOR with index
            xor ^= nums[i]; // XOR with array value
        }
        return xor;
    }
}
