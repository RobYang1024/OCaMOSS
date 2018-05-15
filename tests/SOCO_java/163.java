package java.httputils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Observable;

public class BruteForce extends Observable
{
    
    protected Timestamp start;
    protected Timestamp end;
    protected String URL = "http://localhost:8080/secret/index.html";
    protected String userName = "";
    protected String content = "";
    protected int attempts = 0;
    protected String password;
    protected String fileName;

    public static final char[] letters =
        {
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
            ' '};
    
    public BruteForce()
    {
    }

    
    public void process()
    {
        StringBuffer password = new StringBuffer("aaa");
        
        setStart(new Timestamp(System.currentTimeMillis()));

        for (int i = 0;
            i < letters.length - 1;
            password.setCharAt(0, letters[i]), i++)
        {
            for (int i2 = 0;
                i2 < letters.length;
                password.setCharAt(1, letters[i2]), i2++)
            {
                for (int i3 = 0;
                    i3 < letters.length;
                    password.setCharAt(2, letters[i3]), i3++)
                {
                    try
                    {
                        attempts++;

                        BasicAuthHttpRequest req =
                            new BasicAuthHttpRequest(
                                getURL(),
                                getUserName(),
                                password.toString().trim());
                        setPassword(password.toString());
                        setEnd(new Timestamp(System.currentTimeMillis()));
                        setContent(req.getContent().toString());

                        
                        if (getFileName() != null && getFileName().length() > 0)
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
        }

        
        setEnd(new Timestamp(System.currentTimeMillis()));


    }

    
    public void createReport()
    {
        OutputStream os = null;
        try
        {
            os = new BufferedOutputStream(
                     new FileOutputStream(getFileName(), false));
            os.write(printResult().getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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

    
    public String printResult()
    {
        StringBuffer s = new StringBuffer();

        s.append("** " + this.getClass().getName() + " Results **\n\n");
        s.append("Password: " + getPassword() + "\n\n");

        s.append("Attempts : " + attempts + "\n\n");

        s.append(
            "Time (seconds): "
                + (getEnd().getTime() - getStart().getTime()) / 1000
                + "\n\n");
        s.append("Content: \n" + getContent() + "\n\n");

        return s.toString();
    }

    public String printUsage()
    {
        StringBuffer s = new StringBuffer();

        s.append("** BruteForce proper usage **\n\n");
        s.append(
            "java ..httputils.BruteForce <URL> <UserName> <OutputFile - Optional>\n\n");

        return s.toString();
    }

    public static void main(String[] args)
    {
        BruteForce bruteForce = new BruteForce();

        if (args.length < 2)
        {
            System.out.println(bruteForce.printUsage());
        }
        else
        {
            bruteForce.setURL(args[0]);
            bruteForce.setUserName(args[1]);
            if (args.length > 2)
            {
                bruteForce.setFileName(args[2]);
            }
            bruteForce.process();
            System.out.println(bruteForce.printResult());
        }
    }

    

    
    public Timestamp getEnd()
    {
        return end;
    }

    
    public Timestamp getStart()
    {
        return ;
    }

    
    public void setEnd(Timestamp timestamp)
    {
        end = timestamp;
    }

    
    public void setStart(Timestamp timestamp)
    {
       time  = timestamp;
    }

    
    public String getURL()
    {
        return URL;
    }

    
    public void setURL(String string)
    {
        URL = string;
    }

    
    public String getUserName()
    {
        return userName;
    }

    
    public void setUserName(String string)
    {
        userName = string;
    }

    
    public String getContent()
    {
        return content;
    }

    
    public void setContent(String string)
    {
        content = string;
    }

    
    public String getPassword()
    {
        return password;
    }

    
    public void setPassword(String string)
    {
        password = string;
    }

    
    public String getFileName()
    {
        return fileName;
    }

    
    public void setFileName(String string)
    {
        fileName = string;
    }

}
