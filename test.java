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

    public static boolean Lock(String name,int uid,HashMap<Integer,TreeNode> tree, HashMap<String,Integer> name_to_id){
        //System.out.println(name+" "+name_to_id.get(name));
        TreeNode node = tree.get(name_to_id.get(name));
        
        if(node.is_locked==true){
            return false;
        }

        if(canLock(node)==false){
            return false;
        }

        node.is_locked = true;
        node.uid=uid;
        TreeNode parentNode = node.parent;
        while(parentNode!=null){

            parentNode.lockD_counts+=1;
            HashMap<Integer,HashSet<Integer>> child_lock_uid = parentNode.child_uid;
            if(!child_lock_uid.containsKey(uid)){
                child_lock_uid.put(uid,new HashSet<Integer>());
            }
            HashSet<Integer> locknodes =  child_lock_uid.get(uid);
            locknodes.add(name_to_id.get(name));
            child_lock_uid.put(uid,locknodes);
            parentNode=parentNode.parent;

        }

        return true;
    }

    public static boolean Unlock(String name,int uid,HashMap<Integer,TreeNode> tree, HashMap<String,Integer> name_to_id){

        TreeNode node = tree.get(name_to_id.get(name));
        if(node.is_locked==false || node.uid!=uid){
            return false;
        }

        node.is_locked=false;
        TreeNode parentNode = node.parent;

        while(parentNode!=null){
            parentNode.lockD_counts-=1;
            HashMap<Integer,HashSet<Integer>> child_lock_uid = parentNode.child_uid;
            if(child_lock_uid.containsKey(uid)){
                HashSet<Integer> lockd = child_lock_uid.get(uid);
                lockd.remove(name_to_id.get(name));
                if(child_lock_uid.get(uid).size()==0){
                    child_lock_uid.remove(uid);
                }
            }
            parentNode=parentNode.parent;
        }
        return true;
    }

    public static boolean upgrade(String name, int uid, HashMap<Integer,TreeNode> tree, HashMap<String,Integer> name_to_id){
        TreeNode node = tree.get(name_to_id.get(name));
        if(node.is_locked==true){
            return false;
        }

        HashMap<Integer,HashSet<Integer>> lock_uids = node.child_uid;

        if(lock_uids.size()==0 || lock_uids.size()>1){
            return false;
        }

        if(!lock_uids.containsKey(uid)){
            return false;
        }

        
        ArrayList<Integer> child_lock_nodes = new ArrayList<Integer>();

        for(int n: lock_uids.get(uid)){
            child_lock_nodes.add(n);
        }
        for(int nd : child_lock_nodes){
            Unlock(tree.get(nd).name,uid,tree,name_to_id);
        }

        Lock(name,uid,tree,name_to_id);

        
        return true;
    }

    public static boolean canLock(TreeNode node){
        if(node.lockD_counts>0){
            return false;
        }

        TreeNode parentNode= node.parent;

        while(parentNode!=null){
            if(parentNode.is_locked==true){
                return false;
            }
            parentNode=parentNode.parent;
        }
        return true;

    }

}