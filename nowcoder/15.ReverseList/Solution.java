public class Solution {
	/*
	 * 正常情况下，链表在反转过程中，会拆分成三段，
	 * 一段是前面反转好的链表，一个是当前进行反转的节点，一个是剩余的链表
	 * 所以需要三个node变量去定位上述三部分
	 */
	public ListNode ReverseList(ListNode head) {
		if(head==null)//若传入为null，则返回null
			return null;
		ListNode prev = null,cur = head,next;//三个node变量
		if(head.next!=null){//若只有一个node，则直接返回node
			cur=head;
			next=cur.next;
		}else
			return cur;
		while(next!=null){//进行反转操作
			cur.next=prev;
			prev=cur;
			cur=next;
			next=next.next;
		}
		cur.next=prev;//上面的while条件是next节点为空，当next为空时，最后一个cur node还未进行反转，所以在这里加
		return cur;
	}
}