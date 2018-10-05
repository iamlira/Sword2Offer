public class Solution {
	/*
	 * 题目描述:给一个压入栈的数组，和一个弹出栈的数组，判断弹出栈是否是压入栈的弹出顺序
	 * 思路:使用一个辅助栈，一次压入压入栈数组的数字，并每次判断辅助栈的peek和弹出栈当前是否相等
	 * 若不等，则继续压入，若相等则弹出，最后如果压入栈数组和弹出栈数组索引都超出长度后，判断辅助栈是否为空
	 * 如果判断期间，压入栈没有数字可以压入，弹出栈索引小于数组长度，且辅助栈不为空则无法进行操作，则返回false
	 */
	public boolean IsPopOrder(int [] pushA,int [] popA) {
	      boolean result=false;
	      Stack<Integer> stack_push=new Stack<>();
	      int index_push=0,index_pop=0;
	      while(index_pop<popA.length){
	    	  if(stack_push.isEmpty()){//栈一开始是空，压入数字
	    		  stack_push.push(pushA[index_push]);
	    		  index_push++;
	    	  }
	    	  while(stack_push.peek()!=popA[index_pop]&&index_push<pushA.length){//如果peek不等，一直压入
	    		  stack_push.push(pushA[index_push]);
	    		  index_push++;
	    	  }
	    	  if(!stack_push.isEmpty()&&stack_push.peek()==popA[index_pop]){//如果peek相等，则弹出，pop索引加1
	    		  stack_push.pop();
	    		  index_pop++;
	    	  }
	    	  if(!stack_push.isEmpty()&&index_pop<popA.length&&stack_push.peek()!=popA[index_pop]&&!(index_push<pushA.length)){
	    		  return false;//直接返回false
	    	  }
	      }
	      if(stack_push.isEmpty())
	    	  result=true;
	      return result;
    }
}