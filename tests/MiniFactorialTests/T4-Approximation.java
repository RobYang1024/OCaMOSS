import java.math.*;

public class main {
	
	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	
	//Abschätzung mit Stirling-Formel
	public static int factorial(int n)
    {
		double ret = Math.sqrt(2 * Math.PI * n) * Math.pow(n/Math.E,n);
		
        return (int)ret;
    }
}
