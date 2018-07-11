public class Solution {
	/*
	 * 题目描述:输入两个链表，找出它们的第一个公共结点。
	 * 思路:同样是以空间换时间的思路，第一次正向遍历并不是很好找到公共点，所以从尾部向前找
	 * 从后往前的思路则与栈联系在一起，把两个数组遍历，并压入栈中，然后每次pop比较是否一致，如果一致，则把Node记录下来
	 * 直到不一致的时候，则common记录的是上一个一致的Node，返回common即可
	 */
	public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
		ListNode common = null;
		Stack<ListNode> stack_1=new Stack<>();
		Stack<ListNode> stack_2=new Stack<>();
		while(pHead1!=null){//压入栈中
			stack_1.push(pHead1);
			pHead1=pHead1.next;
		}
		while(pHead2!=null){//压入栈中
			stack_2.push(pHead2);
			pHead2=pHead2.next;
		}
		while(!stack_1.isEmpty()&&!stack_2.isEmpty()){
			if(stack_1.peek()!=stack_2.peek()){//如果不一致，则已经找到第一个相同Node，break并返回common
				break;
			}
			common=stack_1.pop();//把一致的Node记录下来
			stack_2.pop();
		}
		return common;
	}
}