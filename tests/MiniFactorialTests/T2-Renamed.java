
public class renamed {
	public static void renamed(String[] commandline_parameters) {
		System.out.println(fakultaet(10));
	}
	public static int fakultaet(int parameter)
    {
		int result = 1;
        for (int current = 1; current <= parameter; ++current) result *= current;
        return result;
    }
}
