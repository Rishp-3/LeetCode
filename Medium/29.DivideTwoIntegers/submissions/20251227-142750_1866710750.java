class Solution {
    public int divide(int dividend, int divisor) {
        // 1. Handle Overflow Edge Case
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 2. Sign nikaal lo
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // 3. Convert to Long to prevent overflow during Math.abs
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);
        
        int result = 0;

        // OPTIMIZATION: Start Point calculate karo
        // Long 64 bit hota hai, lekin humara number int range ka hai.
        // Hum calculate karte hain ki 'a' aur 'b' ke bits me kitna difference hai.
        // Example: a=100 (bits: 7), b=3 (bits: 2) -> Max shift = 7-2 = 5.
        // Loop 31 ki jagah sirf 5 se start hoga.
        
        int shift = Long.numberOfLeadingZeros(b) - Long.numberOfLeadingZeros(a);

        // Sirf utni baar loop chalega jitni zarurat hai
        for (int i = shift; i >= 0; i--) {
            if (a >= (b << i)) {
                a -= (b << i);
                result |= (1 << i);
            }
        }

        return negative ? -result : result;
    }
}