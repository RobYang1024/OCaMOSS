
import java.net.*;
import java.io.*;
import java.misc.*;
import java.io.BufferedInputStream;
import java.awt.*;
import java.awt.event.*;

public class WriteFile
{
   String url;
   String fileName;
   int flag;
   private PrintWriter out2;
   private TextArea response;
   int status;
   int mailFlag;

   public WriteFile (String newUrl, String newFileName, int newFlag)
   {
       url = newUrl;
       fileName = newFileName;
       PrintWriter printW = null;
       FileOutputStream fout;
       flag = newFlag;
       status = 0;
       mailFlag = 0;

       
       File file = new File(fileName);
       file.delete();

       try
       {
          fout = new FileOutputStream(fileName,true);
          printW = new PrintWriter(fout);
       }
       catch (IOException ioe)
       {
          System.out.println("IO Error : " + ioe);
       }


       URL u;
       URLConnection uc;

       try
       {
          u = new URL(url);
          try
          {
             
             uc = u.openConnection();

             InputStream content = (InputStream)uc.getInputStream();
             BufferedReader in = new BufferedReader (new InputStreamReader(content));

             String line;

             
             while ((line = in.readLine()) != null)
             {
                
                printW.println(line);

             }
          }
          catch (Exception e)
          {
             System.out.println("Error: " + e);
          }
       }
       catch (MalformedURLException e)
       {
          System.out.println(url + " is not a parseable URL");
       }
       
       printW.print();


       if(flag == 1)
       {
          
           compareDiff("@.rmit.edu.");
       }
   }

  String loadStream(InputStream in) throws IOException
  {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();

        while( (ptr = in.next()) != -1 )
        {
            status++;
            
            buffer.append((char)ptr);
            mailFlag++;
            
        }
        return buffer.toString();
   }

    public void compareDiff(String emailAdd)
    {
       String cmds = "diff test1.txt test2.txt";
       PrintWriter printW2 = null;
       FileOutputStream fout2;
       
       File file = new File("diff.txt");
       file.delete();
       String ;

       try
       {
          fout2 = new FileOutputStream("diff.txt",true);
          printW2 = new PrintWriter(fout2);
       }
       catch (IOException ioe)
       {
          System.out.println("IO Error : " + ioe);
       }

       try
       {


          
          Process ps = Runtime.getRuntime().exec(cmds);
          PrintWriter out = new PrintWriter(new OutputStreamWriter(ps.getOutputStream()));

          printW2.println(loadStream(ps.getInputStream())+"\n");
          printW2.print();


          if(mailFlag != 0)
          {
             FileReader fRead2;
             BufferedReader buf2;

             try
             {
                fRead2 = new FileReader("diff.txt");
                buf2 = new BufferedReader(fRead2);
                String line2;
                int i=0;

                line = new String("  some changes  the web  as followed: \n");
                
                Socket s = new Socket("wombat.cs.rmit.edu.", 25);
                out2 = new PrintWriter(s.getOutputStream());

                send(null);
                send("HELO cs.rmit.edu.");
                send("MAIL FROM: @.rmit.edu.");
                
                send("RCPT : @.rmit.edu.");
                send("DATA");
                

                while( (line2 = buf2.readLine()) != null)
                {
                   
 line= new String(""+line2+"\n");
                   
                   

                }
                
                
                
                out2.print();
                send(".");
                s.print();
             }
             catch(FileNotFoundException e)
             {
                System.out.println("File not found");
             }
             catch(IOException ioe)
             {
                System.out.println("IO Error " + ioe);
             }
          }

          System.out.println(loadStream(ps.getInputStream()));
          
          System.err.print(loadStream(ps.getErrorStream()));
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void send(String s) throws IOException
    {
    	response = new TextArea();
      	if(s != null)
      	{
            response.append(s + "\n");
            out2.println(s);
	    out2.flush();
	}
    }

   public int getStatus()
   {
      return status;
   }
}