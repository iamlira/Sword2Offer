package fuckboss;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main {

	/*
	 * ��Ŀ������
	 * ����൱��QQȺ�б��Ա����ʾ
	 * ���ȸ���һ�����ݣ�ÿ�����û������ֺ���ݣ�
	 * 0������ͨ�û���1�������Ա��2����Ⱥ��
	 * Ȼ���ٸ���һϵ���û��������߲���
	 * ÿ������ �����ֺ�����0 1
	 * 0�������ߣ�1��������
	 * 
	 * ��ʾʱ���ߵ��û��϶���ʾ����������
	 * �������û���ʾ�У�����ݵ��ڵ�������棬�����ͬ������������ʾ
	 * ���룺
	 * �����
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
	 * �����
	 * ����б����ʾ˳��
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

