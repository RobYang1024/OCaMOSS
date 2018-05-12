import java.util.*;


public class CitiesDriver {
	static Scanner scanner;
	static MyTree tree;



	public static void readTree () {
		
		int n = scanner.nextInt();
		tree = new MyTree(n);			
	

        int i = 0;
        while(i<n){
			int safe = scanner.nextInt();
			tree.updateSafety(i, safe);
            i++;
        }

		int y=0;
        while(y<n){
			int left = scanner.nextInt();
			int right = scanner.nextInt();
			tree.updateNode(y,left,right);
            y++;
        }
	}

	public static void readQueries () {
		
		int m = scanner.nextInt();
		
		for (int i=0;i<m;i++) {
			int start = scanner.nextInt();
			int end = scanner.nextInt();
			if (tree.isPathSafe(start,end)==true)
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		readTree();				  
		readQueries();				
	}
}
/* 
*/

import java.util.*;

class MyNode {
    boolean isCitySafe;				

    int leftChildIndex;			 
    int rightChildIndex;		
	int parentIndex;

    public boolean isIsCitySafe() {
        return isCitySafe;
    }

    public void setIsCitySafe(boolean isCitySafe) {
        this.isCitySafe = isCitySafe;
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
}

 
public class MyTree {
   

    private final int MAX_NODES = 1000;
    MyNode[] myTree = new MyNode[MAX_NODES];
    int size = 0;
	int root = 0;
	LinkedList <Integer> q;		


	public MyTree(int n){
		for (int i = 0; i < n; i++)
			myTree[i] = new MyNode();
		size = n;
	}
	public void updateSafety(int node, int safe){
		myTree[node].setIsCitySafe(safe == 1);
	}
	public void updateNode(int node, int left, int right){
		myTree[node].setLeft(left);
		myTree[node].setRight(right);
		if (left != -1)
			myTree[left].setParent(node);
		if (right != -1)
			myTree[right].setParent(node);
	}

	public boolean isPathSafe(int start, int end){
		int[] pre = new int[size];			
		boolean[] visited = new boolean[size];
		q = new LinkedList <Integer>();
	
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
	
		boolean isCitySafe = myTree[start].isIsCitySafe();
		while (curnode != start){
			isCitySafe = myTree[curnode].isIsCitySafe() && isCitySafe;
			curnode = pre[curnode];
		}
		return isCitySafe;
	}
}
