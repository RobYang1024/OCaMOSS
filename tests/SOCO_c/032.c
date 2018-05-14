

#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include <ctype.h>

int (){
	system("wget -p --convert-links http://www.cs.rmit.edu./students/");
	system("mkdir old");
	system("mv www.cs.rmit.edu./images/*.*   old/");
	system("mv www.cs.rmit.edu./students/*.* old/");
	sleep(86400);
	system("wget -p --convert-links http://www.cs.rmit.edu./students/");
	system("mkdir new");
	system("mv www.cs.rmit.edu./images/*.*  new/");
	system("mv www.cs.rmit.edu./students/*.* new/");
	system("diff old new > report.txt");
	system("mailx -s   \"Report of difference \"    < report.txt ");
	exit(1);
}


