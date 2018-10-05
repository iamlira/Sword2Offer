public class Solution {
	public int JumpFloor(int target) {//同斐波那契数列,不过第1,2项有点不同,这里使用递归写法
		if(target<=0)
			return 0;
		else if(target==1)
			return 1;
		else if(target==2)
			return 2;
		return JumpFloor(target-1)+JumpFloor(target-2);
    }
}