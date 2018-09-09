package fuckboss;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	public static int sum=0;
	public static void main(String[] args) {
		//-------input------
		Scanner in = new Scanner(System.in);
		int n,m;
		n=in.nextInt();
		m=in.nextInt();
		int[][] graph=new int[n+1][n+1];
		for(int i=1;i<=n;i++){
			graph[i][i]=1;
		}
		for(int i=0;i<m;i++){
			int a=in.nextInt();
			int b=in.nextInt();
			graph[b][a]=1;
		}
		//-----------------
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				if(graph[i][j]==1&&i!=j){
					search(graph,i,j);
				}
			}
		}
		int count=0;
		for(int i=1;i<=n;i++){
			int bool=0;
			for(int j=0;j<=n;j++){
				bool+=graph[i][j];
			}
			count+=bool==n?1:0;
		}
		System.out.println(count);
	}
	public static void search(int[][] graph,int i,int j){
		Queue<Integer> que=new LinkedList<>();
		que.offer(j);
		while(!que.isEmpty()){//每次粉丝入队，再从粉丝的粉丝里找，如果该粉丝不是i索引的粉丝，则继续入队，一层一层往下找
			int tmp=que.poll();
			for(int x=1;x<graph.length;x++){
				if(graph[tmp][x]==1&&tmp!=x&&x!=i&&graph[i][x]!=1){//注意判断条件
					graph[i][x]=1;
					que.offer(x);
				}
			}
		}
	}

}
