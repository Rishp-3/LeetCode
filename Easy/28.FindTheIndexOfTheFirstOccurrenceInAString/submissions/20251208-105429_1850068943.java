class Solution {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        if (m == 0) return 0;          // LeetCode convention
        if (m > n) return -1;

        // Build LPS (Longest Prefix Suffix) array for needle
        int[] lps = buildLPS(needle);

        int i = 0; // index for haystack
        int j = 0; // index for needle

        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {          // full match
                    return i - j;      // starting index
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];    // jump using LPS
                } else {
                    i++;               // move in haystack
                }
            }
        }

        return -1;
    }

    // Build LPS array for pattern (needle)
    private int[] buildLPS(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        int len = 0;   // length of previous longest prefix suffix
        int i = 1;

        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];  // try smaller prefix
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}
