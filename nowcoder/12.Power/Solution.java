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
//        result=pow(base,exponent);//递归
        if(negative)
        	result=1d/result;
        return result;
	  }
	/*
	 * 采用递归写法，可以得出公式：
	 * a^n=(1.当指数为偶数)a^(n/2)*a^(n/2),(2.当指数为奇数)a^((n-1)/2)*a^((n-1)/2)*a
	 */
	public double pow(double base,int exponent){
		if(exponent==1)
			return base;
		if(exponent==0)
			return 1d;
		double result=pow(base,exponent>>1);//使用右移一位，无论奇数偶数都适用
		result*=result;
		if((exponent&1)==1)//若是奇数，再乘一个底数
			result*=base;
		return result;
	}
}