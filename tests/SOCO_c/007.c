#include <sys/time.h>
#include <strings.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>


int main()
{

  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir home");
  system("mv www.cs.rmit.edu./images/*.*   home/");
  system("mv www.cs.rmit.edu./students/*.* home/");
  system("cd www.cs.rmit.edu./images");
  sleep(1);
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir second");
  system("mv www.cs.rmit.edu./images/*.*  second/");
  system("mv www.cs.rmit.edu./students/*.* second/");
  system("cd www.cs.rmit.edu./images");
  system("diff home second > difference.txt");
  system("mailx -s   \"Difference in  \"    < difference.txt ");
  return 0;
}
