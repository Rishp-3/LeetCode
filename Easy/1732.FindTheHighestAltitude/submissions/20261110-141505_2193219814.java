class Solution {
    public int largestAltitude(int[] gain) {
        int sum = 0, max = 0;
        for (int g : gain) {
            sum += g;
            if (sum > max) max = sum;
        }
        return max;
    }
}