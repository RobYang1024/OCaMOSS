








#include<stdio.h>
#include<strings.h>
#include<sys/time.h>
#include<stdlib.h>

#define MINUTE 45

int main()
{
  system("rm index.html");
  system("wget http://www.cs.rmit.edu./students/");
  sleep(MINUTE);
  system("mv index.html first.html");
  system("wget http://www.cs.rmit.edu./students/");
  system("sdiff first.html index.html > report.html");
  FILE* fp = fopen("report.html","r");
  if (fp != NULL)
   {
     printf("Please  the changes in your mail");
     system("mailx -s \"WatchDog \"  < report.html");
    }
  system("rm first.html");
}

