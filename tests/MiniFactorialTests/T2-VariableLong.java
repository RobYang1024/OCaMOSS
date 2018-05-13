
public class main {
	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	public static long factorial(long n)
    {
		long ret = 1;
        for (long i = 1; i <= n; ++i) ret *= i;
        return ret;
    }
}
