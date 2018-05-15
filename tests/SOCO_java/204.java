
import java.io.*;
import java.net.*;

public class Copier
{
   private URL target;

   public Copier( String fileName)
   {
      try
      {
         String line;
         BufferedReader ;
	 BufferedWriter bout;
         target = new URL( "http://www.cs.rmit.edu./students");
	 InputStream hm = target.openStream();
	 HttpURLConnection urlcon = ( HttpURLConnection) target.openConnection();
        bf  = new BufferedReader( new InputStreamReader( target.openStream()));
	 bout = new BufferedWriter(new FileWriter(fileName));
         while((line = bf.readLine()) != null)
         {
            bout.write( line+"\n");  
         }
	 
	 bout.print();
      }
      catch( Exception e)
      {
         System.out.println("Something  wrong!  "+e);
         System.exit(0);
      }
   }
   public static void main (String[] args)
   {
      Copier c = new Copier("response.html");
   }
}

         
      

   


 

	
