public class Solution {
	/*
	 * 题目描述：输入一个整数数组，
	 * 实现一个函数来调整该数组中数字的顺序，
	 * 使得所有的奇数位于数组的前半部分，
	 * 所有的偶数位于位于数组的后半部分，
	 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
	 * 
	 * 由于题目中要求相对位置不变，所以不能使用两个指针指向数组最左和最右进行移动交换
	 * 这里使用两个数组分别记录下奇数和偶数，然后再遍历两个数组写回原数组
	 */
	public void reOrderArray(int [] array) {
		int[] odd=new int[array.length],even=new int[array.length];
		int odd_index=0,even_index=0;
		for(int i=0;i<array.length;i++){
			if(array[i]%2==1)
				odd[odd_index++]=array[i];
			else
				even[even_index++]=array[i];
		}
		for(int i=0;i<odd_index;i++){
			array[i]=odd[i];
		}
		for(int i=odd_index,j=0;i<array.length;i++,j++){
			array[i]=even[j];
		}
	}
}