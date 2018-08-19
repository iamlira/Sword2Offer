package com;

public class Solution {
    /*
    题目描述：
    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

    The replacement must be in-place, do not allocate extra memory.

    Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
    1,2,3 → 1,3,2
    3,2,1 → 1,2,3
    1,1,5 → 1,5,1


    思路：参考别人的思路
    这道题让我们求下一个排列顺序，有题目中给的例子可以看出来，如果给定数组是降序，
    则说明是全排列的最后一种情况，则下一个排列就是最初始情况，可以参见之前的博客 Permutations 全排列。
    我们再来看下面一个例子，有如下的一个数组

    1　　2　　7　　4　　3　　1

    下一个排列为：

    1　　3　　1　　2　　4　　7

    那么是如何得到的呢，我们通过观察原数组可以发现，如果从末尾往前看，数字逐渐变大，到了2时才减小的，
    然后我们再从后往前找第一个比2大的数字，是3，那么我们交换2和3，
    再把此时3后面的所有数字转置一下即可，步骤如下：

    1　　2　　7　　4　　3　　1

    1　　2　　7　　4　　3　　1

    1　　3　　7　　4　　2　　1

    1　　3　　1　　2　　4　　7
     */
    public void nextPermutation(int[] nums) {
        int index_start=0,index_end=0;
        for(int i=nums.length-2;i>=0;i--){
            if(nums[i]<nums[i+1]) {
                index_start = i;
                for(int j=nums.length-1;j>index_start;j--){
                    if(nums[j]>nums[index_start]) {
                        index_end=j;
                        break;
                    }
                }
                swap(nums,index_end,index_start);
                reverse(nums,index_start+1);
                return;
            }
        }
        reverse(nums,0);
    }
    public void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
    public void reverse(int[] arr,int start){
        if(start>=arr.length)
            return;
        int i=start,j=arr.length-1;
        while(i<j){
            swap(arr,i,j);
            i++;
            j--;
        }
    }
    public static void main(String[] args){
        int[] arr={5,4,7,5,3,2};
        Solution solu=new Solution();
        solu.nextPermutation(arr);
        for(int num:arr)
            System.out.println(num);
    }
}
