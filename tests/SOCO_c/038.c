#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <sys/time.h>

#define SUCCESS 0;

int ()
{	
	
	system("rm index.*");	
	system("wget --non-verbose http://www.cs.rmit.edu./students");
	system("sleep 10");
	system("wget --non-verbose http://www.cs.rmit.edu./students");		
	system("diff index.html index.html.1 >> output.txt");
	printf("Comparison stored in output.txt");
	system("mutt -a output.txt @cs.rmit.edu. ");
	
	return SUCCESS;
}
