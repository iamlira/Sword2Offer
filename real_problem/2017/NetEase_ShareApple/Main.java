import java.util.*;

public class Main{
	/*
	 * ��Ŀ����
	n ֻ��ţ����һ�ţ�ÿ����ţӵ�� ai ��ƻ����������Ҫ������֮��ת��ƻ����ʹ�����������ţӵ�е�ƻ��������ͬ��ÿһ�Σ���ֻ�ܴ�һֻ��ţ��������ǡ������ƻ������һ����ţ�ϣ���������Ҫ�ƶ����ٴο���ƽ��ƻ�������������������� -1��
	��������:
	ÿ���������һ������������ÿ�����������ĵ�һ�а���һ������ n��1 <= n <= 100������������һ�а��� n ������ ai��1 <= ai <= 100����
	�������:
	���һ�б�ʾ������Ҫ�ƶ����ٴο���ƽ��ƻ���������������������� -1��
	ʾ��1
	����
	
	4
	7 15 9 5
	���

	3
	
	˼·��
	���ȣ�������Ҫȷ�����������Ƿ�����������ƽ��������������������ƽ�������򷵻�-1
	��Ŀ����Լ����ÿ��ֻ���ƶ�����ƻ���������ڼ����ƽ��������Ҫȷ��ÿ�����Ƿ����ͨ��n���ƶ�����ƻ����
	�ﵽƽ����������ƽ�����Ĳ��Ƿ���Ա�2�����������У������-1
	������ͳ�ƽ׶Σ�����ֻ��Ҫͳ�Ʊ�ƽ����С�������ɣ���������������ƽ����С��
	ȡ���ߵĲ����2������Ҫ�ƶ��Ĵ���������ۼӼ��ɵõ���
	 */
	public static void main(String[] args){
		Scanner scanner=new Scanner(System.in);
		int n=scanner.nextInt();
		int[] input=new int[n];
		int sum=0,avg=0,count=0;
		for(int i=0;i<n;i++){
			input[i]=scanner.nextInt();
			sum+=input[i];
		}
		if(sum%n!=0){
			System.out.println("-1");
			return;
		}else
			avg=sum/n;
		for(int i=0;i<n;i++){
			if(input[i]!=avg){
				if(Math.abs(avg-input[i])%2!=0){
					System.out.println("-1");
					return;
				}else{
					count+=avg>input[i]?(avg-input[i])/2:0;
				}
			}
		}
		System.out.println(count);
	}
}