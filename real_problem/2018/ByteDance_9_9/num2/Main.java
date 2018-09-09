package fuckboss;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//----input----
		Scanner in = new Scanner(System.in);
		int m = in.nextInt();
		int[][] area=new int[m][m];
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				area[i][j]=in.nextInt();
			}
		}
		//------------
		int count=0;
		for(int i=0;i<m;i++){
			for(int j=0;j<m;j++){
				if(area[i][j]==1){//对岛屿做深搜，如果是一个岛屿就count+1,然后深搜时把岛屿为1的都赋值0，就不会重复
					count+=1;
					dfs(area,i,j);
				}
			}
		}
		System.out.println(count);
	}
	public static void dfs(int[][] area,int i,int j){
		if(i<0||i>=area.length||j<0||j>=area[0].length||area[i][j]==0){
			return ;
		}
		area[i][j]=0;
		dfs(area,i+1,j);//上下左右作深搜
		dfs(area,i-1,j);
		dfs(area,i,j+1);
		dfs(area,i,j-1);
	}

}
