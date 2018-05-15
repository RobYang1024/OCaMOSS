#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#define TRUE 0
()
{
FILE *fp;
system("rmdir ./www.cs.rmit.edu.");
char chk[1];
strcpy(chk,"n");
  while(1)
  {
       
   	system("wget -p http://www.cs.rmit.edu./students/");
		
		system("md5sum ./www.cs.rmit.edu./images/*.* > ./www.cs.rmit.edu./text1.txt");
		
		
		if (strcmp(chk,"n")==0)		
		{		
		system("mv ./www.cs.rmit.edu./text1.txt   ./text2.txt");
		system("mkdir ./");
		
		system("mv ./www.cs.rmit.edu./students/index.html ./");
		}
		else
		{
		
		
		system(" diff ./www.cs.rmit.edu./students/index.html .//index.html | mail @cs.rmit.edu. ");
		system(" diff ./www.cs.rmit.edu./text1.txt ./text2.txt | mail @cs.rmit.edu. ");
		system("mv ./www.cs.rmit.edu./students/index.html ./");
		system("mv ./www.cs.rmit.edu./text1.txt   ./text2.txt");				
		}
		sleep(86400);
		strcpy(chk,"y");
		
	}
}		    	      
      
      
