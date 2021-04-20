class Solution {
    public int maxProfit(int[] prices) {
        int minIndex = 0, maxProfit = 0;
        for(int i = 0; i < prices.length; i++){
            if(prices[i] < prices[minIndex]){
                minIndex = i;
            }
            if(prices[i] - prices[minIndex] > maxProfit 
                && i > minIndex){
                    maxProfit = prices[i] - prices[minIndex];
            }
        }
        return maxProfit;
    }
}
