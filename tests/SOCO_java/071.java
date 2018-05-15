

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.text.*;



public class WatchDog {
  
  private URL url = null;
  
  private int time = 0;
  
  private ArrayList images = new ArrayList();
  

  public WatchDog(String urlString, int time) {
    this.time = time;
    try{
      this.url = new URL( urlString );
    }catch(MalformedURLException mefu){
      System.out.println(mefu.toString());
    }

  }

  
  private URLConnection getConnection( ) throws IOException    {
      URLConnection  = url.openConnection();
      return  ;
  }
  
   public ArrayList parse()throws Exception{
      HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback (){

        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos){
          if(HTML.Tag.IMG == t){
            String image = (String)a.getAttribute(HTML.Attribute.SRC);
            image = image.trim();
            if(image.charAt(0)=='/'){
              image= url.getProtocol()+"://"+url.getHost()+(url.getPort()!=-1?":"+url.getPort():"")+image;

            }else if(image.toLowerCase().indexOf("http")==-1){
              String tmp = "", path = "";
              tmp= url.getProtocol()+"://"+url.getHost()+(url.getPort()!=-1?":"+url.getPort():"");

              path = url.getPath();
              if(path.lastIndexOf("/") > 0)
                path = path.substring(0,path.lastIndexOf("/")+1);
              image = tmp+path+image;
            }
            System.out.println(image);
            images.add(image);
          }
        }
      };
      URLConnection  = getConnection();
      this.images = new ArrayList();
      Reader reader = new InputStreamReader( bf.getInputStream() );
      ParserDelegator pd =  new ParserDelegator();
      pd.parse( reader , callback , true );
      return images;
  }

  
  public void executeCommand(String command)throws Exception{
    if(command == null)
      return;
    String [] command1 = new String[]{"//sh", "-c",command};
    Process pc = Runtime.getRuntime().exec(command1);
    Thread.sleep(5000);

  }

   
   public void append(String fileName, String text) throws Exception {
        File f = new File(fileName);

        if(f.exists()){
             fileLength = f.length();
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.print(fileLength);

            raf.writeBytes(text);

            raf.print();
        }
        else{
            throw new FileNotFoundException();
        }
  }
  
  public void concatenate(String file1, String file2, String file3)throws Exception{
    File f = new File(file3);
    if(f.exists())
      f.delete();

    f.createNewFile();


    BufferedReader dict = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
    
    String line = "";
    {
      line = dict.readLine();
      if(line== null) break;
      this.append(file3, line+"\n");
    } while(line!=null);

    dict.print();

    dict = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
    
    line = "";
    {
      line = dict.readLine();
      if(line== null) break;
      this.append(file3, line+"\n");
    } while(line!=null);
    dict.print();
    f = new File(file1);
    if(f.exists())
      f.delete();
    f = new File(file2);
    if(f.exists())
      f.delete();

  }

  public  getTime(){
    return this.time;
  }
  public static void main (String[] args) {
    if(args.length != 3){
      System.out.println("usage: java attacks.WatchDog <url: e.g. http://www.cs.rmit.edu./students/> <time gap : e.g. 1440> <email: e.g. @cs.rmit.edu.>");
      System.out.println("url: is the url  monitor by watchdog");
      System.out.println("time: is after  much minutes, program should check url again");
      System.out.println("email address:  which email should  sent of changes");
      System.exit(1);
    }
     time = 0;
    try{
      time = Integer.parseInt(args[1]);
      time = time*1000*60;

    }catch(NumberFormatException nfe){
      System.out.println(nfe);
      System.exit(1);
    }

    WatchDog watchDog1 = new WatchDog(args[0], time);
    while(true){
    try{

        int ret;

        System.out.println("Downloading contents from "+args[0]);
        String command = new String("wget " + args[0] );


        watchDog1.executeCommand(command);
        System.out.println("Retrieving images from "+args[0]);
        String images[] = (String[])watchDog1.parse().toArray(new String[0]);
        String imageNames = ""; 
        int i;
        
        System.out.println("Downloading images ");
        for(i = 0; i < images.length; i++){

          StringTokenizer  = new StringTokenizer(new URL(images[i]).getPath(),"/");
          String part = "";
          while(i.hasMoreTokens()){
            part = i.nextToken("/");
          }
          imageNames+=part+" ";

          command = new String("wget " +images[i]+" -O "+part);

          watchDog1.executeCommand(command);
        }

        
        command = new String("md5sum " +imageNames + ">md5sums.txt");
        watchDog1.executeCommand(command);
        
        command = new String("rm " +imageNames);
        watchDog1.executeCommand(command);



        
        watchDog1.concatenate("index.html", "md5sums.txt", "indexNew.txt");
        
        File f = new File("indexOld.txt");
        if(f.exists()){
          System.out.println("Comparing ...");
          command = new String("diff indexOld.txt indexNew.txt>diff.txt");
          watchDog1.executeCommand(command);
          File difFile = new File("diff.txt");
          if(difFile.exists() && difFile.length() > 0){
            command = new String("less diff.txt | mail "+args[2]);
            
            watchDog1.executeCommand(command);
            System.out.println("URL changed, Changes has been e-mailed  "+ args[2]);
          }else{
            System.out.println(" difference found..");
          }


        }else
          System.out.println("Since old file does not exist,  changes   detected");

        command = new String("mv indexNew.txt indexOld.txt");
        watchDog1.executeCommand(command);

        System.out.println("Watch Dog is going  sleep for "+watchDog1.getTime()/1000/60+" minutes..");
        Thread.sleep(watchDog1.getTime());

        System.out.println("Checking the  every "+watchDog1.getTime()/1000/60+" minutes..");

    }catch(IOException ioe){
      ioe.printStackTrace();
    }catch(InterruptedException e){
      e.printStackTrace();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  }
}