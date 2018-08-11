package fuckboss;

import java.util.Scanner;

public class Main {
	/*
	 * ��Ŀ������
	 * ����n��m��k�������һ������n��a��m��z��char[]
	 * ������Ҫ������char[]��ȫ���еĵ�k��
	 * 
	 * ˼·��
	 * ȫ���У��ҵ���k����
	 * �и��ŵĽⷨ��ʹ��C++��stl��next permutation����
	 * ʵ����ͬ�ĺ���������k�Σ��ú���˼·���������Բ�
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
