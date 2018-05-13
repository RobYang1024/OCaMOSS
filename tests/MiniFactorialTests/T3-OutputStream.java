import java.io.PrintWriter;


public class main {
	public static void main(String[] args) {
		PrintWriter outStream;
		outStream = new PrintWriter(System.out);
		outStream.println(factorial(10));
	}
	public static int factorial(int n)
    {
		int ret = 1;
        for (int i = 1; i <= n; ++i) ret *= i;
        return ret;
    }
}
