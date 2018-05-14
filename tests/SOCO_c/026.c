






#include <stdio.h>	
#include <sys/time.h>

#include <strings.h>
#include <stdlib.h>
#include <ctype.h>



int () 
	{
	 FILE *fileopen;
  
	char *t_tst,chk[6]; 
    
	system("wget -p --convert-links http://www.cs.rmit.edu./students/");
	
  
	system("mkdir original");

  
	system("mv www.cs.rmit.edu./images/*.*  original/");

	system("mv www.cs.rmit.edu./students/*.* original/");
	

	sleep(75);

	system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  
	system("mkdir two");
  
	system("mv www.cs.rmit.edu./images/*.* fresh/");

	system("mv www.cs.rmit.edu./students/*.* fresh/");
  
	system("diff one two > image_dif.txt");
  
	fileopen = fopen("imagedif.txt","r");
	t_tst = fgets(chk, 6, fileopen);
  
	if (strlen(chk) != 0)
     
	  system("mailx -s  \" WatchDog program has observed some Differences \" @.rmit.edu. < image_dif.txt");
	
		return 0;
	
}











