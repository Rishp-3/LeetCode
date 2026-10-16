class Solution {
    public boolean isValid(String s) {
        int n = s.length();
        if (n % 2 != 0) return false; // odd length can't be valid

        char[] stack = new char[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == '(' || c == '{' || c == '[') {
                stack[++top] = c;
            } else {
                if (top == -1) return false;

                char open = stack[top--];

                if ((c == ')' && open != '(') ||
                    (c == '}' && open != '{') ||
                    (c == ']' && open != '[')) {
                    return false;
                }
            }
        }

        return top == -1;
    }
}
