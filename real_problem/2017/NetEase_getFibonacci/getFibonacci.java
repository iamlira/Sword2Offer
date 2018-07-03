import java.util.Scanner;

public class getFibonacci {
	/*
	 * 题目描述:Fibonacci数列就形如：0, 1, 1, 2, 3, 5, 8, 13, ...
	 * 在Fibonacci数列中的数我们称为Fibonacci数。
	 * 给你一个N，你想让其变为一个Fibonacci数，每一步你可以把当前数字X变为X-1或者X+1，
	 * 现在给你一个数N求最少需要多少步可以变为Fibonacci数。
	 * 思路:拿到N后，用循环求出斐波那契数F(n),使得F(n)<=N<F(n+1)
	 * 然后打印出较小的差值即可
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
        while (in.hasNext())
        {
            int N = in.nextInt();
            int k_1=0,k_2=1,cur=k_1,small=cur,big=cur;
            while(true){
            	if(cur<=N)
            		small=cur;
            	else{
            		big=cur;
            		break;
            	}
            	cur=k_1+k_2;
            	k_1=k_2;
            	k_2=cur;
            }
            System.out.println((N-small)<(big-N)?(N-small):(big-N));
        }
	}

}
