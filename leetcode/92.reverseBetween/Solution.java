public class Solution {
	/*
	 * 题目描述:反转给定链表的第m个到第n个
	 * 思路:和ReverseLinkedList思路一样，例如1->2->3->4,反转过程为1<-2 3->4,1<-2<-3 4,1<-2<-3<-4...
	 * 这里声明三个变量，依次记录反转中的前中后三段链表，然后循环反转
	 * 用一个dummy指针，dummy.next=head,返回时返回dummy.next，无论头指针怎么变，都可以用dummy.next找到头
	 * 这题不同点是反转区间，所以先找到起始点，然后递增至n，索引的细节部分要注意
	 */
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode result=new ListNode(0);
		result.next=head;
		if(m==n)//如果m==n，则不需要反转
			return result.next;
		ListNode prev=result,cur=head,next=cur.next;
		for(int i=1;i<m;i++){//从1开始，到m
			prev=prev.next;
			cur=cur.next;
			next=cur.next;
		}
		ListNode tmp=prev,temp=cur;//tmp表示第m-1个节点，temp表示反转后的第n个节点
		while(m<=n){//注意是m<=n
			cur.next=prev;
			prev=cur;
			cur=next;
			if(next!=null)
				next=next.next;
			m++;
		}
		temp.next=cur;//将区间内的链表反转好后，原来的第n个节点是现在的区间的第一个，原来第m个则是区间最后一个
		tmp.next=prev;//所以把第n个节点记录，赋值给第m-1的next,现在的cur是第n+1个，所以temp.next=cur;
		return result.next;
	}
}