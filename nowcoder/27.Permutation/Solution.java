public class Solution {
	public ArrayList<String> Permutation(String str) {
		ArrayList<String> result=new ArrayList<>();
		if(str==null||str.length()==0)
			return result;
		StringBuilder sb=new StringBuilder(str);
		Permutation(sb,0,result);
		Collections.sort(result);
		return result;
	}
	public void Permutation(StringBuilder str,int index,ArrayList<String> result){
		if(index==str.length()-1)
			result.add(new String(str));
		else
			for(int i=index;i<str.length();i++){
				if(i==index||str.charAt(index)!=str.charAt(i)){
					char tmp=str.charAt(i);
					str.setCharAt(i, str.charAt(index));
					str.setCharAt(index, tmp);
					Permutation(str,index+1,result);
					tmp=str.charAt(i);
					str.setCharAt(i, str.charAt(index));
					str.setCharAt(index, tmp);
				}
			}
	}
}