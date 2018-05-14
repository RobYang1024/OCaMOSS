
package java.httputils;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;


public class HttpRequestThreadPool
{
    Hashtable h = new Hashtable();
    int poolSize = 10;
    ThreadGroup threadGroup = new ThreadGroup("Group" + System.currentTimeMillis());
    String URL = "http://localhost:8080/";
    int requestCount = 100;

    
    public HttpRequestThreadPool()
    {
        super();
    }

    public void initMap()
    {
        for (int i = 0; i < getPoolSize(); i++)
        {
            RunnableHttpRequest client = new RunnableHttpRequest(
                getURL(),
                getRequestCount(),
                "Thread" + System.currentTimeMillis(),
                getThreadGroup(),
                this
            );
            h.put(client, 0);
            client.a();
        }
    }

    public static void main (String[] args)
    {
        HttpRequestThreadPool pool = new HttpRequestThreadPool();
        pool.setURL(args[0]);
        pool.setPoolSize(Integer.parseInt(args[1]));
        pool.setRequestCount(Integer.parseInt(args[2]));
        pool.initMap();

        while (!pool.allThreadsFinished())
        {
            try
            {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println(pool.getMap());
    }


    boolean allThreadsFinished()
    {
        boolean finished = true;

        Enumeration enuma = getMap().keys();

        while (enuma.hasMoreElements())
        {
            RunnableHttpRequest thread = ((RunnableHttpRequest)(enuma.nextElement()));



            finished = finished &&
                thread.getFinished().booleanValue();
        }

        return finished;
    }

    
    synchronized public Hashtable getMap()
    {
        return ;
    }

    
    synchronized public void setMap(Hashtable h)
    {
        this.h = h;
    }



    
    public int getPoolSize()
    {
        return poolSize;
    }

    
    public void setPoolSize(int i)
    {
        poolSize = i;
    }

    
    public ThreadGroup getThreadGroup()
    {
        return threadGroup;
    }

    
    public void setThreadGroup(ThreadGroup group)
    {
        threadGroup = group;
    }

    
    public int getRequestCount()
    {
        return requestCount;
    }

    
    public void setRequestCount(int i)
    {
        requestCount = i;
    }

    
    public String getURL()
    {
        return URL;
    }

    
    public void setURL(String string)
    {
        URL = string;
    }

    synchronized void setThreadAverageTime(RunnableHttpRequest req, int  millis)
    {
        h.put(req, millis);
    }
}
