public class Solution {
	public int RectCover(int target) {//ͬ쳲��������У��������ѭ��ʵ��
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