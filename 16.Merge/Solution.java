public class Solution {
	/*
	 * ������������������һ���ݹ����
	 * ���� �õݹ��������
	 * ÿ�λ�ȡ��Сֵ�Ľڵ㣬Ȼ���¼���ٽ��ýڵ��next�����´εݹ�Ľ������
	 */
	public ListNode Merge(ListNode list1,ListNode list2) {
		if(list1==null)//��һ���ڵ�Ϊ�գ��򷵻�����һ���ڵ�
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