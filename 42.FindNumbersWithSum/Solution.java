class Solution {
    /*
    题目描述：输入一个递增排序的数组和一个数字S，在数组中查找两个数，
    使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。

    思路：使用头尾指针，记录两个数组索引值的和与积，每次对比
     */
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> result=new ArrayList<>();
        if(array==null||array.length==0)
            return result;
        int head=0,tail=array.length-1,total,min_product=Integer.MAX_VALUE;
        while(head!=tail){
            total=array[head]+array[tail];
            if(total<sum){//若小了，则增大head索引
                head++;
            }else if(total>sum){//若大了，则减小tail索引
                tail--;
            }else if(total==sum){
                if(array[head]*array[tail]<min_product){
                    min_product=array[head]*array[tail];
                    result.removeAll(result);
                    result.add(array[head]);
                    result.add(array[tail]);
                }
                head++;
            }
        }
        return result;
    }
}
