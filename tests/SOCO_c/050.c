#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <strings.h>
#include <ctype.h>

int ()
{
  FILE *fp; 
  char *chk,[4];
  int i=1;
  while (i == 1) 
  {
  
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");

  system("mkdir first"); 
  system("mkdir second"); 

  
  system("mv www.cs.rmit.edu./images/*.*  first/");
  system("mv www.cs.rmit.edu./students/*.* first/");

  sleep(86400); 

  
  system("wget -p --convert-links http://www.cs.rmit.edu./students/");

  
  system("mv www.cs.rmit.edu./images/*.* second/");
  system("mv www.cs.rmit.edu./students/*.* second/");

  
  
  system("diff first second > imagesdifference.txt");

  
  fp = fopen("imagesdifference.txt","r");
  
  chk = fgets(, 4, fp);
  
  if (strlen() != 0)
     system("mailx -s  \"Difference from WatchDog\"  < imagesdifference.txt");
  }
  return 0;
}
