package fuckboss;

import java.util.Scanner;
import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		String input=in.nextLine();
		String[] data=input.split("/");
		Stack<String> stack=new Stack<>();
		StringBuilder result = new StringBuilder();
		for(int i=0;i<data.length;++i){
            if(".".equals(data[i])||"".equals(data[i]))
                continue;
            if("..".equals(data[i])){
                if(!stack.empty())
                    stack.pop();
            }else
                stack.push(data[i]);
        }
		while(!stack.empty()){
            result.insert(0,stack.pop());
            result.insert(0,"/");
        }
        if(result.length()==0)
            result.append("/");
		System.out.println(result.toString());
	}

}
