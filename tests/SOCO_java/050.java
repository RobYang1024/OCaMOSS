import java.net.*;
import java.io.*;
import java.*;
import java.Runtime.*;
import java.Object.*;
import java.util.*;
import java.util.StringTokenizer;

public class makePasswords
{
  public String [ ]  alphabet1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
  "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
  "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
  "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
  
  public String [ ]  alphabet2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
  "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
  "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
  
  public String [ ]  alphabet3 = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
  "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
  "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
  "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
  
  Vector in = new Vector();
  
  public void makePass()
  {
   try
  {
  PrintWriter pw = new PrintWriter(new FileWriter("new.txt"));
  
    for(int i = 0; i < alphabet1.length; i++)
    {
      for(int j = 0; j < alphabet2.length; j++)
       {
        for(int k = 0; k < alphabet3.length; k++)
	   {
	    String newStr = (alphabet1[i]+alphabet2[j]+alphabet3[k]);
	    pw.println(newStr);
	  }
      }
    }
    }catch(Exception ex){} 
  }
  
  
  private StringTokenizer tokenizer;
  private BufferedReader bf;
  private String line;
  private String first;
   
  public void loadFile()throws NoSuchElementException, IOException
  {
    
    try{
    bf = new BufferedReader(new FileReader("new.txt"));
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
  
  
  
  public static void main(String args[])
  {
    makePasswords mP = new makePasswords();
    mP.makePass();
    Vector v = mP.getVector();
    
  }
}
