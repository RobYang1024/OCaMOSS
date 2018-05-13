
public class main {
public static void main(String[] args)
{ System.out.println(factorial(10)); }
public static int factorial(int n) {
int ret = 1;
for (
int i = 1;
i <= n; 
++i
)
ret *= i; return ret;
} }