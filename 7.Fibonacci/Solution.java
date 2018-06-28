public class Solution {
	public int Fibonacci(int n) {//使用循环，不断记录当前值，和前两项值，每次循环当前值等于前两项之和
		if(n==0)
			return 0;
		int f1=1,f2=1,fn=1;
		for(int i=1;i<=n;i++){
			if(i==1||i==2)
				fn=1;
			else{
				fn=f1+f2;
				f1=f2;
				f2=fn;
			}
		}
		return fn;
		/*if(n==2||n==1)//递归做法
			return 1;
		else
			return Fibonacci(n-1)+Fibonacci(n-2);*/
    }
}