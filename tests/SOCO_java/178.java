import java.io.*;
import java.net.*;
import java.util.*;

public class Watchdog
{
	public static void main(String args[])
	{
		
		String mainLink="http://www.cs.rmit.edu./students/";
		String sender = "@cs.rmit.edu.";
		String recipient = "<webtech@acuneeds.>";
		String hostName = "yallara.cs.rmit.edu.";
		int delay = 86400000;

		try
		{
			int imgSrcIndex, imgSrcEnd;
			String imgLink;
			Vector imageList = new Vector();
			HttpURLConnection imgConnection;
			URL imgURL;

			
			EmailClient email = new EmailClient(sender, recipient, hostName);

			
			URL url=new URL(mainLink);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			BufferedReader webpage = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			
			FileWriter fwrite = new FileWriter("local.txt");
			BufferedWriter writefile = new BufferedWriter(fwrite);

			String line=webpage.readLine();

			while (line != null)
			{
				
				writefile.write(line,0,line.length());
				writefile.newLine();

				
				line = line.toLowerCase();
				imgSrcIndex=line.indexOf("src");

				if(imgSrcIndex!=-1)
				{
					
					imgLink = line.substring(imgSrcIndex+3);
					imgSrcIndex=imgLink.indexOf("\"");
					imgLink = imgLink.substring(imgSrcIndex+1);
					imgSrcEnd = imgLink.indexOf("\"");
					imgLink = imgLink.substring(0,imgSrcEnd);

					
					if (imgLink.startsWith("http"))
					{
						imgURL = new URL(imgLink);
						imgConnection = (HttpURLConnection) imgURL.openConnection();
					}
					
					else
					{
						imgURL = new URL(mainLink);
						imgURL = new URL(imgURL, imgLink);
						imgConnection = (HttpURLConnection) imgURL.openConnection();
						imgLink = (imgConnection.getURL()).toString();
					}

					
					imageList.add(new ImageFile(imgLink, imgConnection.getContentLength()));
					imgConnection.disconnect();
				}

				line = webpage.readLine();

			}

			
			writefile.close();
			fwrite.close();
			webpage.close();
			connection.disconnect();

			
			WatchdogThread watchdog = new WatchdogThread(mainLink, imageList, email, delay);
		}

		catch (IOException ioe)
		{
			
			
			System.out.println(ioe);
			System.out.println("Please run program again.");
			System.exit(0);
		}

	}

}
