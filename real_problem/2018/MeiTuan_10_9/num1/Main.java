package com;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int X=in.nextInt();
        int[] price=new int[n];
        for(int i=0;i<n;i++) {
        	price[i]=in.nextInt();
        }
		int i, j;
		int M = X+100; // 待查找近似值
		/* 01背包 */
		int dp[] = new int[M+1];
		for (i = 0; i < price.length; ++i) {
			for (j = M; j >= price[i]; --j) {
				int tmp = dp[j - price[i]] + price[i];
				if (tmp > dp[j]) {
					dp[j] = tmp;
				}
			}
		}
		for(i=X;i<dp.length;i++) {
			if(dp[i]>=X) {
				System.out.println(dp[i]);
				break;
			}
		}
	}
	public static int sum(int m, int n)//递归
	{
		if(m<=0||n<=0)
			return 0;
		if (m == 1 && n == 1)
			return 1;
		return sum(m-1,n)+sum(m,n-1);
	}
}
