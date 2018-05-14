

             
	     


#include<stdio.h>
#include<stdlib.h>
#include<string.h>

    
    int  ()
    {
       char url[30];
       int exitValue=-1;
       FILE *fr;

       char s[300]; 	     
	system("rm index.html*");
        system("wget http://www.cs.rmit.edu./students/ ");    
	 system("mv index.html one.html");
	 
           printf("System completed Writing\n"); 
	   system("sleep 3600");
	    
	    
            system("wget http://www.cs.rmit.edu./students/ "); 
            
	          
	    	     
	     exitValue=system("diff one.html index.html > .out" );
	     
             fr=fopen(".out","r");
	     
	     strcpy(s,"mailx -s \"Testing Again\"");
	     	     
	     strcat(s,"  < .out");
	     if(fgets(url,30,fr))
	     {
	     system(s);
	     
	     system("rm one.html");  
	     
	     printf("\nCheck your mail") ; 
	     fclose(fr);        
	     }
	       else
	           {
	            printf(" changes detected"); 
		    
	            system("rm one.html"); 
		    fclose(fr);   
		   }
		   return 0;   
      }
      
      
   



