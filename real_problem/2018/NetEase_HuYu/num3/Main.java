package fuckboss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
	/*
	 * 题目描述：
	 * 
	 * 第一行是总的测试阳历，
	 * 第二行是有多少时间段
	 * 第三行之后是游戏服务器一星期内开服的时间段
	 * 然后是上线时间测试个数
	 * 之后是上线时间点
	 * 
	 * 输入：
	 * 
		1
		4
		3 1 10:00:00-15:00:00
		1 2 08:00:00-14:00:00 18:00:00-20:00:00
		6 3 09:00:00-11:00:00 13:00:00-14:00:00 17:00:00-22:00:00
		7 3 09:00:00-10:30:00 13:30:00-14:00:00 17:30:00-22:00:00
		5
		2 19:03:30
		3 14:02:23
		1 02:00:00
		5 17:00:00
		4 13:13:13
		输出：
		各个测试时间点是否可以上线，如果可以上线，则输出0，如果不能上线则挑选最近的时间点上线，输出相距的秒数
		线下测试正确，线上不对，可能输入部分有bug，没有时间改
	 */
	public static void main(String[] args) throws ParseException {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int i=0;i<T;i++){
			int day_num=sc.nextInt();
			sc.nextLine();
			HashMap<Integer, List<String>> time=new HashMap<>(); 
			String[] tt=new String[day_num];
			for(int j=0;j<day_num;j++){
				tt[j]=sc.nextLine();
			}
			for(int j=0;j<day_num;j++){
//				String tmp=sc.nextLine();
				String[] tmps=tt[j].split(" ");
				int time_size=Integer.valueOf(tmps[1]);
				List<String> time_list=new ArrayList<>();
				for(int k=2;k<tmps.length;k++){
					time_list.add(tmps[k]);
				}
				time.put(Integer.valueOf(tmps[0]), new ArrayList<String>(time_list));
			}
			int test_num=sc.nextInt();
			sc.nextLine();
			String zz[]=new String[test_num];
			for(int j=0;j<test_num;j++){
				zz[j]=sc.nextLine();
			}
			label:
			for(int j=0;j<test_num;j++){
//				String tmp=sc.nextLine();
				String tmps[]=zz[j].split(" ");
				int day=Integer.valueOf(tmps[0]);
				SimpleDateFormat df=new SimpleDateFormat("hh:mm:ss"); 
				List<String> list_date=time.get(day);
				for(int k=0;list_date!=null&&k<list_date.size();k++){
					Date date_now=df.parse(tmps[1]);
					String[] str_compare=list_date.get(k).split("-");
					Date date_on=df.parse(str_compare[0]);
					Date date_off=df.parse(str_compare[1]);
					if(date_now.getTime()>=date_on.getTime()&&date_now.getTime()<=date_off.getTime()){
						System.out.println(0);
						continue label; 
					}else if(date_now.getTime()<date_on.getTime()){
						System.out.println((date_on.getTime()-date_now.getTime())/1000);
						continue label;
					}
				}
				int min=Integer.MAX_VALUE,day_index=0;
				for(int key:time.keySet()){
					if(key-day<min&&key-day>0){
						day_index=key;
						min=key-day;
					}
				}
				Date date_now=df.parse(tmps[1]);
				String[] str_compare=time.get(day_index).get(0).split("-");
				Date date_on=df.parse(str_compare[0]);
				date_on.setTime(date_on.getTime()+(day_index-day)*24*60*60*1000);
				System.out.println((date_on.getTime()-date_now.getTime())/1000);
			}
		}
	}
}
