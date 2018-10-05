public class Solution {
	/*
	 * 题目描述：输入一个整型数组，数组里有正数也有负数。
	 * 数组中一个或连续的多个整数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为O(n)。
	 * 例如输入的数组为{1, -2, 3, 10, -4, 7, 2, -5}，和最大的子数组为｛3, 10, -4, 7, 2}。
	 * 因此输出为该子数组的和18 。
	 * 思路:我们试着从头到尾逐个累加示例数组中的每个数字。初始化和为0。
	 * 第一步加上第一个数字1， 此时和为1。接下来第二步加上数字-2，和就变成了-1。第三步刷上数字3。
	 * 我们注意到由于此前累计的和是－1 ，小于0，那如果用-1 加上3 ，得到的和是2 ， 比3 本身还小。
	 * 也就是说从第一个数字开始的子数组的和会小于从第三个数字开始的子数组的和。因此我们不用考虑从第一个数字开始的子数组，
	 * 之前累计的和也被抛弃。
	 */
	public int FindGreatestSumOfSubArray(int[] array) {
		if(array==null||array.length==0)
			return 0;
		int max=Integer.MIN_VALUE,curmax=0;
		for(int i=0;i<array.length;i++){
			if(curmax<=0)
				curmax=array[i];
			else
				curmax+=array[i];
			if(curmax>max)
				max=curmax;
		}
		return max;
	}
}