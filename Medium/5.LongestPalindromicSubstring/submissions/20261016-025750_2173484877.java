
class Solution {
    public String longestPalindrome(String s) {
        int start = 0, maxLen = 1;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            // Odd length palindrome
            int left = i, right = i;

            while (left >= 0 && right < n &&
                   s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > maxLen) {
                    start = left;
                    maxLen = right - left + 1;
                }
                left--;
                right++;
            }

            // Even length palindrome
            left = i;
            right = i + 1;

            while (left >= 0 && right < n &&
                   s.charAt(left) == s.charAt(right)) {
                if (right - left + 1 > maxLen) {
                    start = left;
                    maxLen = right - left + 1;
                }
                left--;
                right++;
            }
        }

        return s.substring(start, start + maxLen);
    }
}
