public class Solution {
	/*
	 * ��Ŀ����:��һ��ѹ��ջ�����飬��һ������ջ�����飬�жϵ���ջ�Ƿ���ѹ��ջ�ĵ���˳��
	 * ˼·:ʹ��һ������ջ��һ��ѹ��ѹ��ջ��������֣���ÿ���жϸ���ջ��peek�͵���ջ��ǰ�Ƿ����
	 * �����ȣ������ѹ�룬������򵯳���������ѹ��ջ����͵���ջ�����������������Ⱥ��жϸ���ջ�Ƿ�Ϊ��
	 * ����ж��ڼ䣬ѹ��ջû�����ֿ���ѹ�룬����ջ����С�����鳤�ȣ��Ҹ���ջ��Ϊ�����޷����в������򷵻�false
	 */
	public boolean IsPopOrder(int [] pushA,int [] popA) {
	      boolean result=false;
	      Stack<Integer> stack_push=new Stack<>();
	      int index_push=0,index_pop=0;
	      while(index_pop<popA.length){
	    	  if(stack_push.isEmpty()){//ջһ��ʼ�ǿգ�ѹ������
	    		  stack_push.push(pushA[index_push]);
	    		  index_push++;
	    	  }
	    	  while(stack_push.peek()!=popA[index_pop]&&index_push<pushA.length){//���peek���ȣ�һֱѹ��
	    		  stack_push.push(pushA[index_push]);
	    		  index_push++;
	    	  }
	    	  if(!stack_push.isEmpty()&&stack_push.peek()==popA[index_pop]){//���peek��ȣ��򵯳���pop������1
	    		  stack_push.pop();
	    		  index_pop++;
	    	  }
	    	  if(!stack_push.isEmpty()&&index_pop<popA.length&&stack_push.peek()!=popA[index_pop]&&!(index_push<pushA.length)){
	    		  return false;//ֱ�ӷ���false
	    	  }
	      }
	      if(stack_push.isEmpty())
	    	  result=true;
	      return result;
    }
}