import java.io.*;
import java.util.*;

class demo {
    public boolean isRobotBounded(String arr) {
        int [][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int face =0 ; 
        int posx = 0, posy = 0 ;
        char [] inst = arr.toCharArray();

        for(char d : inst){
            if(d=='L'){
                if(face==0){
                    face=3;
                }
                else{
                    face=face-1;
                }
            }
            else if(d=='R'){
                if(face==3){
                    face=0;
                }
                else{
                    face=face+1;
                }
            }
            else{
                posx =  posx + dir[face][0];
                posy =  posy +dir[face][1];
            }
        }

        return (face!=0) || (posx==0 && posy==0);
    }
    
    public int gcd(int div,int dvd){
        int a = div , b = dvd;

        while(dvd % div !=0){
            int rem = dvd % div;
            dvd = div;
            div = rem;
        }

        return div;
    }

    public static void print_all_sum_rec(int target, int current_sum, int start,ArrayList<ArrayList<Integer>> output, ArrayList<Integer> result){

        if(current_sum == target){
           output.add(new ArrayList<>(result));
        }
        else if(current_sum > target){
            return;
        }

        for(int i=start;i<target;i++){
            result.add(i);
            print_all_sum_rec(target,current_sum+i,i,output,result);
            result.remove(result.size()-1);
        }
    }

    public class Mysort implements Comparator<int []>{
        public int compare(int a[],int b[]){
            return b[1]-a[1];
        }
    }

    public static void main(String [] args){
                
        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            System.err.println("Error");
        }

        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        print_all_sum_rec(5,0,1,output,result);
        for(ArrayList<Integer> arr : output){
            System.out.println(arr);
        }

        PriorityQueue<Map.Entry<String,Integer>> pq = new PriorityQueue<>(
            (a,b)->a.getValue()==b.getValue() ? b.getKey().compareTo(a.getKey()) : a.getValue()-b.getValue());,
        );
        for(Map.Entry<Integer,String> ent : Map.entrySet()){
            ent.getKey()
            ent.getValue()
        }
    }


} 