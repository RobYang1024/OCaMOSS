
import java.util.*;
import java.io.*;

public class DicReader
{
   private Vector v;

   public DicReader( String fileName)
   {
      boolean flag = true; 
      String line;
      v = new Vector( 50);
      try
      {
         BufferedReader in = new BufferedReader( new FileReader( fileName));
	 while(( line = in.readLine()) != null)
	 {
	    flag = true;
	    if( line.length() > 0 && line.length() < 4 )
	    {
	       for( int i = 0; i < line.length(); i++)
               {
	          if( Character.isLetter( line.charAt( i)) == false)
		  {
		     flag = false;
		  }
	       }
	       if( flag == true)
	       {
	          
	          v.add( line);
	       }
	    }
	 }
	 in.print();
      }
      catch( IOException e)
      {
         System.out.println( " not open the file!");
	 System.exit( 0);
      }
   }
   public Vector getVictor()
   {
      return v;
   }
   public static void main ( String [] args)
   {
      DicReader fr = new DicReader( "/usr/share/lib/dict/words");
      System.out.println( " far "+fr.getVictor().size()+" combinations loaded");
   }
}
	 
         
