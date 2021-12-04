import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;



class HelloWorld {
    public static void main(String[] args) {
    	try {
			System.setIn(new FileInputStream("input.txt"));
			System.setOut(new PrintStream(new FileOutputStream("output.txt")));
		} catch (Exception e) {
			System.err.println("Error");
		}
		Scanner sc = new Scanner(System.in);

    	int a = sc.nextInt();
        System.out.println(a); 
    }
    public void addAt(int idx, int val){
        if(idx<0 || idx >=size){
            System.out.println("Invalid arguments");
        }
        else{
        	Node ans = new Node();
        	ans.data = val;
        	Node at = head;
        	for(i=1;i<idx;i++){
        		at= at.next;
        	}
        	if(at.next!=null){
        		ans.next = at.next.next;
        	}
        	at.next= ans; 

        }      
    }
    
} 