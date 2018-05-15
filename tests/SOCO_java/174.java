 




import java.util.*;
import java.io.*;

public class MyTimer
{	

	public static void main(String args[])
	{
		Watchdog watch = new Watchdog();
		Timer time = new Timer();
		time.schedule(watch,864000000,864000000);
		
			
	}
}
