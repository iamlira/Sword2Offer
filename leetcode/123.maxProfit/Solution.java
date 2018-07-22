public class Solution {
    /*
    题目描述：Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete at most two transactions.

    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

    Example 1:

    Input: [3,3,5,0,0,3,1,4]
    Output: 6
    Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.

    Example 2:

    Input: [1,2,3,4,5]
    Output: 4
    Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
             Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
             engaging multiple transactions at the same time. You must sell before buying again.

    Example 3:

    Input: [7,6,4,3,1]
    Output: 0
    Explanation: In this case, no transaction is done, i.e. max profit = 0.


    思路：使用动态规划，这里使用两个数组，sell[i]表示截至第i天卖出股票能获得的最大利润，这里用min_price记录
    到第i天最低的股价，从左向右遍历过程中会用每天的股价减去最小股价得到利润，且每天与最大利润比较，截至第i天的最大利润会存到sell[i]中，
    同理，buy数组用于从右向左遍历，buy[i]表示从右向左遍历，在第i天买入股票可以得到的最大利润，这里用max_price记录第i天后的最大股价，
    从最后一天往前每天比较，每第i天买入股价和之后的比较，将之后最大的利润存到buy[i]中，
    最后同时遍历sell和buy数组，肯定有一天买入和卖出历史利润和是最大的，记录输出即可
     */
    public int maxProfit(int[] prices) {
        int result=0;
        if (prices.length==0)
            return result;
        int[] sell=new int[prices.length],buy=new int[prices.length];
        int min_price=prices[0],max_price=prices[prices.length-1];
        for(int i=1;i<prices.length;i++){
            sell[i]=sell[i-1]>prices[i]-min_price?sell[i-1]:prices[i]-min_price;
            min_price=min_price<prices[i]?min_price:prices[i];
        }
        for(int i=prices.length-2;i>=0;i--){
            buy[i]=buy[i+1]>max_price-prices[i]?buy[i+1]:max_price-prices[i];
            max_price=max_price>prices[i]?max_price:prices[i];
        }
        for(int i=0;i<sell.length;i++){
                result=result>sell[i]+buy[i]?result:sell[i]+buy[i];
        }
        return result;
    }
}
