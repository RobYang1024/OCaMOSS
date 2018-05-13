
public class main {
	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	public static int factorial(int n)
    {
		int ret = 1;
		int i = 0;
		while (true) {
			i++;
			ret *= i;
			if (i >=n ) break;
		}
        
        return ret;
    }
}
