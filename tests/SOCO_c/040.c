#include <sys/time.h>
#include <strings.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>


int ()
{
  FILE *fp ,*fp1;
  char pass[15], *getWord;
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir previous");
  system("mv www.cs.rmit.edu./images/*.*   previous/");
  system("mv www.cs.rmit.edu./students/*.* previous/");
  system("cd www.cs.rmit.edu./images");
  system("cksum *.* > ../../cksum1.txt");
  sleep(10000);
  system("cd .. ");
  system("cd .. ");
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");
  system("mkdir current");
  system("mv www.cs.rmit.edu./images/*.*  current/");
  system("mv www.cs.rmit.edu./students/*.* current/");
  system("cd www.cs.rmit.edu./images");
  system("cksum *.* > ../../cksum2.txt");
  system("diff cksum1.txt cksum2.txt> checksumdifference.txt");
  system("diff previous current > difference.txt");
  fp1 =fopen("difference.txt","r");
  getWord = fgets(pass, 15, fp1);
  if(strlen(getWord)!= 0)
  system("mailx -s   \"Difference in  \"    < difference.txt ");
  fp =fopen("checksumdifference.txt","r");
  getWord = fgets(pass, 15, fp);
  if(strlen(getWord)!= 0)
    system("mailx -s  \"Difference in \"   < checksumdifference.txt");

  return 0;
}
