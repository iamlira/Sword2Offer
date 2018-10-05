public class Solution {
	/*
	 * 使用二分法，s和e代表左右索引，mid代表中间索引
	 */
	public int minNumberInRotateArray(int[] array) {
		int result = Integer.MAX_VALUE;
		if (array.length == 0)
			return 0;
		int s=0,e=array.length-1,mid=(s+e)/2;
		while(array[s]>=array[e]){//旋转数组二分查找时，若左边比右边大，则持续循环
			if(e-s<=1){//若s就在e右边，且array[s]>=array[e]，则e索引位置为最小值，退出循环
				mid=e;
				break;
			}
			if(array[s]==array[e]&&array[mid]==array[e]){//特殊情况，例如{1,1,1,0,1}情况则进入一下分支
				while(array[s]==array[e]){
					s++;
					mid=(s+e)/2;
					if(array[mid]<result)
						result=array[mid];
				}
				return result;
			}else if(array[mid]>=array[s]){//若mid索引值比s索引值大，则最小值在mid和e中间
				s=mid;
			}else if(array[mid]<=array[e]){//若mid索引值比e索引值小，则最小值在mid和s中间
				e=mid;
				if(array[mid]<result)
					result=array[mid];
			}
			mid=(s+e)/2;
				
		}
		return array[mid];
	}
}