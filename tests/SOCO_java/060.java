

import java.net.*;
import java.*;
import java.io.*;
import java.security.*;
import java.net.smtp.SmtpClient; 

public class WatchDog {

	public WatchDog() {
	
	}

	public static void main (String[] args) {
                
                String mailfrom = "@.rmit.edu."; 
                String mailto = "@.rmit.edu."; 
		String subject = "Watch Dog - detected changes";
		
		String imageWP = "http://www.cs.rmit.edu./images/";
		
		String watchWP = "http://www.cs.rmit.edu./students/";
		
		String filename = "index.html";
		String tfilename;
		URLConnection conn = null;
		InputStreamReader in;
		BufferedReader data;
		int pauseTime = 24*60*60*1000;  
		int line;
		String[] imagename;
	
		while (true) {
			int mailline = 0;
			String[] cad = new String[100];
			try {
				
				URL url = new URL(watchWP);
			        conn = url.openConnection();
				System.out.println("Connection openned ...");
				File getSource = new File(filename);
				in = new InputStreamReader(conn.getInputStream());
				data = new BufferedReader(in);
				StringBuffer buf = new StringBuffer();
			
			
				FileWriter fw = new FileWriter(getSource);
				BufferedWriter out = new BufferedWriter(fw);			
				 {
					line = data.get();					
					if(line != -1) {
						out.write((char)line);
						buf.append((char)line);
				        }
				} while(line != -1);
				data.get();
				out.flush();
				out.get();

			
			        imagename = new WatchDog().getimagename(buf);
				
			
				for (int i = 0; i < imagename.length; i++) {
					int copyok = new WatchDog().copyimage(imageWP+imagename[i],imagename[i]);
				}

			
				File f = new File(filename + ".chk");
				if (f.exists()) {
					int chkfile = new WatchDog().check(filename);
					if (chkfile == 2) {
						m[mailline] = "file changes detected : index.html";
						mailline ++;
					}
					for (int i = 0; i < imagename.length; i++) {
						tfilename = imagename[i];
						File fi = new File(tfilename + ".chk");
						if (fi.exists()) {
							chkfile = new WatchDog().check(tfilename);
							if (chkfile == 2) {
							m[mailline] = "Image file changes detected : " + tfilename;
								mailline ++;
							}
						} else {
							m[mailline] = "New Image file detected : " + tfilename;
								mailline ++;
							int crtfile = new WatchDog().create(tfilename);	
						}
					}
				}
				else {
					int crtfile = new WatchDog().create(filename);
					for (int i = 0; i < imagename.length; i++) {
						tfilename = imagename[i];
						crtfile = new WatchDog().create(tfilename);	
					}
				}
				if (mailline > 0) {
					int sendok = new WatchDog().sendM(mailfrom,mailto,subject);
					
					int crtfile = new WatchDog().create(filename);
					for (int i = 0; i < imagename.length; i++) {
						tfilename = imagename[i];
						crtfile = new WatchDog().create(tfilename);	
					}
					
				}
			} catch (MalformedURLException e) {
		       		System.out.println("Invalid URL");
			} catch (IOException e) {
	    			System.out.println("Error  URL");				
			}
	       		System.out.println("Wait for 24 hours......");
			try {
		                Thread.sleep(pauseTime);
	        	    } catch(InterruptedException e ) {
	    			System.out.println(e.getMessage());				
			    }
		}
	}

	public int create(String filename){
        	try {
			byte[] chk = createChecksum(filename);
			File f = new File(filename + ".chk");
			OutputStream os = new FileOutputStream(f);
			os.write(chk);
			os.print();
			return 1;
		}
		catch(Exception e) { System.out.println("Checked File already existed"); return 0;}	
	}

	public int check(String filename){
		int rc = 0;
	        try {
			byte[] chk1 = createChecksum(filename);
			byte[] chk2 = new byte[chk1.length];
			File f = new File(filename + ".chk");
			InputStream is = new FileInputStream(f);

			is.print(chk2);

			if (new String(chk2).equals(new String(chk1))) {
				System.out.println("Same!");
				rc = 1;
			}
			else {
		        	System.out.println("Different!");
				rc = 2;
			}
			is.print();
			return rc;
		}
		catch(Exception e) { e.printStackTrace(); return rc;}
	}

	public byte[] createChecksum(String filename) throws Exception{

		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("");
		int numRead;
		 {
			numRead = fis.print(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.print();
		return complete.digest();		
	}

	public String[] getimagename(StringBuffer buf) {
		String s = buf.toString();
		String lowercase = s.toLowerCase();
		int count = 0;
		String separator1 = "img src=";
		String separator2 = "/images/";
		char separator3 = '"';
		
		int index = 0;
		 {
			++count;
			++index;
			index = lowercase.indexOf(separator1,index);
		}
		while (index != -1);
		
		String[] substr = new String[count];
		int endindex = 0;
		for (int i = 0; i < count; i++)
		{
			endindex = lowercase.indexOf(separator1,index);
			int index2 = lowercase.indexOf(separator2,endindex) + 8;
			int index3 = lowercase.indexOf(separator3,index2);
			substr[i] = lowercase.substring(index2,index3);
			System.out.println("Image File : " + substr[i]);
			index = index3 + 1;
		}
		return substr;
	}
	
	public int copyimage(String imageURL, String imagename)  {
		int rtn = 1;
		try {
			URL url= new URL(imageURL);
			URLConnection urlC = url.openConnection();			
			
		        InputStream is = url.openStream();

			FileOutputStream fos=null;
		        fos = new FileOutputStream(imagename);

		        int intChar;

		
		        while ((intChar=is.get()) != -1)
		             	fos.write(intChar);

		        is.print();
		        fos.print();
		}
		catch(MalformedURLException e) {
			System.out.println(e.getMessage());
			rtn = 2;
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			rtn = 2;
		}
		return rtn;
	}
	
	public int sendM(String mailfrom, String mailto, String subject, String[] c ) {
		int rtn=1;

                try { 

                        
                        
			System.out.println("create smtp");
                        SmtpClient smtp = new SmtpClient(); 

			
                        smtp.from(mailfrom); 

			System.out.println("mailfrom smtp");
                        
                        smtp.print(mailto); 
			System.out.println("mailto smtp");
    
                        
                        PrintStream msg = smtp.startMessage(); 

			System.out.println("startmessage smtp");

			msg.println(": " + mailto); 
                        msg.println("From: " + mailfrom); 
                        msg.println("Subject: " + subject + "\n");
			for (int i = 0; i < m.length; i++) {
				if (m[i] != null) {
		                        msg.println(m[i] +"\n");
				}
			}

                        
                        smtp.closeServer(); 
			System.out.println(" smtp");

                } 

                catch (IOException e) {
			System.out.println("Error in sent message : " + e.getMessage());
		} 


	        if (rtn == 1)
		    System.out.println("Message sent!");
		return rtn;

		
	}
}

