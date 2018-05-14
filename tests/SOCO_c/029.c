#include<stdio.h>
#include<stdlib.h>
#include<strings.h>
#include<ctype.h>

int ()
{

 FILE *fp;
 char [100];
 int flag = 0;
 system("rm index.html*");
 system("wget http://www.cs.rmit.edu./students/");
 system("wget -p --convert-links http://www.cs.rmit.edu./students/");
 system("mv index.html index1.html");
 sleep(8640);
 system("wget http://www.cs.rmit.edu./students/");
 system("wget -p --convert-links http://www.cs.rmit.edu./students/");

 system("diff index1.html index.html > out.txt");

 fp = fopen("out.txt","r");

 if (fgets(,100,fp) != NULL)
 {

   flag = 1;
   printf("\nThere  changes in the website.Check your mail\n");
   system("mailx -s \"output of watchdog\"  < out.txt");

 }

  if(flag == 0 )
   printf("\n    changes in the website\n ");
 fclose(fp);
 exit(1);

 }














