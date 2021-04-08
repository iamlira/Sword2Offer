class Solution {
    public String longestPalindrome(String s) {
        int[][] dp = new int[s.length()][s.length()];
        int maxLen = 0, left = 0, right = 0;
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = 1;
        }
        for (int i = 1; i < s.length(); i++) {
            int index = 0;
            for (int j = 0; j < s.length() - i; j++) {
                if (s.charAt(index) == s.charAt(index + i)) {
                    dp[index][index + i] = (index + 1) <= (index + i - 1) ?
                            (dp[index + 1][index + i - 1] != 0 ? dp[index + 1][index + i - 1] + 2 : 0) : 2;
                    if(dp[index][index + i] > maxLen){
                        maxLen = dp[index][index + i];
                        left = index;
                        right = index + i;
                    }
                }
                index++;
            }
        }
        return s.substring(left, right+1);
    }
}
