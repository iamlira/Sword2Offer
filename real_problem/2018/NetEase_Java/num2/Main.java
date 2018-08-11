package fuckboss;

import java.util.*;

public class Main {
	/*
	 * 题目描述：
	 * 给出一系列数字，代表每堆苹果的个数，然后再给出一系列数字，
	 * 每个数字代表这个苹果的索引号
	 * 现在需要输出每个苹果在第几个堆
	 * 
	 * 输入
	 * 2 7 3
	 * 1 5
	 * 输出
	 * 1
	 * 2
	 * 
	 * 思路：
	 * 可以把输入数组一次累加
	 * 例如上例可以计算成 2 9 12
	 * 然后计算给出苹果和上述数组的比较大小
	 * 若比第i个小，就是再哪个堆里
	 * 
	 * 如果直接暴力解，会超时，所以使用二分法
	 */
    private static int BinarySearch(int q, int[] arr_sum) {

        int mid,left,right;

        left=0;
        right=arr_sum.length-1;

        while(left<right){
            mid=(left+right)/2;
            if(arr_sum[mid]==q){
                return mid+1;
            }
            else if(arr_sum[mid]<q){
                left=mid+1;
            }
            else{
                right=mid;
            }
        }
        return left+1;
    }
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String temp=scan.nextLine();
        String[] tempArr;
        int n=Integer.parseInt(temp);
        temp=scan.nextLine();
        tempArr=temp.split(" ");
        int[] apple=new int[n];
        for(int i=0;i<tempArr.length;i++)
        {
            apple[i]=Integer.parseInt(tempArr[i]);
        }
        for(int i=1;i<n;i++)
        {
            apple[i]=apple[i]+apple[i-1];
        }
        int m=scan.nextInt();
        int[] need_ans=new int[m];
        for(int i=0;i<m;i++){
            need_ans[i]=scan.nextInt();
        }
        /************************输入输出结束，开始计算*********************/
        for(int i=0;i<m;i++) {
            System.out.println(BinarySearch(need_ans[i],apple));
        }
    }
}
