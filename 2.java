import java.io.*;
import java.util.*;

public class Solution {

	public static int sum(int [] arr ){
		int m1= 0;
		int m2= 0;
		for(int i=0;i<n;i++){
			if(arr[i]>m1){
				m2=m1;
				m1=arr[i];
			}
			else if(arr[i]>m2){
				m2=arr[i];
			}
		}
		return m1+m2;
	}

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	int n= sc.nextInt();
    	int [] arr = new int [n]; 
    	for(int i=0;i<n;i++){
    		arr[i]=sc.nextInt();
    	}
    	int k =sc.nextInt();

    	int [] dp = new int [n];
    	if(n==1){
    		System.out.println(Math.max(0,arr[0]));
    		return ;
    	}
    	else if(n==2){
    		System.out.println(Math.max(arr[1],Math.max(0,arr[0])));
    		return ;
    	}
    	dp[0]= Math.max(0,arr[0]);
    	dp[1]= Math.max(0,arr[1]);
    	for(int i=2;i<n;i++){
    		int [] a= Arrays.copyOfRange(arr, 0, i-1);
    		int val = sum(a);
    		dp[i]=Math.max(0,arr[i]+val);  
    	} 
    	int ans=0;
    	for(int e:dp){
    		ans=Math.max(ans,e);
    	}
    	System.out.println(ans);
    }
}