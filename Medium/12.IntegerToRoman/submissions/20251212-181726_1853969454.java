class Solution {
    // Arrays ko memory mein constant rakhne ke liye static final rakha hai
    private static final String[] THOUSANDS = {"", "M", "MM", "MMM"};
    private static final String[] HUNDREDS  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] TENS      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] ONES      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    public String intToRoman(int num) {
        // Validation hata diya agar LeetCode constraints (1 <= num <= 3999) guaranteed hain
        // Isse unnecessary branching bachegi.
        
        // StringBuilder ko fixed size 15 diya (Max Roman length for 3999 is 15 chars).
        // Calculation ka overhead hat gaya.
        StringBuilder sb = new StringBuilder(15);
        
        // Direct append without temp variables
        sb.append(THOUSANDS[num / 1000]);
        sb.append(HUNDREDS[(num / 100) % 10]);
        sb.append(TENS[(num / 10) % 10]);
        sb.append(ONES[num % 10]);
        
        return sb.toString();
    }
}