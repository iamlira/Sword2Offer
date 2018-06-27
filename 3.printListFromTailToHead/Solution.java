public class Solution {
	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {//利用堆栈的特性实现从尾到头
		ArrayList<Integer> result=new ArrayList<>();//使用Arraylist作为堆栈，每次从头部加入，实现和堆栈相同的效果
		while(listNode!=null){
			result.add(0, listNode.val);
			listNode=listNode.next;
		}
		return result;
	}
}