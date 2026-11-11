class Solution {
    public boolean isPalindrome(int x) {
        int n = x;
        long s = 0;
        while (n > 0) {
            s = s * 10 + n % 10;
            n /= 10;
        }
        return s == x;
    }
}