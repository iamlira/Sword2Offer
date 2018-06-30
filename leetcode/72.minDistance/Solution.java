public class Solution {
	/*
	 * ��̬�滮���ȹ���[word1.length()+1]*[word2.length()+1]��С�ı�
	 * [0][0]λ���������մ��ľ���
	 * �������Է��ֹ��ɣ�
	 * ��word1[i]==word2[j]ʱ�����ǵľ������dp[i-1][j-1]
	 * ��word1[i]!=word2[j]ʱ�����ǵľ������min(dp[i-1][j-1],dp[i-1][j],dp[i][j-1])+1;
	 * ��������,dp[word1.length()][word2.length()]���ǽ��
	 */
	public int minDistance(String word1, String word2) {
		int[][] dp=new int[word1.length()+1][word2.length()+1];
		for(int i=0;i<=word2.length();i++)//��ʼ״̬
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