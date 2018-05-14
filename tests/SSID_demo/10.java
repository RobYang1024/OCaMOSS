/** Class for a tree node
 */
class MyNode {
    // You may use this class if you'd like.  It may help you if you use
    // an array based implementation of a binary tree

    // you can add other variables if you'd like
    boolean isSafe;				  // is the city safe?
    int leftChildIndex;			  // left child index in array
    int rightChildIndex;		 // right child index in array

    public MyNode(int safe, int left, int right){
		if(safe==1) isSafe=true;
		else isSafe=false;
		leftChildIndex=left;
		rightChildIndex=right;
	}
	// fill in your accessor and mutator methods here
    // ...
	public int getLeftIndex(){
		return leftChildIndex;
	}
	public int getRightIndex(){
		return rightChildIndex;
	}
	public void setLeftIndex(int left){
		leftChildIndex=left;
	}
	public void setRightIndex(int right){
		rightChildIndex=right;
	}

}

/** Class for a binary tree ADT
 */
public class MyTree {
    // You may want to use an array based implementation for 
    // your tree, or change this to another implementation

    // Note that this code and the driver code do not necessarily
    // match well -- you decide how you want these two parts to work
    // together.

    private final int MAX_NODES = 1000;
    MyNode[] myTree = new MyNode[MAX_NODES];
    int size = 0;	// make sure to update this variable as needed

    // fill in your ADT methods here
    // ...
	public MyTree(int n){
		size=n;
		for(int i=0;i<n;i++)
			myTree[i]=new MyNode(0,-1,-1);
		}

	public void setSafe(int i, int safe){
		if(safe==1) myTree[i].isSafe=true;
		else myTree[i].isSafe=false;

	}
	public void setChild(int i,int left, int right){
		myTree[i].setLeftIndex(left);
		myTree[i].setRightIndex(right);
	}
	public boolean  safeToTwo(int root, int i,int j){
	    return (safeToOne(root,i)&&safeToOne(root,j));
	}
	public boolean safeToOne(int root, int i){
	/*	if((myTree[root].getLeftIndex()==i&&myTree[root].getLeftIndex().isSafe)||
			(myTree[root].getRightIndex()==i&&myTree[root].getRightIndex().isSafe))
		return myTree[i].isSafe;
			else return safeToOne(myTree[root].getLeftIndex(),i)||safeToOne(myTree[root].getRightIndex(),i);
	*/
	return false;
	}
		



}


import java.util.*;

/**
 * Driver class, you may have to change it to fit your implementation
 * Make sure you compile and run this class
 */
public class CitiesDriver {
    static Scanner sc;
	static MyTree citytree;
	static Query[] q;
    
    /** Read in the tree
     */
    public static void readTree () {
	/// Read in the tree
	// read in one integer
	int n = sc.nextInt();
	citytree = new MyTree(n);
	// read in tree, safety line
	for (int i=0;i<n;i++) {
	    int safe=sc.nextInt();			   // modify this line
	    citytree.setSafe(i,safe);
	}
	// loop to read pairs of children
	for (int i=0;i<n;i++) {
	    int left=sc.nextInt();			   // modify this line
	    int right=sc.nextInt();
		citytree.setChild(i,left,right);// modify this line
	}
    }
    
    /** Read in query statements
     */
    public static void readQueries () {
	// read in one integer
	int m = sc.nextInt();
	q = new Query[m];
	// loop to read pairs of children for query
	for (int i=0;i<m;i++) {
	    int a=sc.nextInt();			   // modify this line
	    int b=sc.nextInt();			   // modify this line
		q[i]=new Query(a,b);
	}
    }
    
    public static void main(String[] args) {
	sc = new Scanner(System.in);
	readTree();				   // read in the tree
	readQueries();				// read in the queries

	for(int i=0;i<10;i++){
		if(safetravel(q[i].start,q[i].end))
			System.out.println("YES");
			else System.out.println("No");

    }
	}

	public static boolean safetravel(int i, int j){
		return citytree.safeToTwo(0,i,j);
	}
}

	class Query{
		int start;
		int end;
		public Query(int a,int b){
			start=a;
			end=b;
		}

}
