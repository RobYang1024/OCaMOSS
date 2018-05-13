import java.math.BigInteger;


public class main {
	
	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	
	public static BigInteger factorial(int n)
    {
		BigInteger newNameForBigI = BigInteger.ONE;
        for (int j = 1; j <= n; ++j) {
        	newNameForBigI = newNameForBigI.multiply(BigInteger.valueOf(j));
        }
        return newNameForBigI;
    }
}
