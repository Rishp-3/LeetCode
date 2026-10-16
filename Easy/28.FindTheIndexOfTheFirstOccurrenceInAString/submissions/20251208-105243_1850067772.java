class Solution {
    public int strStr(String haystack, String needle) {

        if (needle.length() == 0) return 0;

        char k = needle.charAt(0);

        for (int i = 0; i < haystack.length(); i++) {
            char d = haystack.charAt(i);

            if (k == d && (i + needle.length()) <= haystack.length()) {

                int num = -1;

                for (int j = 0; j < needle.length(); j++) {
                    char ne = needle.charAt(j);
                    char ha = haystack.charAt(i + j);

                    if (ne != ha) break;
                    num = j;
                }

                if (num == needle.length() - 1) return i;
            }
        }
        return -1;
    }
}
