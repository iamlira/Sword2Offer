import java.util.Scanner;

public class getFibonacci {
	/*
	 * ��Ŀ����:Fibonacci���о����磺0, 1, 1, 2, 3, 5, 8, 13, ...
	 * ��Fibonacci�����е������ǳ�ΪFibonacci����
	 * ����һ��N�����������Ϊһ��Fibonacci����ÿһ������԰ѵ�ǰ����X��ΪX-1����X+1��
	 * ���ڸ���һ����N��������Ҫ���ٲ����Ա�ΪFibonacci����
	 * ˼·:�õ�N����ѭ�����쳲�������F(n),ʹ��F(n)<=N<F(n+1)
	 * Ȼ���ӡ����С�Ĳ�ֵ����
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
        while (in.hasNext())
        {
            int N = in.nextInt();
            int k_1=0,k_2=1,cur=k_1,small=cur,big=cur;
            while(true){
            	if(cur<=N)
            		small=cur;
            	else{
            		big=cur;
            		break;
            	}
            	cur=k_1+k_2;
            	k_1=k_2;
            	k_2=cur;
            }
            System.out.println((N-small)<(big-N)?(N-small):(big-N));
        }
	}

}
