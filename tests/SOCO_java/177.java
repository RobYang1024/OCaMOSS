import java.io.*;
import java.net.*;
import java.util.*;


public class WatchdogThread implements Runnable
{
    private Thread t;
    private Vector imageList;
    private String mainLink;
    private EmailClient email;
    private int delay;

	
	
	
	
    public WatchdogThread(String nMainLink, Vector images, EmailClient nEmail, int nDelay)
    {
        t=new Thread(this);

        mainLink = nMainLink;
        imageList=images;
        email = nEmail;
        delay = nDelay;

        t.start();
    }

    public void run()
    {
		String errors = "";

		try
		{
			
			Thread.currentThread().sleep(delay);

			
			errors = checkHTML();
			errors += checkImages();

			
			if (!errors.equals(""))
			{
				String message = "  some changes in the Students Home    as follows.\n\n";
				message += errors;

				email.sendMail("Change in Students Home ", message);
			}

			
			WatchdogThread watchdog = new WatchdogThread(mainLink, imageList, email, delay);
		}

		catch (InterruptedException e )
		{
			System.out.print();
		}

	}

	private String checkHTML()
	{
		String errors = "";
		String line, line2;
		int lineNum = 1;

		try
		{
			FileReader fRead = new FileReader("local.txt");
			BufferedReader file = new BufferedReader(fRead);

			URL url=new URL(mainLink);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			BufferedReader webpage = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line=file.readLine();
			line2=webpage.readLine();

			
			while ((line != null) && (line2 != null))
			{
				
				if (!line.equals(line2))
				{
					errors += "At line " + lineNum + "\n";
					errors += "Old:" + line.trim() + "\n";
					errors += "New:" + line2.trim() + "\n\n";
				}

				line = file.readLine();
				line2=webpage.readLine();
				lineNum+=1;
			}

			
			if (line != null)
			{
				errors += "  the following extra lines at the end of the old file.\n\n";

				while (line != null)
				{
					errors += line + "\n";
					line = file.readLine();
				}

				errors += "\n";
			}

			
			if (line2 != null)
			{
				errors += "  the following extra lines at the end of the new file.\n\n";

				while (line2 != null)
				{
					errors += line2 + "\n";
					line2 = file.readLine();
				}

				errors += "\n";
			}

			file.close();
			fRead.close();
			webpage.close();
			connection.disconnect();
		}
		catch (IOException ioe)
		{
			System.out.print(ioe);
		}
		return errors;
	}

	private String checkImages()
	{
		String errors = "";
		ImageFile image;
		HttpURLConnection imgConnection;
		URL imgURL;
		int contentLen;

		try
		{
			
			
			
			for (int i=0;i<imageList.size();i++)
			{
				image = (ImageFile) imageList.elementAt(i);

				imgURL = new URL(image.getImageUrl());
				imgConnection = (HttpURLConnection) imgURL.openConnection();

				contentLen = imgConnection.getContentLength();

				if (contentLen == -1)
					errors += "\nUnable  retrieve image file \"" + image.getImageUrl() + "\"\n";

				else if(image.getImageSize() != imgConnection.getContentLength())
					errors += "\nImage file \"" + image.getImageUrl() + "\" has changed in size\n";

				imgConnection.disconnect();
			}
		}
		catch (IOException ioe)
		{
			System.out.print(ioe);
		}

		return errors;
	}

}
