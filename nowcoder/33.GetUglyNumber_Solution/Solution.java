public class Solution {
	/*
	 * 题目描述:把只包含质因子2、3和5的数称作丑数（Ugly Number）。
	 * 例如6、8都是丑数，但14不是，因为它包含因子7。
	 * 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
	 * 思路：本体思路是用空间换时间，我们把之前已知的丑数记录下来
	 * uglyNum被定义成index大小的数组就是用来记录丑数的
	 * 第一个索引的丑数是1
	 * 然后每次用2,3,5去乘，得到最小的，然后记录在数组最后，依次操作
	 * 最后拿出最后一个索引处的值就是解
	 */
	public int GetUglyNumber_Solution(int index) {
		if(index<=0)
			return 0;
		int[] uglyNum=new int[index];
		int nextindex=1,index2=0,index3=0,index5=0;//下一个需要记录的索引是nextindex，index2、index3、index5索引处的值要乘上2,3,5
		uglyNum[0]=1;//第一个索引值为1
		while(nextindex<index){
			int tmp=uglyNum[index2]*2<uglyNum[index3]*3?uglyNum[index2]*2:uglyNum[index3]*3;
			tmp=tmp<uglyNum[index5]*5?tmp:uglyNum[index5]*5;//要拿到这三个数中的最小值去放在数组最后
			uglyNum[nextindex]=tmp;
			//找到每次要乘2,3,5的索引，条件是索引乘上2,3,5之后一定要比当前找到的丑数小，才可以作为下一个索引
			while(uglyNum[index2]*2<=tmp){
				index2++;
			}
			while(uglyNum[index3]*3<=tmp){
				index3++;
			}
			while(uglyNum[index5]*5<=tmp){
				index5++;
			}
			nextindex++;
		}
        return uglyNum[index-1];
    }
}