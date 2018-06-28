public class Solution {
	/*
	 * 此题不可将所求数进行右移求解，因为负数在补码表示后，右移会填充1，将会陷入死循环
	 * 正解应该是将1进行左移求解
	 */
	public int NumberOf1(int n) {
		int count=0,flag=1;
		while(flag!=0){
			if((n&flag)!=0)
				count++;
			flag=flag<<1;
		}
		return count;
    }
}