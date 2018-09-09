package fuckboss;

import java.util.Scanner;

public class Main {
	public static int sum=0;
	public static void main(String[] args) {
		//-------input------
		Scanner in = new Scanner(System.in);
		char[] str=in.nextLine().toCharArray();
		//------------------
		int[] ip=new int[str.length];
		for(int i=0;i<str.length;i++){
			ip[i]=str[i]-'0';
		}
		
		dfs(ip,0,0);

		System.out.println(sum);

	}

	public static void dfs(int[] ip, int count,int index) {
		if(count==4){
			if(index>=ip.length){
				sum++;
			}
			return ;
		}
		
		if(index>=ip.length){
			return ;
		}
		
		int data=0;
		for(int i=0;i<3;i++){
			if(index<ip.length){
				data=data*10+ip[index];
				index++;
				if(data==0){
					dfs(ip, count+1, index);
					break;
				}
				else if(data<=255){
					dfs(ip, count+1, index);
				}
			}
		}
		
	}

}
