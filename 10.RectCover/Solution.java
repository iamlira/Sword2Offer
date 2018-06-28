public class Solution {
	public int RectCover(int target) {//同斐波那契数列，这里采用循环实现
		if(target<=0)
			return 0;
		int f1=1,f2=2,fn=0;
		for(int i=1;i<=target;i++){
			if(i==1)
				fn=f1;
			else if(i==2)
				fn=f2;
			else{
				fn=f1+f2;
				f1=f2;
				f2=fn;
			}
		}
		return fn;
    }
}