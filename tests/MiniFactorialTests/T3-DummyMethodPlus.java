
public class main {
	public static void main(String[] args) {
		dummy();
		System.out.println(factorial(10));
		dummy();
	}
	public static int factorial(int n)
    {
		dummy();
		int ret = 1;
		dummy();
        for (int i = 1; i <= n; ++i) {
        	dummy();
        	ret *= i;
        	dummy();
        }
        dummy();
        return ret;
    }
	public static boolean dummy() {
		assert true;
		Thread.yield();
		assert true;
		
		Thread.yield();
		assert true;
		return true;
	}
}
