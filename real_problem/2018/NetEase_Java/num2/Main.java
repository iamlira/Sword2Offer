package fuckboss;

import java.util.*;

public class Main {
	/*
	 * ��Ŀ������
	 * ����һϵ�����֣�����ÿ��ƻ���ĸ�����Ȼ���ٸ���һϵ�����֣�
	 * ÿ�����ִ������ƻ����������
	 * ������Ҫ���ÿ��ƻ���ڵڼ�����
	 * 
	 * ����
	 * 2 7 3
	 * 1 5
	 * ���
	 * 1
	 * 2
	 * 
	 * ˼·��
	 * ���԰���������һ���ۼ�
	 * �����������Լ���� 2 9 12
	 * Ȼ��������ƻ������������ıȽϴ�С
	 * ���ȵ�i��С���������ĸ�����
	 * 
	 * ���ֱ�ӱ����⣬�ᳬʱ������ʹ�ö��ַ�
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
        /************************���������������ʼ����*********************/
        for(int i=0;i<m;i++) {
            System.out.println(BinarySearch(need_ans[i],apple));
        }
    }
}
