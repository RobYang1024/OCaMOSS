import java.util.*;

/**
 * Driver class, you may have to change it to fit your implementation
 * Make sure you compile and run this class
 */
public class CitiesDriver {
    static Scanner sc;
    static MyTree mt;
    /** Read in the tree
     */
    public static void readTree () {
	/// Read in the tree
	// read in one integer
	int n = sc.nextInt();
	// read in tree, safety line
	for (int i=0;i<n;i++) {
	   int safety=sc.nextInt();
       mt.updateSafety(i,safety);}
	
	// loop to read pairs of children
	for (int i=0;i<n;i++) {
	   int left=sc.nextInt();			  
	   int right=sc.nextInt();
	   mt.updateChild(i,left,right);
	  }
    }
    
    /** Read in query statements
     */
    public static void readQueries () {
	// read in one integer
	int m = sc.nextInt();
	// loop to read pairs of children for query
	for (int i=0;i<m;i++) {
	    int start=sc.nextInt();			  
	    int end=sc.nextInt();
		boolean exist=mt.safePath(start,end);
		if(exist==true)
			System.out.println("Yes");
	    else System.out.println("No");
	}
    }
    
    public static void main(String[] args) {
	sc = new Scanner(System.in);
	mt=new MyTree();
	readTree();				   // read in the tree
	readQueries();				// read in the queries

	// then process the queries ... fill in your calls below
    }
}

import java.util.*;
/** Class for a tree node
 */
class MyNode{
    boolean isSafe;				  // is the city safe?
    int leftChildIndex;			  // left child index in array
    int rightChildIndex;		 // right child index in array
}

/** Class for a binary tree ADT
 */
public class MyTree {
    private final int MAX_NODES = 1000;
    MyNode[] myTree = new MyNode[MAX_NODES];
    int size = 0;	// make sure to update this variable as needed

	public void updateSafety(int cityIndex, int safe)
	{myTree[cityIndex]=new MyNode();
	 size++;
	 if(safe==0)
	   myTree[cityIndex].isSafe=false;
	 else myTree[cityIndex].isSafe=true;
	}

	public void updateChild(int cityIndex, int left, int right)
	{myTree[cityIndex].leftChildIndex=left;
	 myTree[cityIndex].rightChildIndex=right;
	}

	public boolean safePath(int start, int end)
 	{if(myTree[start].isSafe==false)
	      return false;
     else if(myTree[end].isSafe==false)
          return false;
     else if(start==end)
		 return true;
	 else 
	   {for(int i=0;i<size;i++)
		  if(myTree[i].leftChildIndex==start||myTree[i].rightChildIndex==start&&myTree[i].isSafe==true)
		      return safePath(i, end);
		   else if(myTree[i].leftChildIndex==end||myTree.rightChildIndex==end&&myTree[i].isSafe==true)
			   return safePath(start, i);
			  
	      else return false;}
	}
}

