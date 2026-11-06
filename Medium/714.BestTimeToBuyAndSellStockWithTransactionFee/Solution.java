class Solution {
    public int maxProfit(int[] prices, int fee) {
        int cash = 0;
        int hold = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            int p = prices[i];
            int newCash = hold + p - fee;
            if (newCash > cash) cash = newCash;

            int newHold = cash - p;
            if (newHold > hold) hold = newHold;
        }
        return cash;
    }
}