public class Solution {
	/*
	 * 题目描述：定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
	 * 声明两个栈，一个用于正常的数据压入，另外一个用作记录当前栈的最小元素
	 * 操作声明，每次压入时，都在另外一个栈压入最小值，因为该栈顶永远是最小值，所以可解
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