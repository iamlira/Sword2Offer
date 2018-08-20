package com;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    /*
    题目描述：
    Given a collection of numbers that might contain duplicates, return all possible unique permutations.

    Example:

    Input: [1,1,2]
    Output:
    [
        [1,1,2],
        [1,2,1],
        [2,1,1]
    ]

    思路：
    在全排列的基础上进行判断可以解带有重复元素的全排列
    将以下的  if(!contains(nums,index,i)) {  语句去掉之后就是全排列
    全排列可以有两种解法，一种是设置一个boolean数组代表所有元素是否被使用过，然后做深搜
    一种是剑指offer上的解法，将第一个位置固定住，用每一个元素都作为头元素，依次交换过
    然后求后面的序列的全排列就是解。所以每次递增往后固定一个位置，依次交换，递归调用函数
    这里带有重复元素的解法是，在每次交换前，判断固定位置和要交换位置中间是否有和要交换元素相同的值
    如果有，说明这个元素已经在固定位置作为头元素求解过，所以跳过这个元素，进行下一次求解即可
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result=new ArrayList<>();
        search(0,nums,result);
        return result;
    }
    public void search(int index,int[] nums,List<List<Integer>> result){
        if(index==nums.length){
            List<Integer> tmp=new ArrayList<>();
            for(int i=0;i<nums.length;i++){
                tmp.add(nums[i]);
            }
            result.add(tmp);
            return;
        }
        for(int i=index;i<nums.length;i++){
            if(!contains(nums,index,i)) {//判断固定位置和交换位置中间是否有与交换位置值相同的值
                swap(nums, index, i);//index作为固定头元素，依次交换
                search(index + 1, nums, result);//每次递归则固定index下一个位置
                swap(nums, index, i);
            }
        }
    }
    public void swap(int[] arr,int i,int j){
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
    public boolean contains(int[] arr,int i,int j){
        for(int t=i;t<j;t++){
            if(arr[t]==arr[j])
                return true;
        }
        return false;
    }
    public static void main(String[] args){
        int[] arr={1,1,3};
        Solution solu=new Solution();
        List<List<Integer>> result=solu.permuteUnique(arr);
        for(List num:result)
            System.out.println(num);
    }
}
