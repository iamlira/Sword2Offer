public class Solution {
	/*
	 * ��Ŀ����:��ת��������ĵ�m������n��
	 * ˼·:��ReverseLinkedList˼·һ��������1->2->3->4,��ת����Ϊ1<-2 3->4,1<-2<-3 4,1<-2<-3<-4...
	 * ���������������������μ�¼��ת�е�ǰ�к���������Ȼ��ѭ����ת
	 * ��һ��dummyָ�룬dummy.next=head,����ʱ����dummy.next������ͷָ����ô�䣬��������dummy.next�ҵ�ͷ
	 * ���ⲻͬ���Ƿ�ת���䣬�������ҵ���ʼ�㣬Ȼ�������n��������ϸ�ڲ���Ҫע��
	 */
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode result=new ListNode(0);
		result.next=head;
		if(m==n)//���m==n������Ҫ��ת
			return result.next;
		ListNode prev=result,cur=head,next=cur.next;
		for(int i=1;i<m;i++){//��1��ʼ����m
			prev=prev.next;
			cur=cur.next;
			next=cur.next;
		}
		ListNode tmp=prev,temp=cur;//tmp��ʾ��m-1���ڵ㣬temp��ʾ��ת��ĵ�n���ڵ�
		while(m<=n){//ע����m<=n
			cur.next=prev;
			prev=cur;
			cur=next;
			if(next!=null)
				next=next.next;
			m++;
		}
		temp.next=cur;//�������ڵ�����ת�ú�ԭ���ĵ�n���ڵ������ڵ�����ĵ�һ����ԭ����m�������������һ��
		tmp.next=prev;//���԰ѵ�n���ڵ��¼����ֵ����m-1��next,���ڵ�cur�ǵ�n+1��������temp.next=cur;
		return result.next;
	}
}