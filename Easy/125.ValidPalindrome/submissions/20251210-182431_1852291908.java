class Solution {
    public boolean isPalindrome(String s) {
        int st = 0, la = s.length() - 1;

        while (st < la) {
            char left = s.charAt(st);
            char right = s.charAt(la);

            // left non-alphanumeric skip
            while (st < la && !isAlphaNum(left)) {
                st++;
                left = s.charAt(st);
            }

            // right non-alphanumeric skip
            while (st < la && !isAlphaNum(right)) {
                la--;
                right = s.charAt(la);
            }

            if (st >= la) break;

            // lowercase compare (ASCII)
            if (toLower(left) != toLower(right)) {
                return false;
            }

            st++;
            la--;
        }

        return true;
    }

    // '0'–'9' , 'A'–'Z' , 'a'–'z'
    private boolean isAlphaNum(char c) {
        return (c >= '0' && c <= '9') ||
               (c >= 'A' && c <= 'Z') ||
               (c >= 'a' && c <= 'z');
    }

    // sirf A-Z ko a-z banata hai, baaki same
    private char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char)(c + ('a' - 'A'));
        }
        return c;
    }
}
