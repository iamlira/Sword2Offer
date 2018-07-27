package com;


public class Sort {
    public static void main(String[] args){
        Sort s=new Sort();
        int[] test={2,3,5,1,5,6,3,3,56,76};
        //s.MergeSort(test,0,test.length-1);
        //s.HeapSort(test);
        //s.QuickSort(test,0,test.length-1);
        s.ShellSort(test);
        for(int i=0;i<test.length;i++){
            System.out.println(test[i]);
        }

    }
    //=====merge========
    public void MergeSort(int[] a,int low,int high){
        if(low>=high){
            return;
        }
        int mid=(low+high)/2;
        MergeSort(a,low,mid);
        MergeSort(a,mid+1,high);
        merge(a,low,mid,high);
    }
    public void merge(int[] a,int low,int mid,int high){
        int[] tmp=new int[high-low+1];
        int i=low,j=mid+1,index=0;
        while(i<=mid&&j<=high){
            if(a[i]<=a[j]) {
                tmp[index++] = a[i];
                i++;
            }else if(a[i]>a[j]){
                tmp[index++]=a[j];
                j++;
            }
        }
        while(i<=mid){
            tmp[index++]=a[i];
            i++;
        }
        while(j<=high){
            tmp[index++]=a[j];
            j++;
        }
        for(int k=0,l=low;k<tmp.length;k++,l++){
            a[l]=tmp[k];
        }
    }
    //======merge======

    //=====heap=======
    public void HeapSort(int[] a){
        for(int i=a.length/2-1;i>=0;i--){
            adjustHeap(a,i,a.length);
        }
        for(int i=a.length-1;i>0;i--){
            swap(a,0,i);
            adjustHeap(a,0,i);
        }
    }
    public void adjustHeap(int[] a,int index,int length){
        int tmp=a[index];
        for(int i=2*index+1;i<length;i=2*i+1){
            if(i+1<length&&a[i]<a[i+1])
                i++;
            if(a[i]>tmp) {
                a[index] = a[i];
                index=i;
            }
        }
        a[index]=tmp;
    }
    public void swap(int[] a,int i,int j){
        int tmp=a[i];
        a[i]=a[j];
        a[j]=tmp;
    }
    //======heap======

    //======quick=====
    public void QuickSort(int[] a,int left,int right){
        if(left>=right)
            return;
        int tmp=a[left];
        int i=left,j=right;
        while(i!=j){
            while(a[j]>=tmp&&i<j){
                j--;
            }
            while(a[i]<=tmp&&i<j)
                i++;
            if(i<j)
                swap(a,i,j);
        }
        a[left]=a[i];
        a[i]=tmp;
        QuickSort(a,left,i-1);
        QuickSort(a,i+1,right);
    }
    //======quick=====

    //======shell=====
    public void ShellSort(int[] a){
        for(int gap=a.length/2;gap>0;gap/=2){
            for(int i=gap;i<a.length;i++){
                int j=i;
                int tmp=a[j];
                if(a[j]<a[j-gap]) {
                    while(j-gap>=0&&tmp<a[j-gap]){
                        a[j]=a[j-gap];
                        j-=gap;
                    }
                    a[j]=tmp;
                }
            }
        }
    }
    //======shell=====
}
