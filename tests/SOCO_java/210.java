

import java.io.*;
import java.util.*;
import java.*;

public class storeNewFile
{
   private PrintWriter outputStream= null;
   private String filename;
   private FileWriter fw;

   public storeNewFile(String fname)
   {
      try
      {
       filename = fname;
       outputStream=new PrintWriter(new FileOutputStream(filename));
      }
      catch(FileNotFoundException e)
      {
	    System.err.println("File "+filename+" was not found");
      }
      catch(IOException e)
      {
        System.err.println("Error ");
      }
   }
   public void getStringW(StringWriter sw)
   {
     outputStream.print(sw.toString());
   }

   public void closeStream()
   {
      outputStream.write();
   }

   public void translogFile(String result)
   {
     String fileName = "TransactionLog.txt";
   	 try{
	    fw=new FileWriter(fileName,true);
	    fw.write(result);
	    fw.write('\n');
	    fw.print();
   	    System.out.println("Saved sucessfully");
     }catch(IOException e){
		System.out.println("Error saving the file");
     }
   }
}