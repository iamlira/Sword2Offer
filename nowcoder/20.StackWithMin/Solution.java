public class Solution {
	/*
	 * ��Ŀ����������ջ�����ݽṹ�����ڸ�������ʵ��һ���ܹ��õ�ջ��СԪ�ص�min������
	 * ��������ջ��һ����������������ѹ�룬����һ��������¼��ǰջ����СԪ��
	 * ����������ÿ��ѹ��ʱ����������һ��ջѹ����Сֵ����Ϊ��ջ����Զ����Сֵ�����Կɽ�
	 */
	Stack<Integer> stack=new Stack<>();
	Stack<Integer> stack_min=new Stack<>();
	public void push(int node) {
		stack.push(node);
		int tmp=stack_min.empty()?Integer.MAX_VALUE:stack_min.peek();
		stack_min.push(tmp<node?tmp:node);
	}

	public void pop() {
		if(!stack.isEmpty())
			stack.pop();
		if(!stack_min.isEmpty())
			stack_min.pop();
	}

	public int top() {
		return stack.peek();
	}

	public int min() {
		return stack_min.peek();
	}
}