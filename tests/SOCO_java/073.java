

public class Execute {
   public Execute(String cmdline) {
      try {
         
         String[] cmd = {"//sh", "-c", cmdline};
         Process p = Runtime.getRuntime().exec(cmd);
         
         p.waitFor();
      } catch (Exception e){}
   }
}

