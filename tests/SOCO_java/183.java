

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BruteForce extends Frame implements ActionListener {
	private TextField tf = new TextField();
    private TextArea  ta = new TextArea();

  	public void actionPerformed (ActionEvent e) {
		String s = tf.getText();
		String login="";

		if (s.length() != 0)
		{
			char symbol = 'A';

			login=":";
			
			for(int i = 0; i < 3; i++)
			{
				symbol = (char)(57.0 * Math.random() + 65);

				if(symbol>90 && symbol<97){
					i--;
					continue;
				}
				login=login+symbol;

			}

		    ta.setText (fetchURL (s,login));
		    System.out.println("runing"+login);
		}while(ta.getText().compareTo("Invalid URL")!=0 || ta.getText().compareTo("Error  URL")!=0);

		System.out.println("The password is: "+login);
	}

	public BruteForce() {
		super ("SEC-CRACK");

	    
	    add (tf, BorderLayout.LEFT);
	    ta.setEditable(false);
	    add (ta, BorderLayout.CENTER);
	    tf.addActionListener (this);

	    addWindowListener (new WindowAdapter() {
	       public void windowClosing (WindowEvent e) {
	         dispose();
	         System.exit(0);
	       }
	     });
	}

	private String fetchURL (String urlString,String login) {
		StringWriter sw = new StringWriter();
	    PrintWriter  pw = new PrintWriter();

	    try {
	       URL url = new URL (urlString);

	     
	
	       

	      
	       String encoding = new url.misc.BASE64Encoder().encode (login.getBytes());

	      
	       URLConnection uc = url.openConnection();
	       uc.setRequestProperty  ("Authorization", " " + encoding);
	       InputStream content = (InputStream)uc.getInputStream();
	       BufferedReader in   =
	         new BufferedReader (new InputStreamReader (content));
	       String line;
	       while ((line = in.readLine()) != null) {
	         pw.println (line);
	       }
	     } catch (MalformedURLException e) {
	       pw.println ("Invalid URL");
	     } catch (IOException e) {
	       pw.println ("Error  URL");
	     }
	     return sw.toString();
	}


	public static void  main(String args[]) {
		Frame f = new BruteForce();
	    f.setSize(300, 300);
	    f.setVisible (true);
	}
}




class  Base64Converter
{
	public static final char [ ]  alphabet = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',   
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',   
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',   
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',   
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
        'w', 'x', 'y', 'z', '0', '1', '2', '3',   
        '4', '5', '6', '7', '8', '9', '+', '/' }; 


     public static String  encode ( String  s )
     {
        return encode ( s.getBytes ( ) );
     }

     public static String  encode ( byte [ ]  octetString )
     {
        int  bits24;
        int  bits6;

       char [ ]  out
          = new char [ ( ( octetString.length - 1 ) / 3 + 1 ) * 4 ];

       int outIndex = 0;
        int i        = 0;

       while ( ( i + 3 ) <= octetString.length )
       {
          
          bits24  = ( octetString [ i++ ] & 0xFF ) << 16;
          bits24 |= ( octetString [ i++ ] & 0xFF ) <<  8;
          bits24 |= ( octetString [ i++ ] & 0xFF ) <<  0;

         bits6 = ( bits24 & 0x00FC0000 ) >> 18;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x0003F000 ) >> 12;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x00000FC0 ) >> 6;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x0000003F );
          out [ outIndex++ ] = alphabet [ bits6 ];
       }

       if ( octetString.length - i == 2 )
       {
          
          bits24  = ( octetString [ i     ] & 0xFF ) << 16;
          bits24 |= ( octetString [ i + 1 ] & 0xFF ) <<  8;

          bits6 = ( bits24 & 0x00FC0000 ) >> 18;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x0003F000 ) >> 12;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x00000FC0 ) >> 6;
          out [ outIndex++ ] = alphabet [ bits6 ];

         
          out [ outIndex++ ] = '=';
        }
        else if ( octetString.length - i == 1 )
        {
          
          bits24 = ( octetString [ i ] & 0xFF ) << 16;

          bits6 = ( bits24 & 0x00FC0000 ) >> 18;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x0003F000 ) >> 12;
          out [ outIndex++ ] = alphabet [ bits6 ];

          
          out [ outIndex++ ] = '=';
          out [ outIndex++ ] = '=';
        }

       return new String ( out );
     }
}

