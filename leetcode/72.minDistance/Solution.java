public class Solution {
	/*
	 * 动态规划，先构造[word1.length()+1]*[word2.length()+1]大小的表
	 * [0][0]位置是两个空串的距离
	 * 举例可以发现规律：
	 * 若word1[i]==word2[j]时，他们的距离就是dp[i-1][j-1]
	 * 若word1[i]!=word2[j]时，他们的距离就是min(dp[i-1][j-1],dp[i-1][j],dp[i][j-1])+1;
	 * 最后填表即可,dp[word1.length()][word2.length()]就是结果
	 */
	public int minDistance(String word1, String word2) {
		int[][] dp=new int[word1.length()+1][word2.length()+1];
		for(int i=0;i<=word2.length();i++)//初始状态
			dp[0][i]=i;
		for(int i=0;i<=word1.length();i++)
			dp[i][0]=i;
		for(int i=1;i<word1.length()+1;i++){
			for(int j=1;j<word2.length()+1;j++){
				if(word1.charAt(i-1)==word2.charAt(j-1))
					dp[i][j]=dp[i-1][j-1];
				else{
					int tmp=dp[i-1][j-1]<dp[i-1][j]?dp[i-1][j-1]:dp[i-1][j];
					tmp=tmp<dp[i][j-1]?tmp:dp[i][j-1];
					dp[i][j]=tmp+1;
				}
			}
		}
		return dp[word1.length()][word2.length()];
	}
}