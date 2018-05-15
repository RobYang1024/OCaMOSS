

import java.Runtime;
import java.io.*;

public class differenceFile
{
  StringWriter sw =null;
  PrintWriter pw = null;
  public differenceFile()
  {
    sw = new StringWriter();
    pw = new PrintWriter();
  }
  public String compareFile()
  {
    try
    {
      Process  = Runtime.getRuntime().exec("diff History.txt Comparison.txt");

      InputStream write = sw.getInputStream();
      BufferedReader bf = new BufferedReader (new InputStreamReader(write));
      String line;
      while((line = bf.readLine())!=null)
          pw.println(line);
      if((sw.toString().trim()).equals(""))
      {
         System.out.println(" difference");
         return null;
      }
      System.out.println(sw.toString().trim());
    }catch(Exception e){}
    return sw.toString().trim();
  }
}