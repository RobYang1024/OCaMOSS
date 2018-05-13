/**
 * Class with comments.
 * More comments.
 * 
 * @author Author
 * @see #factorial(int)
 *
 */
public class main {
/**
 * Function with comments.
 * More comments.
 * @param args
 */
public static void main(String[] args)
{ 	//prints factorial of ten
	System.out.println(factorial(10)); }
/**
 * Function with comments.
 * More comments.
 * Factorial of n.
 * @param n
 * @return factorial
 */
public static int factorial(int n) {
	//lots of comments
	int ret = 1;
	//lots of comments
	for (int i = 1;i <= n;++i)
		//lots of comments
		ret *= i;
	//lots of comments
	return ret;
	}
}