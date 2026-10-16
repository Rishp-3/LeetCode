class Solution {
    public String intToRoman(int num) {
        // Max possible length 15 hai (e.g., MMMDCCCLXXXVIII)
        // Ek raw char array banayenge jo StringBuilder se fast hai
        char[] ans = new char[15];
        int i = 0;

        // THOUSANDS (M)
        while (num >= 1000) {
            ans[i++] = 'M';
            num -= 1000;
        }

        // HUNDREDS (CM, D, CD, C)
        if (num >= 900) { ans[i++] = 'C'; ans[i++] = 'M'; num -= 900; }
        if (num >= 500) { ans[i++] = 'D'; num -= 500; }
        if (num >= 400) { ans[i++] = 'C'; ans[i++] = 'D'; num -= 400; }
        while (num >= 100) {
            ans[i++] = 'C';
            num -= 100;
        }

        // TENS (XC, L, XL, X)
        if (num >= 90) { ans[i++] = 'X'; ans[i++] = 'C'; num -= 90; }
        if (num >= 50) { ans[i++] = 'L'; num -= 50; }
        if (num >= 40) { ans[i++] = 'X'; ans[i++] = 'L'; num -= 40; }
        while (num >= 10) {
            ans[i++] = 'X';
            num -= 10;
        }

        // ONES (IX, V, IV, I)
        if (num >= 9) { ans[i++] = 'I'; ans[i++] = 'X'; num -= 9; }
        if (num >= 5) { ans[i++] = 'V'; num -= 5; }
        if (num >= 4) { ans[i++] = 'I'; ans[i++] = 'V'; num -= 4; }
        while (num >= 1) {
            ans[i++] = 'I';
            num -= 1;
        }

        // Final String create karo sirf utne hisse ki jitna use hua
        return new String(ans, 0, i);
    }
}