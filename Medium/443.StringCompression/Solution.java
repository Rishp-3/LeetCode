class Solution {
    public int compress(char[] chars) {
        int k = 0;  
        int i = 0;  

        while (i < chars.length) {
            char c = chars[i];
            int start = i;

            while (i < chars.length && chars[i] == c) i++;
            int count = i - start;

            chars[k++] = c;
            if (count > 1) {
                for (char d : String.valueOf(count).toCharArray()) {
                    chars[k++] = d;
                }
            }
        }
        return k;
    }
}