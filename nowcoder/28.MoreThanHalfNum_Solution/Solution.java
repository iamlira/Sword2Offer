public class Solution {
	/*
	 * 题目描述：数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
	 * 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，
	 * 超过数组长度的一半，因此输出2。如果不存在则输出0。
	 * 思路:这道题跟剑指offer略有出入，这道题的样例可能存在出现次数不超过总长度一半的数
	 * 原本的思路是用一个临时number和count记录当前遍历的数字，如果下一个数和当前number一样，则count++，
	 * 否则count--,如果count=0，则把数字赋值给number，count=1，遍历结束之后超过一半的数字肯定会被记录
	 * 而这题可能没有这样的数字，所以先把数组排序，如果存在一段相同数字则记录他的长度，若长度大于数组长度一半，则break
	 * 然后判断count是否大于数组一半去输出结果
	 */
	public int MoreThanHalfNum_Solution(int [] array) {
		int number=Integer.MIN_VALUE,count=0;
		Arrays.sort(array);
		for(int i=0;i<array.length;i++){
			if(number!=array[i]){
				number=array[i];
				count=1;
			}else if(number==array[i]){
				count++;
			}
			if(count>array.length/2)
				break;
		}
		return count>array.length/2?number:0;
	}
}