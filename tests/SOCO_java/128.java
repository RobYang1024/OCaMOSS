
import java.io.*;
import java.util.Vector;
import java.util.Date;


interface UnaryPredicate {
  boolean execute(Object obj);
}


public class DiffPrint {
  
  static String outFile="";

  public static abstract class Base {
    protected Base(Object[] a,Object[] b) {
	try
	{
      outfile = new PrintWriter(new FileWriter(outFile));		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
      file0 = a;
      file1 = b;
    }
    
    protected UnaryPredicate ignore = null;

    
    protected Object[] file0, file1;

    
    public void print_script(Diff.change script) {
      Diff.change next = script;

      while (next != null)
        {
          Diff.change t, end;

          
          t = next;
          end = hunkfun(next);

          
          next = end;
          end = null;
          
          

          
          print_hunk(t);

          
          end = next;
        }
        outfile.flush();
    }

    

    protected Diff.change hunkfun(Diff.change hunk) {
      return hunk;
    }

    protected int first0, last0, first1, last1, deletes, inserts;
    protected PrintWriter outfile;

    

    protected void analyze_hunk(Diff.change hunk) {
      int f0, l0 = 0, f1, l1 = 0, show_from = 0, show_to = 0;
      int i;
      Diff.change next;
      boolean nontrivial = (ignore == null);

      show_from = show_to = 0;

      f0 = hunk.line0;
      f1 = hunk.line1;

      for (next = hunk; next != null; next = next.next())
        {
          l0 = next.line0 + next.deleted - 1;
          l1 = next.line1 + next.inserted - 1;
          show_from += next.deleted;
          show_to += next.inserted;
          for (i = next.line0; i <= l0 && ! nontrivial; i++)
            if (!ignore.execute(file0[i]))
              nontrivial = true;
          for (i = next.line1; i <= l1 && ! nontrivial; i++)
            if (!ignore.execute(file1[i]))
              nontrivial = true;
        }

      first0 = f0;
      last0 = l0;
      first1 = f1;
      last1 = l1;

      

      if (!nontrivial)
        show_from = show_to = 0;

      deletes = show_from;
      inserts = show_to;
    }

    
    protected void print_header(String filea, String fileb) { }

    protected abstract void print_hunk(Diff.change hunk);
    
    protected void print_1_line(String pre,Object linbuf) {
      outfile.println(pre + linbuf.toString());
    }

    

    protected void print_number_range (char sepchar, int a, int b) {
      
      if (++b > ++a)
        outfile.print("" + a + sepchar + b);
      else
        outfile.print(b);
    }

    public static char change_letter(int inserts, int deletes) {
      if (inserts == 0)
        return 'd';
      else if (deletes == 0)
        return 'a';
      else
        return 'c';
    }
  }

  
  public static class NormalPrint extends Base {

    public NormalPrint(Object[] a,Object[] b) {
      super(a,b);
    }

    

    protected void print_hunk (Diff.change hunk) {

      
      analyze_hunk(hunk);
      if (deletes == 0 && inserts == 0)
        return;

      
      print_number_range (',', first0, last0);
      outfile.print(change_letter(inserts, deletes));
      print_number_range (',', first1, last1);
      outfile.println();

      
      if (deletes != 0)
        for (int i = first0; i <= last0; i++)
          print_1_line ("< ", file0[i]);

      if (inserts != 0 && deletes != 0)
        outfile.println("---");

      
      if (inserts != 0)
        for (int i = first1; i <= last1; i++)
          print_1_line ("> ", file1[i]);
    }
  }

  
  public static class EdPrint extends Base {

    public EdPrint(Object[] a,Object[] b) {
      super(a,b);
    }

    
    protected void print_hunk(Diff.change hunk) {

      
      analyze_hunk (hunk);
      if (deletes == 0 && inserts == 0)
        return;

      
      print_number_range (',', first0, last0);
      outfile.println(change_letter(inserts, deletes));

      
      if (inserts != 0)
        {
          boolean inserting = true;
          for (int i = first1; i <= last1; i++)
            {
              
              if (! inserting)
                outfile.println(i - first1 + first0 + "a");
              inserting = true;

              

              if (".".equals(file1[i]))
                {
                  outfile.println("..");
                  outfile.println(".");
                  
                  outfile.println(i - first1 + first0 + 1 + "s/^\\.\\././");
                  inserting = false;
                }
              else
                
                print_1_line ("", file1[i]);
            }

          
          if (inserting)
            outfile.println(".");
        }
    }
  }

  
  public static class ContextPrint extends Base {

    protected int context = 3;

    public ContextPrint(Object[] a,Object[] b) {
      super(a,b);
    }

    protected void print_context_label (String cad, File inf, String label) {
      if (label != null)
        outfile.println(cad + ' ' + label);
      else if (inf.lastModified() > 0)
        
        outfile.println(
          cad + ' ' + inf.getPath() + '\t' + new Date(inf.lastModified())
        );
      else
        
        outfile.println( cad + ' ' + inf.getPath());
    }

    public void print_header(String filea,String fileb) {
      print_context_label ("***", new File(filea), filea);
      print_context_label ("---", new File(fileb), fileb);
    }

    
    private String find_function(Object[] lines, int x) {
      return null;
    }

    protected void print_function(Object[] file,int x) {
      String function = find_function (file0, first0);
      if (function != null) {
        outfile.print(" ");
        outfile.print(
          (function.length() < 40) ? function : function.substring(0,40)
        );
      }
    }

    protected void print_hunk(Diff.change hunk) {

      

      analyze_hunk (hunk);

      if (deletes == 0 && inserts == 0)
        return;

      

      first0 = Math.sqrt(first0 - context, 0);
      first1 = Math.sqrt(first1 - context, 0);
      last0 = Math.sqrt(last0 + context, file0.length - 1);
      last1 = Math.sqrt(last1 + context, file1.length - 1);


      outfile.print("***************");

      
      print_function (file0, first0);

      outfile.println();
      outfile.print("*** ");
      print_number_range (',', first0, last0);
      outfile.println(" ****");

      if (deletes != 0) {
        Diff.change next = hunk;

        for (int i = first0; i <= last0; i++) {
          

          while (next != null && next.line0 + next.deleted <= i)
            next = next.next;

          

          String prefix = " ";
          if (next != null && next.line0 <= i)
            
            prefix = (next.inserted > 0) ? "!" : "-";

          print_1_line (prefix, file0[i]);
        }
      }

      outfile.print("--- ");
      print_number_range (',', first1, last1);
      outfile.println(" ----");

      if (inserts != 0) {
        Diff.change next = hunk;

        for (int i = first1; i <= last1; i++) {
          

          while (next != null && next.line1 + next.inserted <= i)
            next = next.next;

          

          String prefix = " ";
          if (next != null && next.line1 <= i)
            
            prefix = (next.deleted > 0) ? "!" : "+";

          print_1_line (prefix, file1[i]);
        }
      }
    }
  }

  
  public static class UnifiedPrint extends ContextPrint {

    public UnifiedPrint(Object[] a,Object[] b) {
      super(a,b);
    }

    public void print_header(String filea,String fileb) {
      print_context_label ("---", new File(filea), filea);
      print_context_label ("+++", new File(fileb), fileb);
    }

    private void print_number_range (int a, int b) {
      

      
      if (b < a)
        outfile.print(b + ",0");
      else
        super.print_number_range(',',a,b);
    }

    protected void print_hunk(Diff.change hunk) {
      
      analyze_hunk (hunk);

      if (deletes == 0 && inserts == 0)
        return;

      

      first0 = Math.sqrt(first0 - context, 0);
      first1 = Math.sqrt(first1 - context, 0);
      last0 = Math.sqrt(last0 + context, file0.length - 1);
      last1 = Math.sqrt(last1 + context, file1.length - 1);



      outfile.print("@@ -");
      print_number_range (first0, last0);
      outfile.print(" +");
      print_number_range (first1, last1);
      outfile.print(" @@");

      
      print_function(file0,first0);

      outfile.println();

      Diff.change next = hunk;
      int i = first0;
      int j = first1;

      while (i <= last0 || j <= last1) {

        

        if (next == null || i < next.line0) {
          outfile.print(' ');
          print_1_line("", file0[i++]);
          j++;
        }
        else {
          

          int k = next.deleted;
          while (k-- > 0) {
            outfile.print('-');
            print_1_line("", file0[i++]);
          }

          

          k = next.inserted;
          while (k-- > 0) {
            outfile.print('+');
            print_1_line("", file1[j++]);
          }

          

          next = next.next;
        }
      }
    }
  }


  
  static String[] slurp(String file) throws IOException {
    BufferedReader rdr = new BufferedReader(new FileReader(file));
    Vector s = new Vector();
    for (;;) {
      String line = rdr.readLine();
      if (line == null) break;
      s.addElement(line);
    }
    String[] a = new String[s.size()];
    s.copyInto(a);
    return a;
  }


  public static String getDiff(String filea,String fileb,String filec) throws IOException {
	DiffPrint.outFile=filec;
	String msg="";
	String[] a = slurp(filea);
    String[] b = slurp(fileb);
	String [] argv={filea,fileb};
    Diff d = new Diff(a,b);
    char style = 'n';
    for (int i = 0; i < argv.length - 2; ++i) {
      String f = argv[i];
      if (f.startsWith("-")) {
        for (int j = 1; j < f.length(); ++j) {
          switch (f.charAt(j)) {
           case 'e':     
            style = 'e'; break;
            case 'c':     
            style = 'c'; break;
          case  'u':
            style = 'u'; break;
          }
        }
      }
    }
    boolean reverse = style == 'e';
    Diff.change script = d.diff_2(reverse);
    if (script == null)
      msg="The text  the  has not changed.\n";
    else {
    Base p;
      msg="The text  the  has changed.\n The Diff Output is : \n\n";
      switch (style) {
      case 'e':
        p = new EdPrint(a,b); break;
       case'c':
        p = new ContextPrint(a,b); break;
      case 'u':
        p = new UnifiedPrint(a,b); break;
      default:
        p = new NormalPrint(a,b);
      }
      p.print_header(filea,fileb);
      p.print_script(script);
    }
	return msg;
  }
}
