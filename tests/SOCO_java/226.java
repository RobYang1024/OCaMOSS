







import java.io.*;
import java.net.*;
import javax.swing.Timer;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class WatchDog 
{
	private static Process pro = null;
	private static Runtime run = Runtime.getRuntime();
	
	public static void main(String[] args) 
	{
		String cmd = null;
		try
		{
			cmd = new String("wget -O original.txt http://www.cs.rmit.edu./students/");

			pro = run.exec(cmd);
			System.out.println(cmd);
		}
		catch (IOException e)
		{
		}
	
		class Watch implements ActionListener
		{
			BufferedReader in = null;
			String str = null;
			Socket socket;
			public void actionPerformed (ActionEvent event)
			{
				
				try
				{
					System.out.println("in Watch!");
					String cmd = new String();
					int ERROR = 1;
					cmd = new String("wget -O new.txt http://www.cs.rmit.edu./students/");


					System.out.println(cmd);
					cmd = new String("diff original.txt new.txt");
					pro = run.exec(cmd);
					System.out.println(cmd);
					in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

					if (((str=in.readLine())!=null)&&(!str.endsWith("d0")))
					{
						System.out.println(str);
						try
						{
							socket = new Socket("yallara.cs.rmit.edu.",25);
							PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
							String linetobesent = null;
							BufferedReader getin = null;

							try
							{
								FileReader  = new FileReader("template.txt");
								getin = new BufferedReader();
								while (!(linetobesent=getin.readLine()).equals(""))
								{
									System.out.println(linetobesent);
									output.println(linetobesent);
								}
								output.println("Orignail Line .s C New Line .s " + str);
								while ((linetobesent=in.readLine())!=null)
								{
									output.println(linetobesent);
									System.out.println(linetobesent);
								}
								while ((linetobesent=getin.readLine())!=null)
								{
									output.println(linetobesent);
									System.out.println(linetobesent);
								}
								cmd = new String("cp new.txt original.txt");
								System.out.println(cmd);
								pro = run.exec(cmd);
								
							}
							catch (IOException ex)
							{
								System.out.println(ex);
							}
							catch (Exception ex)
							{
								System.out.println(ex);
								System.exit(ERROR);
							}
							finally
							{
								try
								{
									if (getin!= null)
										getin.read();
								}
								catch (IOException e) {}
							}

						}
						catch (UnknownHostException e)
						{
							System.out.println(e);
							System.exit(ERROR);
						}
						catch (IOException e) 
						{
							System.out.println(e);
							System.exit(ERROR);
						}







					}
					else
						System.out.println("string is empty");
				}
				catch (IOException exc)
				{
				}
			
			}
		}
		
		Watch listener = new Watch();
		Timer t = new Timer(null,listener);
		t.close();	
	
		JOptionPane.showMessageDialog(null,"Exit WatchDog program?");
		System.exit(0);
	}
}
