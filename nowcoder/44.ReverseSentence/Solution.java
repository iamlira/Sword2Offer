class Solution {
    /*
    题目描述：牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，
    写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，
    但却读不懂它的意思。例如，“student. a am I”。
    后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”。
    Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？

    思路：这里采用快慢指针，若快指针指向的字符是空个，则切割快到慢指针之前都字符，并加入之前的结果字符串
     */
    public String ReverseSentence(String str) {
        String result="";
        int head=0,tail=0;
        while(tail!=str.length()){
            if(str.charAt(tail)==' '){
                result=str.substring(head,tail)+result;
                result=" "+result;//注意空格需要在这里添加
                head=tail+1;
            }else if(tail==str.length()-1){
                result=str.substring(head)+result;
            }
            tail++;
        }
        return result;
    }
}
