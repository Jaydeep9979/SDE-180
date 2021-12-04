import java.io.*;
import java.util.*;

public class Solution {


	static boolean checkPerfectSquare(double x)  
    { 
		double sq = Math.sqrt(x); 
	
		return ((sq - Math.floor(sq)) == 0); 
    } 
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n= sc.nextInt();
        for(int i=0;i<n;i++){
        	int d2= sc.nextInt();
        	int k= sc.nextInt();
        	int ans=4;
        	for(double j=1;j<Math.sqrt(d2);j++){
        		double t2= d2;
        		double m = t2- (j*j)
        		if(checkPerfectSquare(m)==true){
        			ans+=4;
        		}
        	}
        	if(k<ans){
        		System.out.println("impossible");
        	}
        	else{
        		System.out.println("possible");
        	}
        }

        
    }
}