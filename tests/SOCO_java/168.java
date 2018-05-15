
package java.httputils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;


public class BruteForceThreadPool extends ThreadGroup implements Observer
{
    protected String URL = "http://localhost:8080/secret/index.html";
    protected int poolSize = 6;

    protected Collection threadList = new ArrayList();
    protected String fileName = "BruteForceReport.txt";
    protected boolean finished = false;
    protected String userName = "";
    
    public BruteForceThreadPool(String name)
    {
        super(name);
    }

    
    public BruteForceThreadPool(ThreadGroup parent, String name)
    {
        super(parent, name);
    }

    
    public synchronized void update(Observable o, Object arg)
    {
        
        System.out.println("Update method called  the observer.");
        RunnableBruteForce rbf = (RunnableBruteForce) o;
        rbf.createReport();

        
        
        for (Iterator iter = threadList.iterator(); iter.hasNext();)
        {
            RunnableBruteForce target = (RunnableBruteForce) iter.next();
            target.setStop(true);
        }
        finished = true;
    }


    
    protected void start(int threads)
    {
        
        
        int load = BruteForce.letters.length / threads;
        int remainder = BruteForce.letters.length % threads;

        
        for (int i = 0, end = ( + load);
            end < BruteForce.letters.length;
            i = end, end += load)
        {
            RunnableBruteForce runnable = new RunnableBruteForce();
            runnable.setURL(getURL());
            runnable.setRangeStart();
            runnable.setUserName(userName);
            
            runnable.setRangeEnd(
                end + load > BruteForce.letters.length ?
                BruteForce.letters.length :
                end);

            runnable.addObserver(this);
            runnable.setFileName(getFileName());
            
            threadList.add(runnable);
        }

        
        for (Iterator iter = threadList.iterator(); iter.hasNext();)
        {
            RunnableBruteForce target = (RunnableBruteForce) iter.next();
            new Thread(target).start();
        }

    }

    public static void main(String[] args)
    {
        BruteForceThreadPool pool = new BruteForceThreadPool("BruteForceThreadGroup");

        if (args.length < 4)
        {
            pool.printUsage();
            return;
        }
        pool.setURL(args[0]);
        pool.userName = args[1];
        pool.setFileName(args[2]);

        pool.get(Integer.parseInt(args[3]));
        while (true)
        {
            try
            {
                Thread.currentThread().sleep(100);
                if (pool.finished)
                {
                    break;
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }


    public String printUsage()
    {
        StringBuffer s = new StringBuffer();

        s.append("** BruteForceThreadPool proper usage **\n\n");
        s.append(
            "java ..httputils.BruteForceThreadPool <URL> <UserName> <OutputFile> < Of Threads = 6>\n\n");

        return s.toString();
    }

    
    public Collection getThreadList()
    {
        return threadList;
    }

    
    public void setThreadList(Collection collection)
    {
        threadList = collection;
    }


    
    public String getFileName()
    {
        return fileName;
    }

    
    public void setFileName(String string)
    {
        fileName = string;
    }

    
    public String getURL()
    {
        return URL;
    }

    
    public void setURL(String string)
    {
        URL = string;
    }

    
    public int getPoolSize()
    {
        return poolSize;
    }

    
    public void setPoolSize(int i)
    {
        poolSize = i;
    }

}
