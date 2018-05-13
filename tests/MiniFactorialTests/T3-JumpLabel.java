
	public class main {
		
		static int currentFactor = 1;
		static int currentResult = 1;
		
		public static void main(String[] args) {
			
			System.out.println(factorial(10));
			

		}
		
		public static int factorial(int n)
	    {
			fac:
			while(currentFactor!=n) {
				currentResult *= currentFactor;
				continue fac;
			}
			return currentResult;
	    }
	}
