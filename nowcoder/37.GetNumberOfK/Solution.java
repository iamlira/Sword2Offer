class Solution {
    /*
    题目描述：统计一个数字在排序数组中出现的次数。
    思路：第一步先查找排序数组中目标数字，理所当然想到二分法
    但是这里稍微不同，找到数字后，还需要找到第一个目标数字的索引和最后一个目标数字的索引
    在递归中添加约束条件找到数字
     */
    public int GetNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0)
            return 0;
        int start=GetFirstK(array,0,array.length-1,k),end=GetLastK(array,0,array.length-1,k);
        return start==-1?0:end-start+1;//若start==-1，则说明数组不存在目标数字，返回0
    }

    public int GetFirstK(int[] array, int start, int end, int k) {//找到第一个目标数字的索引
        if(start>end)
            return -1;
        int mid=(start+end)/2;
        int mid_data=array[mid];
        if(mid_data==k){
            if(mid==0||(mid>0&&array[mid-1]!=k))//如果mid到了数组最前面或者mid_data前一个数字不是目标数字，则说明找到第一个索引
                return mid;
            else
                end=mid-1;//不然需要继续往前找
        }else if(mid_data>k)//二分查找
            end=mid-1;
        else if(mid_data<k)
            start=mid+1;
        return GetFirstK(array,start,end,k);//递归
    }
    public int GetLastK(int[] array, int start, int end, int k) {//找到目标数字的最后一个索引
        if(start>end)
            return -1;
        int mid=(start+end)/2;
        int mid_data=array[mid];
        if(mid_data==k){//同理
            if(mid==array.length-1||(mid<array.length-1&&array[mid+1]!=k))
                return mid;
            else
                start=mid+1;
        }else if(mid_data>k)
            end=mid-1;
        else if(mid_data<k)
            start=mid+1;
        return GetLastK(array,start,end,k);
    }
}
