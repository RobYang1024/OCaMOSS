#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>



void emptyFile(char* name)
{
	FILE* myFile;
	myFile = fopen(name,"w");
	fclose(myFile);
}

int (void)
{
	FILE* myFile;
	char* myString;
	
	myString = malloc(sizeof(char ) * 100);

	
	
	emptyFile(".old.html");
	emptyFile(".new.html");

	
	system("wget -O .old.html -q http://www.cs.rmit.edu./students/");

	while(1)
	{
		
		emptyFile(".new.html");

		
		system("wget -O .new.html -q http://www.cs.rmit.edu./students/");
		
		
		system("diff .old.html .new.html > watch.txt");

		myFile = fopen("watch.txt","r");
		if(myFile != (FILE*) NULL)
		{
			fgets(myString,100,myFile);
			if(strlen(myString) > 0)
			{
				
				
				system("mail @cs.rmit.edu. < watch.txt");
				
				system("cp .new.html .old.html");
			}
		}
		
		sleep(60*60*24);
	}
	
	return 1;
}


