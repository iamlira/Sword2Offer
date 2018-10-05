public class Solution {
	/*
	 * 题目描述:输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
	 * 返回结果为复制后复杂链表的head。
	 * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
	 * 思路:A->B->C 变成A->A'->B->B'->C->C'，然后进行赋值，拆分
	 */
	public RandomListNode Clone(RandomListNode pHead)
	{
		if(pHead==null)
			return null;
		RandomListNode result=new RandomListNode(0),tmp=pHead,cur;
		//第一步：复制next 如原来是A->B->C 变成A->A'->B->B'->C->C'
		while(tmp!=null){
			RandomListNode clone=new RandomListNode(0);
			clone.label=tmp.label;
			clone.next=tmp.next;
			tmp.next=clone;
			tmp=clone.next;
		}
		//复制random tmp是原来链表的结点 tmp.next是复制tmp的结点
		tmp=pHead;
		while(tmp!=null){
			RandomListNode clone=tmp.next;
			if(tmp.random!=null)
				clone.random=tmp.random.next;
			tmp=clone.next;
		}
		//第三步 将链表拆分为两个链表
		tmp=pHead;//用于遍历原有的链表节点
		result.next=pHead.next;//复制链表的头结点
		cur=result.next;//用于遍历复制链表的头结点
		while(cur!=null){
			tmp.next=cur.next;
			if(cur.next!=null)//最后一个节点的next为空，不需要复制
				cur.next=cur.next.next;
			cur=cur.next;
			tmp=tmp.next;
		}
		return result.next;
	}
}