



public class SMTPException extends Exception {

       private String msg; 
       
       public SMTPException(String message) {
              msg = message;
       }

       
       public String getMessage() {
              return msg;
       }
}