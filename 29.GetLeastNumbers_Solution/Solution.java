public class Solution {
	/*
	 * 题目描述：输入n个整数，找出其中最小的K个数。
	 * 例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
	 * 思路：使用辅助k大小的空间去记录每次的最小的k个数，每次比辅助空间中最大的值要小时就去掉辅助空间中的最大值，加入这个值
	 * 遍历完input即可得到答案
	 * 剑指offer中使用的stl中的树结构，这里采用数组，每次加入后使用快排进行排序，这里辅助数组最后一个数字就是最大值
	 */
	public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
		ArrayList<Integer> result=new ArrayList<>();
		if(input.length<k||k==0)//这里的条件很坑爹，不合理
			return result;
		int[] tmp=new int[k];
		for(int i=0;i<k;i++){//赋值最大值
			tmp[i]=Integer.MAX_VALUE;
		}
		for(int i=0;i<input.length;i++){//每次遍历的数字比k辅助空间最大的要小，就把最大的赋值成当前数字
			if(input[i]<tmp[k-1]){
				tmp[k-1]=input[i];
			    java.util.Arrays.sort(tmp);//然后做排序，确保每次加入后辅助空间最后一个是最大数字
			}
		}
		for(int i=0;i<k;i++)
			result.add(tmp[i]);
		return result;
	}
}