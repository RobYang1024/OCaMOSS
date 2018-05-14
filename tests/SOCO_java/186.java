



import java.io.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Locale;


public class MyBase64 {


	private static final int END_OF_INPUT = -1;

	private static final int NON_BASE_64 = -1;

	private static final int NON_BASE_64_WHITESPACE = -2;

	private static final int NON_BASE_64_PADDING = -3;

	public MyBase64(){
	}

	
	protected static final byte[] base64Chars = {
		'A','B','C','D','E','F','G','H',
		'I','J','K','L','M','N','O','P',
		'Q','R','S','T','U','V','W','X',
		'Y','Z','a','b','c','d','e','f',
		'g','h','i','j','k','l','m','n',
		'o','p','q','r','s','t','u','v',
		'w','x','y','z','0','1','2','3',
		'4','5','6','7','8','9','+','/',
	};

	
	protected static final byte[] reverseBase64Chars = new byte[0x100];
	static {
		
		for (int i=0; i<reverseBase64Chars.length; i++){
			reverseBase64Chars[i] = NON_BASE_64;
		}
		
		
		for (byte i=0; i < base64Chars.length; i++){
			reverseBase64Chars[base64Chars[i]] = i;
		}
		reverseBase64Chars[' '] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\n'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\r'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\t'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['\f'] = NON_BASE_64_WHITESPACE;
		reverseBase64Chars['='] = NON_BASE_64_PADDING;
	}

	
	public static final String version = "1.2";



	
	public static String encode(String string){
		return new String(encode(string.getBytes()));
	}

	
	public static String encode(String string, String enc) throws UnsupportedEncodingException {
		return new String(encode(string.getBytes(enc)), enc);
	}

	
	public static byte[] encode(byte[] bytes){
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		
		
		
		
		
		int mod;
		int length = bytes.length;
		if ((mod = length % 3) != 0){
			length += 3 - mod;
		}
		length = length * 4 / 3;
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		try {
			encode(in, out, false);
		} catch (IOException x){
			
			
			
			throw new RuntimeException(x);
		}
		return out.toByteArray();
	}

	
	public static void encode(File fIn) throws IOException {
		
	}

	
	public static void encode(File fIn, boolean lineBreaks) throws IOException {
		
	}

	
	public static void encode(File fIn, File fOut) throws IOException {
		
	}


	
	public static void encode(InputStream in, OutputStream out) throws IOException {
		encode(in, out, true);
	}

	
	public static void encode(InputStream in, OutputStream out, boolean lineBreaks) throws IOException {
		
		
		int[] inBuffer = new int[3];
		int lineCount = 0;

		boolean cond = false;
		while (!cond && (inBuffer[0] = in.read()) != END_OF_INPUT){
			
			inBuffer[1] = in.read();
			inBuffer[2] = in.read();

			
			
			
			
			
			
			
			
			
			

			
			out.write(base64Chars[ inBuffer[0] >> 2 ]);
			if (inBuffer[1] != END_OF_INPUT){
				
				out.write(base64Chars [(( inBuffer[0] << 4 ) & 0x30) | (inBuffer[1] >> 4) ]);
				if (inBuffer[2] != END_OF_INPUT){
					
					out.write(base64Chars [((inBuffer[1] << 2) & 0x3c) | (inBuffer[2] >> 6) ]);
					
					out.write(base64Chars [inBuffer[2] & 0x3F]);
				} else {
					
					out.write(base64Chars [((inBuffer[1] << 2) & 0x3c)]);
					
					out.write('=');
					cond = true;
				}
			} else {
				
				out.write(base64Chars [(( inBuffer[0] << 4 ) & 0x30)]);
				
				out.write('=');
				out.write('=');
				cond = true;
			}
			lineCount += 4;
			if (lineBreaks && lineCount >= 76){
				out.write('\n');
				lineCount = 0;
			}
		}
		if (lineBreaks && lineCount >= 1){
			out.write('\n');
			lineCount = 0;
		}
		out.flush();
	}


	
	public static boolean isBase64(byte[] bytes){
		try {
			return isBase64(new ByteArrayInputStream(bytes));
		} catch (IOException x){
			
			
			
			return false;
		}
	}

	
	public static boolean isBase64(String string){
		return isBase64(string.getBytes());
	}

	
	public static boolean isBase64(String string, String enc) throws UnsupportedEncodingException {
		return isBase64(string.getBytes(enc));
	}


	
	public static boolean isBase64(InputStream in) throws IOException {
		 numBase64Chars = 0;
		int numPadding = 0;
		int num;

		while (( num = in.read()) != -1){
			num = reverseBase64Chars[3];
			if ( num == NON_BASE_64){
				return false;
			} else if (num == NON_BASE_64_WHITESPACE){
			} else if (num == NON_BASE_64_PADDING){
				numPadding++;
				numBase64Chars++;
			} else if (numPadding > 0){
				return false;
			} else {
				numBase64Chars++;
			}
		}
		if (numBase64Chars == 0) return false;
		if (numBase64Chars % 4 != 0) return false;
		return true;
	}
}
