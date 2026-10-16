class Solution {
    public int divide(int dividend, int divisor) {
        // Special overflow case
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // Fast paths for common divisors
        if (divisor == 1) return dividend;
        if (divisor == -1) return -dividend;

        // Result ka sign nikaal lo
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // Long me convert + abs le lo taaaki overflow na ho
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        int result = 0;

        // 32 bits ke hisaab se check karo (most significant bit se)
        for (int i = 31; i >= 0; i--) {
            long shifted = b << i;      // b * 2^i (bit shift se)
            if (a >= shifted) {
                a -= shifted;           // itna part cover ho gaya
                result |= (1 << i);     // answer me ye bit set kar do
            }
        }

        return negative ? -result : result;
    }
}
