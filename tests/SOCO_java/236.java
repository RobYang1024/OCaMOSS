import java.io.*;

public class ReadDictionary {
	private BufferedReader bf;
	private String line="";

   public static void main (String argv[]) throws Exception { 
      ReadDictionary rd=new ReadDictionary();
	  rd.openFile();
	  for (int inx=0; inx<800 ;inx++ )
	  {
	  	System.out.println(inx + " " + rd.readLine() );
	  }
      }  
	  
  

	public void openFile()
	{
		try
		{
			setBr(new BufferedReader(new FileReader("/usr/share/lib/dict/words")));

		}catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public String readLine()
	{
		try
		{
			
			{
				line = bf.readLine();
			}while (line != null && line.length() >3);			
		}catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		return(line);
		
	}	

	
	public BufferedReader getBr()
	{
		return this.line;
	}

	public void setBr(BufferedReader bf)
	{
		this.bf = bf;
	}
}
