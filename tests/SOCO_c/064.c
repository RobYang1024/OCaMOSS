#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  FILE *open;
  
  char *tst,check[5];
    
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir one");
  
  system("mv www.cs.rmit.edu./images/*.*  one/");
  system("mv www.cs.rmit.edu./students/*.* one/");
  
  sleep(90);

  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  
  system("mkdir two");
  
  system("mv www.cs.rmit.edu./images/*.* two/");
  system("mv www.cs.rmit.edu./students/*.* two/");
  
  system("diff one two > imagedif.txt");
  
  open = fopen("imagedif.txt","r");
  tst = fgets(check, 5, open);
  
  if (strlen(check) != 0)
     
	  system("mailx -s  \" WatchDog Difference \" @.rmit.edu. < imagedif.txt");
  
  return 0;
  system("rm -r one");
  system ("rm -r two");
}
