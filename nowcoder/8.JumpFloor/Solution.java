public class Solution {
	public int JumpFloor(int target) {//ͬ쳲���������,������1,2���е㲻ͬ,����ʹ�õݹ�д��
		if(target<=0)
			return 0;
		else if(target==1)
			return 1;
		else if(target==2)
			return 2;
		return JumpFloor(target-1)+JumpFloor(target-2);
    }
}