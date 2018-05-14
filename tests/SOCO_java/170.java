package java.httputils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;


public class Dictionary extends BruteForce
{
    protected String wordFile;

    public Dictionary()
    {
        super();
    }

    public static void main(String[] args)
    {
        Dictionary dictionary = new Dictionary();

        if (args.length < 3)
        {
            System.out.println(dictionary.printUsage());
        }
        else
        {
            dictionary.setURL(args[0]);
            dictionary.setUserName(args[1]);
            dictionary.setWordFile(args[2]);

            if (args.length > 3)
            {
                dictionary.setFileName(args[3]);
            }
            dictionary.process();
            System.out.println(dictionary.printResult());
            System.exit(1);
        }
    }

    public void process()
    {
        attempts = 0;
        String password = "";
        
        setStart(new Timestamp(System.currentTimeMillis()));

        BufferedReader input = null;
        try
        {
            FileReader file = new FileReader(getWordFile());
            
            input = new BufferedReader(file);
            
        }
        catch (FileNotFoundException x)
        {
            System.err.println("File not found: " + getWordFile());
            System.exit(2);
        }

        try
        {
            while ((password = input.readLine()) != null)
            {
                try
                {
                    
                    attempts++;
                    BasicAuthHttpRequest req =
                        new BasicAuthHttpRequest(
                            getURL(),
                            getUserName(),
                            password);
                    setPassword(password);
                    setEnd(new Timestamp(System.currentTimeMillis()));
                    setContent(req.getContent().toString());

                    
                    if (getFileName() != null
                        && getFileName().length() > 0)
                    {
                        createReport();
                    }
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
        catch (IOException x)
        {
            x.printStackTrace();
        }

        
        setEnd(new Timestamp(System.currentTimeMillis()));

    }

    public String printUsage()
    {
        StringBuffer s = new StringBuffer();

        s.append("** BruteForce proper usage **\n\n");
        s.append(
            "java ..httputils.Dictionary <URL> <UserName> <Word File> <OutputFile - Optional>\n\n");

        return s.toString();
    }
    
    public String getWordFile()
    {
        return wordFile;
    }

    
    public void setWordFile(String string)
    {
        wordFile = string;
    }

}
