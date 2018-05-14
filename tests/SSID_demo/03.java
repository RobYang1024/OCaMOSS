import java.util.*;

/**
 * Driver class
 */

public class CitiesDriver {

    public static Scanner sc;
    public static MyTree tree;

    // Read in the tree
    public static void readTree () {
		
		// Read in the tree
		// read in one integer
		int read1Integer  = sc.nextInt();
	
		tree = new MyTree (read1Integer );

		// read in tree, safety line
		for (int i = 0; i < read1Integer ; i++) {
	    		tree.setSafe(i,sc.nextInt());
		}
	
		// loop to read pairs of children
		for (int i = 0; i < read1Integer ; i++) {
	    		tree.setNode(i,sc.nextInt(),sc.nextInt());
		}

		

    }
    
    // Read in query statements
    public static void readQueries () {
		
		// read in one integer
		int read1Integer = sc.nextInt();
		
		// loop to read pairs of children for query
		for (int i=0;i<read1Integer;i++) {
	    	if (tree.findpath(sc.nextInt(),sc.nextInt())){
				System.out.println("YES");
		}
			else {
				System.out.println("NO");
			}
		}

    }
    
    public static void main(String[] args) {
		sc = new Scanner(System.in);
		readTree();				   // read in the tree
		readQueries();				// read in the queries

    }

}
/** Class for a tree node
 */
class MyNode {

    boolean isSafe;				  // is the city safe?
    int left;			  // left child index in array
    int right;		 // right child index in array

    public MyNode () {
		isSafe = true;
		left = -1;
		right = -1;
	}

	public int left () {
		return left;
	}

	public int right () {
		return right;
	}

	public boolean isSafe () {
		return isSafe;
	}

	public void setSafe (int n) {
		if (n == 0) isSafe = false;
		else if (n == 1) isSafe = true;
	}

	public void setLeft (int index) {
		left = index;
	}

	public void setRight (int index) {
		right = index;
	}

}

/** Class for a binary tree ADT
 */
public class MyTree {

    private final int MAX_NODES = 1000;
    MyNode[] myTree = new MyNode[MAX_NODES];
    int size = 0;

	public MyTree (int n) {
		size = n;
		for (int i = 0; i < n; i++) {
			myTree[i] = new MyNode ();
		}
	}

	public void setSafe (int index, int status) {
		myTree[index].setSafe(status);
	}

	public void setNode (int index, int left, int right) {
		myTree[index].setLeft(left);
		myTree[index].setRight(right);
	}

	public void print () {
		print(myTree,0);
	}

	public boolean findpath (int from, int to) {
		if (!myTree[from].isSafe() || !myTree[to].isSafe()) {
			return false;
		}

		if (from == to) return true;

		if (directPath(from,to)) return true;

		if (directPath(to,from)) return true;

		if (directPath(0,from) && directPath(0,to)) return true;

		return false;
	}

	public boolean directPath (int index, int target) {
		if (index >= 0) {
			if (!myTree[index].isSafe()) return false;
			else {
				if (index == target) return true;

				if (directPath(myTree[index].left(),target)) return true;
				else return directPath(myTree[index].right(),target);
			}
		}

		return false;
	}

	public boolean searchTree (int index, int target) {

		if (index >= 0) {
			if (index == target) return true;

			if (searchTree(myTree[index].left(),target)){ 
				return false;
			}
			else{
				return searchTree(myTree[index].right(),target);
			}
		}

		return false;
	}

	public void print (MyNode[] tree, int index) {
		
		if (index >= 0) {
			print(tree,tree[index].left());

			System.out.print(index);
			if (!tree[index].isSafe()) System.out.print("*");
			System.out.print(" ");

			print(tree,tree[index].right());
		}

	}

}
