package fuckboss;

import java.util.Scanner;

public class Ali {
	/*
	 * ÊäÈë:4 2,2 2,8 4,4 7,2
	Êä³ö:30
	ÊäÈë·¶Àı:3 2,2 2,8 6,6
	Êä³ö·¶Àı:28
	 */
	static int min=Integer.MAX_VALUE;
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int[] x=new int[n];
		int[] y=new int[n];
		int[] arr=new int[n];
		sc.nextLine();
		for(int i=0;i<n;i++){
			String tmp=sc.nextLine();
			String[] tmps=tmp.split(",");
			x[i]=Integer.valueOf(tmps[0]);
			y[i]=Integer.valueOf(tmps[1]);
			arr[i]=i;
		}
		
		int[][] dis=new int[n+1][n];
		for(int i=0;i<n;i++){
			for(int j=i+1;j<n;j++){
				dis[i][j]=Math.abs(x[i]-x[j])+Math.abs(y[i]-y[j]);
				dis[j][i]=dis[i][j];
			}
		}
		for(int i=0;i<n;i++){
			dis[n][i]=x[i]+y[i];
		}
		search(arr,dis,0);
		System.out.println(min);
	}
	public static void search(int[] arr,int[][] dis,int index){
		if(index==arr.length){
			int sum=0;
			for(int i=0;i<arr.length-1;i++){
				sum+=dis[arr[i]][arr[i+1]];
			}
			sum+=dis[arr.length][arr[0]]+dis[arr.length][arr[arr.length-1]];
			if(sum<min)
				min=sum;
		}else{
			for(int i=index;i<arr.length;i++){
				swap(arr,index,i);
				search(arr,dis,index+1);
				swap(arr,index,i);
			}
		}
	}
	public static void swap(int[] arr,int i,int j){
		int tmp=arr[i];
		arr[i]=arr[j];
		arr[j]=tmp;
	}
}
