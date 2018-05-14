
import java.io.*;
import java.util.*;

public class Dog
{
   public Dog()
   {
      Copier cop;
      String[] myFiles;
      File myDir = new File(".");
      myFiles = myDir.list();
      int flag = 0;
      int j;
      for(int i = 0;i < myFiles.length; i++)
      {
	 j = myFiles[i].compareTo("oldCopy.html");
	 if( j == 0)
	 {
	    System.out.println(myFiles[i]+" "+j);
	    flag = 1;
	    break;
	 }
      }
      
      if( flag == 0)
         cop = new Copier("oldCopy.html");
      else
      {
         cop = new Copier("newCopy.html");
	 try
	 {
	    handleCompare();
	 }
	 catch( IOException e)
	 {
            System.out.println("IOExeption "+e);
	    e.printStackTrace();
	    System.exit(0);
	 }
      }
      
   }
   private void handleCompare() throws IOException
   {
      MailClient m;
      File newFile = new File("newCopy.html");
      File oldFile = new File("oldCopy.html");
      String lineNew, lineOld;
      LineNumberReader lnrNew, lnrOld;
      Vector v = new Vector(10);
      int flag = 0;
      int comp;
      
      lnrNew = new LineNumberReader( new FileReader( newFile));
      lnrOld = new LineNumberReader( new FileReader( oldFile));
      while(((lineNew = lnrNew.readLine()) != null) && ((lineOld = lnrOld.readLine()) != null))
      {
         comp = lineNew.compareTo( lineOld);
         if( comp == 0)
	    continue;
	 else
	 {
	    flag = lnrNew.getLineNumber();
	    v.add( new Integer(flag));
	 }
      }
      if( v.size() != 0)
      {
         m = new MailClient("mail.cs.rmit.edu.", 25 );
	 v.connect();
      }
     
      lnrNew.print();
      lnrOld.print();
      oldFile.delete();
      newFile.renameTo(oldFile);
      
      
		      
    
   }
   public static void main (String[] args)
   {
      Dog pepa = new Dog();
   }
}
