class Solution {
    public int maxProfit(int[] prices) {
        int min = prices[0];
        int maxProfit = 0;
        int len = prices.length;

        for (int i = 1; i < len; i++) {
            int price = prices[i];
            if (price < min) {
                min = price; // naya min
            } else {
                int profit = price - min;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        return maxProfit;
    }
}
