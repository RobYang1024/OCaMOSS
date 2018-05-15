


#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  char word[15], *chk;
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir one");
  system("mv www.cs.rmit.edu./images/*.*  one/");
  system("mv www.cs.rmit.edu./students/*.* one/");
  sleep(15);
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir two");
  system("mv www.cs.rmit.edu./images/*.* two/");
  system("mv www.cs.rmit.edu./students/*.* two/");
  system("diff one two > difference.txt");
  system("mailx -s  \"Message1\"   < difference.txt");
  return 0;
}
