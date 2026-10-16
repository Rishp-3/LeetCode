class Solution {
    // Lookup Tables (Logic wahi hai, bas static block mein use karenge)
    private static final String[] M = {"", "M", "MM", "MMM"};
    private static final String[] C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    // Ye array saare 3999 answers ko store karega
    private static final String[] CACHE = new String[4000];

    // Static Block: Ye code sirf EK BAAR chalega jab app start hogi
    static {
        for (int i = 0; i < 4000; i++) {
            CACHE[i] = M[i / 1000] + 
                       C[(i % 1000) / 100] + 
                       X[(i % 100) / 10] + 
                       I[i % 10];
        }
    }

    public String intToRoman(int num) {
        // Run-time par 0 calculation. Direct Memory Access.
        return CACHE[num];
    }
}