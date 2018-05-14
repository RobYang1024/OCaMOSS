# include <stdlib.h>
# include <stdio.h>
# include <strings.h>


int ()
{
	
	FILE* fpp;										
	FILE* fp;
			
	char s[100];
	int i;
	
	while(1)										
	{
		system("wget -nv http://www.cs.rmit.edu./students");				
		
		i=0;		
		
		fp = fopen("dummyindex.txt","r");
		
		if(fp == (FILE*) NULL)								
		{
			printf(" is  previously saved webpage in the file\n");
			i=1;
			fp = fopen("dummyindex.txt","w");

		}
				
		fclose(fp);
		
				
		
		
		
		system("diff index.html dummyindex.txt > compareoutput.txt");		

				
		if(fpp != (FILE*) NULL)	
		{
			
			fpp = fopen("compareoutput.txt","r");					
			
			fgets(s,100,fpp);							
			
			fclose(fpp);								
		
		
		   
			if((strlen(s)>0) && i==0)								
			{
				system("mail @cs.rmit.edu. < compareoutput.txt");	
				system("cp index.html dummyindex.txt");				
				printf("Message has been sent\n");				
			}
			else
				printf(" is  change in the \n");			
		}
		
		

		system("rm index.html")	;				
		
		sleep(86400);									
	}
				
	return 1;
}
