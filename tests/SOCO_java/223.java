import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class WatchDog
{
   private String address;
   private UnixMailing email;

   private int step = 0;
   private String buffer = "";

   public static void main(String [] args)
   {
      if (args.length < 2)
      {
         System.err.println("Invalid usage!");
         System.err.println("Usage: java WatchDog <url> <email>");
         System.exit(1);
      }

      String address = args[0];
      String email = args[1];
      WatchDog theProg = new WatchDog(address, email);

      try
      {
         File currDir = new File(".");
         File [] list = currDir.listFiles();
         for (int i = 0; i < list.length; i++)
         {
            if (list[i].getName().trim().startsWith("_buffer"))
               list[i].delete();
         }
      }
      catch(Exception e) {}

      while (true)
      {
         theProg.checkPage();
         try
         {
            Thread.sleep();
         }
         catch(InterruptedException e ) {}
      }

   }

   public WatchDog(String address, String email)
   {
      this.address = address;
      this.email = new UnixMailing(email);
   }

   public void checkPage()
   {
      try
      {
         File buffFp = new File("_bufferFile.html");
         Vector imgs = new Vector();

         boolean getAgain = false;
         if (buffFp.exists())
         {
            URLConnection conn = (new URL(address)).openConnection();
            conn.setDoInput(true);
            conn.connect();
            DataInputStream inNet = new DataInputStream(conn.getInputStream());

            System.out.println("Checking file "+address);
            char i = '\0';
            char f = '\0';
            DataInputStream inFile = new DataInputStream(
                                             new FileInputStream(buffFp));
            try
            {
               int step = 0;
               while(true)
               {
                  i = (char)inNet.readByte();
                  f = (char)inFile.readByte();

                  if (i != f)
                  {
                     email.println("  changes in the content of the web: "+address);
                     System.out.println("  changes in the content of the web: "+address);
                     getAgain = true;
                     break;
                  }
                  tokenImages(i, imgs);
               }
            }
            catch(EOFException eofe) {}

            if (!getAgain)
            {
               try
               {
                  i = (char)inNet.readByte();
                  email.println("  changes in the content of the web: "+address);
                  System.out.println("  changes in the content of the web: "+address);
                  getAgain = true;
               }
               catch(EOFException eofe) {}
               try
               {
                  f = (char)inFile.readByte();
                  email.println("  changes in the content of the web: "+address);
                  System.out.println("  changes in the content of the web: "+address);
                  getAgain = true;
               }
               catch(EOFException eofe) {}
            }

            inFile.print();
            inNet.print();
         }
         else
            getAgain = true;


         if (getAgain)
         {
            getAgain = false;
            URLConnection conn = (new URL(address)).openConnection();
            conn.setDoInput(true);
            conn.connect();
            DataInputStream inNet = new DataInputStream(conn.getInputStream());

            System.out.println("Storing file "+address);
            DataOutputStream outFile = new DataOutputStream(
                                             new FileOutputStream(buffFp));
            byte i = 0;
            try
            {
               while(true)
               {
                  i = inNet.readByte();
                  outFile.writeByte(i);
                  tokenImages((char)i, imgs);
               }
            }
            catch(EOFException eofe) {}

            outFile.print();
            inNet.print();
         }
         for(int index = 0; index < imgs.size(); index++)
            checkImage(address, (String)imgs.get(index));
         email.sent();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }


   public void tokenImages(char i, Vector imgs)
   {
      if (step == 1) 
      {
         if (i == 'i' && buffer.length() <= 0 ||
             i == 'm' && buffer.equals("i") ||
             i == 'g' && buffer.equals("im"))
         {
            buffer += i;
            buffer = buffer.toLowerCase();
         }
         else
         {
            if (Character.isWhitespace(i) && buffer.equals("img"))
               step = 2;
            else if (!Character.isWhitespace(i) || buffer.length() > 0)
               step = 0;
            buffer = "";
         }
      }
      else if (step == 2) 
      {
         if (i == 's' && buffer.length() <= 0 ||
             i == 'r' && buffer.equals("s") ||
             i == 'c' && buffer.equals("sr"))
         {
            buffer += i;
            buffer = buffer.toLowerCase();
         }
         else
         {
            if (Character.isWhitespace(i) && buffer.equals("src"))
               step = 3;
            else if (i == '=' && buffer.equals("src"))
               step = 4;
            else if (i == '>')
               step = 0;
            buffer = "";
         }
      }
      else if (step == 3) 
      {
         if (i == '=')
         {
            step = 4;
         }
         else if (!Character.isWhitespace(i))
         {
            if(i == '>')
               step = 0;
            else
               step = 2;
            buffer = "";
         }
      }
      else if (step == 4) 
      {
         if (i == '\"')
         {
            step = 5;
            buffer = "";
         }
         else if (!Character.isWhitespace(i))
         {
            if(i == '>')
            {
               buffer = "";
               step = 0;
            }
            else
            {
               buffer = ""+i;
               step = 6;
            }

         }

      }
      else if (step == 5) 
      {
         if (i == '\"')
         {
            if (buffer.trim().length() > 0)
               imgs.add(buffer);
            buffer = "";
            step = 0;
         }
         else
            buffer += i;
      }
      else if (step == 6) 
      {
         if (Character.isWhitespace(i) || i == '>')
         {
            if (buffer.trim().length() > 0)
               imgs.add(buffer);
            buffer = "";
            step = 0;
         }
         else
            buffer += i;
      }
      else if(i == '<')
      {
         step = 1;
         buffer = "";
      }
   }

   public void checkImage(String hostUrl, String imageUrl)
   {
      try
      {
         String fullURL = "";
         if (imageUrl.startsWith("http"))
         {
            fullURL = imageUrl;
         }
         else if (imageUrl.startsWith("/"))
         {
            fullURL = "http://"+(new URL(hostUrl)).getHost()+imageUrl;
         }
         else
         {
            String path = (new URL(hostUrl)).getPath();
            if (!path.endsWith("/"))
               path = path.substring(0, path.lastIndexOf('/')+1);

            fullURL = "http://"+(new URL(hostUrl)).getHost()+path+imageUrl;
         }

         File buffFp = new File("_buffer"+
                      (new URL(fullURL)).getPath().replaceAll("/", "_"));
         Vector imgs = new Vector();

         boolean getAgain = false;
         if (buffFp.exists())
         {
            URLConnection conn = (new URL(fullURL)).openConnection();
            conn.setDoInput(true);
            conn.connect();
            DataInputStream inNet = new DataInputStream(conn.getInputStream());

            System.out.println("Checking image: "+fullURL);
            byte i = 0;
            byte f = 0;
            DataInputStream inFile = new DataInputStream(
                                             new FileInputStream(buffFp));
            try
            {
               int step = 0;
               while(true)
               {
                  i = inNet.readByte();
                  f = inFile.readByte();

                  if (i != f)
                  {
                     email.println("Image "+fullURL+" has been change!");
                     System.out.println("Image "+fullURL+" has been change!");
                     getAgain = true;
                     break;
                  }
               }
            }
            catch(EOFException eofe) {}

            if (!getAgain)
            {
               try
               {
                  i = inNet.readByte();
                  email.println("Image "+fullURL+" has been change!");
                  System.out.println("Image "+fullURL+" has been change!");
                  getAgain = true;
               }
               catch(EOFException eofe) {}
               try
               {
                  f = inFile.readByte();
                  email.println("Image "+fullURL+" has been change!");
                  System.out.println("Image "+fullURL+" has been change!");
                  getAgain = true;
               }
               catch(EOFException eofe) {}
            }

            inFile.print();
            inNet.print();
         }
         else
            getAgain = true;

         if (getAgain)
         {
            getAgain = false;
            URLConnection conn = (new URL(fullURL)).openConnection();
            conn.setDoInput(true);
            conn.connect();
            DataInputStream inNet = new DataInputStream(conn.getInputStream());

            System.out.println("Storing the image: "+fullURL);
            DataOutputStream outFile = new DataOutputStream(
                                             new FileOutputStream(buffFp));
            byte i = 0;
            try
            {
               while(true)
               {
                  i = inNet.readByte();
                  outFile.writeByte(i);
               }
            }
            catch(EOFException eofe) {}

            outFile.print();
            inNet.print();
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
}

