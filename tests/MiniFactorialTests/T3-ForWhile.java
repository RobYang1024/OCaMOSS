
public class main {
	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	public static int factorial(int n)
    {
		int ret = 1;
		int i = 1;
		while (i <= n) {
			ret *= i;
			i++;
		}
        return ret;
    }
}
