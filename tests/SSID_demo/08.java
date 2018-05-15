import java.util.*;
/** Class for a tree node
 */
 
// This class is actually MyNode class although it is called MyTree

public class MyTree {

    // data field
	public int city;         // id of the city
    public boolean isSafe;	 // is the city safe?
    public int leftIndex;    // left child index in array
    public int rightIndex;   // right child index in array
	
	// constructor
	public MyTree( int id) {
		city = id;
		isSafe = false;
		leftIndex = -1;
		rightIndex = -1;
	}
} // end MyNode class

/**
 * Driver class, you may have to change it to fit your implementation
 * Make sure you compile and run this class 
 */

public class CitiesDriver { 
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // number of cities
		MyTree[] cities = new MyTree[n];

		// read isSafe
		for  ( int i = 0; i < n; i++ ) {
			cities[i] = new MyTree(i);
			int isSafe = sc.nextInt();
			if(isSafe == 1)
				cities[i].isSafe = true;
			else 
				cities[i].isSafe = false;
		} 

		// read children
		for ( int i = 0; i < n; i++ ) {
			int left = sc.nextInt();
			int right = sc.nextInt();
			cities[i].leftIndex = left;
			cities[i].rightIndex = right;
		}

		// read queries
		int m = sc.nextInt( ); // number of queries
		for( int i=0; i<m; i++ ){
			int beg = sc.nextInt( );
			int end = sc.nextInt( );
			if( findSafePath(cities, beg, end) )
				System.out.println("YES");
		    else 
				System.out.println("NO");
		}
	} // end main

	private static boolean findSafePath ( MyTree[] cities, int beg, int end ) {
		if( !cities[beg].isSafe ) 
			return false;
		else if ( !cities[end].isSafe )
			return false;
		else { // both are safe
			MyTree curr1 = cities[beg];
			MyTree curr2 = cities[end];

			if( curr1.city ==0 ) { // beg is root
				if(curr2.city ==0 ) // end is root
					return true; // checked already
				else { // end is not root
					while(curr2.city != 0) { // not root
						if ( !curr2.isSafe )
							return false;
						curr2 = getParent(cities, curr2.city);
					}
					return true;
				}
			} // end beg is root
			else if( curr2.city == 0 ) { // end is root
				if(curr1.city ==0 ) // beg is root
					return true;
				else { // beg is not root
					while(curr1.city != 0) {
						if ( !curr1.isSafe )
							return false;
						curr1 = getParent(cities, curr1.city);
					}
					return true;
				}
			} // end of the case end is root
			else { // both are not root
				while(curr1.city != 0 && curr1.city != end) {
					if(!curr1.isSafe)
						return false;
					curr1 = getParent(cities, curr1.city);
				}
				if( curr1.city == end ) {
					if(cities[end].isSafe)
						return true;
					else 
						return false;
				}
				else { // curr1 reaches root
					while(curr2.city != 0 && curr2.city != beg) {
						if (!curr2.isSafe)
							return false;
						curr2 = getParent(cities,curr2.city);
					}
					if(curr2.city == beg) {
						if(cities[beg].isSafe)
							return true;
						else 
							return false;
					}
					else { // both reach root
						if(cities[0].isSafe) // root is safe
							return true;
						else 
							return false;
					} // end both reaches root
				} // end curr1 reaches root
			} // end both are not root
		} // both are safe
	} // end findSafePath

	private static MyTree getParent(MyTree[] cities, int child) {
		for ( int i = 0; i < cities.length; i++ ) {
			if(cities[i].leftIndex == child || cities[i].rightIndex == child)
				return cities[i];
		}
		return null;
	} // end getParent

} //  end CitiesDriver class
