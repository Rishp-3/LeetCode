class Solution {
    public int maxProfit(int[] prices) {
         int maxp=0 , buy=prices[0] ;
        for(int i=0;i<prices.length-1;i++){
            if(prices[i]>prices[i+1]){
                 maxp+=prices[i]-buy;
                 buy=prices[i+1];
            }
            
        }
        if(buy<prices[prices.length-1]){
            maxp+=prices[prices.length-1]-buy;
        }
        return maxp;
    }
}