public class Solution {
	public double Power(double base, int exponent) {
        if(base==0&&exponent<=0)
        	return 0d;
        boolean negative=exponent<0?true:false;
        exponent=exponent>=0?exponent:-exponent;
        double result=1d;
        for(int i=1;i<=exponent;i++){
        	result*=base;
        }
//        result=pow(base,exponent);//�ݹ�
        if(negative)
        	result=1d/result;
        return result;
	  }
	/*
	 * ���õݹ�д�������Եó���ʽ��
	 * a^n=(1.��ָ��Ϊż��)a^(n/2)*a^(n/2),(2.��ָ��Ϊ����)a^((n-1)/2)*a^((n-1)/2)*a
	 */
	public double pow(double base,int exponent){
		if(exponent==1)
			return base;
		if(exponent==0)
			return 1d;
		double result=pow(base,exponent>>1);//ʹ������һλ����������ż��������
		result*=result;
		if((exponent&1)==1)//�����������ٳ�һ������
			result*=base;
		return result;
	}
}