class Solution {
    private static final String[] THOUSANDS = {"", "M", "MM", "MMM"};
    private static final String[] HUNDREDS  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] TENS      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] ONES      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    public String intToRoman(int num) {
        if (num <= 0 || num > 3999) throw new IllegalArgumentException("num must be in 1..3999");

        // digit extraction
        int thou = num / 1000;
        int hund = (num % 1000) / 100;
        int ten  = (num % 100) / 10;
        int one  = num % 10;

        // compute length to size StringBuilder exactly (micro-opt)
        int len = THOUSANDS[thou].length() + HUNDREDS[hund].length() + TENS[ten].length() + ONES[one].length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(THOUSANDS[thou]).append(HUNDREDS[hund]).append(TENS[ten]).append(ONES[one]);
        return sb.toString();
    }
}
