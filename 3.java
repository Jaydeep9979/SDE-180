import java.io.*;
import java.util.*;
public class MyClass {
    public static void main(String[] args) {
	 	
	 	

    	Scanner sc = new Scanner(System.in);
    	sc.nextLine();
    	int totolnodes= sc.nextInt();
    	int childspernode = sc.nextInt();
    	int totalQuery = sc.nextInt();
    	HashMap<Integer,BinaryTreeNode> tree = new HashMap<Integer,BinaryTreeNode>();
    	HashMap<String,Integer> map = new HashMap<>();
    	for(int i=0;i<totolnodes;i++){
    		String name = sc.nextLine();
    		System.out.println(name+"hello-");
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
    		String q = sc.next();
         //   System.out.println(q);
    		String [] qrr = q.strip().split(" ");	
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
    			//result = upgrade(name,uid,tree);
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
            parentNode=parentNode.parent;
        }
        return true;
    }

    public static boolean Lock(String name, int uid, HashMap<Integer,BinaryTreeNode> tree, HashMap<String,Integer> map){
        BinaryTreeNode node = tree.get(map.get(name));
        if(node.islocked==true){
            return false;
        }
        node.uid=uid;
        if(canLock(node)==false){
            return false;
        }
        node.islocked=true;
        BinaryTreeNode parentNode=node.parent;
        while(parentNode!=null){
            parentNode.lockedD_count+=1;
            parentNode=parentNode.parent;
        }
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