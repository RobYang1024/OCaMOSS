import java.util.*;

/**
 * Driver class, you may have to change it to fit your implementation
 * Make sure you compile and run this class
 */
public class CitiesDriver {
	static Scanner sc;
	static MyTree tree;

	/** Read in the tree
	 */
	public static void readTree () {
		// Read in the tree
		// Read in size of the tree
		int n = sc.nextInt();
		tree = new MyTree(n);			//Create MyTree with n nodes
		// read in tree, safety line
		for (int i=0;i<n;i++){ 
			int safe = sc.nextInt();
			tree.updateSafety(i, safe);
		}

		// loop to read pairs of children
		for (int i=0;i<n;i++) {
			int left = sc.nextInt();
			int right = sc.nextInt();
			tree.updateNode(i,left,right);
		}
	}

	/** Read in query statements
	 */
	public static void readQueries () {
		// read in one integer
		int m = sc.nextInt();
		// loop to read pairs of children for query
		for (int i=0;i<m;i++) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			if (tree.isPathSafe(start,end)==true)
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		readTree();				   // read in the tree
		readQueries();				// read in the queries
	}
}
/* 
Class for a tree node
*/

import java.util.*;
class MyNode {
    boolean isSafe;				  // is the city safe?
    int leftChildIndex;			  // left child index in array
    int rightChildIndex;		 // right child index in array
	int parentIndex;

	//All the accessors and mutators
	//Boring stuff
	public void setIsSafe(boolean safe){
		isSafe = safe;
	}
	public void setLeft(int left){
		leftChildIndex = left;
	}
	public void setRight(int right){
		rightChildIndex = right;
	}
	public void setParent(int parent){
		parentIndex = parent;
	}
	public int getLeft(){
		return leftChildIndex;
	}
	public int getRight(){
		return rightChildIndex;
	}
	public int getParent(){
		return parentIndex;
	}
	public boolean getIsSafe(){
		return isSafe;
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
    int size = 0;
	int root = 0;
	LinkedList <Integer> q;			//Queue for BFS

	/*
	Fill up the tree with the nodes and set the size of the tree
	Even more boring stuff
	*/
	public MyTree(int n){
		for (int i = 0; i < n; i++)
			myTree[i] = new MyNode();
		size = n;
	}
	public void updateSafety(int node, int safe){
		myTree[node].setIsSafe(safe == 1);
	}
	public void updateNode(int node, int left, int right){
		myTree[node].setLeft(left);
		myTree[node].setRight(right);
		if (left != -1)
			myTree[left].setParent(node);
		if (right != -1)
			myTree[right].setParent(node);
	}
	/*
	In this problem, we are given a tree. Therefore, given any nodes u and v, there is only one unique path from u to v.
	This is because a tree is acyclic and all the nodes are connected. The question asks whether it is possible to find a 
	"safe" path from nodes u to v. 

	The easiest way to solve this problem would be to view the binary tree as a graph instead of a binary tree and run BFS/DFS 
	on the tree. Personally I prefer coding BFS, hence I coded BFS. I used the binary tree structure as an adjacency list, where
	there is an edge from the current node to its left child, an edge from the current node to its right child and an edge from the 
	current node to its parent.

	After running BFS, I recreate the path using the array pre[] (which stores the predecessor of the node) and check whether the
	path is safe. The runtime for BFS is O(N) for every query (where N is the size of binary tree). Suppose there are M queries, then
	it will take O(MN) to run all M queries.
	*/
	public boolean isPathSafe(int start, int end){
		int[] pre = new int[size];				//Stores who is the pre of the current node in the path
		boolean[] visited = new boolean[size];	//To store whether the particular node is visited
		q = new LinkedList <Integer>();
		//Start of BFS
		q.offer(new Integer(start));
		visited[start] = true;
		while (q.size()!=0){
			int u = q.poll();
			if (u == end)
				break;
			int left = myTree[u].getLeft();
			int right = myTree[u].getRight();
			int parent = myTree[u].getParent();
			if (left != -1 && visited[left] == false){
				visited[left] = true;
				q.offer(new Integer(left));
				pre[left] = u;
			}
			if (right != -1 && visited[right] == false){
				visited[right] = true;
				q.offer(new Integer(right));
				pre[right] = u;
			}
			if (parent != -1 && visited[parent] == false){
				visited[parent] = true;
				q.offer(new Integer(parent));
				pre[parent] = u;
			}
		}
		int curnode = end;
		//If the path is safe, then logical and of all the nodes in the path will yield whether the path is safe
		boolean isSafe = myTree[start].getIsSafe();
		while (curnode != start){
			isSafe = myTree[curnode].getIsSafe() && isSafe;
			curnode = pre[curnode];
		}
		return isSafe;
	}
}
