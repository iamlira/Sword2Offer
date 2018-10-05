public class Solution {
	/*
	 * ��������£������ڷ�ת�����У����ֳ����Σ�
	 * һ����ǰ�淴ת�õ�����һ���ǵ�ǰ���з�ת�Ľڵ㣬һ����ʣ�������
	 * ������Ҫ����node����ȥ��λ����������
	 */
	public ListNode ReverseList(ListNode head) {
		if(head==null)//������Ϊnull���򷵻�null
			return null;
		ListNode prev = null,cur = head,next;//����node����
		if(head.next!=null){//��ֻ��һ��node����ֱ�ӷ���node
			cur=head;
			next=cur.next;
		}else
			return cur;
		while(next!=null){//���з�ת����
			cur.next=prev;
			prev=cur;
			cur=next;
			next=next.next;
		}
		cur.next=prev;//�����while������next�ڵ�Ϊ�գ���nextΪ��ʱ�����һ��cur node��δ���з�ת�������������
		return cur;
	}
}