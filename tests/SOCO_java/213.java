import java.io.*;
import java.util.*;

public class getImage
{
  private FileWriter fw;
  public getImage()
  {}
  public void translogFile(String[] result)
  {
     String fileName = "ImageList.txt";
   	 try{
	    fw=new FileWriter(fileName,false);
	    for(int i=0;result[i]!=null;i++)
        {
          fw.write(result[i]);
	      fw.write('\n');
	    }
	    fw.close();
   	    System.out.println("Saved sucessfully");
     }catch(IOException e){
		System.out.println("Error saving the file");
     }
  }
  public void translogFile1(String[] result)
  {
     String fileName = "JpgGifList.txt";
   	 try{
	    fw=new FileWriter(fileName,false);
	    for(int i=0;result[i]!=null;i++)
        {
          fw.write(result[i]);
	      fw.write('\n');
	    }
	    fw.close();
   	    System.out.println("Saved sucessfully");
     }catch(IOException e){
		System.out.println("Error saving the file");
     }
  }
  public void tokenFile(String fileName)
  {
      
      String tuteId="";
      String aWord="";
      String bWord="";
      String cWord="";
      String line=null;
      String[] myArray = new String[10];
      String[] myArrayB = new String[10];
      boolean studFlag=false;
      int j=0;
      int i=0;
      try
	    {
	        BufferedReader inputStream= new BufferedReader(new FileReader(fileName));
	     	line=inputStream.readLine();
			while (line!= null)
			{
				StringTokenizer word= new StringTokenizer(line," '\"' ");
				while (word.hasMoreTokens())
				{
					aWord=word.nextToken();
                    if(aWord.endsWith("gif") || aWord.endsWith("jpg"))
                    {
                       if(!aWord.startsWith("http"))
                       {
                           bWord = "http://www.cs.rmit.edu."+aWord;
                           myArray[j++]=bWord;
                           System.out.println(bWord);
                           StringTokenizer word1= new StringTokenizer(aWord,"/");
                           while (word1.hasMoreTokens())
                           {
                             cWord=word1.nextToken();
                             if(cWord.endsWith("gif") || cWord.endsWith("jpg"))
                             {
                                myArrayB[i++]=cWord;
                             }
                           }
                       }
                       else
                       {

                         myArray[j++]=aWord;
                         System.out.println(aWord);
                         StringTokenizer word1= new StringTokenizer(aWord,"/");
                         while (word1.hasMoreTokens())
                         {
                          cWord=word1.nextToken();
                          if(cWord.endsWith("gif") || cWord.endsWith("jpg"))
                          {
                            myArrayB[i++]=cWord;
                          }
                         }
                       }
					}
			    }
				line=inputStream.readLine();
			}
			 translogFile(myArray);
			 translogFile1(myArrayB);
		     inputStream.close();

        }
 		catch(FileNotFoundException e)
		{
			System.err.println("File "+fileName+" was not found");
        }
		catch(IOException e)
		{
			System.err.println("Error ");
        }
  }

}