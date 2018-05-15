
import java.util.*;
import java.net.*;
import java.io.*;
public class ScheduleTask extends Thread
{

    private int flag=0,count1=0,count2=0;
    private Vector change;
    public ScheduleTask(Vector init)
     {
       try
       {

          Runtime run = Runtime.getRuntime();
          String command_line = "lynx http://yallara.cs.rmit.edu./~/index.html -dump";
          Process result = run.exec(command_line);
          BufferedReader in = new BufferedReader(new InputStreamReader(result.getInputStream()));
          String inputLine;
          Vector newVector = new Vector();
          change = new Vector();
          while ((inputLine = in.readLine()) != null)
          {
             newVector.addElement(inputLine);
          }
          if(init.size()>newVector.size())
             {
                for(int k=0;k<newVector.size();k++)
                   {
                     if(!newVector.elementAt(k).toString().equals(init.elementAt(k).toString()))
                        change.addElement((Object)newVector.elementAt(k));
                     count1=k;
                   }
                for(int j=count1+1;j<init.size();j++)
                {
                  if(init.elementAt(j)!=null)
                     change.addElement((Object)init.elementAt(j));
                  else
                     break;
                }
             }
           else if(init.size()<=newVector.size())
           {
                for(int l=0;l<init.size();l++)
                   {
                     if(!init.elementAt(l).toString().equals(newVector.elementAt(l).toString()))
                        change.addElement((Object)newVector.elementAt(l));
                     count2=l;
                   }
                for(int j=count2+1;j<newVector.size();j++)
                {
                  if(newVector.elementAt(j)!=null)
                     change.addElement((Object)newVector.elementAt(j));
                  else
                     break;
                }
           }
          
        if(!newVector.equals(init))
        {
        Email aEmail = new Email(change);
        aEmail.send();
        sleep(1000*60*24);
        flag=1;
        }
       }
       catch(Exception e)
       {
          System.out.println(e);
       }
    }
           public int getFlag()
       {
         return(flag);
       }
}