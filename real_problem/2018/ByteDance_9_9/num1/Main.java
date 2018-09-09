package fuckboss;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		char[] str=in.nextLine().toCharArray();
		
		int[] a=new int[257];
		int max,pre,index;
		max=0;
		pre=0;
		Arrays.fill(a, -1);
		
		for(int i=0;i<str.length;i++){
			if(a[str[i]]==-1){
				a[str[i]]=i;
			}
			else{
				index=a[str[i]];
				a[str[i]]=i;
				if(index>=pre){
					pre=index+1;
				}
			}
			max=Math.max(max, i-pre+1);
		}
		
		System.out.println(max);

	}

}
