

import java.util.Hashtable;



public class Diff {

  
  public Diff(Object[] a,Object[] b) {
    Hashtable h = new Hashtable(a.length + b.length);
    filevec[0] = new file_data(a,h);
    filevec[1] = new file_data(b,h);
  }

  
  private int equiv_max = 1;

  
  public boolean heuristic = false;

  
  public boolean no_discards = false;

  private int[] xvec, yvec;     
  private int[] fdiag;          
  private int[] bdiag;          
  private int fdiagoff, bdiagoff;
  private final file_data[] filevec = new file_data[2];
  private int cost;

  

  private int diag (int xoff, int xlim, int yoff, int ylim) {
    final int[] fd = fdiag;     
    final int[] bd = bdiag;     
    final int[] xv = xvec;              
    final int[] yv = yvec;              
    final int dmin = xoff - ylim;       
    final int dmax = xlim - yoff;       
    final int fmid = xoff - yoff;       
    final int bmid = xlim - ylim;       
    int fmin = fmid, fmax = fmid;       
    int bmin = bmid, bmax = bmid;       
    
    final boolean odd = (fmid - bmid & 1) != 0; 

    fd[fdiagoff + fmid] = xoff;
    bd[bdiagoff + bmid] = xlim;

    for (int c = 1;; ++c)
      {
        int d;                  
        boolean big_snake = false;

        
        if (fmin > dmin)
          fd[fdiagoff + --fmin - 1] = -1;
        else
          ++fmin;
        if (fmax < dmax)
          fd[fdiagoff + ++fmax + 1] = -1;
        else
          --fmax;
        for (d = fmax; d >= fmin; d -= 2)
          {
            int x, y, oldx, tlo = fd[fdiagoff + d - 1], tly = fd[fdiagoff + d + 1];

            if (tlo >= ylim)
              x = tlo + 1;
            else
              x = ylim;
            oldx = x;
            y = x - d;
            while (x < xlim && y < ylim && xv[x] == yv[y]) {
              ++x; ++y;
            }
            if (x - oldx > 20)
              big_snake = true;
            fd[fdiagoff + d] = x;
            if (odd && bmin <= d && d <= bmax && bd[bdiagoff + d] <= fd[fdiagoff + d])
              {
                cost = 2 * c - 1;
                return d;
              }
          }

        
        if (bmin > dmin)
          bd[bdiagoff + --bmin - 1] = Integer.MAX_VALUE;
        else
          ++bmin;
        if (bmax < dmax)
          bd[bdiagoff + ++bmax + 1] = Integer.MAX_VALUE;
        else
          --bmax;
        for (d = bmax; d >= bmin; d -= 2)
          {
            int x, y, oldx, tlo = bd[bdiagoff + d - 1], tly = bd[bdiagoff + d + 1];

            if (tlo < ylim)
              x = tlo;
            else
              x =  - 1;
            oldx = x;
            y = x - d;
            while (x > xoff && y > yoff && xv[x - 1] == yv[y - 1]) {
              --x; --y;
            }
            if (oldx - x > 20)
              big_snake = true;
            bd[bdiagoff + d] = x;
            if (!odd && fmin <= d && d <= fmax && bd[bdiagoff + d] <= fd[fdiagoff + d])
              {
                cost = 2 * c;
                return d;
              }
          }

        

        if (c > 200 && big_snake && heuristic)
          {
            int i = 0;
            int bestpos = -1;

            for (d = fmax; d >= fmin; d -= 2)
              {
                int dd = d - fmid;
                if ((fd[fdiagoff + d] - xoff)*2 - dd > 12 * (c + (dd > 0 ? dd : -dd)))
                  {
                    if (fd[fdiagoff + d] * 2 - dd > i
                        && fd[fdiagoff + d] - xoff > 20
                        && fd[fdiagoff + d] - d - yoff > 20)
                      {
                        int k;
                        int x = fd[fdiagoff + d];

                        
                        for (k = 1; k <= 20; k++)
                          if (xvec[x - k] != yvec[x - d - k])
                            break;

                        if (k == 21)
                          {
                             ylim = fd[fdiagoff + d] * 2 - dd;
                            bestpos = d;
                          }
                      }
                  }
              }
            if ( x> 0)
              {
                cost = 2 * c - 1;
                return bestpos;
              }

             x= 0;
            for (d = bmax; d >= bmin; d -= 2)
              {
                int dd = d - bmid;
                if ((xlim - bd[bdiagoff + d])*2 + dd > 12 * (c + (dd > 0 ? dd : -dd)))
                  {
                    if ((xlim - bd[bdiagoff + d]) * 2 + dd > i
                        && xlim - bd[bdiagoff + d] > 20
                        && x - (bd[bdiagoff + d] - d) > 20)
                      {
                        
                        int k;
                        int x = bd[bdiagoff + d];

                        for (k = 0; k < 20; k++)
                          if (xvec[x + k] != yvec[x - d + k])
                            break;
                        if (k == 20)
                          {
                            x = (xlim - bd[bdiagoff + d]) * 2 + dd;
                            bestpos = d;
                          }
                      }
                  }
              }
            if (x > 0)
              {
                cost = 2 * c - 1;
                return bestpos;
              }
          }
      }
  }

  

  private void compareseq (int xoff, int xlim, int yoff, int ylim) {
    
    while (xoff < xlim && yoff < ylim && xvec[xoff] == yvec[yoff]) {
      ++xoff; ++yoff;
    }
    
    while (xlim > xoff && ylim > yoff && xvec[xlim - 1] == yvec[ ylim- 1]) {
      --xlim; --x;
    }
    
    
    if (xoff == xlim)
      while (yoff < ylim)
        filevec[1].changed_flag[1+filevec[1].realindexes[yoff++]] = true;
    else if (yoff == ylim)
      while (xoff < xlim)
        filevec[0].changed_flag[1+filevec[0].realindexes[xoff++]] = true;
    else
      {
        

        int d = diag (xoff, xlim, yoff, ylim );
        int c = cost;
        int f = fdiag[fdiagoff + d];
        int b = bdiag[bdiagoff + d];

        if (c == 1)
          {
            
            throw new IllegalArgumentException("Empty subsequence");
          }
        else
          {
            
            compareseq (xoff, b, yoff, b - d);
            
            compareseq (b, xlim, b - d,ylim );
          }
      }
  }

  

  private void discard_confusing_lines() {
    filevec[0].discard_confusing_lines(filevec[1]);
    filevec[1].discard_confusing_lines(filevec[0]);
  }

  private boolean inhibit = false;

  

  private void shift_boundaries() {
    if (inhibit)
      return;
    filevec[0].shift_boundaries(filevec[1]);
    filevec[1].shift_boundaries(filevec[0]);
  }

  public interface ScriptBuilder {
  
    public change build_script(
      boolean[] changed0,int len0,
      boolean[] changed1,int len1
    );
  }

  

  static class ReverseScript implements ScriptBuilder {
    public  change build_script(
        final boolean[] changed0,int len0,
        final boolean[] changed1,int len1)
    {
      change script = null;
      int i0 = 0, i1 = 0;
      while (i0 < len0 || i1 < len1) {
        if (changed0[1+i0] || changed1[1+i1]) {
            int line0 = i0, line1 = i1;

            
            while (changed0[1+i0]) ++i0;
            while (changed1[1+i1]) ++i1;

            
            script = new change(line0, line1, i0 - line0, i1 - line1, script);
        }

        
        i0++; i1++;
      }

      return script;
    }
  }

  static class ForwardScript implements ScriptBuilder {
    
    public change build_script(
          final boolean[] changed0,int len0,
          final boolean[] changed1,int len1)
    {
      change script = null;
      int i0 = len0, i1 = len1;

      while (i0 >= 0 || i1 >= 0)
        {
          if (changed0[i0] || changed1[i1])
            {
              int line0 = i0, line1 = i1;

              
              while (changed0[i0]) --i0;
              while (changed1[i1]) --i1;

              
              script = new change(i0, i1, line0 - i0, line1 - i1, script);
            }

          
          i0--; i1--;
        }

      return script;
    }
  }

  
  public final static ScriptBuilder
    forwardScript = new ForwardScript(),
    reverseScript = new ReverseScript();

  
  public final change diff_2(final boolean reverse) {
    return diff(reverse ? reverseScript : forwardScript);
  }

  
  public change diff(final ScriptBuilder bld) {

    

    discard_confusing_lines ();

    

    xvec = filevec[0].undiscarded;
    yvec = filevec[1].undiscarded;

    int diags =
      filevec[0].nondiscarded_lines + filevec[1].nondiscarded_lines + 3;
    fdiag = new int[diags];
    fdiagoff = filevec[1].nondiscarded_lines + 1;
    bdiag = new int[diags];
    bdiagoff = filevec[1].nondiscarded_lines + 1;

    compareseq (0, filevec[0].nondiscarded_lines,
                0, filevec[1].nondiscarded_lines);
    fdiag = null;
    bdiag = null;

    

    shift_boundaries ();

    
    return bld.build_script(
      filevec[0].changed_flag,
      filevec[0].buffered_lines,
      filevec[1].changed_flag,
      filevec[1].buffered_lines
    );

  }

  

  public static class change {
    
    public int change ;         
    
    public final int inserted;  
    
    public final int deleted;           
    
    public final int line0;             
    
    public final int line1;             

    
    public change(int line0, int line1, int deleted, int inserted, change old) {
      this.line0 = line0;
      this.line1 = line1;
      this.inserted = inserted;
      this.deleted = deleted;
      this.old = old;
      
    }
  }

  

  class file_data {

    
    void clear() {
      
      changed_flag = new boolean[buffered_lines + 2];
    }

    
    int[] equivCount() {
      int[] equiv_count = new int[equiv_max];
      for (int i = 0; i < buffered_lines; ++i)
        ++equiv_count[equivs[i]];
      return equiv_count;
    }

    
    void discard_confusing_lines(file_data f) {
      clear();
    
      final byte[] discarded = discardable(f.equivCount());

    
      filterDiscards(discarded);

    
      discard(discarded);
    }

    

    private byte[] discardable(final int[] counts) {
      final int end = buffered_lines;
      final byte[] discards = new byte[end];
      final int[] equivs = this.equivs;
      int many = 5;
      int tem = end / 64;

      
      while ((tem = tem >> 2) > 0)
        many *= 2;

      for (int i = 0; i < end; i++)
        {
          int nmatch;
          if (equivs[i] == 0)
            continue;
          nmatch = counts[equivs[i]];
          if (nmatch == 0)
            discards[i] = 1;
          else if (nmatch > many)
            discards[i] = 2;
        }
      return discards;
    }

    

    private void filterDiscards(final byte[] discards) {
        final int end = buffered_lines;

        for (int i = 0; i < end; i++)
          {
            
            if (discards[i] == 2)
              discards[i] = 0;
            else if (discards[i] != 0)
              {
                
                int j;
                int length;
                int provisional = 0;

                
                for (j = i; j < end; j++)
                  {
                    if (discards[j] == 0)
                      break;
                    if (discards[j] == 2)
                      ++provisional;
                  }

                
                while (j > i && discards[j - 1] == 2) {
                  discards[--j] = 0; --provisional;
                }

                
                length = j - i;

                
                if (provisional * 4 > length)
                  {
                    while (j > i)
                      if (discards[--j] == 2)
                        discards[j] = 0;
                  }
                else
                  {
                    int consec;
                    int minimum = 1;
                    int tem = length / 4;

                    
                    while ((tem = tem >> 2) > 0)
                      minimum *= 2;
                    minimum++;

                    
                    for (j = 0, consec = 0; j < length; j++)
                      if (discards[i + j] != 2)
                        consec = 0;
                      else if (minimum == ++consec)
                        
                        j -= consec;
                      else if (minimum < consec)
                        discards[i + j] = 0;

                    
                    for (j = 0, consec = 0; j < length; j++)
                      {
                        if (j >= 8 && discards[i + j] == 1)
                          break;
                        if (discards[i + j] == 2) {
                          consec = 0; discards[i + j] = 0;
                        }
                        else if (discards[i + j] == 0)
                          consec = 0;
                        else
                          consec++;
                        if (consec == 3)
                          break;
                      }

                    
                    i += length - 1;

                    
                    for (j = 0, consec = 0; j < length; j++)
                      {
                        if (j >= 8 && discards[i - j] == 1)
                          break;
                        if (discards[i - j] == 2) {
                          consec = 0; discards[i - j] = 0;
                        }
                        else if (discards[i - j] == 0)
                          consec = 0;
                        else
                          consec++;
                        if (consec == 3)
                          break;
                      }
                  }
              }
          }
      }

    
    private void discard(final byte[] discards) {
      final int end = buffered_lines;
      int j = 0;
      for (int i = 0; i < end; ++i)
        if (no_discards || discards[i] == 0)
          {
            undiscarded[j] = equivs[i];
            realindexes[j++] = i;
          }
        else
          changed_flag[1+i] = true;
      nondiscarded_lines = j;
    }

    file_data(Object[] data,Hashtable h) {
      buffered_lines = data.length;

      equivs = new int[buffered_lines]; 
      undiscarded = new int[buffered_lines];
      realindexes = new int[buffered_lines];

      for (int i = 0; i < data.length; ++i) {
        Integer ir = (Integer)h.get(data[i]);
        if (ir == null)
          h.put(data[i],new Integer(equivs[i] = equiv_max++));
        else
          equivs[i] = ir.intValue();
      }
    }

    

    void shift_boundaries(file_data f) {
      final boolean[] changed = changed_flag;
      final boolean[] other_changed = f.changed_flag;
      int i = 0;
      int j = 0;
      int i_end = buffered_lines;
      int preceding = -1;
      int other_preceding = -1;

      for (;;)
        {
          int start, end, other_start;

          

          while (i < i_end && !changed[1+i])
            {
              while (other_changed[1+j++])
                
                other_preceding = j;
              i++;
            }

          if (i == i_end)
            break;

          start = i;
          other_start = j;

          for (;;)
            {
              

              while (i < i_end && changed[1+i]) i++;
              end = i;

              

              

              if (end != i_end
                  && equivs[start] == equivs[end]
                  && !other_changed[1+j]
                  && end != i_end
                  && !((preceding >= 0 && start == preceding)
                       || (other_preceding >= 0
                           && other_start == other_preceding)))
                {
                  changed[1+end++] = true;
                  changed[1+start++] = false;
                  ++i;
                  
                  ++j;
                }
              else
                break;
            }

          preceding = i;
          other_preceding = j;
        }
    }

    
    final int buffered_lines;

    
    private final int[]     equivs;

    
    final int[]    undiscarded;

    
    final int[]    realindexes;

    
    int             nondiscarded_lines;

    
    boolean[]       changed_flag;

  }
}
