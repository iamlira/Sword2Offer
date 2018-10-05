public class Solution {
	/*
	 * ��Ŀ����:��ֻ����������2��3��5��������������Ugly Number����
	 * ����6��8���ǳ�������14���ǣ���Ϊ����������7��
	 * ϰ�������ǰ�1�����ǵ�һ���������󰴴�С�����˳��ĵ�N��������
	 * ˼·������˼·���ÿռ任ʱ�䣬���ǰ�֮ǰ��֪�ĳ�����¼����
	 * uglyNum�������index��С���������������¼������
	 * ��һ�������ĳ�����1
	 * Ȼ��ÿ����2,3,5ȥ�ˣ��õ���С�ģ�Ȼ���¼������������β���
	 * ����ó����һ����������ֵ���ǽ�
	 */
	public int GetUglyNumber_Solution(int index) {
		if(index<=0)
			return 0;
		int[] uglyNum=new int[index];
		int nextindex=1,index2=0,index3=0,index5=0;//��һ����Ҫ��¼��������nextindex��index2��index3��index5��������ֵҪ����2,3,5
		uglyNum[0]=1;//��һ������ֵΪ1
		while(nextindex<index){
			int tmp=uglyNum[index2]*2<uglyNum[index3]*3?uglyNum[index2]*2:uglyNum[index3]*3;
			tmp=tmp<uglyNum[index5]*5?tmp:uglyNum[index5]*5;//Ҫ�õ����������е���Сֵȥ�����������
			uglyNum[nextindex]=tmp;
			//�ҵ�ÿ��Ҫ��2,3,5����������������������2,3,5֮��һ��Ҫ�ȵ�ǰ�ҵ��ĳ���С���ſ�����Ϊ��һ������
			while(uglyNum[index2]*2<=tmp){
				index2++;
			}
			while(uglyNum[index3]*3<=tmp){
				index3++;
			}
			while(uglyNum[index5]*5<=tmp){
				index5++;
			}
			nextindex++;
		}
        return uglyNum[index-1];
    }
}