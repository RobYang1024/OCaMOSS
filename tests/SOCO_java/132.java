
import java.net.*;
import java.io.*;
import java.awt.*;


public class WatchDog extends Thread
{
   private String myUrl = "http://yallara.cs.rmit.edu./~";
   private PrintWriter p;
   private int changes = 0, flag = 0;
   private int FLAG = -1;
   private String fileName;


   public static void main (String args[])
   {
      WatchDog wd = new WatchDog();
   }


   public WatchDog()
   {
      readFile("file1.txt", flag);
      
   }


   public void run()
   {
      try
      {
         

         sleep(86400);
         readFile("file2.txt",1);
      }
      catch(Exception e)
      {   }
   }


   public void readFile(String fileName, int flag)
   {
      String data;

      File file = new File(fileName);
      file.delete();

      

      try
      {
         FileOutputStream fos = new FileOutputStream(fileName,true);
         PrintWriter pw = new PrintWriter(fos);

         URL url = new URL (myUrl);
         URLConnection urlCon = url.openConnection();

         InputStream is = (InputStream)urlCon.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader  = new BufferedReader(isr);


         

          
          {
             data = sw.readLine();
             pw.println(data);
          } while (data != null);

          pw.close();

       }
       catch (Exception e)
       {   }

       

       if(flag == 0)
       {   }
       else 
       {
           if(flag == 1)
           {
              detectChanges();
           }
       }
   }


    public void detectChanges()
    {
       File file = new File("difference.txt");
       file.delete();
       String message = "", data ="";

       try
       {
          

          FileOutputStream fos = new FileOutputStream("difference.txt",true);
          PrintWriter pw = new PrintWriter(fos);

          Process ps = Runtime.getRuntime().exec("diff file1.txt file2.txt");
          PrintWriter out = new PrintWriter(new OutputStreamWriter(ps.getOutputStream()));

          pw.println(readInputStream(ps.getInputStream()));
          pw.println("\n");
          pw.close();


          

          if(changes == 1)
          {
             FileReader f = new FileReader("difference.txt");
             BufferedReader bf  = new BufferedReader(f);


             Socket  = new Socket("wombat.cs.rmit.edu.", 25);
             p = new PrintWriter(bf.getOutputStream());

             sendMail(null);
             sendMail("HELO cs.rmit.edu.");
             sendMail("MAIL FROM: @.rmit.edu.");
             sendMail("RCPT : @.rmit.edu.");
             sendMail("DATA");

             
             {
                message = new String(message + data +"\n");
             } while ((data = bf.readLine()) != null);


             p.print(message);
             sendMail(".");
             
          }
        }
        catch(Exception e)
        {   }
    }



    public void sendMail(String text)
    {
       if(text == null)
       {   }
       else
       {
          TextArea message = new TextArea();

          message.append(text);
          message.append("\n");

          p.println(text);
          p.flush();
       }
    }

    String readInputStream(InputStream is)
    {
      String str;
      StringBuffer buf = new StringBuffer();
      InputStream isr = new BufferedInputStream(is);
      int check = 0;

      try
      {
         
         {
            buf.append((char)check);
            changes = 1;
         } while((check = isr.get()) != FLAG);
      }
      catch(IOException ioe)
      { }

      str = buf.toString();

      return str;
    }
}
