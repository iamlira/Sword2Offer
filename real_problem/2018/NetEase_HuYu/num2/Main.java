package fuckboss;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {

	/*
	 * 题目描述：
	 * 这个相当于QQ群列表成员的显示
	 * 首先给出一段数据，每行有用户的名字和身份，
	 * 0代表普通用户，1代表管理员，2代表群主
	 * 然后再给出一系列用户的上下线操作
	 * 每行数据 有名字和数字0 1
	 * 0代表下线，1代表上线
	 * 
	 * 显示时上线的用户肯定显示在下线上面
	 * 上下线用户显示中，高身份的在地身份上面，如果相同则按名字升序显示
	 * 输入：
	 * 大概是
	 * 6
	 * zhq 1
	 * ly 2
	 * ctl 0
	 * ch 1
	 * lc 0
	 * ty 1
	 * 5
	 * zhq 0
	 * zhq 1
	 * ctl 1
	 * ch 1
	 * ctl 0
	 * 
	 * 输出：
	 * 最后列表的显示顺序
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		member[] members=new member[n];
		HashMap<String, Integer> members_map=new HashMap<>();
		HashMap<String, Integer> status_map=new HashMap<>();
		sc.nextLine();
		for(int i=0;i<n;i++){
			String tmp=sc.nextLine();
			String[] tmps=tmp.split(" ");
			int member=Integer.valueOf(tmps[0]);
			String name=tmps[1];
			members_map.put(name,member );
		}
		int m=sc.nextInt();
		sc.nextLine();
		for(int i=0;i<m;i++){
			String tmp=sc.nextLine();
			String[] tmps=tmp.split(" ");
			String name=tmps[0];
			int status=Integer.valueOf(tmps[1]);
			status_map.put(name, status);
		}
		List<member> online_list=new ArrayList<>();
		List<member> offline_list=new ArrayList<>();
		for(String key:status_map.keySet()){
			if(status_map.get(key)==1){
				member tmp=new member();
				tmp.name=key;
				if(members_map.get(key)!=null)
					tmp.member=members_map.get(key);
				else 
					tmp.member=0;
				online_list.add(tmp);
			}
			if(status_map.get(key)==0){
				member tmp=new member();
				tmp.name=key;
				if(members_map.get(key)!=null)
					tmp.member=members_map.get(key);
				else
					tmp.member=0;
				offline_list.add(tmp);
			}
		}
		Collections.sort(online_list, new Comparator<member>() {

			@Override
			public int compare(member o1, member o2) {
				// TODO Auto-generated method stub
				if(o1.member<o2.member){
					return 1;
				}else if(o1.member>o2.member){
					return -1;
				}else
					return o1.name.compareTo(o2.name);
			}
		});
		Collections.sort(offline_list,new Comparator<member>() {

			@Override
			public int compare(member o1, member o2) {
				// TODO Auto-generated method stub
				if(o1.member<o2.member){
					return 1;
				}else if(o1.member>o2.member){
					return -1;
				}else
					return o1.name.compareTo(o2.name);
			}
		});
		for(member tmp:online_list){
			System.out.println(tmp.name);
		}
		for(member tmp:offline_list){
			System.out.println(tmp.name);
		}
	}
	static class member{
		String name="";
		int member=0;
	}
}

