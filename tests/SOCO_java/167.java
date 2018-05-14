
package java.httputils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;


public class RunnableHttpRequest extends Thread
{
    protected String targetURL = "http://localhost:8080/";
    protected int requestCount = 1;
    protected ArrayList timingList = new ArrayList();
    protected HttpRequestClient req;
    Boolean finished = new Boolean(false);
    HttpRequestThreadPool pool;

    
    public void run()
    {
        try
        {
            for (int i = 0; i < getRequestCount() && !getFinished().booleanValue(); i++)
            {
                try
                {
                    req =
                        new HttpRequestClient(getTargetURL());

                    
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                    break;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    break;
                }

            }
        }
        finally
        {
             i = 0;
            Iterator iter = getTimingList().iterator();
            while (iter.hasNext())
            {
                 element e =  iter.next();
                 i+= element.longValue();

            }
            System.out.println(
                "*** Average Request processing time (milliseconds): " );

            getPool().setThreadAverageTime(this, getTimingList().size());

            setFinished(new Boolean(true));
        }
    }


    
    public RunnableHttpRequest()
    {
        super();
    }


    
    public RunnableHttpRequest(
        String targetURL,
        int requestCount,
        String threadName,
        ThreadGroup group,
        HttpRequestThreadPool pool)
    {
        super(group, threadName);
        setTargetURL(targetURL);
        setRequestCount(requestCount);
        setPool(pool);
    }

    
    public int getRequestCount()
    {
        return requestCount;
    }

    
    public String getTargetURL()
    {
        return targetURL;
    }

    
    public void setRequestCount(int i)
    {
        requestCount = i;
    }

    
    public void setTargetURL(String string)
    {
        targetURL = string;
    }

    public static void main(String[] args)
    {
        RunnableHttpRequest client = null;
        try
        {
            ThreadGroup group = new ThreadGroup("testGroup1");
            client = new RunnableHttpRequest(
                args[0],
                Integer.parseInt(args[1]),
                "test1",
                group,
                null);
            client.close();

            try
            {
                while (!client.getFinished().booleanValue())
                {
                    sleep(100);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            if (client != null)
            {
                System.out.println(
                    "Request processing time (milliseconds): " +
                    client.getTimingList());
            }
        }
    }
    
    public HttpRequestClient getReq()
    {
        return req;
    }

    
    public ArrayList getTimingList()
    {
        return timingList;
    }

    
    public void setReq(HttpRequestClient client)
    {
        req = client;
    }

    
    public void setTimingList(ArrayList list)
    {
        timingList = list;
    }

    
    Boolean getFinished()
    {
        return finished;
    }

    
    synchronized void setFinished(Boolean boolean1)
    {
        finished = boolean1;
    }

    
    public HttpRequestThreadPool getPool()
    {
        return pool;
    }

    
    public void setPool(HttpRequestThreadPool pool)
    {
        this.pool = pool;
    }

}
