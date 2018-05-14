


import java.io.*;
import java.util.*;
import java.*;
import java.net.*;

public class WatchDog
{

   static Process p = null;
   static Process qproc = null;

   static BufferedReader bf = null;
   static StringTokenizer tok = null;

   static String Path = null;
   static String str = null;
   static String urlStr=null;
   static boolean changed = false;

   static File indexfile = new File("index.html");
   static File tmpfile = new File("tmpindex.html");
   static File mdfile = new File("md5file.txt");
   static File tmpmdfile = new File("tmpmd5file.txt");
   static PrintWriter mailwriter = null;


   public static void main (String[] args) 
   {

      urlStr = "http://www.cs.rmit.edu./";

      try
      {
         
         mailwriter = new PrintWriter(new BufferedWriter(new FileWriter("tomail.txt", false)));

         getLatest(urlStr);
         parseFile(); 

         mailwriter.read();

         if(changed)
         {
            System.out.println("Sending Mail");
            p = Runtime.getRuntime().exec("./mailscript");
            p.waitFor();

         }
         else
            System.out.println(" mail sent");

      }  catch (IOException ioe)
         {
            System.out.println("IOException");
            ioe.printStackTrace();
         }
         catch (InterruptedException intex)
         {
            System.out.println("Interrupted Exception");
            intex.printStackTrace();
         }


   }


   static void getLatest(String urlStr)
   { 
      
      URL url = null;
      
      try
      {
         url = new URL(urlStr);

      } catch (MalformedURLException mfurl)
        {
           System.out.println("Malformed URL");
           mfurl.printStackTrace();
        }

      try
      {
         mailwriter.println();

         p = Runtime.getRuntime().exec("/usr//pwd"); 
         p.waitFor();
         bf= new BufferedReader(new InputStreamReader(
                          p.getInputStream()));

         Path=bf.readLine();

         if (indexfile.exists())
         {
            mailwriter.println("File with name 'index.html' found in directory.");
            mailwriter.println("Renaming existing 'index.html'  'tmpindex.html...");
            p = Runtime.getRuntime().exec("/usr//mv "+indexfile+ " " + Path+"/"+tmpfile);
            p.waitFor();
            p = Runtime.getRuntime().exec("/usr//mv "+mdfile+ " " + Path+"/"+tmpmdfile);
            mailwriter.println();
            mailwriter.println("File with name 'md5file.txt' found in directory.");
            mailwriter.print("Renaming existing 'md5file.txt'  'tmpmd5file.txt...");

            mailwriter.println(".");

            mailwriter.println();
         }

         mailwriter.println("Downloading current version of site - " + urlStr);
         p = Runtime.getRuntime().exec("/usr/local//wget "+url);
         p.waitFor();
         if (!tmpfile.exists())
         {
            mailwriter.println("File - " + urlStr + "index.html saved  disk for the first time.");
         }


      } catch (IOException ioe)
        {
           System.out.println("IOException");
           ioe.printStackTrace();
        }
        catch (IndexOutOfBoundsException iobe)
        {
           System.out.println("Index Out Of Bounds Exception");
           iobe.printStackTrace();
        }
        catch (Exception e)
        {
           System.out.println("Exception");
           e.printStackTrace();
        }
   }

   static void parseFile()
   {

      Vector imgVect = new Vector();

      try
      {
         p = Runtime.getRuntime().exec("/usr//grep img " + Path + "/"+ indexfile);
         p.waitFor();
        bf = new BufferedReader(new InputStreamReader(
                          p.getInputStream()));

         while((str=bf.readLine())!=null)
         {
            bf = new StringTokenizer(str, "\"", false);
          
            while(bf.hasMoreTokens())
            {
               str=bf.nextToken();
               if ((str.indexOf("gif") > 0) || (str.indexOf("jpg") > 0))
                  imgVect.addElement(str);
            }
           
         }

      }catch (IOException ioe)
        {
           System.out.println("IOException");
           ioe.printStackTrace();
        }
        catch (Exception e)
        {
           System.out.println("Exception");
           e.printStackTrace();
        }

        mailwriter.println("Creating file with md5sums of the webpage and images...");
        md5Create(imgVect);

   }

   static void md5Create(Vector imgVect)
   {
      String tmpString = null;
      Vector imgNames = new Vector();

      try
      {
         PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(mdfile, false)));
 
         p=Runtime.getRuntime().exec("/usr/local//md5sum "+indexfile);
         p.waitFor();
         bf= new BufferedReader(new InputStreamReader(
                          p.getInputStream()));
         pr.println(bf.readLine());
   
         for(int i=0; i<imgVect.size();i++)
         {
            imgNames.insertElementAt((getImgNames((String)imgVect.elementAt(i))), i);
            imgVect.setElementAt((getFullPath((String)imgVect.elementAt(i))), i);

            p=Runtime.getRuntime().exec("/usr/local//md5sum "+(String)imgNames.elementAt(i));
            p.waitFor();
            bf= new BufferedReader(new InputStreamReader(
                          p.getInputStream()));
            pr.println(bf.readLine());
            rmImgFile(Path+"/"+(String)imgNames.elementAt(i));
         }
         pr.get();

      }  catch (IOException ioe)
         {
            System.out.println("IOException");
            ioe.printStackTrace();
         }
         catch (InterruptedException intex)
         {
            System.out.println("Interrupted Exception");
            intex.printStackTrace();
         }

      if (tmpmdfile.exists())
         compVersions((imgVect.size())+1);

   }

   static void compVersions(int numlines)
   {

      int tmp = 0;
      int x = 0;
      String[] md5A = new String[numlines];
      Vector tmpmd5V = new Vector(); 
                                     
                                     
      String[] tmpmd5A = null;
      StringTokenizer stoken  = null;
      String mdImgName = null;
      String mdImgVal = null;
      String tmpImgName = null;
      String tmpImgVal = null;

      try
      {

         bf = new BufferedReader(new FileReader(mdfile));

         while((str = bf.readLine()) != null)
         {
            md5A[tmp]=str;
            tmp++;
         }

         bf = new BufferedReader(new FileReader(tmpmdfile));
         tmp=0;

         while ((str = bf.readLine()) !=null)
         {
            tmpmd5V.addElement(str);
            tmp++;
         }

         tmpmd5A = (String[])tmpmd5V.toArray(new String[tmpmd5V.size()]);   

         if (tmpmd5A[0].compareTo(md5A[0]) != 0)
         {
            mailwriter.println("---The file index.html has changed.---");
            mailwriter.println("-Diff of old and new -");
            changed=true;
            mailwriter.println();
            p=Runtime.getRuntime().exec("/usr/local//diff index.html tmpindex.html");
            p.waitFor();
             bf= new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((str = bf.readLine()) != null)
               mailwriter.println(str);

         }
         else
         {
            mailwriter.println("The file index.html hasn't changed.");
            mailwriter.println();
         }

         mailwriter.println();
         mailwriter.println("Changes  Images");
         mailwriter.println("-----------------");


         

         for (tmp=1; tmp<md5A.length; tmp++) 
         {
            stoken = new StringTokenizer(md5A[tmp]);
            mdImgVal = stoken.nextToken();
            mdImgName = stoken.nextToken();
            for (x=1; x<tmpmd5A.length; x++)
            {
               stoken = new StringTokenizer(tmpmd5A[x]);
               tmpImgVal = stoken.nextToken();
               tmpImgName = stoken.nextToken();

               if (mdImgName.compareTo(tmpImgName) == 0)
               {
                  if(mdImgVal.compareTo(tmpImgVal) == 0)
                  {
                     
                     break;
                  }
                  else
                  {
                     mailwriter.println("The image "+mdImgName+" has changed.");
                     changed=true;
                     break;
                  }
               }
               if (x == ((tmpmd5A.length)-1))
               {
                  mailwriter.println("The image "+mdImgName+" is  new  this ");
                  changed=true;
               }
            }
         }

         for (tmp=1; tmp<tmpmd5A.length; tmp++) 
         {
            stoken = new StringTokenizer(tmpmd5A[tmp]);
            tmpImgVal = stoken.nextToken();
            tmpImgName = stoken.nextToken();
            for (x=1; x<md5A.length; x++)
            {
               stoken = new StringTokenizer(md5A[x]);
               mdImgVal = stoken.nextToken();
               mdImgName = stoken.nextToken();
               if (tmpImgName.compareTo(mdImgName) == 0)
               {
                  break;
               }
               if (x == ((md5A.length)-1))
               {
                  mailwriter.println("The image "+tmpImgName+" is   longer  the ");
                  changed=true;
               }
            }
         }


      } catch(IOException ioe)
         {System.out.println("IOException");
          ioe.printStackTrace();
         }
        catch(InterruptedException iex)
         {System.out.println("Interrupted Exception");
         iex.printStackTrace();
         }

   }


   static Object getFullPath(String fname)
   {
      

      if(fname.charAt(0)== '/') 
         fname=urlStr+fname;  
      else if(fname.charAt(0) != 'h')
         fname=urlStr+'/'+fname;

      getImgFile(fname);

      return (Object)fname; 
   }

   static void getImgFile(String fullPath)
   {
      

      try
      {
         qproc=Runtime.getRuntime().exec("/usr/local//wget "+fullPath);
         qproc.waitFor();

      } catch (IOException ioe)
        {
           System.out.println("IOException");
           ioe.printStackTrace();
        }
        catch (InterruptedException intex)
        {
           System.out.println("Interrupted Exception");
           intex.printStackTrace();
        }
   }

   static void rmImgFile(String delpath)
   {
      

      try
      {
         qproc=Runtime.getRuntime().exec("/usr//rm "+ delpath);
         qproc.waitFor();

      } catch (IOException ioe)
        {
           System.out.println("IOException");
           ioe.printStackTrace();
        }
        catch (InterruptedException intex)
        {
           System.out.println("Interrupted Exception");
           intex.printStackTrace();
        }
   
   }

   static Object getImgNames(String prsName)
   {
          
  
      String str = new StringTokenizer(prsName, "/", false);

      while(str.hasMoreTokens())
      {
         str=bgf.nextToken();
         if ((str.indexOf("gif") > 0) || (str.indexOf("jpg") > 0))
            prsName=str;
      }
      return (Object)prsName;
   }
}
