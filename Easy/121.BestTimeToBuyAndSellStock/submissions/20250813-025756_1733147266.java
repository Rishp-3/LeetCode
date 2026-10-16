class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE; // ab tak ka minimum price
        int maxProfit = 0; // ab tak ka maximum profit

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price; // naye minimum ko update karo
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice; // profit update karo
            }
        }

        return maxProfit;
    }
}
