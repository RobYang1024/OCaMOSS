

import java.io.*;
import java.util.*;


public class WatchDog
{
    private static String userid;





    public static void main(String args[])
    {

        
        if (args.length != 1)
        {
              System.out.println("Please provide the Username  mail the possible differences.");
              System.exit(0);
        }
        else
              userid = args[0];


        while(true)
        {
             try
             {
                WatchDog wd = new WatchDog(userid);
                Thread.sleep(1000);
             }
             catch (InterruptedException i )
             {
                   i.printStackTrace();
                    System.exit(-1);
             }
             catch (Exception e)
             {
                    e.printStackTrace();
                    System.exit(-1);
             }
        }
    }




    public WatchDog(String userid)
    {
      checkFileChanges(userid);
    }






    private void checkFileChanges(String userid)
    {

             try
             {
                    Process p = Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students/");
                    Thread.sleep(1000);
                    p = Runtime.getRuntime().exec(new String[] {"//sh", "-c", "diff index.html index.html.1 > diff.txt"});
                    Thread.sleep(10000);


                    if (isFileChanged() == true)
                    {
                      System.out.println("Result: Difference found in . Check your Pine  for details");
                      mailFileChanges(userid);
                      recordFileChanges();
                    }
                    else
                    {
                        System.out.println("Result:  Difference found in ");
                        recordFileChanges();

                    }

             }
             catch (IOException e)
             {
                  e.printStackTrace();
                  System.exit(-1);
             }
             catch (InterruptedException ioe)
             {
                  ioe.printStackTrace();
                  System.exit(-1);
             }
             catch (Exception exp)
             {
                  exp.printStackTrace();
                  System.exit(-1);
             }
    }





    private void recordFileChanges()
    {
         try
         {

           Process p = Runtime.getRuntime().exec("mv index.html.1 index.html");
           Thread.sleep(1000);
        }
        catch (IOException e)
        {
                  e.printStackTrace();
                  System.exit(-1);
        }
        catch (InterruptedException oie)
        {
                ioe.printStackTrace();
                System.exit(-1);
        }
        catch (Exception exp)
        {
                exp.printStackTrace();
                System.exit(-1);
        }
    }





    private void mailFileChanges(String userid)
    {
         try
         {
           Process p = Runtime.getRuntime().exec(new String[] {"//sh", "-c", "diff index.html index.html.1 | mail " + userid + "@cs.rmit.edu."});
           Thread.sleep(1000);
         }
         catch (IOException e)
         {
                  e.printStackTrace();
                  System.exit(-1);
         }
         catch (InterruptedException ioe)
         {
                ioe.printStackTrace();
                System.exit(-1);
         }
         catch (Exception exp)
         {
                exp.printStackTrace();
                System.exit(-1);
         }
    }





    private boolean isFileChanged()
    {
         try
         {
           FileInputStream fIn = new FileInputStream ("diff.txt");
           DataInputStream di = new DataInputStream (fIn);

            Thread.sleep(1000);

           if( di!= null)
           {
              String str = di.readLine();

              if(str!=null)
                  return true;
              else
                  return false;
           }

         }
         catch (FileNotFoundException fe)
         {
                fe.printStackTrace();
                System.exit(-1);
         }
         catch (IOException e)
         {
                  e.printStackTrace();
                  System.exit(-1);
         }
         catch (InterruptedException ioe)
         {
                ioe.printStackTrace();
                System.exit(-1);
         }
         catch (Exception exp)
         {
                exp.printStackTrace();
                System.exit(-1);
         }
      return false;
    }
}







