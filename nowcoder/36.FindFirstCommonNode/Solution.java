public class Solution {
	/*
	 * ��Ŀ����:�������������ҳ����ǵĵ�һ��������㡣
	 * ˼·:ͬ�����Կռ任ʱ���˼·����һ��������������Ǻܺ��ҵ������㣬���Դ�β����ǰ��
	 * �Ӻ���ǰ��˼·����ջ��ϵ��һ�𣬰����������������ѹ��ջ�У�Ȼ��ÿ��pop�Ƚ��Ƿ�һ�£����һ�£����Node��¼����
	 * ֱ����һ�µ�ʱ����common��¼������һ��һ�µ�Node������common����
	 */
	public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
		ListNode common = null;
		Stack<ListNode> stack_1=new Stack<>();
		Stack<ListNode> stack_2=new Stack<>();
		while(pHead1!=null){//ѹ��ջ��
			stack_1.push(pHead1);
			pHead1=pHead1.next;
		}
		while(pHead2!=null){//ѹ��ջ��
			stack_2.push(pHead2);
			pHead2=pHead2.next;
		}
		while(!stack_1.isEmpty()&&!stack_2.isEmpty()){
			if(stack_1.peek()!=stack_2.peek()){//�����һ�£����Ѿ��ҵ���һ����ͬNode��break������common
				break;
			}
			common=stack_1.pop();//��һ�µ�Node��¼����
			stack_2.pop();
		}
		return common;
	}
}