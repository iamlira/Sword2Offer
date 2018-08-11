package fuckboss;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class Main{
	/*
	 * 题目描述：
	 * 大概描述是牛牛上课会打瞌睡，首先给出n和k，但是每节课会离散成n个时间点，每个时间点有分值
	 * 然后再给出一系列数字，只有0,1,其中0代表睡着了，分值拿不到，1代表醒着，可以拿到这个时间点的分值
	 * 但是你可以叫醒牛牛一次，持续k个离散时间点，这个时间段内都可以得分
	 * 
	 * 输入：
	 * 6 3
	 * 1 2 3 2 1 3
	 * 1 0 1 0 0 1
	 * 
	 * 思路：
	 * 用一个滑窗去代表你的叫醒持续时间段，划到末尾，滑窗内都是1，然后划到底计算最大即可
	 * 下列代码并没有优化，实际上可以做到O(n)复杂度
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String[] firstLine = scanner.nextLine().trim().split(" ");
		int n = Integer.parseInt(firstLine[0]);
		int k = Integer.parseInt(firstLine[1]);
		int[] nums = new int[n];
		String[] sencondLine = scanner.nextLine().trim().split(" ");
		for (int i = 0; i < n; i++) {
			nums[i] = Integer.parseInt(sencondLine[i]);
		}
		int[] wake = new int[n];
		String[] tirdLine = scanner.nextLine().trim().split(" ");
		for (int i = 0; i < n; i++) {
			wake[i] = Integer.parseInt(tirdLine[i]);
		}
		int sum = 0;
		for (int i = 0; i < n; i++) {
			if (wake[i] == 1) {
				sum += nums[i];
			}
		}
		int maxTest=0;
		if(n == k)
		{
			for (int i = 0; i < n; i++) {
				maxTest+=nums[i];
			}
			System.out.println(maxTest);
			return;
		}
		int max =0;
		for (int i = 0; i < n; i++) {
			if (wake[i] == 0 ) {
				int num =sum;
				for (int j = i; j < Math.min(i+k,n); j++) {//这里相当于去做滑窗
					if(wake[j]==0) num+=nums[j];
				}
				max = Math.max(max, num);

			}
		}
		System.out.println(max);
	}

}
