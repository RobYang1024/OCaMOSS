         

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

    
    int main ()
    {
      FILE *fp;
      char s[300] , ptr[20]; 	     
	system("rm *.html* ");
      system("wget http://www.cs.rmit.edu./students/ ");    
	system("mv index.html First.html");
	system("sleep 10");
	system("wget http://www.cs.rmit.edu./students/ "); 
      system("diff First.html index.html > difference.txt" );
	fp=fopen("difference.txt","r");
      if(fgets(ptr,30,fp))
	     {
	     system( "mailx -s   \"Changes were detected \"    < difference.txt ");
           }
    else 
	      printf(" were  changes detected"); 
	     
            return 0;   
      

     }
      
      
   



