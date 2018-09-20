package fuckboss;

import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int m,n,k;
		m=in.nextInt();
		n=in.nextInt();
		k=in.nextInt();
		in.nextLine();
		String target=in.nextLine();
		String[] data=target.split(" ");
		char[][] dic=new char[n][m];
		for(int i=0;i<n;i++){
			String tmp=in.nextLine();
			String[] tmps=tmp.split(" ");
			for(int j=0;j<m;j++){
				dic[i][j]=tmps[j].charAt(0);
			}
		}
		
		for(int z=0;z<data.length;z++){
			find:
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					if(dic[i][j]==data[z].charAt(0)){
						if(search(dic, data[z], i, j, 0)){
							System.out.println(data[z]);
							break find;
						}
					}
				}
			}
		}
		
	}
	public static boolean search(char[][] dic,String target,int i,int j,int index){
		if(index>=target.length()){
			return true;
		}
		if(i<0||i>=dic.length||j<0||j>=dic[0].length||dic[i][j]!=target.charAt(index)){
			return false;
		}else{
			return search(dic,target,i-1,j,index+1)||search(dic,target,i+1,j,index+1)
					||search(dic,target,i,j-1,index+1)||search(dic,target,i,j+1,index+1);
		}
	}
}
