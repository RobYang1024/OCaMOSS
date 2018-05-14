import java.net.*;
import java.io.*;
import java.*;
import java.Runtime.*;
import java.Object.*;
import java.util.*;
import java.util.StringTokenizer;


public class ReadFile
{
  private StringTokenizer tokenizer;
  private BufferedReader bf;
  private String line;
  private String first;
  Vector in = new Vector();
  
  public void loadFile()throws NoSuchElementException, IOException
  {
    System.out.println("in loadFile");
    try{
    bf = new BufferedReader(new FileReader("words"));
    }
    catch(FileNotFoundException fe){}
    catch(IOException io){}
    while((line = bf.readLine())!=null)
      {

        int index = 0;
        tokenizer = new StringTokenizer(line);
        try
	   {
	     first = tokenizer.nextToken();
	     
	     
	     if (first.length() == 3)
	     {
		in.add(first);
	     }
	  }
        catch(NoSuchElementException n)
	   {
          System.out.println("File Loaded Succesfully");

        }

      }
   }
   public Vector getVector()
   {
    return in;
   }
   public static void main (String args[])
   {
     Vector v = new Vector();
     try
     {
       System.out.println("in ");
	 ReadFile  rf = new ReadFile();
       rf.loadFile();
       v = rf.getVector();
	 
     }
     catch(IOException e)
     {
      System.out.println(e);
     }
     System.out.println("size:" + v.size());
     for (int i = 0; i< v.size(); i++)
      {
        System.out.println(i+1+ ":" + v.elementAt(i));
      }
     
     
   }
   
}
