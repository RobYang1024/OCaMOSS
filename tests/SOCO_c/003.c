

#include "stdio.h"
#include "stdlib.h"


int getDifference()
{
	system("wget http://www.cs.rmit.edu./students/");
	return system("diff index.html index.html.1");
}


int sendMail()
{
	int ret;
	printf("\n Program has Detected a Change  the webpage, a mail has been send    yallara .\n");
	ret = system("diff index.html index.html.1 | mail @cs.rmit.edu.");
	sleep(1000);
	system("clear");
}

void clean()
{
	system("rm index.htm*.*");
}
()
{
	while(1){

	int ret = 0;
	ret = getDifference();
	if(ret>0)
	{
	sendMail();
	}
else
	{
	printf("\n  is  difference in the web \n");
	}
	clean();
	printf("Checking the  every 24 hrs..");

	sleep(86400000);
	}
}


