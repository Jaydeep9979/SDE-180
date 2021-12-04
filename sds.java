
import java.io.*;
import java.util.*;

public class test{

    public static void main(String [] args){

        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            System.err.println("Error");
        }

        Scanner sc = new Scanner(System.in);
        int totalnodes=Integer.parseInt(sc.nextLine());
        int childpernodes=Integer.parseInt(sc.nextLine());
        int totalquery = Integer.parseInt(sc.nextLine());
        HashMap<Integer,TreeNode> tree = new HashMap<>();
        HashMap<String,Integer> name_to_id = new HashMap<>();

        for(int i=0;i<totalnodes;i++){
            String name = sc.nextLine();
            if(i==0){
                tree.put(i,new TreeNode(name,null,0,false));
            }
            else{
                int parent_index = (i-1)/childpernodes;
                TreeNode parent = tree.get(parent_index);
                tree.put(i,new TreeNode(name,parent,0,false));
            }
            name_to_id.put(name,i);
        }

        for(int k=0;k<totalquery;k++){
            String query = sc.nextLine();
            String [] q = query.trim().split(" ");
            int op= Integer.valueOf(q[0]);
            String name = q[1];
            int uid = Integer.valueOf(q[2]); 

            boolean result = false;
            if(op==1){
                result = Lock(name,uid,tree,name_to_id);
            }
            else if(op==2){
                result = Unlock(name,uid,tree,name_to_id);
            }
            else{
                result = upgrade(name,uid,tree,name_to_id);

            }

            System.out.println(result);

        }

    }

    public static  class TreeNode{
        int working_thread=0;
        int uid;
        String name;
        TreeNode parent=null;
        int lockD_counts=0;
        HashMap<Integer,HashSet<Integer>> child_uid = new HashMap<>();
        boolean is_locked=false;
        public TreeNode(String name, TreeNode parent,int lockD_counts, boolean is_locked){
            this.name = name;
            this.parent = parent;
            this.lockD_counts = lockD_counts;
            this.is_locked = is_locked;
        }
    }

    public static boolean Lock(String name,int uid,HashMap<Integer,TreeNode> tree){
        TreeNode node = tree.get(name_to_id.get(name));
        //if node is already in use by another thread then go back  
        node.working_thread+=1;
        if(node.working_thread>1){
            node.working_thread-=1;
            return false;
        }
        
        if(node.is_locked==true){
            node.working_thread-=1;
            return false;
        }

        node.is_locked = true;  
        if(node.lockD_counts>0){
            node.working_thread-=1;
            node.is_locked = false;
            return false;
        }
        
        TreeNode parentNode = node.parent;
        
        if(update_parents(parentNode)==true){
            node.working_thread-=1;
            return true;
        }
        else{
            node.is_locked=false;
            node.working_thread-=1;
            return false;
        }

    }
    
    public static boolean update_parents(TreeNode node){
        if(node==null){
            return true;
        }
        node.lockD_counts+=1;
        if(node.is_locked==true){
            node.lockD_counts-=1;
            returm false;  
        }
        boolean result  = update_parents(node.parent);
        if(result){
            return true;
        }
        else{
            node.lockD_counts-=1;
            return false;
        }
    }       

    public static boolean canLock(TreeNode node)
    {
        TreeNode parentNode= node.parent;
        while(parentNode!=null){
            parentNode=parentNode.parent;
        }
        return true;
    }

    public static boolean  Lock(TreeNode node){
        node.working_thread+=1;
        if(node.working_thread>1){
            node.working_thread-=1;
            return false;
        }
        if(node.is_locked==true){
            node.working_thread-=1;
            return false;
        }

        node.is_locked=true;
        
        TreeNode parentNode= node.parent;

        ArrayList<TreeNode> parents = new ArrayList<TreeNode>();

        while(parentNode!=null){
            if(parentNode.is_locked==true){
                roll_back(parents);
                return false;
            }
            parents.add(parentNode);
            parentNode=parentNode.parent;
        }
        return false;

    }
    public static void roll_back(ArrayList<TreeNode> children){
        for(TreeNode child : children){
            child.lockedD_count-=1;
        }

    }
}