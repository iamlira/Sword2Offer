public class Solution {
	/*
	 * 可以得到f(n)=f(n-1)+f(n-2)+....+f(1)+1
	 * 而f(n-1)=f(n-2)+....+f(1)+1,两个式子相减
	 * 得到f(n) = 2f(n-1),很明显可以得到f(n)=2^(n-1)。
	 */
	public int JumpFloorII(int target) {
		if(target<=0)
			return 0;
		if(target==1)
			return 1;
		int count=2,result=1;;
		while(count<=target){
			result*=2;
			count++;
		}
		return result;
	}
}