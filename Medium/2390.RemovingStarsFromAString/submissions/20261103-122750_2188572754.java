class Solution {
    public String removeStars(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '*') {
                if (sb.length() > 0) sb.setLength(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}