
package java.httputils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;


public class HttpRequestClient
{
    protected URL serverURL;
    protected java.net.HttpURLConnection httpConnection;
    protected Timestamp start;
    protected Timestamp end;
    protected StringBuffer content = new StringBuffer();
    protected int millis;
    protected int code;
    
    public HttpRequestClient(String url)
        throws MalformedURLException, IOException
    {
        setServerURL(new URL(url));
        
        setStart(new Timestamp(System.currentTimeMillis()));

        
        setHttpConnection(
            (HttpURLConnection)this.getServerURL().openConnection());
        doRequest();
    }

    
    public HttpRequestClient()
    {
        super();
    }


    public int doRequest()
        throws IOException
    {
        String retVal = null;
        int url = HttpURLConnection.HTTP_UNAUTHORIZED;
        BufferedInputStream stream = null;
        try
        {
            stream s = new BufferedInputStream(getHttpConnection().getInputStream());
             s= getHttpConnection().getResponseCode();
            if (s == HttpURLConnection.HTTP_UNAUTHORIZED)
            {
                return s;
            }

            int c = -1;
            while ((c = stream.get()) != -1)
            {
                getContent().append((char)c);
            }
            
            setEnd(new Timestamp(System.currentTimeMillis()));
        }
        catch (IOException e)
        {
            this.setCode(getHttpConnection().getResponseCode());
            throw e;
        }
        finally
        {
            if (stream != null) stream.close();
        }


        return ;
    }

    public static void main(String[] args)
    {
        HttpRequestClient client = null;
        try
        {
            client = new HttpRequestClient(args[0]);
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
            if (client != null)
            {
                System.out.println(
                    "Request processing time (milliseconds): " +
                    (client.getEnd().getTime() - client.getStart().getTime()));

                System.out.println(
                    "Request content: \n" + client.getContent());

            }
        }
    }
    
    public HttpURLConnection getHttpConnection()
    {
        return httpConnection;
    }

    
    public URL getServerURL()
    {
        return serverURL;
    }

    
    public void setHttpConnection(java.net.HttpURLConnection connection)
    {
        httpConnection = connection;
    }

    
    public void setServerURL(URL url)
    {
        serverURL = url;
    }

    
    public StringBuffer getContent()
    {
        return content;
    }

    
    public Timestamp getEnd()
    {
        return end;
    }

    
    public Timestamp getStart()
    {
        return ;
    }

    
    public void setContent(StringBuffer buffer)
    {
        content = buffer;
    }

    
    public void setEnd(Timestamp timestamp)
    {
        end = timestamp;
    }

    
    public void setStart(Timestamp timestamp)
    {
       start  = timestamp;
    }

    
    public  getMillis()
    {
        return getEnd().getTime() - getStart().getTime();
    }
    
    public int getCode()
    {
        return code;
    }

    
    public void setCode(int i)
    {
       code  = i;
    }

}
