public class Solution {
	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {//���ö�ջ������ʵ�ִ�β��ͷ
		ArrayList<Integer> result=new ArrayList<>();//ʹ��Arraylist��Ϊ��ջ��ÿ�δ�ͷ�����룬ʵ�ֺͶ�ջ��ͬ��Ч��
		while(listNode!=null){
			result.add(0, listNode.val);
			listNode=listNode.next;
		}
		return result;
	}
}