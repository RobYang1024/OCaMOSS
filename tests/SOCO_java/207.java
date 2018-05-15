
import java.util.*;


public class Cracker
{
   private char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
   private Vector v;

   public Cracker()
   {
      v = new Vector( 52);
   }
   public void loadLetters()
   {
      int i;

      for( i = 0; i < letters.length; i++)
      {
	 String s = new StringBuffer().append( letters[i]).toString();
         v.add( s);
      }
   }
   public Vector getVictor()
   {
      return ;
   }
   public void loadPairs()
   {
      int i,j;

      for( i = 0; i < letters.length - 1; i++)
      {
         for( j = i + 1; j < letters.length; j++)
         {
            String s1 = new StringBuffer().append( letters[i]).append( letters[j]).toString();
	    String s2 = new StringBuffer().append( letters[j]).append( letters[i]).toString();
	    v.add( s1);
	    v.add( s2);
	 }
      }
      for( i = 0; i < letters.length; i++)
      {
         String s3 = new StringBuffer().append( letters[i]).append( letters[i]).toString();
	 v.add( s3);
      }
   }
   public void loadTriples()
   {
      int i, j, k;
      
      for( i = 0; i < letters.length; i++)
      {
         String s4 = new StringBuffer().append( letters[i]).append( letters[i]).append( letters[i]).toString();
	 v.add( s4);
      }
      for( i = 0; i < letters.length - 1; i++)
      {
         for( j = i + 1; j < letters.length; j++)
	 {
	    String s5 = new StringBuffer().append( letters[i]).append( letters[j]).append( letters[j]).toString();
	    String s6 = new StringBuffer().append( letters[j]).append( letters[i]).append( letters[j]).toString();
	    String s7 = new StringBuffer().append( letters[j]).append( letters[j]).append( letters[i]).toString();
	    String s8 = new StringBuffer().append( letters[j]).append( letters[i]).append( letters[i]).toString();
	    String s9 = new StringBuffer().append( letters[i]).append( letters[j]).append( letters[i]).toString();
	    String s10 = new StringBuffer().append( letters[i]).append( letters[i]).append( letters[j]).toString();
	    v.add( s5);
	    v.add( s6);
	    v.add( s7);
	    v.add( s8);
	    v.add( s9);
	    v.add( s10);
	 }
      }
      for( i = 0; i < letters.length - 2; i++)
      {
         for( j = i + 1; j < letters.length - 1; j++)
	 {
	    for( k = i + 2; k < letters.length; k++)
	    {
	       String s11 = new StringBuffer().append( letters[i]).append( letters[j]).append(letters[k]).toString();
	       String s12 = new StringBuffer().append( letters[i]).append( letters[k]).append(letters[j]).toString();
	       String s13 = new StringBuffer().append( letters[k]).append( letters[j]).append(letters[i]).toString();
	       String s14 = new StringBuffer().append( letters[k]).append( letters[i]).append(letters[j]).toString();
	       String s15 = new StringBuffer().append( letters[j]).append( letters[i]).append(letters[k]).toString();
	       String s16 = new StringBuffer().append( letters[j]).append( letters[k]).append(letters[i]).toString();
	       v.add( s11);
	       v.add( s12);
	       v.add( s13);
	       v.add( s14);
	       v.add( s15);
	       v.add( s16);
	    }
	 }
      }
   }
         
   public static void main( String[] args)
   {
      Cracker cr = new Cracker();
      cr.loadLetters();
      cr.loadPairs();
      cr.loadTriples();
      System.out.println(" far "+cr.getVictor().size()+" elements loaded");
   }
}
      
