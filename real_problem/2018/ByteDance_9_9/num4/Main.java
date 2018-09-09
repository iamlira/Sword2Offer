package fuckboss;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//------input------
		Scanner in = new Scanner(System.in);
		int n,tmp,count;
		n=in.nextInt();
		//-----------------
		count=0;
		
		for(int i=0;i<n;i++){
			tmp=in.nextInt();
			
			if(count==0){
				if((tmp&0b1000000)==0){
					count=0;
				}
				else if((tmp&0b11100000)==0b11000000){
					count=1;
				}
				else if((tmp&0b11110000)==0b11100000){
					count=2;
				}
				else if((tmp&0b11111000)==0b11110000){
					count=3;
				}
				else{
					System.out.println(0);
					return ;
				}
			}
			else{
				if((tmp&0b11000000)!=0b10000000){
					System.out.println(0);
					return ;
				}
				else{
					count--;
				}
			}
			 
		}
		
		System.out.println(1);

	}

}
