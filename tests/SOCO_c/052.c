#include<stdio.h>
#include<stdlib.h>

int ()
{

FILE *fin1;
FILE *fin2;
int flag=0;


while(1)
{
    
        system("wget -p http://www.cs.rmit.edu./students");

        system("cd www.cs.rmit.edu./");

    
    
   if(flag>0)
   {
    
    fin1=fopen("./watchtext/index.html","r");
    fin2=fopen("./watchtext/test2.txt","r");
    system("diff ./www.cs.rmit.edu./students/index.html ./watchtext/index.html | mail @cs.rmit.edu.");
    system("cp ./www.cs.rmit.edu./students/index.html ./watchtext/index.html ");
    system("md5sum ./www.cs.rmit.edu./images/*.* > ./www.cs.rmit.edu./test2.txt");
    system("diff ./www.cs.rmit.edu./test2.txt ./watchtext/test2.txt | mail @cs.rmit.edu.");
    system("cp ./www.cs.rmit.edu./test2.txt ./watchtext/test2.txt");
    system("rm ./www.cs.rmit.edu./test2.txt");
           
    fclose(fin2);
    fclose(fin1);   
   }    
  
    if(flag==0)
    {
     system("mkdir watchtext");     
     if((fin1=fopen("./watchtext/index.html","r"))==NULL)
     {
      system("cp ./www.cs.rmit.edu./students/index.html ./watchtext/index.html");
      system("md5sum ./www.cs.rmit.edu./images/*.* > ./watchtext/test2.txt");
      
      flag++;
      }
     
   } 
    
  
  
   printf("Running every 24 hours");     
  sleep(86400); 
  
}
 system("rmdir ./watchtext");      
}       
       
       
       
       
