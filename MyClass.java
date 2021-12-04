import java.io.*;
import java.util.*;
public class MyClass {

    public static void main(String[] args) {

        try {
            System.setIn(new FileInputStream("input.txt"));
            System.setOut(new PrintStream(new FileOutputStream("output.txt")));
        } catch (Exception e) {
            System.err.println("Error");
        }

        Scanner sc = new Scanner(System.in);
        int totolnodes= Integer.parseInt(sc.nextLine());
        int childspernode = Integer.parseInt(sc.nextLine());
        int totalQuery = Integer.parseInt(sc.nextLine());
        HashMap<Integer,BinaryTreeNode> tree = new HashMap<Integer,BinaryTreeNode>();
        HashMap<String,Integer> map = new HashMap<>();
        for(int i=0;i<totolnodes;i++){
            String name = sc.nextLine();
            if(i==0){
                tree.put(i,new BinaryTreeNode(name,false,null,0));
            }
            else{
                int pidx = (i-1)/childspernode; 
                BinaryTreeNode parentNode = tree.get(pidx);
                tree.put(i,new BinaryTreeNode(name,false,parentNode,0));
            }
            map.put(name,i);
        }


        for(int k=0;k<totalQuery;k++){
            String q = sc.nextLine();
            // System.out.println(q);
            String [] qrr = q.trim().split(" ");
            int qtype =Integer.valueOf(qrr[0].trim());
            String name= qrr[1];
            int uid =Integer.valueOf(qrr[2]);
            boolean result = false;
            if(qtype==1){
                result = Lock(name,uid,tree,map);
            }
            else if(qtype==2){
                result = unLock(name,uid,tree,map);
            }
            else{
                result = upgrade(name,uid,tree,map);
            }

            System.out.println(result);

        }

    }

    public static class BinaryTreeNode{
        String name;
        int uid;
        boolean islocked=false;
        BinaryTreeNode parent= null;
        int lockedD_count=0;
        HashMap<Integer,HashSet<Integer>> children_lokcby_uids = new HashMap<>();
        public BinaryTreeNode(String name,boolean islocked,BinaryTreeNode parent,int lockedD_count){
            this.name=name;
            this.islocked=islocked;
            this.parent=parent;
            
            this.lockedD_count=lockedD_count;
        }

    }

    public static boolean unLock(String name, int uid, HashMap<Integer,BinaryTreeNode> tree,HashMap<String,Integer> map){
        BinaryTreeNode node = tree.get(map.get(name));
        if(node.islocked==false || node.uid!=uid){
            return false;
        }

        node.islocked=false;
        BinaryTreeNode  parentNode = node.parent;

        while(parentNode!=null){
            parentNode.lockedD_count-=1;
            HashMap<Integer,HashSet<Integer>> children= parentNode.children;
            if(children.containsKey(uid)){
                HashSet<Integer> tem=children.get(uid);
                tem.remove(map.get(node.name));
                if(children.get(uid).size()==0){
                    children.remove(uid);
                }
            }
            parentNode=parentNode.parent;
        }
        return true;
    }

    public static boolean Lock(String name, int uid, HashMap<Integer,BinaryTreeNode> tree, HashMap<String,Integer> map){
        BinaryTreeNode node = tree.get(map.get(name));
        if(node.islocked==true){
            return false;
        }

        if(canLock(node)==false){
            return false;
        }
        node.uid=uid;
        node.islocked=true;
        BinaryTreeNode parentNode=node.parent;
        while(parentNode!=null){
            parentNode.lockedD_count+=1;
            HashMap<Integer,HashSet<Integer>> children= parentNode.children;
            if(!children.containsKey(uid)){
                children.put(uid,new HashSet());
            }
            HashSet<Integer> newchildren = children.get(uid);
            newchildren.add(map.get(node.name));
            children.put(uid,newchildren);
            parentNode=parentNode.parent;
        }
        return true;
    }

    public static boolean upgrade(String name, int uid, HashMap<Integer,BinaryTreeNode> tree, HashMap<String,Integer> map){
        BinaryTreeNode node = tree.get(map.get(name));
        if(node.islocked==true){
            return false;
        }

        if(node.children.size()>1 || node.children.size()==0){
            return false;
        }

        if(!node.children.containsKey(uid)){
            return false;
        }

        ArrayList<Integer> newcds = new ArrayList<Integer>();

        for(int childlockids : node.children.get(uid)){
            newcds.add(childlockids);
        }

        for(int child_lock_id : newcds){
            unLock(tree.get(child_lock_id).name,uid,tree,map);
        }
    
        Lock(name,uid,tree,map);
        return true;
    }

    public static boolean canLock(BinaryTreeNode node){
        if(node.lockedD_count>0){
            return false;
        }
        BinaryTreeNode parentNode = node.parent;
        while(parentNode!=null){
            if(parentNode.islocked==true){
                return false;
            }
            parentNode=parentNode.parent;
        }
        return true;

    }
}