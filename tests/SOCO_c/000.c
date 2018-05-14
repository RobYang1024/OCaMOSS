#include<stdio.h>
#include<string.h>
#include<strings.h>
#include<stdlib.h>
#include<sys/time.h>


public static void main()
{
	int i;
	char ar[100];
	FILE* f;
	FILE* ;
	system("wget -O first www.rmit.edu./students");  
	while(1)
	{
		sleep(86400);	
		system("rm -f thed");
		system("rm -f new");
		system("wget -O new www.cs.rmit.edu./students");	
		system("diff new first >thed");	
		f=fopen("thed","r");
		if(fgets(ar,100,f)!=NULL)
		{		
			 printf("\n\n The  has CHANGEDS");		
			system("mail @cs.rmit.edu. <thed"); 
			system("cp new first");
			fclose(f);
		}
		else
		{
		fclose(f);
		printf("\n\nthe  has not changed ");
		}
	}

}

	
	
	
		


