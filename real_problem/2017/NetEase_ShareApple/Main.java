import java.util.*;

public class Main{
	/*
	 * 题目描述
	n 只奶牛坐在一排，每个奶牛拥有 ai 个苹果，现在你要在它们之间转移苹果，使得最后所有奶牛拥有的苹果数都相同，每一次，你只能从一只奶牛身上拿走恰好两个苹果到另一个奶牛上，问最少需要移动多少次可以平分苹果，如果方案不存在输出 -1。
	输入描述:
	每个输入包含一个测试用例。每个测试用例的第一行包含一个整数 n（1 <= n <= 100），接下来的一行包含 n 个整数 ai（1 <= ai <= 100）。
	输出描述:
	输出一行表示最少需要移动多少次可以平分苹果，如果方案不存在则输出 -1。
	示例1
	输入
	
	4
	7 15 9 5
	输出

	3
	
	思路：
	首先，我们需要确认输入数组是否能有整数的平均数，若不能有整数的平均数，则返回-1
	题目中有约束，每次只能移动两个苹果，所以在计算出平均数后，需要确认每个数是否可以通过n次移动两个苹果，
	达到平均数，即与平均数的差是否可以被2整除，若不行，则输出-1
	最后进入统计阶段，这里只需要统计比平均数小的数即可，若遍历到的数比平均数小，
	取两者的差，除以2就是需要移动的次数。最后累加即可得到答案
	 */
	public static void main(String[] args){
		Scanner scanner=new Scanner(System.in);
		int n=scanner.nextInt();
		int[] input=new int[n];
		int sum=0,avg=0,count=0;
		for(int i=0;i<n;i++){
			input[i]=scanner.nextInt();
			sum+=input[i];
		}
		if(sum%n!=0){
			System.out.println("-1");
			return;
		}else
			avg=sum/n;
		for(int i=0;i<n;i++){
			if(input[i]!=avg){
				if(Math.abs(avg-input[i])%2!=0){
					System.out.println("-1");
					return;
				}else{
					count+=avg>input[i]?(avg-input[i])/2:0;
				}
			}
		}
		System.out.println(count);
	}
}
