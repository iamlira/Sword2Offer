class Solution {
    /*
    题目描述：小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,
    他马上就写出了正确答案是100。但是他并不满足于此,
    他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。
    没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。
    现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列?

    思路：用两个值代表连续数组的第一个值和最后一个值，并用一个total变量表示连续数组的和
    如果total小于目标值，则说明数组不够大，将big++，total加上这个big继续比较
    如果total==目标值，则将序列加入arraylist中
    如果total大于目标值，则说明数组太大，将small++，total减去之前的small，继续比较
    循环终止条件是当small大于目标值的一半时，说明接下去的连续数组的和都会大于目标值，停止搜索
     */
    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result=new ArrayList<>();
        int small=1,big=2,total=small+big;
        while(small<(sum+1)/2){
            if(total<sum){
                big++;
                total+=big;
            }else if(total==sum){
                ArrayList<Integer> tmp=new ArrayList<>();
                for(int i=small;i<=big;i++){
                    tmp.add(i);
                }
                result.add(tmp);
                big++;//匹配后将big++，total+=big继续比较
                total+=big;
            }else if(total>sum){
                total-=small;
                small++;
            }
        }
        return result;
    }
}
