class Solution {
    public int maxProfit(int[] prices) {
        int min = prices[0];   // pehle din ka price
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i]; // naya minimum
            } else {
                maxProfit = Math.max(maxProfit, prices[i] - min); // profit update
            }
        }
        return maxProfit;
    }
}
