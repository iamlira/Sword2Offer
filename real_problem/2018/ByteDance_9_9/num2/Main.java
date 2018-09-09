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
				if(area[i][j]==1){//�Ե��������ѣ������һ�������count+1,Ȼ������ʱ�ѵ���Ϊ1�Ķ���ֵ0���Ͳ����ظ�
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
		dfs(area,i+1,j);//��������������
		dfs(area,i-1,j);
		dfs(area,i,j+1);
		dfs(area,i,j-1);
	}

}
