package com;

import java.util.*;

public class Main {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int np = sc.nextInt();
        int nq = sc.nextInt();
        int nr = sc.nextInt();
        int[] a={np,nq,nr};

        int sum=a[0]+a[1]+a[2];
        int[] sort=new int[sum],count={0};

        for(int i=0;i<a.length;i++){
            sort[0]=i+1;
            a[i]--;
            search(sort,a,1,count);
            a[i]++;
            sort[0]=0;
        }
        System.out.println(count[0]);


    }
    public static void search(int[] sort, int[] a, int index,int[] count) {
        if(index==sort.length){
            count[0]++;
            return;
        }
        for(int i=0;i<a.length;i++){
            if(a[i]>0){
                if(i+1!=sort[index-1]){
                    sort[index]=i+1;
                    a[i]--;
                    search(sort,a,index+1,count);
                    a[i]++;
                    sort[index]=0;
                }
            }


        }

    }

}
