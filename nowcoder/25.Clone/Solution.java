public class Solution {
	/*
	 * ��Ŀ����:����һ����������ÿ���ڵ����нڵ�ֵ���Լ�����ָ�룬һ��ָ����һ���ڵ㣬��һ������ָ��ָ������һ���ڵ㣩��
	 * ���ؽ��Ϊ���ƺ��������head��
	 * ��ע�⣬���������벻Ҫ���ز����еĽڵ����ã�������������ֱ�ӷ��ؿգ�
	 * ˼·:A->B->C ���A->A'->B->B'->C->C'��Ȼ����и�ֵ�����
	 */
	public RandomListNode Clone(RandomListNode pHead)
	{
		if(pHead==null)
			return null;
		RandomListNode result=new RandomListNode(0),tmp=pHead,cur;
		//��һ��������next ��ԭ����A->B->C ���A->A'->B->B'->C->C'
		while(tmp!=null){
			RandomListNode clone=new RandomListNode(0);
			clone.label=tmp.label;
			clone.next=tmp.next;
			tmp.next=clone;
			tmp=clone.next;
		}
		//����random tmp��ԭ������Ľ�� tmp.next�Ǹ���tmp�Ľ��
		tmp=pHead;
		while(tmp!=null){
			RandomListNode clone=tmp.next;
			if(tmp.random!=null)
				clone.random=tmp.random.next;
			tmp=clone.next;
		}
		//������ ��������Ϊ��������
		tmp=pHead;//���ڱ���ԭ�е�����ڵ�
		result.next=pHead.next;//���������ͷ���
		cur=result.next;//���ڱ������������ͷ���
		while(cur!=null){
			tmp.next=cur.next;
			if(cur.next!=null)//���һ���ڵ��nextΪ�գ�����Ҫ����
				cur.next=cur.next.next;
			cur=cur.next;
			tmp=tmp.next;
		}
		return result.next;
	}
}