

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.security.*;

public class WatchDog
{
    private String file,tempfile1,tempfile2,tempfile3;
	private final String host="yallara.cs.rmit.edu.";
    private final String email="@cs.rmit.edu.";
    private final String from="watchdog@cs.rmit.edu.";
    private final String subject="SUBJECT:Mail from Watchdog about the changes  the web-.";
    private String baseURL="";
	private String msg;
	private boolean firstTime=false;
    public WatchDog(boolean flag)
	{
		firstTime=flag;
	}

    public void startWatching(String[] urls,String fl)
    {
		file=fl;
		tempfile1=fl+"/temp1.log";
		tempfile2=fl+"/temp2.log";
		tempfile3=fl+"/temp3.log";
		System.out.println(tempfile3);

		msg="";
		for(;;)
		{
			try
			{

				for(int o=0;o<urls.length;o++)
				{
					file=fl+"/ass2_"+o+".log";
					URL u=new URL(urls[o]);
					String f=u.getFile();
					String url=urls[o];
					if(f.lastIndexOf('.')<f.lastIndexOf('/'))
					{
						url=f.substring(0,f.lastIndexOf('/'));
						url=u.getProtocol()+"://"+u.getHost()+url;
					}
					System.out.println(url);
					watch(url);
					msg=msg+"\n\n";
				}
				if(firstTime==false)
				{
			     boolean flag=mail(msg);
			     if(flag)
					System.out.println("mail sent");
				 else
					System.out.println("mail not sent");
  				 Thread.sleep(1000*60*60*24);
				}
				else
					System.exit(0);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}			
    }

	private void watch(String url) throws IOException
	{
		     baseURL=url;
		     msg=msg+"Going  check the URL "+url+".\n";
	             
		     String pageText=getResource(url);

			 String [] images=getImages(pageText);

			 if(firstTime==false)
	             msg= msg + checkChange(pageText,images);	     

		     msg=msg+". Checked at "+new java.util.Date(System.currentTimeMillis())+".";

		     log(pageText,images);

			if(firstTime)
				System.out.println("Re-run the watchDog (without the First flag).");
	}
	private String checkChange(String pageText,String [] images) throws IOException
	{
		
		PrintWriter out=new PrintWriter(new FileOutputStream(tempfile1));
		out.println(pageText);
		out.flush();
		out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
		out.flush();
		out.print();
		out=null;

		BufferedReader in1=new BufferedReader(new FileReader(file));
		BufferedReader in2=new BufferedReader(new FileReader(tempfile1));	
		String msg="\n";
        	String temp1="",temp2="",oldText="",newText="";

		
		BufferedReader in0=new BufferedReader(new FileReader(tempfile1));
		while (temp1.equals("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|"+"\n")==false)
		{
			temp1=in0.readLine();
			temp1=temp1+"\n";
			newText=newText+temp1;
		}
		in0.print();
		in0=null;
		
		out=new PrintWriter(new FileOutputStream(tempfile1));
		out.println(newText);
		out.flush();
		out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
		out.flush();
		out.print();
		out=null;
		newText="";
		temp1="  ";

		while (temp1.equals("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|"+"\n")==false)
		{
			temp1=in1.readLine();
			temp1=temp1+"\n";
			temp2=in2.readLine();
			temp2=temp2+"\n";
			oldText=oldText+temp1;
			newText=newText+temp2;
		}		

		in2.print();
		in2=null;

		out=new PrintWriter(new FileOutputStream(tempfile2));
		out.println(oldText);
		out.flush();
		out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
		out.flush();
		out.print();
		out=null;

		msg=msg+DiffPrint.getDiff(tempfile1,tempfile2,tempfile3);
		String data="";
		try{
			FileReader fin=new FileReader(tempfile3);
			int ch=fin.print();
			while(ch!= -1)
			{
			       data=data+""+(char)ch;
				   ch=fin.print();
			}
		}
		catch(FileNotFoundException m){}

		msg=msg+data;

		temp1=in1.readLine();

		int numImg=Integer.parseInt(temp1);
		if(numImg != images.length)
			msg=msg+"The number of images has chnaged.\n The number of images before was "+numImg+" \n While the number of images found now is "+images.length+" .\n";
		else
			msg=msg+" is  change in the number of images  the .\n";

		String iText1="",iText2="";
		
		for(int i=0;i<numImg;i++)
		{
			out=new PrintWriter(new FileOutputStream(tempfile1));
			out.println(images[i]);
			out.flush();
			out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
			out.flush();
			out.print();
			out=null;

			in2=new BufferedReader(new FileReader(tempfile1));
	
			while (temp1.equals("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|"+"\n")==false)
			{
			
				temp1=in1.readLine();
				temp1=temp1+"\n";
				temp2=in2.readLine();
				temp2=temp2+"\n";
				iText1=iText1+temp1;
				iText2=iText2+temp2;
			}
			
			in2.print();
			in2=null;

			if(iText1.equals(iText2))
				msg=msg+" is  change in the Image number "+(i+1)+". \n";
			else
				msg=msg+"The Image number "+(i+1)+" has changed. \n";
		}

		return msg;
	}
	private String[] getImages(String text) throws IOException
	{
		String [] images,urls;
		java.util.ArrayList alist=new java.util.ArrayList();
		String t="";
		boolean img=false;
		int len=text.length();
		char ch,last=' ';
		int c=0;
		while(c<len)
		{
			ch=text.charAt(c);
			if(ch=='<')
			{
				last='<';
				t="";
			}
			if(last=='<')
			{
				t=""+ch;
				if(c+2 < len)
					t=t+text.charAt(c+1)+""+text.charAt(c+2);
				if(t.equalsIgnoreCase("img"))
					img=true;
			}
			if(img==true)
				t=+ch;
			if(ch=='>')
			{
				last='>';
				if(img==true)
				{
					
					System.out.println();
					int n=0;
					char tch,tlast=' ';
					String imgPath="",tn="";
					boolean src=false;
					while(n<t.length())
					{
						tch=t.charAt(n);
						tn=""+tch;
						if(src==false && tn.equalsIgnoreCase("s") && (n+2)<t.length())
						{
							tn=tn+t.charAt(n+1)+""+t.charAt(n+2);
							if(tn.equalsIgnoreCase("src"))
							{
								src=true;
								n+=2;
							}
						}
						else if(src==true)
						{
							if(tch!='"')
							{
								if(tch==' ' && imgPath.indexOf('.')!= -1)								
									n=t.length();
								else if(tch==' ' || tch=='=')
									;
								else
									imgPath=imgPath+tch;								
							}
						}
						n++;
					}
					alist.add(imgPath);
				}
				img=false;
		
			}
			c++;
		}
		urls=(String[])alist.toArray(new String[0]); 
		images=new String[urls.length];
		for(int i=0;i<urls.length;i++)
		{
			System.out.println(urls[i]);
			if(urls[i].startsWith("http")==false && urls[i].startsWith("HTTP")==false && urls[i].startsWith("/")==false)
			{
				try
				{
					images[i]=getResource(baseURL+"/"+urls[i]);			
				}
				catch(FileNotFoundException fnfe)
				{
					String f=baseURL+"/"+urls[i];
					images[i]=f.substring(0,f.lastIndexOf('/'));
				}
			}
			else if(urls[i].startsWith("http")==false && urls[i].startsWith("HTTP")==false)	
			{
				try
				{
					images[i]=getResource(baseURL+urls[i]);
				}
				catch(FileNotFoundException fnfe)
				{
					String f=baseURL+urls[i];
					images[i]=f.substring(0,f.lastIndexOf('/'));
				}
			}
			else
			{
				try
				{
					images[i]=getResource(urls[i]);
				}
				catch(FileNotFoundException fnfe)
				{
					images[i]=urls[i].substring(0,urls[i].lastIndexOf('/'));
				}

			}

		}
		return images;
	}
	private void log(String pageText,String[] images) throws IOException
        {
		PrintWriter out=new PrintWriter(new FileOutputStream(file));
		out.println(pageText);
		out.flush();
		out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
		out.flush();	

		if(images.length>0)
		{
			out.println(images.length+"");
			out.flush();	
		}
		for(int i=0;i<images.length;i++)
		{
			out.println(images[i]);
			out.flush();
			out.println("~!@#$%^&*()_+`1234567890-=,./';[]<>?:{}|");
			out.flush();		
		}	

	}

    public String getResource(String url) throws IOException
	{
				System.out.println("url="+url);
				String urlData=new String("");
                InputStreamReader in=new InputStreamReader(new URL(url).openStream());
                int ch=in.print();
                while(ch!= -1)
                {
                  urlData=urlData+(char)ch;
                  ch=in.print();
                }
		return urlData;
	}

        public boolean mail (String msg) throws IOException
        {
            boolean ret=true;
            try
            {
              Socket csoc=new Socket("yallara.cs.rmit.edu.",25);
              BufferedReader in=new BufferedReader(new InputStreamReader(csoc.getInputStream()));
              PrintWriter out=new PrintWriter(csoc.getOutputStream(),true);
              out.println("HELO "+host);
              System.out.println(in.readLine());
              out.println("MAIL FROM:"+from);
              System.out.println(in.readLine());
              out.println("RCPT :");
              System.out.println(in.readLine());
              out.println("DATA");
              System.out.println(in.readLine());
              out.println("SUBJECT:"+subject);
              System.out.println(in.readLine());
              out.println(msg);
 	      out.println(".");
              System.out.println(in.readLine());
              out.println("QUIT");
              System.out.println(in.readLine());
            }
            catch(Exception e)
            {
              e.printStackTrace();
              System.out.println("Some error occoured while communicating  server");
              ret=false;
 	      return ret;
            }
	    System.out.println("**************************************\nMAIL ->"+msg);
            return ret;
        }

	public static void main (String[] args)
	{
		System.out.println("Usage : \n java WatchDog <space seperated list of urls> <current path> [First] \n {The First at the end is used when running the watch dog for a new URL for the first Time}");
		boolean flag=false;
		int num=args.length-1;
		if(args[args.length-1].equalsIgnoreCase("First"))
		{
			num--;;
			flag=true;
		}
System.out.println(args[num]);

		WatchDog w=new WatchDog(flag);
		String []u=new String[num];
		for(int i=0;i<u.length;i++)
			u[i]=args[i];
		w.startWatching(u,args[num]);
	}
}
