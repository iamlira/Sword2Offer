package com;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        for(int i=0;i<n;i++) {
            int count;
            count=scanner.nextInt();
            int[] seq=new int[count];
            HashMap<Integer,Integer> map=new HashMap<>();
            for(int j=0;j<count;j++){
                seq[j]=scanner.nextInt();
            }
            for(int j=0;j<seq.length;j++){
                map.put(seq[j],--count);
            }
            for(int j=0;j<seq.length;j++){
                seq[j]=-1;
            }
            for(Integer j:map.keySet()){
                seq[map.get(j)]=j;
            }
            StringBuilder result=new StringBuilder("");
            for(int j=0;j<seq.length;j++){
                if(seq[j]!=-1)
                    result.append(" "+seq[j]);
            }
            result.deleteCharAt(0);
            System.out.println(result);
        }
    }


}