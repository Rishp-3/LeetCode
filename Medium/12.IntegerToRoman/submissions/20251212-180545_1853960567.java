class Solution {
    public String intToRoman(int num) {
        if (num <= 0 || num > 3999) {
            throw new IllegalArgumentException("num must be in range 1..3999");
        }

        int[] vals =    {1000, 900, 500, 400, 100,  90,  50,  40,  10,   9,   5,   4,  1};
        String[] syms = {"M",  "CM","D", "CD", "C", "XC", "L", "XL", "X", "IX","V", "IV","I"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++) {
            while (num >= vals[i]) {
                sb.append(syms[i]);
                num -= vals[i];
            }
        }
        return sb.toString();
    }
}
