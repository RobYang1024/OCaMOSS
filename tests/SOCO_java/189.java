

import java.io.*;
import java.net.*;
import java.util.*;
import java.String;
import java.Object;
import java.awt.*;



public class WatchDog
{
  private URL url;
  private URLConnection urlcon;
  private int lastModifiedSince = 0;
  private int lastModified[] = new int[2];

  private int count = 0;

  public static String oldFile;
  public static String newFile;

  private String diffFile;

  private BufferedWriter bw;
  private Process p;
  private Runtime r;
  private String fileName;

  
  
  private ArrayList old[]= new ArrayList[500];
  private ArrayList news[] = new ArrayList[500];
  private String info = "";
  private int index = 0;

  public WatchDog(String fileName)
  {
    this.fileName = fileName;
    oldFile = fileName + ".old";
	newFile = fileName + ".new";
    diffFile = "testFile.txt";
  }
  public static void main(String args[])
  {
    WatchDog wd = new WatchDog("TestDog");

	wd.detectChange(WatchDog.oldFile);
    while (true)
    {
      try
      {
    	Thread.sleep(86400000); 
	  }
	  catch (InterruptedException eee)
	  {
        System.out.println(eee.getMessage());
	  }
      wd.lastModifyChange();
    }
  }

  
  public void detectChange(String fName)
  {
    try
    {
      url = new URL("http://www.cs.rmit.edu./students/");
      urlcon = url.openConnection();
      urlcon.connect();

      lastModified[count] = urlcon.getLastModified();

      int length = urlcon.getContentLength();
      String contentType = urlcon.getContentType();

      if (url != null)
      {
        InputStream stream = (InputStream)(url.getContent());
        if (stream != null)
        {
          InputStreamReader reader = new InputStreamReader (stream);

          try { bw = new BufferedWriter(new FileWriter(fName));}
		  catch (IOException e){};

		  

		  try {
		     int i = stream.get();
		     while (i != -1)
		     {
		       bw.write(i);
		       i = stream.get();
		     }
		      
		  }
          catch (IOException e){};
        }
      }
	  count++;
      System.out.println("Content Type: " + contentType);
    }
    catch (IOException e)
    {
      System.out.println("Error: " + e.getMessage());
    }

  }

  
  public void lastModifyChange()
  {
	detectChange(newFile);
     m = lastModified[1] - lastModified[0];

	count = count - 1;

    if (m == 0 )
    {
      System.out.println("\nThe Web site does not change");
    }
    else
    {
      findDifferent();
      lastModified[0] = lastModified[1];
    }
  }

  
  public void findDifferent()
  {

	r = Runtime.getRuntime();
	try {p = r.exec("diff " + oldFile + " " + newFile);}
	catch (IOException e)
    {
      System.err.println("error: " + e.getMessage());
	}

	try { bw = new BufferedWriter(new FileWriter(diffFile));}
	catch (IOException e){};

	
	InputStream is = p.getInputStream();
	try {
	  int i = is.get();
	  while (i != -1) {
	  bw.write(i);

	  i = is.get();
	  }
	  bw.close();
	}
	catch (IOException e){System.out.println("Error: " + e.getMessage());}

	getDiffContent();

	File difffile = new File(diffFile);

    if (difffile.length() != 0) {
      sendMails();
      System.out.println("Mail was sent  @cs.rmit.edu." );
    }
    else
    System.out.println("WebWatch detected  changes ");

    
    difffile.delete();


  }

  
  public void sendMails()
  {
	try
    {
      MyMail em = new MyMail("wombat.cs.rmit.edu.");

	  em.setFrom("zhenyu_zhang@hotmail.");
	  em.setTo("@cs.rmit.edu.");
	  em.setSubject("Watch dog result: ");

      String output = "\n\nChange in Line: " + info + "\n";
      output += "\n**************************************\n";
      for (int i = 0; i < index; i++)
      {
        output += "\n" + i + 1  + ". Before Change: \n";
        for (int j = 0; j < old[i].size(); j++)
        {
           output += (String)old[i].get(j);
        }

        output += "\n\n   After Change: \n";

        for (int j = 0; j < news[i].size(); j++)
        {
          output += "\n" + (String)news[i].get(j);

        }
        output += "\n\n**************************************\n";
	  }
	  output += "\nDetected Image Changes: \n";
	  output += findImage() + "\n";

      em.setMessage(output);
      em.sendMail();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  
  public void getDiffContent()
  {
    index = 0;
    for (int i = 0; i < 500; i++)
    {
      old[i] = new ArrayList();
      news[i] = new ArrayList();
    }
	try {
      BufferedReader b = new BufferedReader(new FileReader(diffFile));

      info = b.readLine() + " ";

      String text ;
      while ((text = b.readLine()) != null)
      {
        if (text.charAt(0) == '<')
        {
           old[index].add(text);

           while ((text = b.readLine()) != null)
           {

              if (text.charAt(0) == '<')
              {
			     old[index].add(text);
              }
              else
              {
                 break;
              }

	       }
         }

         if (text.charAt(0) == '>')
		 {
           news[index].add(text);

           while ((text = b.readLine()) != null )
           {

              if (text.charAt(0) == '>' )
              {
                 news[index].add(text);

              }
              else
              {
                 break;
              }
	       }

         }

         index ++;
       }


     }
     catch (IOException io)
     {
        System.out.println(io.getMessage());
     }
  }

  
  public String imageDetect(String s)
  {
    StringTokenizer tokens1;
    StringTokenizer tokens2;
    String imChange = "";

    String imString;
    tokens1 = new StringTokenizer(s," <>");
    while (tokens1.hasMoreTokens())
    {
       imString = tokens1.nextToken();
       if (imString.indexOf("src") != -1 || imString.indexOf("SRC") != -1)
       {
          tokens2 = new StringTokenizer(imString,"=\"");
          imChange = tokens2.nextToken();
          imChange = tokens2.nextToken();
          break;
        }
        else
        {
           imChange = null;
        }
     }

    return imChange ;
  }

  
  public String findImage()
  {
    String imChange = "";
    String imString;
    for (int i = 0; i < index; i++)
    {
      imChange += "\n\n" + i + ". Image in old  is: ";
	  for (int j = 0; j < old[i].size(); j++)
      {
         imString = imageDetect((String)old[i].get(j));
         if (imString != null)
         {
            imChange += imString;
         }
      }

      imChange += "\n\n     Image in new  is: ";

      for (int j = 0; j < news[i].size(); j++)
      {
         imString = imageDetect((String)news[i].get(j));
         if (imString != null)
         {

			imChange += imString;
         }
      }

    }
    return imChange;
  }
}


