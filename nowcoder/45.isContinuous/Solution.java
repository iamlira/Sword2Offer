class Solution {
    /*
    题目描述：LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王
    (一副牌原本是54张^_^)...他随机从中抽出了5张牌,想测测自己的手气,看看能不能抽到顺子,
    如果抽到的话,他决定去买体育彩票,嘿嘿！！“红心A,黑桃3,小王,大王,方片5”,“Oh My God!”
    不是顺子.....LL不高兴了,他想了想,决定大\小 王可以看成任何数字,
    并且A看作1,J为11,Q为12,K为13。上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),
    “So Lucky!”。LL决定去买体育彩票啦。 现在,要求你使用这幅牌模拟上面的过程,
    然后告诉我们LL的运气如何， 如果牌能组成顺子就输出true，否则就输出false。
    为了方便起见,你可以认为大小王是0。

    思路：首先对数组排序，然后统计大小王的个数，即0的个数
    然后从非0索引开始，检查相邻数字都间隙，每次遍历用0都个数减去间隙大小，如果0个数小于0并且数组
    还未遍历完成，说明大小王不够填补，输出false
     */
    public boolean isContinuous(int [] numbers) {
        if (numbers==null||numbers.length==0)
            return false;
        int king_num=0;
        java.util.Arrays.sort(numbers);
        for(int i=0;numbers[i]==0;i++){
            king_num++;
        }
        for(int i=king_num;i< numbers.length-1;i++){//从非0索引开始
            if(numbers[i+1]==numbers[i])//如果相邻数字相同肯定不是顺子
                return false;
            king_num-=(numbers[i+1]-numbers[i]-1);//王的个数减去间隔数，间隔数是相邻数差值减1
            if (king_num<0)
                return false;
        }
        return true;
    }
}
