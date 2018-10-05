public class Solution {
	public String PrintMinNumber(int [] numbers) {
		String res="";
		if(numbers==null||numbers.length==0)
			return res;
		ArrayList<Integer> list=new ArrayList<>();
		for(int i=0;i<numbers.length;i++){
			list.add(numbers[i]);
		}
		Collections.sort(list,new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				String s1=o1+""+o2;
				String s2=o2+""+o1;
				return s1.compareTo(s2);
			}
		});
		for(int i:list){
			res=res+i;
		}
		return res;
	}
}