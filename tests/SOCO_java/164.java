
package java.httputils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;


public class BasicAuthHttpRequest extends HttpRequestClient
{
    String userName;
    String password;
    
    protected BasicAuthHttpRequest(String url, String userName, String password)
        throws MalformedURLException, IOException
    {
        setPassword(password);
        setUserName(userName);
        setServerURL(new URL(url));
        
        setStart(new Timestamp(System.currentTimeMillis()));

        String userPassword = userName + ":" + password;

        
        String encoding = new url.misc.BASE64Encoder().encode (userPassword.getBytes());

       

       setHttpConnection(
        (HttpURLConnection)this.getServerURL().openConnection());

        
        getHttpConnection().setRequestProperty ("Authorization", " " + encoding);
        doRequest();
    }

    
    protected BasicAuthHttpRequest(String url)
        throws MalformedURLException, IOException
    {
        super(url);
    }

    
    public BasicAuthHttpRequest()
    {
        super();
    }


    
    public String getPassword()
    {
        return password;
    }

    
    public String getUserName()
    {
        return userName;
    }

    
    public void setPassword(String string)
    {
        password = string;
    }

    
    public void setUserName(String string)
    {
        userName = string;
    }

    public static void main (String[] args)
    {
        BasicAuthHttpRequest client = null;
        try
        {
            client = new BasicAuthHttpRequest(args[0], args[1], args[2]);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (client != null && client.getCode() != HttpURLConnection.HTTP_UNAUTHORIZED)
            {
                System.out.println(
                    "Request response : \n" + client.getCode());


                System.out.println(
                    "Request processing time (milliseconds): " +
                    (client.getEnd().getTime() - client.getStart().getTime()));

                System.out.println(
                    "Request content: \n" + client.getContent());
            }
            else
            {
                System.out.println(
                    "Request response : \n" + client.getCode());


            }
        }
    }
}
