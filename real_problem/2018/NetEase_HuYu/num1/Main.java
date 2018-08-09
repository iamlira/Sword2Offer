package fuckboss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
	/*
	 * 题目描述：
	 * 
	 * 有一张税率表，表示各收入阶段需要缴税
	 * 
	 *输入：
	 *输出：都略
	 */
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);

		int N = Integer.parseInt(s.nextLine());
		int[] ints = new int[N];
		int i = 0;
		int flag = N;
		while(N-- != 0){

			double needPay = 0.0;
			double sum = Double.parseDouble(s.nextLine());
			double needPaySum = sum - 5000;

			if(needPaySum > 0){
				if(needPaySum - 3000 > 0){
					needPay += 3000 * 0.03;
				}
				else{
					needPay += needPaySum * 0.03;
				}
			}
			needPaySum -= 3000;

			if(needPaySum > 0){
				if(needPaySum -9000 > 0){
					needPay += 9000 * 0.1;
				}else{
					needPay += needPaySum * 0.1;
				}

			}
			needPaySum -= 9000;

			if(needPaySum > 0){
				if(needPaySum -13000 > 0){
					needPay += 13000 * 0.2;
				}else{
					needPay += needPaySum * 0.2;
				}
			}
			needPaySum -= 13000;

			if(needPaySum > 0){
				if(needPaySum -10000 > 0){
					needPay += 10000 * 0.25;
				}else{
					needPay += needPaySum * 0.25;
				}
			}
			needPaySum -= 10000;

			if(needPaySum > 0){
				if(needPaySum -20000 > 0){
					needPay += 20000 * 0.3;
				}else{
					needPay += needPaySum * 0.3;
				}
			}
			needPaySum -= 20000;

			if(needPaySum > 0){
				if(needPaySum -25000 > 0){
					needPay += 25000 * 0.35;
				}else{
					needPay += needPaySum * 0.35;
				}
			}
			needPaySum -= 25000;

			if(needPaySum > 0){
				needPay += needPaySum * 0.45;
			}
			ints[i] = (int)(needPay +0.5);
			i++;
		}
		for (int j = 0; j < flag ; j++)
			System.out.println(ints[j]);
	}
}

