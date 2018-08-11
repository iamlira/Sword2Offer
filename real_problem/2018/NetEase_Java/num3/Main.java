package fuckboss;

import java.util.Scanner;

public class Main {
	/*
	 * 题目描述：
	 * 给出n和m和k，会组成一个包含n个a和m个z的char[]
	 * 现在需要输出这个char[]的全排列的第k个
	 * 
	 * 思路：
	 * 全排列，找到第k个，
	 * 有更优的解法，使用C++的stl的next permutation函数
	 * 实现相同的函数，调用k次，该函数思路具体网上自查
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();
		
		char[] words = new char[n + m];
		String end = "";
		for (int i = 0; i < n; i++) {
			words[i] = 'a';
			end += 'a';
		}
		for (int i = n; i < n + m; i++) {
			words[i] = 'z';
			end += 'z';
		}
		for (int i = 0; i < k - 1; i++) {
			nextWord(words);
			if (isEqual(words, end)) {
				System.out.print(-1);
				return;
			}
		}
		String result = "";
		for (int i = 0; i < n + m; i++) {
			result += words[i];
		}
		System.out.print(result);
	}
	
	public static void nextWord(char[] words) {
		int i = words.length - 2;
		int j = words.length - 1;
		while (i >= 0 && words[i] >= words[i + 1]) {
			i--;
		}
		if (i >= 0) {
			while (words[j] <= words[i]) {
				j--;
			}
			char temp = words[i];
			words[i] = words[j];
			words[j] = temp;
			reverse(words, i + 1);	
		}
	}
	
	public static void reverse(char[] words, int start) {
		for (int i = start, j = words.length - 1; i < j; i++, j--) {
			char temp = words[i];
			words[i] = words[j];
			words[j] = temp;
		}
	}
	
	private static boolean isEqual(char[] words, String end) {
		for (int i = 0; i < words.length; i++) {
			if (words[i] != end.charAt(i)) {
				return false;
			}
		}
		return true;
	}

}
