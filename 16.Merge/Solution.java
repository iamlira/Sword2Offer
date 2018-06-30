public class Solution {
	/*
	 * 连接两个排序链表是一个递归过程
	 * 定义 好递归结束条件
	 * 每次获取较小值的节点，然后记录，再将该节点的next赋成下次递归的结果即可
	 */
	public ListNode Merge(ListNode list1,ListNode list2) {
		if(list1==null)//若一个节点为空，则返回另外一个节点
			return list2;
		else if(list2==null)
			return list1;
		ListNode result;
		if(list1.val<list2.val){
			result=list1;
			result.next=Merge(list1.next, list2);
		}
		else{
			result=list2;
			result.next=Merge(list1,list2.next);
		}
		return result;
	}
}