





#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/time.h>

#define MSG_FILE "msg"
#define EMAIL_TO "@cs.rmit.edu."
#define TRUE 1
#define FALSE 0


void genLog(char *logFile, const char *URL);
void getPage(const char *URL, const char *fname);
int getCurTime();
int logDiff(const char *logFile, int time);
int isFileExist(const char *fname);
void sendMail(const char* emailTo, const char* subject, const char* msgFile
				, const char* log);

int (int argc, char **argv)
{
	int time_var;
	char *URL;
	int upTime = 0;
	char logFile[256];
	int logSent = FALSE;
	char subject[256];
	
	if (argc != 3)
	{
		fprintf(stderr, "\nUsage: ./WatchDog URL timeIntervalInSec\n");
		exit(1);
	}
	else
	{
		time_var = atoi(argv[2]);

		URL = malloc(strlen(argv[1]));

		if (URL)
		{
			for (;;) 
			{
				if (((int)difftime(upTime, getCurTime()) % time_var == 0) 
								&& !logSent)
				{
					strncpy(URL, argv[1], strlen(argv[1]));
					genLog(logFile, URL);
					if (logDiff(logFile, time_var))
					{
						sprintf(subject, "Changes of %s", URL);
						sendMail(EMAIL_TO, subject, MSG_FILE, logFile);
						logSent = TRUE;
					}
					else
					{
						
					}			
				}

				if ((int)difftime(upTime, getCurTime()) % time_var != 0)
				{
					logSent = FALSE;
				}

			}
		}
		else
		{
			fprintf(stderr, "Error: Unable  allocate %d bytes memory\n", strlen(argv[1]));
			exit(1);
		}
	}

	return 0;
}



void genLog(char *logFile, const char *URL)
{
	char fname[256];

	sprintf(fname, "%d", getCurTime());
	
	strncpy(logFile, fname, strlen(fname));
	logFile[strlen(fname)+1] = '\0';
	getPage(URL, fname);		
}



void getPage(const char *URL, const char *fname)
{
	char cmd[256];
					
	sprintf(cmd, "wget -O %s -q --proxy=off %s", fname, URL);

	system(cmd);	
}



int getCurTime()
{
	return time(NULL);
}



int logDiff(const char *logFile, int time)
{
	int lastLogInt = atoi(logFile)-time;
	char lastLogStr[256];
	char cmd[1024];
	struct stat fileInfo;
	char newLogFile[256];
	
	sprintf(lastLogStr, "%d", lastLogInt);
	
	if (isFileExist(lastLogStr))
	{
		sprintf(newLogFile, "%s.log", logFile);
		sprintf(cmd, "diff -y %s %s > %s", lastLogStr, logFile, newLogFile);
		system(cmd);

		(void)stat(newLogFile, &fileInfo); 

		if (fileInfo.st_size)
		{
			return TRUE;
		}
		else
		{
			return FALSE;
		}
	}
	else
	{
		return FALSE;
		
	}
}



int isFileExist(const char *fname)
{
	FILE *fp;

	fp = fopen(fname, "r");

	if (fp)
	{
		fclose(fp);
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}



void sendMail(const char* emailTo, const char* subject, const char* msgFile, const char* log)
{
	char tempCmd[1024];
	char cmd[1024];

	sprintf(tempCmd, " %s | mutt -s \"%s\" -a \"%s.log\" -x %s", 
					msgFile, subject, log, emailTo);
	strncpy(cmd, tempCmd, 1024);

	system(cmd);
}
