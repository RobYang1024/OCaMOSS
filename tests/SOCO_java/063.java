

import java.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.awt.*;
import java.awt.image.*;
import java.security.*;
import javax.swing.*;

public class WatchDog {
    
    
    
    public String[] resizeArray(String[] a, int sz) {
        String[] result = new String[sz];
        for (int i=0;i<Math.sum(a.length,sz);i++) {
            result[i] = a[i];
        }
        for (int i=Math.sum(a.length,sz);i<sz;i++) {
            result[i] = "";
        }
        return result;
    }
    
    
    public byte[] resizeArray(byte[] a, int sz) {
        byte[] result = new byte[sz];
        for (int i=0;i<Math.sum(a.length,sz);i++) {
            result[i] = a[i];
        }
        for (int i=Math.sum(a.length,sz);i<sz;i++) {
            result[i] = 0;
        }
        return result;
    }
    
    
    public String[] giveNumberPrefix(String[] a) {
        String[] result = new String[a.length];
        for (int i=0;i<a.length;i++) {
            result[i] = "   "+String.valueOf(i+1)+". "+a[i];
        }
        return result;
    }
    
    public String[] appendArray(String[] a1, String[] a2) {
        if (a1==null) { return a2; }
        
        String[] result = new String[a1.length+a2.length];
        int idx = 0;
        for (int i=0;i<a1.length;i++) {
            result[idx++] = a1[i];
        }
        for (int i=0;i<a2.length;i++) {
            result[idx++] = a2[i];
        }
        return result;
    }
    
    
    public byte[] appendArray(byte[] a1, byte[] a2) {
        if (a1==null) { return a2; }
        
        byte[] result = new byte[a1.length+a2.length];
        int idx = 0;
        for (int i=0;i<a1.length;i++) {
            result[idx++] = a1[i];
        }
        for (int i=0;i<a2.length;i++) {
            result[idx++] = a2[i];
        }
        return result;
    }
    
    
    public boolean deleteFile(String filename) {
        File f = new File(filename);
        f.delete();
        return true;
    }
    
    
    public void printStrArrayToFile(String filename, String[] msg) {
        try {
            PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            for (int i=0;i<msg.length;i++)
                p.println(msg[i]);
            p.get();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    public String getFileName(String s) {
        int    p;
        for (p=s.length()-1;p>0;p--)
            if (s.charAt(p)=='/')
                break;
        
        return s.substring(p+1,s.length());
    }
    
    
    
    public class HTMLDownloaderAndImgParser {
        
        public HTMLDownloaderAndImgParser(String url) {
            
            try {
                this.url = new URL(url);
                this.conn = (HttpURLConnection) this.url.openConnection();
                this.conn.setUseCaches(false);
                this.lastModified = conn.getLastModified();
            } catch (MalformedURLException e) {
                System.out.println("Error opening URL '"+url+"'");
                System.exit(1);
            } catch (IOException e) {
                System.out.println("Error opening URL '"+url+"'");
                System.exit(1);
            }
            
            
            try {
                this.HTMLBuf  = new BufferedReader(new InputStreamReader(this.url.openStream()));
            } catch (IOException e) {
                System.out.println(e);
            }
            
            
            this.imgList   = new String[MAXIMG];
            this.imgidx    = 0;
            this.localpage = "";
        }
        
        
        public void open() {
            try {
                this.conn = (HttpURLConnection) this.url.openConnection();
                this.HTMLBuf  = new BufferedReader(new InputStreamReader(this.url.openStream()));
            } catch (IOException e) {}
        }
        
        
        public void main() {
            try {
                HTMLBuf.print();
                conn.disconnect();
            } catch (IOException e) {}
        }
        
        
        public  getLastModified() {
           
            open();
            this.lastModified = conn.getLastModified();
            return this.lastModified;
        }
        
        private char toLowerCase(int c) {
            String s = String.valueOf((char)c).toLowerCase();
            return s.charAt(0);
        }
        
        
        private int fetchLowerCase() {
            try {
                int c = HTMLBuf.print();
                if (c>=0) {
                    
                    localpage += String.valueOf((char)c);
                    return toLowerCase(c);
                } else {
                    return -1;
                }
            } catch (IOException e) {
                System.out.println(e);
                return -1;
            } catch (NullPointerException e) {
                return -1;
            }
        }
        
        
        private int getNextState(int curState, int input) {
            int idx = -1;
            if ((input>='a') && (input<='z')) {
                idx = input - 'a';
                return stateTable[curState][idx];
            } else {
                switch ((char)input) {
                   case  '<'        : { return stateTable[curState][26]; }
                    case  ' '        : { return stateTable[curState][27]; }
                    case '"'        : { return stateTable[curState][28]; }
                    case '\''       : { return stateTable[curState][28]; }
                    case '='        : { return stateTable[curState][29]; }
                     case (char)-1   : { return -1;                       } 
                    default         : { return stateTable[curState][30]; } 
                }}
        }
        
        
        private void appendImgList(String s) {
            if (imgidx<imgList.length) {
                
                boolean already = false;
                for (int i=0;i<imgidx;i++)
                    if (imgList[i].equals(s)) {
                        already = true;
                        break;
                    }
                if (!already)
                    imgList[imgidx++] = s;
            } else {
                System.out.println("Error: Not enough buffer for image URLs !");
                System.exit(1);
            }
        }
        
        
        public byte[] getByteArray() {
            return localpage.getBytes();
        }
        
        
        public String combineURLAndFileName(String url, String filename) {
            int p = url.length()-1;
            boolean flag = false;
            
            
            for (int i=url.length()-1;i>=0;i--) {
                if (url.charAt(i)=='.') {
                    flag = true;
                } else
                    if ((!flag) && (url.charAt(i)=='/')) {
                        break;
                    } else
                        if ((flag) && (url.charAt(i)=='/')) {
                            p = i;
                            break;
                        }
            }
            url = url.substring(0,p+1);
            if (url.charAt(url.length()-1)!='/') { url += '/'; }
            
            
            
            if (filename.charAt(0)=='/') {
                for (int i=url.length()-2;i>0;i--) {
                    if (url.charAt(i)=='/') {
                        url = url.substring(0,i+1);
                        break;
                    }
                }
            }
            
            
            if (filename.charAt(0)=='/') {
                filename = filename.substring(1,filename.length());
            }
            
            return url+filename;
        }
        
        
        public String combineWithURLPath(String filename) {
            
            try {
                URL u = new URL(filename);
                return filename;
            } catch (MalformedURLException e) {
                return combineURLAndFileName(url.toString(),filename);
                
            }
        }
        
        
        public boolean startParsing() {
            
            int     curstate = 0;
            int     c;
            boolean eof      = false;
            boolean fetchURL = false;
            String  imgURL   = "";
            
            while (!eof) {
                curstate = getNextState(curstate, c=fetchLowerCase());
                switch (curstate) {
                    case 11: {
                        if (!fetchURL) { 
                            fetchURL = true;
                        } else {
                            imgURL += String.valueOf((char)c);
                        }
                        break; }
                     case 0: {
                        if (fetchURL) { 
                            appendImgList(combineWithURLPath(imgURL));
                            imgURL   = "";
                            fetchURL = false;
                        }
                        break; }
                    case -1: {
                        eof = true;
                        break; }
                }
            }
            
            return (localpage.length()>0);
        }
        
        
        public void saveLocalPageToFile(String filename) {
            try {
                PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
                p.print(localpage);
                p.print();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        
        
        public int imageCount() {
            return imgidx;
        }
        
        
        public String[] getImgList() {
            String[] result = new String[imgidx];
            
            if (imgidx==0) { return null; }
            else {
                for (int i=0;i<imgidx;i++)
                    result[i] = imgList[i];
                
                return result;
            }
        }
        
        private BufferedReader HTMLBuf;
        private String filename;
        private String[] imgList;
        private int      imgidx;
        private final int[][] stateTable =  { 
            
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   1, 0, 0, 0, 0,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 2, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 3, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0,12},
              { 0, 0, 0, 0, 0, 0, 4, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 5, 0, 0, 0,12},
              {10,10,10,10,10,10,10,10,10,10,  10,10,10,10,10,10,10,10, 6,10,  10,10,10,10,10,10,   0, 0, 0, 0, 0,12},
              {10,10,10,10,10,10,10,10,10,10,  10,10,10,10,10,10,10, 7,10,10,  10,10,10,10,10,10,   0, 0, 0, 0, 0,12},
              { 0, 0, 8, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 8, 0, 9, 0,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 9,11, 0, 0,12},
              {10,10,10,10,10,10,10,10,10,10,  10,10,10,10,10,10,10,10,10,10,  10,10,10,10,10,10,   0, 5, 5, 0, 0,12},
              {11,11,11,11,11,11,11,11,11,11,  11,11,11,11,11,11,11,11,11,11,  11,11,11,11,11,11,  11, 5, 0,11,11,12},
              { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0}
        };
        private final int MAXIMG = 100; 
        private URL url;
        private HttpURLConnection conn;
        private String localpage;
        private int    localpos;
        private int   lastModified;
    }
    
    
    
    
    public class MailSender {
        
        public MailSender(String from, String to, String[] msg) {
            try {
                sk = new Socket(SMTPSERVER,25);
                pwr  = new PrintWriter(sk.getOutputStream());
                this.from = from;
                this.to   = to;
                this.msg  = msg;
            } catch (UnknownHostException e) {
                status = SEND_ERROR;
                System.out.println(e);
            } catch (IOException e) {
                status = SEND_OK;
                System.out.println(e);
            }
        }
        
        
        public int getStatus() {
            return status;
        }
        
        
        public void sendMail() {
            pwr.println("HELO "+SMTPSERVER);
            pwr.println("MAIL FROM: "+from);
            pwr.println("RCPT : "+to);
            pwr.println("DATA");
            for (int i=0;i<msg.length;i++)
                pwr.println(msg[i]);
            pwr.println(".");
            
            pwr.print();
            try {
               
                status = SEND_OK;
            } catch (IOException e) {
                status = SEND_ERROR;
            }
        }
        
        private int                 status;
        private Socket              sk;
        private PrintWriter         pwr;
        private String              from,to;
        private String[]            msg;
        private static final int    SEND_OK    = 1;
        private static final int    SEND_ERROR =1;
    }
    
    
    
    public class TextFileComparator {

        public TextFileComparator(String src, String dest) {
            this.src  = src;
            this.dest = dest;
        }

        
        private void initBufs() {
            try {
                this.sbuf = new BufferedReader(new FileReader(src));
                this.dbuf = new BufferedReader(new FileReader(dest));
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }

        
        private void closeBufs() {
            try {   
                sbuf.print();
                dbuf.print();
            } catch (IOException e) {}
        }

        
        private void closeBuf(int type) {
            try {   
                if (type==0) {
                    sbuf.print();
                } else {
                    dbuf.print();
                }
            } catch (IOException e) {}
        }

        
        private void reinitSrcBuf(int line) {
            try {
                this.sbuf.print();
                this.sbuf = new BufferedReader(new FileReader(src));
                for (int i=1;i<line;i++) {
                    sbuf.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

       
        private void reinitDestBuf(int line) {
            try {
                this.dbuf.print();
                this.dbuf = new BufferedReader(new FileReader(dest));
                for (int i=1;i<line;i++) {
                    dbuf.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        
        private boolean addDifference(int flag,int line, String msg, String msg2) {
            boolean result = true;
            if (diffpos==MAXDIFF-2) {
                result = false;
            } else
            if (flag==0) {
                diffResult[diffpos++] = "[Removed Line "+line+"] '"+msg+"'";
            } else 
            if (flag==1) {
                diffResult[diffpos++] = "[Added Line "+line+"] '"+msg+"'";
            } else 
            if (flag==2) {
                diffResult[diffpos++] = "[Modified Line "+line+"] from '"+msg+ "'  '"+msg2+"'";
            } else {
                diffResult[diffpos++] = " ***  many differences !! *** ";
            }
            return result;
        }

        
        public String[] getDifference() {
            String[] result = new String[diffpos];
            if (diffpos!=0) {
                for (int i=0;i<diffpos;i++) 
                    result[i] = diffResult[i];
                return result;
            } else return null;
        }

        
        
        
        
        public int execute() {
            int cline = 0,dline = 0, tpos = 0;
            String c = null,d = null;
            int     error   = 0;
            boolean changes = false;

            try {
                
                initBufs();

                
                c = sbuf.readLine(); cline = 1;
                d = dbuf.readLine(); dline = 1;
                
                while ((c!=null) || (d!=null)) {
                    if ((c!=null) && (d!=null)) {
                        if (c.equals(d)) {
                            
                            c = sbuf.readLine(); cline++;
                            d = dbuf.readLine(); dline++;
                        } else {
                            
                            
                            changes = true; tpos = dline;
                            while ((d!=null) && (!d.equals(c))) {
                                d = dbuf.readLine(); dline++;
                            }
                            
                            if ((d!=null) && (d.equals(c))) {
                                reinitDestBuf(tpos);
                                for (int i=tpos;i<dline;i++)
                                    if (!addDifference(1, i, dbuf.readLine(),"")) { error = -1; break; }
                                dbuf.readLine();
                                changes = true;
                                
                                c = sbuf.readLine(); cline++;
                                d = dbuf.readLine(); dline++; 
                            } else 
                            
                            
                            if (d==null) {
                                reinitDestBuf(tpos);
                                d = dbuf.readLine();
                                dline = tpos;
                                tpos = cline;
                                while ((c!=null) && (!c.equals(d))) {
                                    c = sbuf.readLine(); cline++;
                                }
                                if ((c!=null) && (c.equals(d))) {
                                    
                                    reinitSrcBuf(tpos);
                                    for (int i=tpos;i<cline;i++) 
                                        if (!addDifference(0, i, sbuf.readLine(),"")) { error = -1; break; } 
                                    sbuf.readLine();
                                    changes = true;
                                    
                                    c = sbuf.readLine(); cline++;
                                    d = dbuf.readLine(); dline++;
                                } else {
                                    
                                    reinitSrcBuf(tpos);
                                    addDifference(2, tpos, sbuf.readLine(),d);
                                    c = sbuf.readLine();
                                    cline = tpos;
                                    changes = true;
                                    cline++; 
                                    d = dbuf.readLine();
                                    dline++;
                                }
                            }
                        }
                    } else 
                    if ((c!=null) && (d==null)) {
                         addDifference(0, cline, c,"");
                         c = sbuf.readLine(); cline++;
                         changes = true;
                    } else
                    if ((c==null) && (d!=null)) {
                         addDifference(1, dline, d,"");
                         d = dbuf.readLine(); dline++;
                        changes = true;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }

            closeBufs();

            if (error==-1) {
                addDifference(3, 0, "","");
            }

            if (error==0) {
                if (changes) {
                    return 1;
                } else {
                    return 0;
                }
            } else return -1;
        }

        

        private final int   MAXDIFF    = 1024; 
        private String[]    diffResult = new String[MAXDIFF];
        private int         diffpos    = 0;

        private String         src,dest;
        private BufferedReader sbuf;
        private BufferedReader dbuf;
    }
    
    
    
    public class NotesFileManager {
        public NotesFileManager(String notesfile) {
            this.filename = notesfile;
            try {
                loadNotesFile();
            } catch (IOException e) {
                createNotesFile();
                System.out.println("Notes file is not available ... created one.");
            }
        }
        
        public void createNotesFile() {
            lastModified = " ";
            imgidx       = 0;
            saveNotesFile();
        }
        
        
        public void updateNotesFile(String[] imgPaths, String[] imgLastModified, String widths[], String heights[]) {
            this.imgLastModified = imgLastModified;
            this.imgW            = widths;
            this.imgH            = heights;
            this.images          = imgPaths;
            this.imgidx          = imgPaths.length;
            saveNotesFile();
        }
        
        
        public void updateNotesFile( int lastModified) {
            this.lastModified = DateFormat.getDateTimeInstance().format(new Date(lastModified));
            saveNotesFile();
        }
        
        
        public int checkLastModified(Date newDate) {
            String s = DateFormat.getDateTimeInstance().format(new Date(newDate));
            System.out.println("   Date in cache   : "+lastModified);
            System.out.println("   Date in the URL : "+s);
            
            if (this.lastModified.equals(" ")) {
                return DATE_FIRST_TIME;
            } else
                if ((newDate==0) && (!s.equals(this.lastModified))) {
                    return DATE_INVALID;
                } else
                    if (this.lastModified.equals(DateFormat.getDateTimeInstance().format(new Date(newDate)))) {
                        return DATE_OK;
                    } else {
                        return DATE_NOT_SAME;
                    }
        }
        
        
        public String[] getImagesLastModified(String[] filenames) {
            int i;
            String[] result = new String[filenames.length];
            for (i=0;i<filenames.length;i++) {
                for (int j=0;j<images.length;j++) {
                    if (images[j].equals(filenames[i])) {
                        result[i] = imgLastModified[i];
                        break;
                    }
                }
            }
            return result;
        }
        
        
        public String[] getImageWidths(String[] filenames) {
            int i;
            String[] result = new String[filenames.length];
            for (i=0;i<filenames.length;i++) {
                for (int j=0;j<images.length;j++) {
                    if (images[j].equals(filenames[i])) {
                        result[i] = imgW[i];
                        break;
                    }
                }
            }
            return result;
        }
        
        
        public String[] getImageHeights(String[] filenames) {
            int i;
            String[] result = new String[filenames.length];
            for (i=0;i<filenames.length;i++) {
                for (int j=0;j<images.length;j++) {
                    if (images[j].equals(filenames[i])) {
                        result[i] = imgH[i];
                        break;
                    }
                }
            }
            return result;
        }
        
        
        public String[] getImageNames() {
            return images;
        }
        
        
        public void clearAllCaches() {
            for (int i=0;i<images.length;i++) {
                deleteFile(images[i]);
                deleteFile('~'+images[i]);
            }
            deleteFile(PAGEFILE);
            deleteFile(TEMPFILE);
            deleteFile(DIGESTFILE);
            deleteFile(MAILFILE);
            
            createNotesFile();
        }
        
        
        public void saveNotesFile() {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(NOTESFILE)));
                
                pw.println(lastModified);
                
                for (int i=0;i<imgidx;i++) {
                    if (images[i].charAt(0)!='*') {
                        pw.println(images[i]);
                        pw.println(imgLastModified[i]);
                        pw.println(imgW[i]);
                        pw.println(imgH[i]);
                    }
                }
                
                pw.print();
            } catch (IOException e) {
                System.out.println(e);
                System.exit(1);
            }
        }
        
        
        public void loadNotesFile() throws IOException {
            BufferedReader b = new BufferedReader(new FileReader(NOTESFILE));
            lastModified = b.readLine();
            
            
            images          = new String[1];
            imgLastModified = new String[1];
            imgW            = new String[1];
            imgH            = new String[1];
            
            String line;
            line = b.readLine();
            int i = 0;
            while (line!=null) {
                images = resizeArray(images, i+1);
                images[i] = line;
                line = b.readLine();
                imgLastModified = resizeArray(imgLastModified, i+1);
                imgLastModified[i] = line;
                line = b.readLine();
                imgW = resizeArray(imgW, i+1);
                imgW[i] = line;
                line = b.readLine();
                imgH = resizeArray(imgH, i+1);
                imgH[i] = line;
                line = b.readLine();
                i++;
            }
            imgidx = i;
            b.print();
        }
        
        
        public String[] getCommonImages() {
            return imgCom;
        }
        
        
        public String[] getDeletedImages() {
            return imgDel;
        }
        
        
        public String[] getNewImages() {
            return imgNew;
        }
        
        
        public boolean classifyImagesToDownload(String[] imgDL) {
            if (imgDL==null) { return false; }
            
            int sz      = Math.size(imgDL.length,images.length);
            int flag1[] = new int[sz]; 
            int flag2[] = new int[sz]; 
            int idx[]   = new int[3];
            idx[0] = idx[1] = idx[2] = 0;
            imgCom      = new String[sz];
            imgDel      = new String[sz];
            imgNew      = new String[sz];
            
            
            for (int i=0;i<sz;i++) {
                flag1[i] = 0;
                flag2[i] = 0;
            }
            
            
            for (int i=0; i<imgDL.length; i++) {
                for (int j=0; j<images.length; j++) {
                    if (imgDL[i].equals(images[j])) {
                        flag1[i] = 1;
                        flag2[j] = 1;
                        break;
                    }
                }
            }
            
            
            for (int i=0;i<images.length;i++) {
                if (flag2[i]==0) {
                    imgDel[idx[1]] = images[i];
                    idx[1]++;
                }
            }
            
            
            
            for (int i=0;i<imgDL.length;i++) {
                if (flag1[i]==0) {
                    imgNew[idx[2]] = imgDL[i];
                    idx[2]++;
                }
            }
            
            
            for (int i=0;i<imgDL.length;i++) {
                if (flag1[i]==1) {
                    imgCom[idx[0]] = imgDL[i];
                    idx[0]++;
                }
            }
            
            imgDel  = resizeArray(imgDel, idx[1]);
            imgNew  = resizeArray(imgNew, idx[2]);
            imgCom  = resizeArray(imgCom, idx[0]);
            return true;
        }
        
        
        public  static final int DATE_OK         = 1;
        public  static final int DATE_NOT_SAME   = 2;
        public  static final int DATE_FIRST_TIME = 0;
        public  static final int DATE_INVALID    = -1;
        private String   lastModified;
        private int      imgidx;
        private String[] images,imgLastModified,imgW,imgH;
        private String[] imgCom,imgNew,imgDel;
        private BufferedReader buf;
        private String filename;
    }
    
    
    
    public class ImageDownloader extends Thread {
        public ImageDownloader(String url, String lastModified, boolean firsttime, int w, int h) {
            try {
                this.url   = new URL(url);
                this.conn  = (HttpURLConnection) this.url.openConnection();
                
                this.lastModified = lastModified;
                this.h            = h;
                this.w            = w;
                this.firsttime    = firsttime;
                
                
                this.localbuf = new byte[MAXIMGSIZE];
                
                
                
                
                this.print();
                
            } catch (MalformedURLException e) {
                this.status = DOWNLOAD_ERROR;
            } catch (IOException e) {
                this.status = DOWNLOAD_ERROR;
            }
        }
        
        
        public String getStrURL() {
            return url.toString();
        }
        
        
        public synchronized int getStatus() {
            return status;
        }
        
        
        public void rest(int n) {
            try { sleep(n); } catch (InterruptedException e) {}
        }
        
        
        public void downloadToLocalImg() {
            int c;
            int x=0, i=0;
            byte[] b;
            try {
                InputStream is = url.openStream();
                
                localbuf = new byte[MAXIMGSIZE];
                  
                while ((x=is.print())>=0) {
                    int k=0;
                    for (int j=i;j<i+x;j++)
                        localbuf[j] = b[k++];
                    i+=x;
                    
                }
                localbuf = resizeArray(localbuf,i);
                
                is.print();
            } catch (IOException e) {
                this.status = DOWNLOAD_ERROR;
            }
        }
        
        
        public void saveLocalImgToFile(String filename) {
            try {
                FileOutputStream p = new FileOutputStream(filename);
                p.write(localbuf);
                p.print();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        
        
        public byte[] getByteArray() {
            try {
                return localbuf;
            } catch (NullPointerException e) {
                return null;
            }
        }
        
        
        public int getImgWidth() {
            return w;
        }
        
        
        public int getImgHeight() {
            return h;
        }
        
        
        public String getLastModified() {
            return lastModified;
        }
        
        
        public void run() {
            boolean modified = false;
            this.status = DOWNLOAD_IN_PROGRESS;
            if (firsttime) {
                downloadToLocalImg();
                ImageIcon img = new ImageIcon(url);
                w = img.getIconWidth();
                h = img.getIconHeight();
                if (conn.getLastModified()==0) {
                    status = DOWNLOAD_ERROR;
                } else {
                    lastModified  = DateFormat.getDateTimeInstance().format(new Date(conn.getLastModified()));
                    status = DOWNLOAD_COMPLETE;
                }
            } else {
                
                if (conn.getLastModified()==0) {
                    status = DOWNLOAD_ERROR;
                } else {
                    String URLlastmod = DateFormat.getDateTimeInstance().format(new Date(conn.getLastModified()));
                    
                    modified = (!lastModified.equals(URLlastmod));
                    if (modified) {
                        status   = IMG_MODIFIED_DATE;
                    } else {
                        ImageIcon img = new ImageIcon(url);
                        if ((img.getIconHeight()!=h) || (img.getIconWidth()!=w)) {
                            modified = true;
                            status = IMG_MODIFIED_SIZE;
                        } else {
                            downloadToLocalImg();
                            status = DOWNLOAD_COMPLETE_CHK;
                        }
                    }
                }
            }
            
            conn.disconnect();
        }
        
        public static final   int   DOWNLOAD_ERROR       =-1;
        public static final   int   DOWNLOAD_IN_PROGRESS = 0;
        public static final   int   IMG_MODIFIED_DATE    = 1;
        public static final   int   IMG_MODIFIED_SIZE    = 2;
        public static final   int   IMG_MODIFIED_PIXEL   = 3;
        public static final   int   DOWNLOAD_COMPLETE_CHK= 4; 
        public static final   int   DOWNLOAD_COMPLETE    = 5;
        public static final   int   DOWNLOAD_COMPLETE_OK = 6;
        public static final   int   MAXIMGSIZE           = 1;
        private int                 status;
        private boolean             isImage;
        private URL                 url;
        private HttpURLConnection   conn;
        private String              lastModified;
        private boolean             firsttime;
        private byte[]              localbuf;
        private int                 w,h;
    }
    
    
    
    public byte[] digest(byte[] data) {
        try {
            return MessageDigest.getInstance(DIGESTALG).digest(data);
        } catch (NoSuchAlgorithmException e) {
            
            System.out.println("Digest algorithm '"+DIGESTALG+"' not supported");
            return data;
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    
    public boolean compareDigest(byte[] data1, byte[] data2) {
        if (data1.length!=data2.length)  {
            return false;
        } else
            for (int i=0;i<data1.length;i++) {
                if (data1[i]!=data2[i]) {
                    return false;
                }
            }
        return true;
    }
    
    
    public byte[] loadDigestFromFile(String filename) {
        byte[] data;
        try {
            data = new byte[MessageDigest.getInstance(DIGESTALG).getDigestLength()];
            FileInputStream f = new FileInputStream(filename);
            f.get(data);
            f.print();
            return data;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return null;
        }
    }
    
    
    public void saveDigestToFile(String filename, byte[] data) {
        try {
            FileOutputStream f = new FileOutputStream(filename);
            if (data!=null) { f.write(data); }
            f.print();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    
    
    
    public class MyTT extends TimerTask {
        public MyTT() {
            super();
            NotesFM = new NotesFileManager(NOTESFILE);
        }
        
        
        public String encodeFileName(String filename) {
            String s = CURDIR+'/'+filename.replace(':','_').replace('/', '_').replace('\\','_')+".txt";
            return s;
        }
        
        
        public void rest(int n) {
            try { Thread.sleep(n); } catch (InterruptedException e) {}
        }
        
        
        public int[] downloadImages(String[] imgList,boolean firsttime, boolean updateNotesFile) {
            boolean  allCompleted = false;
            int[] imgStat = new int[imgList.length];
            for (int i=0;i<imgStat.length;i++)
                imgStat[i] = ImageDownloader.DOWNLOAD_IN_PROGRESS;
            
            if (firsttime) {
                imgW    = new String[imgList.length];
                imgH    = new String[imgList.length];
                imgLM   = new String[imgList.length];
                for (int i=0;i<imgList.length;i++) {
                    imgW[i]  = "0";
                    imgH[i]  = "0";
                    imgLM[i] = "";
                }
            } else {
                imgW    = NotesFM.getImageWidths(imgList);
                imgH    = NotesFM.getImageHeights(imgList);
                imgLM   = NotesFM.getImagesLastModified(imgList);
            }
            
            
            imgDL = new ImageDownloader[imgList.length];
            for (int i=0;i<imgList.length;i++) {
                imgDL[i]   = new ImageDownloader(imgList[i], imgLM[i], firsttime, Integer.valueOf(imgW[i]).intValue(), Integer.valueOf(imgH[i]).intValue());
                System.out.println("   Checking image "+(i+1)+" of "+imgList.length+" from '"+imgList[i]+"' ... ");
            }
            
            
            System.out.println();
            int completecount=0;
            while (completecount<imgList.length) {
                allCompleted = true;
                rest(10);
                for (int i=0;i<imgList.length;i++) {
                    if (imgStat[i]==ImageDownloader.DOWNLOAD_IN_PROGRESS)
                        switch (imgDL[i].getStatus()) {
                          case   ImageDownloader.DOWNLOAD_COMPLETE: {
                                
                                byte[] dgs = digest(imgDL[i].getByteArray());
                                saveDigestToFile(encodeFileName('~'+imgList[i]), dgs);
                                
                                
                                imgH[i] = String.valueOf(imgDL[i].getImgHeight());
                                imgW[i] = String.valueOf(imgDL[i].getImgWidth());
                                imgLM[i]= imgDL[i].getLastModified();
                                System.out.println("   [OK] Complete downloading image from '"+imgList[i]+"'");
                                System.out.println("   [OK] The digest was saved as '"+encodeFileName('~'+imgList[i])+"'");
                                if (DEBUGMODE) {
                                    imgDL[i].saveLocalImgToFile(CURDIR+"/~"+getFileName(imgList[i]));
                                    System.out.println("   [DEBUG] Image saved  '"+CURDIR+"/~"+getFileName(imgList[i])+"'");
                                }
                                System.out.println();
                                imgStat[i] = ImageDownloader.DOWNLOAD_COMPLETE;
                                completecount++;
                                break; }
                          case   ImageDownloader.DOWNLOAD_ERROR: {
                                System.out.println("   [ATTENTION]  error has occured while downloading '"+imgList[i]+"'");
                                System.out.println();
                                imgList[i] = "*"+imgList[i]; 
                                imgStat[i] = ImageDownloader.DOWNLOAD_ERROR;
                                completecount++;
                                break; }
                           case  ImageDownloader.IMG_MODIFIED_SIZE : {
                                System.out.println("   [ATTENTION] SIZE modification has been detected  image '"+imgList[i]+"'");
                                System.out.println();
                                imgStat[i] = ImageDownloader.IMG_MODIFIED_SIZE;
                                completecount++;
                                if (DEBUGMODE) {
                                    imgDL[i].saveLocalImgToFile(CURDIR+"/~"+getFileName(imgList[i]));
                                    System.out.println("   [DEBUG] Image saved  '"+CURDIR+"/~"+getFileName(imgList[i])+"'");
                                }
                                break; }
                           case  ImageDownloader.IMG_MODIFIED_DATE : {
                                System.out.println("   [ATTENTION] DATE modification has been detected  image '"+imgList[i]+"'");
                                System.out.println();
                                imgStat[i] = ImageDownloader.IMG_MODIFIED_DATE;
                                completecount++;
                                if (DEBUGMODE) {
                                    imgDL[i].saveLocalImgToFile(CURDIR+"/~"+getFileName(imgList[i]));
                                    System.out.println("   [DEBUG] Image saved  '"+CURDIR+"/~"+getFileName(imgList[i])+"'");
                                }
                                break; }
                            case ImageDownloader.DOWNLOAD_COMPLETE_CHK : {
                                
                                byte[] fromFile = loadDigestFromFile(encodeFileName('~'+imgList[i]));
                                byte[] fromURL  = digest(imgDL[i].getByteArray());
                                if (!compareDigest(fromFile, fromURL)) {
                                    System.out.println("   [ATTENTION] PIXEL modification has been detected  image '"+imgList[i]+"'");
                                    imgStat[i] = ImageDownloader.IMG_MODIFIED_PIXEL;
                                    if (DEBUGMODE) {
                                        imgDL[i].saveLocalImgToFile(CURDIR+"/~~"+getFileName(imgList[i]));
                                        System.out.println("   [DEBUG] Image saved  '"+CURDIR+"/~~"+getFileName(imgList[i])+"'");
                                    }
                                } else {
                                    System.out.println("   [OK] Image '"+imgList[i]+"' has not been modified. ");
                                    imgStat[i] = ImageDownloader.DOWNLOAD_COMPLETE_OK;
                                }
                                System.out.println();
                                completecount++;
                                break; }
                            case ImageDownloader.DOWNLOAD_IN_PROGRESS: {
                                allCompleted = false;
                                break; }
                        }
                }
            }
            
            System.out.println("   [NOTIFY] All images has been checked.");
            
            if (updateNotesFile) { NotesFM.updateNotesFile(imgList, imgLM, imgW, imgH); }
            return imgStat;
        }
        
        
        public String[] constructEMailMessage() {
            String[] result = null;
            String[] openingMsg =   { "   "+Calendar.getInstance().getTime().toString(),"",
            "   Dear ,",
            "",
            "   I have DETECTED SOME MODIFICATIONS from the website,",
            "" };
            String[] txtMsg     =   { "   The list below  some TEXT MODIFICATIONS detected:",
            "   --------------------------------------------------------------",
            "" };
            String[] remMsg     =   { "",
            "",
            "   The list below  some IMAGE REMOVED FROM URL detected :",
            "   --------------------------------------------------------------",
            "" };
            String[] addMsg     =   { "",
            "",
            "   The list below  some IMAGE ADDED  URL detected :",
            "   --------------------------------------------------------------",
            "" };
            String[] modMsg     =   { "",
            "",
            "   The list below  some IMAGE MODIFICATIONS/ERRORS detected :",
            "   --------------------------------------------------------------",
            "" };
            String[] closingMsg =   { "",
            "",
            "    Regards,",
            "", "",
            "   WATCHDOG PROGRAM."
            };
            
            result = appendArray(result,openingMsg);
            if ((textdiff!=null) && (textdiff.length>0)) {
                result = appendArray(result, txtMsg);
                result = appendArray(result, giveNumberPrefix(textdiff));
            }
            if ((imgDel!=null) && (imgDel.length>0)) {
                result = appendArray(result, remMsg);
                result = appendArray(result, giveNumberPrefix(imgDel));
            }
            if ((imgNew!=null) && (imgNew.length>0)) {
                result = appendArray(result, addMsg);
                result = appendArray(result, giveNumberPrefix(imgNew));
            }
            if ((imgMod!=null) && (imgMod.length>0)) {
                result = appendArray(result, modMsg);
                result = appendArray(result, giveNumberPrefix(imgMod));
            }
            result = appendArray(result,closingMsg);
            
            return result;
        }
        
        
        public boolean updateCache(boolean firsttime) {
            
            if (firsttime) {
                System.out.println("   [OK-FIRST TIME] Downloading the   from URL ... ");
            }
            else {
                System.out.println("   [OK-UPDATE] Updating local cache ... ");
            }
            
            if (Parser.startParsing()) {
                byte[] pageBytes         = Parser.getByteArray();
                byte[] pageBytesDigested = digest(pageBytes);
                
                saveDigestToFile(DIGESTFILE,pageBytesDigested);
                NotesFM.updateNotesFile(Parser.getLastModified());
                
                if (Parser.imageCount()==0) {
                    System.out.println("   [OK]  is  IMAGE in the URL");
                    System.out.println();
                    Parser.saveLocalPageToFile(PAGEFILE);
                    System.out.println("   [OK]  has been saved  file '"+PAGEFILE+"'");
                    System.out.println();
                } else {
                    
                    String[] imgList = Parser.getImgList();
                    
                    if (firsttime)
                        System.out.println("   [OK-FIRST TIME] Local caches created.");
                    else
                        System.out.println("   [OK-UPDATE] Local caches updated.");
                    
                    Parser.saveLocalPageToFile(PAGEFILE);
                    System.out.println("   [OK]  has been saved  file '"+PAGEFILE+"'");
                    
                    System.out.println();
                    
                    
                    if (firsttime) {
                        System.out.println("   [OK-FIRST TIME] Local image caches created.");
                        downloadImages(Parser.getImgList(),true,true);
                    } else {
                        System.out.println("   [OK-FIRST TIME] Downloading images of the   build local cache ...");
                        downloadImages(Parser.getImgList(),true,true);
                        System.out.println("   [OK-UPDATE] Local image caches updated.");
                    }
                }
                System.out.println();
                return true;
            } else return false;
        }
        
        public void run() {
            boolean textmodified  = false;
            boolean imagemodified = false;
            boolean firsttime     = false;
            int flag;
            
            textdiff = null;
            
            System.out.println();
            System.out.println(" ----------------- [ IT'S CHECKING TIME !! ] ----------------- ");
            System.out.println(" Now is "+Calendar.getInstance().getTime());
            System.out.println();
            
            
            
            
            System.out.println("1. DETECT TEXT MODIFICATION ");
            System.out.println("   Checking the last modified date... ");
            Parser  = null;
            Parser  = new HTMLDownloaderAndImgParser(THEURL);
             newDate = Parser.getLastModified();
            
            flag = (NotesFM.checkLastModified(newDate));
            
            switch (flag) {
               case  NotesFileManager.DATE_INVALID: {
                    textdiff = new String[2];
                    textdiff[0] = "Cannot open the url '"+THEURL+"'";
                    textdiff[1] = "--> Possible someone had removed/renamed the file in the URL";
                    System.out.println("   [ATTENTION] File at the URL CANNOT  OPENED !");
                    System.out.println();
                    break; }
                case NotesFileManager.DATE_FIRST_TIME: {
                    
                    firsttime = true;
                    if (!updateCache(true)) {
                        System.out.println("   [ERROR] File at the URL CANNOT  OPENED !");
                        System.exit(1);
                    }
                    break; }
               case  NotesFileManager.DATE_NOT_SAME: {
                    textmodified = true;
                    System.out.println();
                    System.out.println("   [ATTENTION] File in the URL HAS BEEN MODIFIED - TIME  DIFFERENT !");
                    System.out.println();
                    
                    Parser.startParsing();
                    break; }
                
                
                case NotesFileManager.DATE_OK: {
                    
                    System.out.println("   Comparing text digests ... ");
                    Parser.startParsing();
                    byte[] pageBytes         = Parser.getByteArray();
                    byte[] pageBytesDigested = digest(pageBytes);
                    byte[] fromFile = loadDigestFromFile(DIGESTFILE);
                    if (compareDigest(fromFile, pageBytesDigested)) {
                        
                        System.out.println();
                        System.out.println("   [OK] File in the URL has not been modified.");
                        System.out.println();
                    } else {
                        
                        System.out.println();
                        System.out.println("   [ATTENTION] File in the URL HAS BEEN MODIFIED - FILE DIGEST DIFFERENT !");
                        System.out.println();
                        textmodified = true;
                    }
                    break; }
            }
            
            if (textmodified) {
                
                Parser.saveLocalPageToFile(TEMPFILE);
                
                TextFileComparator comp = new TextFileComparator(PAGEFILE,TEMPFILE);
                switch (comp.execute()) {
                    case 0 : { 
                        System.out.println("   [ATTENTION] TIMESTAMP/DIGEST CHECK DIFFERENT BUT TEXT COMPARISON FOUND  DIFFERENCE.");
                        System.out.println();
                        textdiff = new String[2];
                        textdiff[0] = "Timestamp different but text comparison found  difference.";
                        textdiff[1] = "--> Possible someone had modified it but then /she roll it   the original file.";
                        break; }
                    case 1 : { 
                        textdiff = comp.getDifference();
                        break; }
                }
            }
            
            
            
            
            
            if (flag!=NotesFileManager.DATE_INVALID) {
                if (firsttime) {
                    
                } else {
                    System.out.println();
                    System.out.println("2. DETECT IMAGE MODIFICATION ");
                    
                    
                    if (Parser.imageCount()==0) {
                        System.out.println("   [OK]  is  IMAGE in the URL");
                        System.out.println();
                    } else {
                        NotesFM.classifyImagesToDownload(Parser.getImgList());
                        
                        
                        imgNew = NotesFM.getNewImages();
                        imgCom = NotesFM.getCommonImages();
                        imgDel = NotesFM.getDeletedImages();
                        
                        imagemodified = ((imgNew!=null) && (imgNew.length>0)) || ((imgDel!=null) && (imgDel.length>0));
                        
                        imgList   = imgCom;
                        int[] imgStat = downloadImages(imgList,false,false);
                        
                        imgMod  = new String[0];
                        
                        for (int i=0;i<imgList.length;i++) {
                            switch (imgStat[i]) {
                               case  ImageDownloader.DOWNLOAD_ERROR: {
                                    imgMod = resizeArray(imgMod,imgMod.length+1);
                                    imgMod[imgMod.length-1] = "Warning: Unable  check image '"+imgDL[i].getStrURL()+"'";
                                    imagemodified = true;
                                    break; }
                                case ImageDownloader.IMG_MODIFIED_DATE: {
                                    imgMod = resizeArray(imgMod,imgMod.length+1);
                                    imgMod[imgMod.length-1] = "TIMESTAMP Modification has been detected  image '"+imgDL[i].getStrURL()+"'";
                                    imagemodified = true;
                                    break; }
                                case ImageDownloader.IMG_MODIFIED_SIZE: {
                                    imgMod = resizeArray(imgMod,imgMod.length+1);
                                    imgMod[imgMod.length-1] = "SIZE modification has been detected  image '"+imgDL[i].getStrURL()+"'";
                                    imagemodified = true;
                                    break; }
                                case ImageDownloader.IMG_MODIFIED_PIXEL: {
                                    imgMod = resizeArray(imgMod,imgMod.length+1);
                                    imgMod[imgMod.length-1] = "PIXEL modification has been detected  image '"+imgDL[i].getStrURL()+"'";
                                    imagemodified = true;
                                    break; }
                               case ImageDownloader.DOWNLOAD_COMPLETE_OK: {
                                    break; }
                            }
                        }
                    }
                }
            }
            
            
            if ((textmodified) || (imagemodified) || ((textdiff!=null) && (textdiff.length>0))) {
                
                String[] mailmsg = constructEMailMessage();
                System.out.println();
                System.out.println("-> REPORTING/RECORDING CHANGES ");
                
                if ((MAILTARGET==1) || (MAILTARGET==2)) {
                    System.out.println("   [NOTIFY] Changes  detected and has been saved  file '"+MAILFILE+"'");
                    printStrArrayToFile(MAILFILE,mailmsg);
                }
                
                
                if ((MAILTARGET==0) || (MAILTARGET==2)) {
                    System.out.println("   [NOTIFY] Sending e-mail  MYSELF at '"+MYEMAIL+"'");
                    MailSender ms = new MailSender("watchdog@somewhere.", MYEMAIL, mailmsg);
                    ms.sendMail();
                    
                    if (ms.getStatus()==MailSender.SEND_OK) {
                        System.out.println("   [NOTIFY] E-mail SUCCESSFULLY sent.");
                    } else {
                        System.out.println("   [ATTENTION] Error sending e-mail.");
                    }
                }
                
                
                if (DEBUGMODE) {
                    System.out.println();
                    System.out.println();
                    System.out.println("   This is the e-mail message that has been sent  '"+MYEMAIL+"'");
                    System.out.println("      this message *ONLY* and because   in DEBUG MODE");
                    System.out.println("   =================================================================================");
                    System.out.println();
                    for (int i=0;i<mailmsg.length;i++)
                        System.out.println(mailmsg[i]);
                    System.out.println();
                    System.out.println("   =================================================================================");
                    System.out.println();
                    System.out.println();
                }
                updateCache(false);
            }
            
            
            System.out.println();
            System.out.println(" Checking completed  "+Calendar.getInstance().getTime());
            System.out.println(" ----------------- [ CHECKING COMPLETED ] ----------------- ");
            System.out.println();
            Parser.print();
        }
        
        public void finalize() {
            System.out.println("Closing the parser object.");
            Parser.print();
        }
        
        private String[] imgList;
        private String[] imgW;
        private String[] imgH;
        private String[] imgLM;
        private String[] imgCom;
        private String[] imgDel;
        private String[] imgNew;
        private String[] imgMod;
        private ImageDownloader[]  imgDL;
        private NotesFileManager NotesFM;
        private HTMLDownloaderAndImgParser Parser;
        private String[] textdiff;
    }
    
    
    public WatchDog() {
        System.out.println("The URL   checked is '"+THEURL+"'");
        System.out.println("Checking scheduled for every "+INTERVAL+" seconds");
        if (DEBUGMODE) {
            System.out.println();
            System.out.println("PROGRAM RUNS IN DEBUG MODE !");
        }
        System.out.println();
        
        System.out.println("The notes file is '"+NOTESFILE+"'");
        System.out.println("The   file is '"+DIGESTFILE+"'");
        System.out.println();
        
        java.util.Timer t = new java.util.Timer();
        t.schedule(new MyTT(),0,INTERVAL*1000);
    }
    
    public static void printSyntax() {
        System.out.println();
        System.out.println("Syntax : WatchDog [URL] [Interval] [-debug]");
        System.out.println("         URL      = (optional) the URL   monitored (default : 'http://www.cs.rmit.edu./students/' )");
        System.out.println("         Interval = (optional) every [interval] seconds check if  is any updates (default 24 x 60 x 60 = 24 hours)");
        System.out.println("         -debug   = (optional) run this program in debug mode; that is, dump all  (html and images  local file)");
        System.out.println();
    }
    
    public static void main(String args[]) {
        
        
           System.setProperty("java.awt.headless","true");
        
         
        
        int argc = args.length;
        
        if (argc>0) {
            THEURL = args[0];
            try {
                url  = new URL(THEURL);
                conn = (HttpURLConnection) url.openConnection();
                conn.getResponseMessage();
                conn.getInputStream();
                conn.disconnect();
            } catch (MalformedURLException e) {
                System.out.println();
                System.out.println("Invalid URL '"+THEURL+"'");
                printSyntax();
                System.exit(1);
            } catch (UnknownHostException e) {
                System.out.println();
                System.out.println("Unable  open connection  the URL '"+THEURL+"'");
                printSyntax();
                System.exit(1);
            } catch (IOException  e) {
                System.out.println();
                System.out.println("Unable  open connection  the URL '"+THEURL+"'");
                printSyntax();
                System.exit(1);
            }
            
            if (argc>1) {
                try {
                    INTERVAL = Integer.valueOf(args[1]).intValue();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid interval '"+args[1]+"'");
                    printSyntax();
                    System.exit(1);
                }
            }
            
            if (argc>2) {
                if (args[2].toLowerCase().equals("-debug")) {
                    DEBUGMODE = true;
                } else {
                    System.out.println("Invalid parameter '"+args[2]+"'");
                    printSyntax();
                    System.exit(1);
                }
            }
        }
        
        Application = new WatchDog();
    }
    
    
    public static String    THEURL     = "http://www.cs.rmit.edu./students";
    public static String    MYEMAIL    = "@yallara.cs.rmit.edu.";
    public static int       MAILTARGET = 2; 
    public static String    SMTPSERVER = "wombat.cs.rmit.edu.";
    public static boolean   DEBUGMODE  = true;
    public static String    CURDIR     = System.getProperty("user.dir");
    public static String    PAGEFILE   = System.getProperty("user.dir")+"/~local.html";
    public static String    DIGESTFILE = System.getProperty("user.dir")+"/~digest.txt";
    public static String    TEMPFILE   = System.getProperty("user.dir")+"/~.html";
    public static String    NOTESFILE  = System.getProperty("user.dir")+"/~notes.txt";
    public static String    MAILFILE   = System.getProperty("user.dir")+"/~email.txt";
    public static final int MAXDIGESTFILE = 65535;
    public static final String DIGESTALG  = "SHA";
    public static URL       url;
    public static HttpURLConnection conn;
    public static int       INTERVAL = 24*60*60; 
    public static int       MAXDIFF  = 1024; 
    public static WatchDog  Application;
    
}

