#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>

int ()
{
  FILE *fh,*fp,*fp1,*d;
  char
  [10000],PassString[50],p[10000],temp1[10000],filename[50],f1,f2,temp2[100];
  sleep(60*60*24);
  system("wget http://www.cs.rmit.edu./students");
  if(system==0)
  {
   printf("File stored");
  }
  strcpy(filename,"index.html"); 
  if((fh=fopen(filename,"r"))==NULL)
  {
   printf("cannot open file\n");
   exit(1);
  }
  fp=fopen("index.txt","r");
  fp1=fopen("index1.txt","r");
 
  while((fgets(PassString,sizeof(PassString),fh))!= NULL)
  {
    fread(p,sizeof(PassString),sizeof(PassString),fh);
    printf(" contents %s\n",p);
    while((f1!=EOF) || (f2!=EOF))
    {
     f1=getc(fp);
     f2=getc(fp1);
     if(f1<f2)
     {
     strcpy(,p);
     fp=fopen("index.txt","r+b");
     fputs(,fp);
     fflush(fp); 
     fclose(fp); 
    }
    else
    {
      strcpy(temp1,p);
      fp1=fopen("index1.txt","r+b");
      fputs(temp1,fp1);
      fflush(fp1);
      fclose(fp1);
    }
    }
    if(system("diff -b -w index.txt index1.txt > Diff.txt")==0)
    {
      d=fopen("Diff.txt","r");
      if((fgets(,sizeof(),d))!=NULL)
      {
        printf("The difference between  exist");
	system("Mail \r\n Difference");
      }
      
    }
  }
 return 0;
  }
