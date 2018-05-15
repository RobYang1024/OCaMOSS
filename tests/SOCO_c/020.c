
#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>
#include<string.h>
int ()
{
int flag=1;
const char delimiter[]=" \n\t";
FILE *fp1;
char line1[100];
char filename1[50];
char *token;

while(1)
{
system("wget http://www.cs.rmit.edu./students/ ");
system("mv index.html test1");
sleep(3600);
system("wget http://www.cs.rmit.edu./students/ ");
system("mv index.html test2");
system("diff test1 test2 >test3");
system("file test3 >test4"); 
strcpy(filename1,"test4");
if((fp1=fopen(filename1,"r"))==NULL){
printf("cannot open file\n");
exit(1);
}
fgets(line1,sizeof(line1),fp1);
token=strtok(line1,delimiter);
        while(token!=NULL)
                {
if(strcmp(token,"empty")==0)
{
printf(" CHANGES\n");
flag=0;

}
token=strtok(NULL,delimiter);

                 }
if(flag==1)
{
system("mail  < test3");
}
fclose(fp1);
}
return(0);
}
