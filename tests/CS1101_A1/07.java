/** Class for a tree node
 */
class MyNode {
    // You may use this class if you'd like.  It may help you if you use
    // an array based implementation of a binary tree

    // you can add other variables if you'd like
    boolean isSafe;				  // is the city safe?
    int leftChildIndex;			  // left child index in array
    int rightChildIndex;		 // right child index in array

    // fill in your accessor and mutator methods here
    // ...

	public MyNode( int i ) {
		if( i == 0 ) {
			isSafe = false;
		} else 
			isSafe = true;
	}
    
	//accessors
	public int getLeft(){
		return leftChildIndex;
	}

	public int getRight(){
		return rightChildIndex;
	}
	
	//mutators
	public void setLeft(int left){
		this.leftChildIndex = left;
	}

	public void setRight (int right){
		this.rightChildIndex = right;
	}

	public boolean isSafe(){
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
    int size = 0;	// make sure to update this variable as needed

    // fill in your ADT methods here
    // ...

	//insert the cities
	public void insert (int n, int leftIndex, int rightIndex){
		myTree[size] = new MyNode(n);
		myTree[size].setLeft(leftIndex);
		myTree[size].setRight(rightIndex);
		size++;
	}
	
	//use recursive methods to find result
	public boolean queries(int depature, int arrive){
		if (depature != arrive){
			return queries(getParent(depature), 0)&& queries(getParent(arrive), 0)&& myTree[depature].isSafe && myTree[arrive].isSafe;
		} else return myTree[depature].isSafe;
	}

	/*public boolean allSafe(int depature, int root){
		if (departure == root){
			return myTree[0].isSafe;
		} else 
			return myTree[departure].isSafe && allSafe(getParent(depature), myTree[0]);
	}*/

	//method to find the parents of the city
	public int getParent(int city){
		for (int i = city;  i >=0; i --){
			if(myTree[i].getRight() == city || myTree[i].getLeft() == city){
				return i;
			}
		}
		//return 0 if no parent, meaning this city is the root
		return 0;
	}
}

import java.util.*;

/**
 * Driver class, you may have to change it to fit your implementation
 * Make sure you compile and run this class
 */
public class CitiesDriver {
    static Scanner sc;
    //create all the array lists for storing all the input read
	static int [] cities;
	static int [] leftChild;
	static int [] rightChild;
	static int [] departCities;
	static int [] arriveCities;
	//initialize a Mytree instance
	static MyTree tree = new MyTree();

    /** Read in the tree
     */
    public static void readTree () {
	/// Read in the tree
	// read in one integer
	int n = sc.nextInt();
	// read in tree, safety line
	
	cities = new int [n];
	//read in the cities
	for (int i=0;i<n;i++) { 
	    cities[i] = sc.nextInt();
	}// modify this line
	
	// loop to read pairs of children
	leftChild = new int [n];
	rightChild = new int [n];
	for (int i=0;i<n;i++) {
	    leftChild [i] = sc.nextInt();			   // modify this line
	    rightChild [i] = sc.nextInt();			   // modify this line
	}
	
	//insert all the cities with their data into the mytree;
	// datas are like if they are safe or not, then its left city and right city
	for (int i =0; i < n; i++){
		tree.insert(cities[i], leftChild[i], rightChild[i]);
	}
    }
    
    /** Read in query statements
     */
    public static void readQueries () {
	// read in one integer
	int m = sc.nextInt();
	// loop to read pairs of children for query

	departCities = new int [m];
	arriveCities = new int [m];
	for (int i=0;i<m;i++) {
	    departCities [i] = sc.nextInt();			   // modify this line
	    arriveCities [i] = sc.nextInt();			   // modify this line
	}
    }
    
    public static void main(String[] args) {
	sc = new Scanner(System.in);
	readTree();				   // read in the tree
	readQueries();				// read in the queries
	
	int numOfQueries = departCities.length;
	for (int i =0; i < numOfQueries ; i ++){
		boolean isSafe = tree.queries(departCities[i], arriveCities[i]);
		if (isSafe) {
			System.out.println("YES");
		} else System.out.println("NO");
	}
	// then process the queries ... fill in your calls below
    }
}

