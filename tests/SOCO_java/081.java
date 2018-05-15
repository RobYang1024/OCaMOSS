

public class Base64 {

	final static String baseTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	
	public static String encode(byte[] bytes) {

		String tmp = "";
		int i = 0;
		byte pos; 

		for(i=0; i < (bytes.length - bytes.length%3); i+=3) {

			pos = (byte) ((bytes[i] >> 2) & 63); 
			tmp = tmp + baseTable.charAt(pos); 

			pos = (byte) (((bytes[i] & 3) << 4) + ((bytes[i+1] >> 4) & 15)); 
			tmp = tmp + baseTable.charAt( pos );
					
			pos = (byte) (((bytes[i+1] & 15) << 2) + ((bytes[i+2]  >> 6) & 3));
			tmp = tmp + baseTable.charAt(pos);
		
			pos = (byte) (((bytes[i+2]) & 63));
			tmp = tmp + baseTable.charAt(pos);
		
			
			
			if(((i+2)%56) == 0) {
				tmp = tmp + "\r\n";
			}
		}

		if(bytes.length % 3 != 0) {

			if(bytes.length % 3 == 2) {

				pos = (byte) ((bytes[i] >> 2) & 63); 
				tmp = tmp + baseTable.charAt(pos); 

				pos = (byte) (((bytes[i] & 3) << 4) + ((bytes[i+1] >> 4) & 15)); 
				tmp = tmp + baseTable.charAt( pos );
						
				pos = (byte) ((bytes[i+1] & 15) << 2);
				tmp = tmp + baseTable.charAt(pos);
			
				tmp = tmp + "=";

			} else if(bytes.length % 3 == 1) {
				
				pos = (byte) ((bytes[i] >> 2) & 63); 
				tmp = tmp + baseTable.charAt(pos); 

				pos = (byte) ((bytes[i] & 3) << 4); 
				tmp = tmp + baseTable.charAt( pos );
						
				tmp = tmp + "==";
			}
		}
		return tmp;

	}

	
	public static String encode(String src) {
		
		return encode(src.getBytes());	
	}

	public static byte[] decode(String src) throws Exception {

		byte[] bytes = null;

		StringBuffer buf = new StringBuffer(src);

		
		int i = 0;
		char c = ' ';
		char oc = ' ';
		while( i < buf.length()) {			
			oc = c; 
			c = buf.charAt(i);
			if( oc == '\r' && c == '\n') {
				buf.deleteCharAt(i);
				buf.deleteCharAt(i-1);
				i -= 2;
			} else if( c == '\t') {
				buf.deleteCharAt(i);
				i --;
			} else if( c == ' ') {
				i --;
			}
			i++;
		}

		
		if(buf.length() % 4 != 0) {
			throw new Exception("Base64 decoding invalid length");
		}

		
		bytes = new byte[3 * (buf.length() / 4)];
		
		
		int index = 0;
		
		
		for(i = 0; i < buf.length(); i+=4) {

			byte data = 0;
			int nGroup = 0;

			for(int j = 0; j < 4; j++) {

				char theChar = buf.charAt(i + j); 

				if(theChar == '=') {
					data = 0;
				} else {
					data = getBaseTableIndex(theChar); 
				}

				if(data == -1) {
					throw new Exception("Base64 decoding bad character");
				}

				nGroup = 64*nGroup + data;
			}

			bytes[index] = (byte) (255 & (nGroup >> 16));
			index ++;

			bytes[index] = (byte) (255 & (nGroup >> 8));
			index ++;

			bytes[index] = (byte) (255 & (nGroup));
			index ++;
		}
		
		byte[] newBytes = new byte[index];
		for(i = 0; i < index; i++) {
			newBytes[i] = bytes[i];
		}

		return newBytes;
	}

	
	protected static byte getBaseTableIndex(char c) {
		
		byte index = -1;

		for(byte i = 0; i < baseTable.length(); i ++) {
		
			if(baseTable.charAt(i) == c) {
				index = i;
				break;
			}
		}

		return index;
	}
}