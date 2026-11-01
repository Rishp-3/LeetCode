class Solution {
    public String reverseVowels(String s) {
        char[] c = s.toCharArray();
        int l = 0, r = c.length - 1;

        while (l < r) {
            while (l < r && !isVowel(c[l])) l++;
            while (l < r && !isVowel(c[r])) r--;

            char t = c[l];
            c[l] = c[r];
            c[r] = t;
            l++;
            r--;
        }
        return new String(c);
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a': case 'e': case 'i': case 'o': case 'u':
            case 'A': case 'E': case 'I': case 'O': case 'U':
                return true;
            default:
                return false;
        }
    }
}