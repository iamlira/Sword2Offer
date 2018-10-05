class Solution {
    /*
    题目描述：汇编语言中有一种移位指令叫做循环左移（ROL），
    现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
    对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
    例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。

    思路：剑指offer中是通过两次翻转字符求解，这里采用的思路不同，
    先将n对str都长度取余，避免n大于str长度时过多左移，然后直接使用java都切割字符串
    将切割都字符串颠倒顺序返回即可
     */
    public String LeftRotateString(String str,int n) {
        if (str==null||str.length()==0)
            return str;
        String result;
        n=n%str.length();
        result=str.substring(n)+str.substring(0,n);
        return result;
    }
}
