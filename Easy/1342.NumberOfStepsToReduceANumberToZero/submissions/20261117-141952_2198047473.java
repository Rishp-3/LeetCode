class Solution {
    public int numberOfSteps(int num) {
        if (num == 0) return 0;
        return 31 - Integer.numberOfLeadingZeros(num) + Integer.bitCount(num);
    }
}