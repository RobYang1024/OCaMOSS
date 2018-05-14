package java.httputils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;


public class WatchDog
{
    protected final int MILLIS_IN_HOUR = (60 * 60 * 1000);
    protected int interval = 24;
    protected String URL = "http://www.cs.rmit.edu./students/";
    protected String fileName = "WatchDogContent.html";
    protected String command = "./alert_mail.sh";
    protected String savedContent;
    protected String retrievedContent;

    
    public WatchDog()
    {
        super();
    }

    
    public void run() throws Exception
    {
        HttpRequestClient client = null;
        
        
        System.out.println(getClass().getName() +
                        "Retrieving baseline copy of: " + getURL());
        client = new HttpRequestClient(getURL());
        retrievedContent = client.getContent().toString();

        System.out.println(getClass().getName() +
                        "Writing baseline content : " + getFileName());
        writeFile();

        while (true)
        {
            
            System.out.println(getClass().getName() +
                            " Sleeping for hours: " + getInterval());
            Thread.currentThread().sleep(MILLIS_IN_HOUR * getInterval());

            
            System.out.println(getClass().getName() +
                            " Retrieving: " + getURL());

            client = new HttpRequestClient(getURL());
            retrievedContent = client.getContent().toString();

            
            System.out.println(getClass().getName() +
                            "  saved copy: " + getURL());
            savedContent = readFile();

            
            System.out.println(getClass().getName() +
                            " Comparing saved and retrieved. ");
            if (!savedContent.equals(retrievedContent))
            {
                
                System.out.println(getClass().getName() +
                                " Difference found. ");

                writeTempFile();
                runCommand();
            }

            
            writeFile();
        }
    }


    
    public String runCommand()
    {
        String cmd = getCommand() + " \"" + getURL() + "\"";
        try
        {
            Runtime r = Runtime.getRuntime();
            System.out.println(getClass().getName() +
            " Executing: " + cmd);

            Process proc = r.exec(cmd);
        }
        catch (Exception e)
        {
            try
            {
                Runtime r = Runtime.getRuntime();
                Process proc = r.exec(cmd);
            }
            catch (Exception ex)
            {
                System.out.println(getClass().getName()
                    + " Could not run :"
                    + getCommand()
                    + " because : "
                    + ex.getMessage());
            }
        }

        return "Executed successfully";
    }

    
    protected String readFile() throws FileNotFoundException
    {
        BufferedInputStream input = null;
        FileInputStream file = null;
        StringBuffer content = new StringBuffer();
        try
        {
            file = new FileInputStream(getFileName());
            
            input = new BufferedInputStream(file);
            
        }
        catch (FileNotFoundException x)
        {
            System.err.println("File not found: " + getFileName());
            throw x;
        }

        try
        {
            int ch;
            while ((ch = input.get()) != -1)
            {
                content.append((char)ch);
            }
        }
        catch (IOException x)
        {
            x.printStackTrace();
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.get();
                    file.get();
                }
                catch (IOException e)
                {
                }
            }
        }
        return content.toString();
    }

    
    protected void writeFile() throws Exception
    {
        OutputStream os = null;
        try
        {
            os = new BufferedOutputStream(
                     new FileOutputStream(getFileName(), false));
            os.write(getRetrievedContent().getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    
    protected void writeTempFile() throws Exception
    {
        OutputStream os = null;
        try
        {
            os = new BufferedOutputStream(
                     new FileOutputStream(".html", false));
            os.write(getRetrievedContent().getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    public static void main(String[] args)
    {
        WatchDog watchDog = new WatchDog();

        if (args.length < 3)
        {
            watchDog.printUsage();
        }

        
        System.out.println(watchDog.getClass().getName() +
                        ": Initialising with " +
                        args[0] + " \n" +
                        args[1] + " \n" +
                        args[2] + " \n");
        watchDog.setURL(args[0]);
        watchDog.setInterval(Integer.parseInt(args[1]));
        watchDog.setCommand(args[2]);

        
        try
        {
            System.out.println(watchDog.getClass().getName() + ": Invoking the run method.");
            watchDog.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String printUsage()
    {
        StringBuffer s = new StringBuffer();

        s.append("** WatchDog proper usage **\n\n");
        s.append(
            this.getClass().getName() +
            " <URL> <interval> <Command  execute>\n\n");

        return s.toString();
    }

    
    public String getCommand()
    {
        return command;
    }

    
    public String getFileName()
    {
        return fileName;
    }

    
    public int getInterval()
    {
        return interval;
    }

    
    public String getURL()
    {
        return URL;
    }

    
    public void setCommand(String string)
    {
        command = string;
    }

    
    public void setFileName(String string)
    {
        fileName = string;
    }

    
    public void setInterval(int i)
    {
        interval = i;
    }

    
    public void setURL(String string)
    {
        URL = string;
    }

    
    public String getRetrievedContent()
    {
        return retrievedContent;
    }

    
    public String getSavedContent()
    {
        return savedContent;
    }

    
    public void setRetrievedContent(String string)
    {
        retrievedContent = string;
    }

    
    public void setSavedContent(String string)
    {
        savedContent = string;
    }

}
