





import java.util.*;
import java.io.*;

public class WatchDog
{	

	public static void main(String args[])
	{

           Runtime rt1 = Runtime.getRuntime();
	   Process prss1= null;

           try
            {
             prss1 = rt1.exec("wget -R mpg,mpeg, --output-document=first.html http://www.cs.rmit.edu./students/");
            }catch(java.io.IOException e){}

	   MyWatchDogTimer w = new MyWatchDogTimer();
	   Timer time = new Timer();
	   time.schedule(w,864000000,864000000);

		
	}
}
