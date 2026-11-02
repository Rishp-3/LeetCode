class Solution {
    public boolean isSubsequence(String s, String t) {
        int m = s.length(), n = t.length();
        if (m > n) return false;
        if (m == 0) return true;

        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        int i = 0;

        for (int j = 0; j < n && i < m; j++) {
            if (a[i] == b[j]) i++;
        }
        return i == m;
    }
}