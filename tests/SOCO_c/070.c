

#include<stdio.h>
#include<strings.h>
#include<stdlib.h>
#include<ctype.h>
#define MAX_SIZE 255


int  (int argc, char *argv[])
 {
     FILE *fp;
     
   while(1)
    {       
      system("wget -p http://www.cs.rmit.edu./students");



      system("mkdir data"); 
      if((fp=fopen("./data/index.html","r"))==NULL)
       { 
         system("cp www.cs.rmit.edu./students/index.html ./data");
	 
       }
      else
       {  
               
	 
	 system("diff ./data/index.html www.cs.rmit.edu./students/index.html | mail @cs.rmit.edu.");
	 system("cp www.cs.rmit.edu./students/index.html ./data");
       }     



      system("mkdir images"); 
      if((fp=fopen("./images/file.txt","r"))==NULL)
       { 
          system("md5sum www.cs.rmit.edu./images/*.* > ./images/file.txt");
		 
       }
      
      else
       {          
          system("md5sum www.cs.rmit.edu./images/*.* > www.cs.rmit.edu./file.txt");
	 
	 
	 
	 system("diff ./images/file.txt www.cs.rmit.edu./file.txt | mail @cs.rmit.edu.");
	 system("cp www.cs.rmit.edu./file.txt ./images");
       }
     sleep(86400); 
    }	
     return (EXIT_SUCCESS);
  }
     
	   
	  	
