package fuckboss;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class Main{
	/*
	 * ��Ŀ������
	 * ���������ţţ�Ͽλ���˯�����ȸ���n��k������ÿ�ڿλ���ɢ��n��ʱ��㣬ÿ��ʱ����з�ֵ
	 * Ȼ���ٸ���һϵ�����֣�ֻ��0,1,����0����˯���ˣ���ֵ�ò�����1�������ţ������õ����ʱ���ķ�ֵ
	 * ��������Խ���ţţһ�Σ�����k����ɢʱ��㣬���ʱ����ڶ����Ե÷�
	 * 
	 * ���룺
	 * 6 3
	 * 1 2 3 2 1 3
	 * 1 0 1 0 0 1
	 * 
	 * ˼·��
	 * ��һ������ȥ������Ľ��ѳ���ʱ��Σ�����ĩβ�������ڶ���1��Ȼ�󻮵��׼�����󼴿�
	 * ���д��벢û���Ż���ʵ���Ͽ�������O(n)���Ӷ�
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
				for (int j = i; j < Math.min(i+k,n); j++) {//�����൱��ȥ������
					if(wake[j]==0) num+=nums[j];
				}
				max = Math.max(max, num);

			}
		}
		System.out.println(max);
	}

}
