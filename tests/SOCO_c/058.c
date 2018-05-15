#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>


int  (int argc, char *argv[])
{
   FILE *fp;
     
   while(1)
   {       
      system("wget -p http://www.cs.rmit.edu./students");
      
      
      
      system("mkdir Images");
      
      if((fp=fopen("/home/s//SECAS02ANS/Images/file.txt","r"))==NULL)
      { 
         system("cd www.cs.rmit.edu./images | ls > /home/s//SECAS02ANS/Images/file.txt");
	 system("md5sum www.cs.rmit.edu./images/*.* > /home/s//SECAS02ANS/Images/file.txt");
	 fclose(fp);
	 exit(0);
      }
      else
      {          
         fp=fopen("/home/s//SECAS02ANS/Images/file.txt","r");
	 system("cd www.cs.rmit.edu./images | ls > www.cs.rmit.edu./file.txt");
	 system("md5sum www.cs.rmit.edu./images/*.* > www.cs.rmit.edu./file.txt");
	 system("diff /home/s//SECAS02ANS/Images/file.txt www.cs.rmit.edu./file.txt | mail @cs.rmit.edu.");
	 system("mv www.cs.rmit.edu./file.txt /home/s//SECAS02ANS/Images");
	 fclose(fp);
      }
      
      
      
      system("mkdir Text");
      
      if((fp=fopen("/home/s//SECAS02ANS/Text/index.html","r"))==NULL)
      { 
         system("cp www.cs.rmit.edu./students/index.html /home/s//SECAS02ANS/Text");
	 exit(0);
      }
      else
      {          
      	 fopen("/home/s//SECAS02ANS/Text/index.html","r");
	 system("diff /home/s//SECAS02ANS/Text/index.html www.cs.rmit.edu./students/index.html | mail @cs.rmit.edu.");
	 system("mv www.cs.rmit.edu./students/index.html /home/s//SECAS02ANS/Text");
	 fclose(fp);
      }     
       
      
      
      sleep(86400);
    }	 
    return (EXIT_SUCCESS);
}
     
	   
	  	
